package inf112.firegirlwaterboy.model.entity;

import java.util.LinkedList;
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
import inf112.firegirlwaterboy.model.maps.Maps;

/**
 * Player class represents a player character in the game.
 * The player is a sprite that can move, has velocity and is affected by
 * gravity.
 */
public class Player extends Sprite implements IEntity, IPlayer {

  private float speed = 7;
  private World world;
  private Body body;
  private boolean onGround;
  private PlayerType playerType;
  private int countCollected;
  private Queue<Collectable> collected;
  private boolean touchingWall; // Må vurderes om nødvendig for videre utvikling

  /**
   * Initalizes a player, giving them a type and texture
   */
  public Player(PlayerType playerType) {
    super(getTextureForType(playerType));
    this.playerType = playerType;
  }

  //////////////////////////
  /// IENTITY INTERFACE ///
  //////////////////////////

  @Override
  public Texture getTexture() {
    return super.getTexture();
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
  /// IPLAYER INTERFACE ///
  //////////////////////////

  @Override
  public void move(MovementType dir) {
    switch (dir) {
      case UP -> jump();
      case LEFT -> body.setLinearVelocity(-speed, body.getLinearVelocity().y);
      case RIGHT -> body.setLinearVelocity(speed, body.getLinearVelocity().y);
      case STOP -> body.setLinearVelocity(0, body.getLinearVelocity().y);
      default -> throw new IllegalArgumentException("Unexpected value: " + dir);
    }
  }

  @Override
  public void jump() {
    if (onGround) {
      body.applyLinearImpulse(new Vector2(0, 10.5f), body.getWorldCenter(), true);
    }
  }

  @Override
  public void setOnGround(boolean onGround) {
    this.onGround = onGround;
  }

  @Override
  public void setTouchingWall(boolean touchingWall) {
    this.touchingWall = touchingWall;
  }

  @Override
  public PlayerType getPlayerType() {
    return playerType;
  }

  @Override
  public String toString() {
    return playerType.toString();
  }

  @Override
  public void collect(Collectable collectable) {
    collected.add(collectable);
    countCollected++;
  }

  @Override
  public void spawn(World world, Vector2 pos) {
    setSize(getTexture().getWidth() / Maps.PPM, getTexture().getHeight() / Maps.PPM);
    this.world = world;
    onGround = false;
    touchingWall = false;
    collected = new LinkedList<>();
    countCollected = 0;
    createBody(pos);
    setPosition(pos.x, pos.y);
  }

  @Override
  public int getCountCollected() {
    return countCollected;
  }

  //////////////////////////
  ///  PRIVATE METHODS   ///
  //////////////////////////

  private static TextureRegion getTextureForType(PlayerType type) {
    Texture texture;
    try {
      switch (type) {
        case FIREGIRL:
          texture = new Texture(Gdx.files.internal("FIREGIRL.png"));
          break;
        case WATERBOY:
          texture = new Texture(Gdx.files.internal("WATERBOY.png"));
          break;
        default:
          texture = new Texture(Gdx.files.internal("textures/default.png"));
          break;
      }
      return new TextureRegion(texture);
    } catch (Exception e) {
      Gdx.app.log("Texture Loading Error", "Could not load texture: " + e.getMessage());
      return null;
    }
  }

  private void createBody(Vector2 position) {
    BodyDef bdef = new BodyDef();
    bdef.position.set(position.x / Maps.PPM, position.y / Maps.PPM);
    bdef.type = BodyDef.BodyType.DynamicBody;
    bdef.fixedRotation = true;

    bdef.linearDamping = 0;
    this.body = world.createBody(bdef);

    PolygonShape bodyShape = new PolygonShape();
    bodyShape.setAsBox(16 / Maps.PPM, 32 / Maps.PPM);

    FixtureDef fdef = new FixtureDef();
    fdef.shape = bodyShape;
    fdef.density = 0.5f;
    fdef.friction = 0;
    fdef.restitution = 0;
    body.createFixture(fdef).setUserData(this);
    bodyShape.dispose();
  }
}
