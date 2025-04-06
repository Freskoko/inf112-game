package inf112.firegirlwaterboy.model.entity;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.controller.MovementType;
import inf112.firegirlwaterboy.model.maps.MapsFactory;
import inf112.firegirlwaterboy.model.types.ElementType;
import inf112.firegirlwaterboy.model.types.PlayerType;

/**
 * Player class represents a player character in the game.
 * The player is a sprite that can move, has velocity and is affected by
 * gravity.
 */
public class Player extends Sprite implements IEntity<PlayerType>, IPlayer {

  private float speed = 7;
  private float jumpSpeed = 10.5f;
  private Body body;
  private boolean onGround;
  private PlayerType playerType;
  private boolean isAlive;
  private int collectedCount;
  private Queue<Collectable> collected;
  private boolean touchingEdge;
  private boolean finished;
  private Platform currentPlatform;
  private boolean powerUp;

  /**
   * Initalizes a player, giving them a type and texture
   */
  public Player(PlayerType playerType) {
    super(getTextureForType(playerType));
    this.playerType = playerType;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Player player = (Player) obj;
    return playerType == player.playerType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(playerType);
  }

  //////////////////////////
  /// IENTITY INTERFACE ///
  //////////////////////////

  @Override
  public void dispose() {
    super.getTexture().dispose();
  }

  @Override
  public Body getBody() {
    return body;
  }

  @Override
  public void draw(Batch batch) {
    super.draw(batch);
  }

  @Override
  public void update() {
    while (!collected.isEmpty()) {
      Collectable collectable = collected.poll();
      collectable.collect();
    }

  
    Vector2 position = body.getPosition();
    setPosition(position.x - getWidth() / 2, position.y - getHeight() / 2);
  }

  //////////////////////////
  //// IPLAYER INTERFACE ///
  //////////////////////////

  @Override
  public void move(MovementType dir) {
    switch (dir) {
      case UP -> jump();
      case LEFT -> body.setLinearVelocity(-speed, body.getLinearVelocity().y);
      case RIGHT -> body.setLinearVelocity(speed, body.getLinearVelocity().y);
      case STOP -> body.setLinearVelocity(0, body.getLinearVelocity().y);
      default -> throw new IllegalArgumentException("Unexpected MovementType for move() method: " + dir);
    }
  }

  @Override
  public void setOnGround(boolean groundStatus) {
    this.onGround = groundStatus;
  }

  @Override
  public void setTouchingEdge(boolean edgeStatus) {
    this.touchingEdge = edgeStatus;
  }

  @Override
  public PlayerType getType() {
    return playerType;
  }

  @Override
  public String toString() {
    return playerType.toString();
  }

  @Override
  public void spawn(World world, Vector2 pos) {
    setSize(getTexture().getWidth() / MapsFactory.PPM, getTexture().getHeight() / MapsFactory.PPM);
    onGround = true;
    touchingEdge = false;
    isAlive = true;
    finished = false;
    collected = new LinkedList<>();
    collectedCount = 0;
    createBody(world, pos);
    setPosition(pos.x, pos.y);
  }

  @Override
  public int getCollectedCount() {
    return collectedCount;
  }

  @Override
  public void interactWithElement(ElementType elementType) {
    isAlive = playerType.getImmunity().equals(elementType);
    if (!isAlive && powerUp) {
      isAlive = true;
      powerUp = false;
    }
  }

  @Override
  public void interactWithCollectable(Collectable collectable) {
    if (collectable.getRequiredPlayer().equals(playerType)) {
      collected.add(collectable);
      collectedCount++;
    }
    powerUp |= collectable.getPowerUp();
  }

  @Override
  public boolean isAlive() {
    return isAlive;
  }

  @Override
  public boolean isOnGround() {
    return onGround;
  }

  @Override
  public boolean isTouchingEdge() {
    return touchingEdge;
  }

  @Override
  public void setFinished(boolean finished) {
    this.finished = finished;
  }

  @Override
  public boolean isFinished() {
    return finished;
  }

  //////////////////////////
  //// PRIVATE METHODS /////
  //////////////////////////

  private static TextureRegion getTextureForType(PlayerType type) {
    Texture texture;
    try {
      switch (type) {
        case FIREGIRL:
          texture = new Texture(Gdx.files.internal("assets/maps/mapassets/players/FIREGIRL.png"));
          break;
        case WATERBOY:
          texture = new Texture(Gdx.files.internal("assets/maps/mapassets/players/WATERBOY.png"));
          break;
        default:
          texture = new Texture(Gdx.files.internal("/default.png"));
          break;
      }
      return new TextureRegion(texture);
    } catch (Exception e) {
      Gdx.app.log("Texture Loading Error", "Could not load texture: " + e.getMessage());
      return null;
    }
  }

  private void createBody(World world, Vector2 pos) {
    Float width = getWidth();
    Float height = getHeight();

    BodyDef bdef = new BodyDef();
    bdef.position.set(pos);
    bdef.type = BodyDef.BodyType.DynamicBody;
    bdef.fixedRotation = true;
    bdef.linearDamping = 0;
    this.body = world.createBody(bdef);

    PolygonShape bodyShape = new PolygonShape();
    bodyShape.setAsBox(width / 2, height / 2);

    FixtureDef fdef = new FixtureDef();
    fdef.shape = bodyShape;
    fdef.density = 0.5f;
    //fdef.friction = 0.1f;
    fdef.restitution = 0f;
    body.createFixture(fdef).setUserData(this);
    bodyShape.dispose();
  }

  private void jump() {
    if (!touchingEdge )
      if (onGround || currentPlatform != null) {
        body.applyLinearImpulse(new Vector2(0, jumpSpeed), body.getWorldCenter(), true);
        currentPlatform = null;
      }
  }

  public void setOnPlatform(Platform platform) {
    this.currentPlatform = platform;
    //body.setGravityScale(0);
  }

  public int getCountCollected() {
    return collectedCount;
  }
}
