package inf112.firegirlwaterboy.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import inf112.firegirlwaterboy.controller.MovementType;
import inf112.firegirlwaterboy.model.maps.LayerType;
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
  private PlayerType playerType;
  private boolean isAlive;
  private int collectedCount;
  private boolean finished;
  private boolean powerUp;
  private World world;

  private Animation<TextureRegion> runningAnimation;
  private TextureRegion standingTexture, headTexture, haloTexture;
  private float stateTime;

  private final float width = 1.0f;
  private final float height = 2.0f;
  private final float bodyHeightPlacement = -0.45f;

  /**
   * Initalizes a player, giving them a type and texture
   */
  public Player(PlayerType playerType) {
    super(new Texture(Gdx.files.internal("assets/players/" + playerType.name() + "-body.png")));
    this.playerType = playerType;
    initializeAnimations();
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
    return playerType.hashCode();
  }

  @Override
  public String toString() {
    return playerType.toString();
  }

  @Override
  public void dispose() {
    super.getTexture().dispose();
  }

  @Override
  public void draw(Batch batch) {
    super.draw(batch);
    Vector2 position = body.getPosition();
    batch.draw(headTexture, position.x - 1.5f, position.y - 1.1f, 3, 3);
    if (powerUp) {
      batch.draw(haloTexture, position.x - 1.5f, position.y - 1.1f, 3, 3);
    }
  }

  @Override
  public void update() {
    if (!isAlive)
      return;

    stateTime += Gdx.graphics.getDeltaTime();
    TextureRegion currentFrame = getFrame();
    setRegion(currentFrame);

    if (body.getLinearVelocity().x < 0 != currentFrame.isFlipX()) {
      currentFrame.flip(true, false);
    }
    if (body.getLinearVelocity().x < 0 != headTexture.isFlipX()) {
      headTexture.flip(true, false);
    }

    Vector2 position = body.getPosition();
    setPosition(position.x - width / 2, position.y - height / 2 + bodyHeightPlacement);
  }

  @Override
  public PlayerType getType() {
    return playerType;
  }

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
  public void spawn(World world, Vector2 pos) {
    isAlive = true;
    finished = false;
    powerUp = false;
    collectedCount = 0;
    this.world = world;
    setSize(width, height);
    createBody(world, pos);
    setPosition(pos.x, pos.y);
  }

  @Override
  public int getCollectedCount() {
    return collectedCount;
  }

  @Override
  public void interactWithElement(Element element) {
    isAlive = playerType.getImmunity().equals(element.getType());
    if (!isAlive && powerUp) {
      isAlive = true;
      powerUp = false;
    }
  }

  @Override
  public void interactWithCollectable(Collectable collectable) {
    if (collectable.getRequiredPlayers().contains(playerType)) {
      collectedCount++;
      collectable.collect();
    }
    powerUp |= collectable.getType().isPowerUp();
  }

  @Override
  public boolean isAlive() {
    return isAlive;
  }

  @Override
  public void setFinished(boolean finished) {
    this.finished = finished;
  }

  @Override
  public boolean isFinished() {
    return finished;
  }

  /**
   * Initializes the TextureRegions/Animation.
   */
  private void initializeAnimations() {
    standingTexture = new TextureRegion(
        new Texture(Gdx.files.internal("assets/players/" + playerType.name() + "-body.png")));

    headTexture = new TextureRegion(
        new Texture(Gdx.files.internal("assets/players/" + playerType.name() + "-head.png")));
      
    haloTexture = new TextureRegion(
        new Texture(Gdx.files.internal("assets/players/Halo.png")));

    Array<TextureRegion> runningFrames = new Array<>();
    for (int i = 1; i < 9; i++) {
      Texture texture = new Texture(Gdx.files.internal("assets/players/" + playerType.name() + "-run" + i + ".png"));
      TextureRegion region = new TextureRegion(texture);
      runningFrames.add(new TextureRegion(region));
    }
    runningAnimation = new Animation<>(0.1f, runningFrames, Animation.PlayMode.LOOP);
    stateTime = 0f;
  }

  /**
   * @return The current frame of the player based on their state (running or
   *         standing).
   */
  private TextureRegion getFrame() {
    return isMoving() ? runningAnimation.getKeyFrame(stateTime, true) : standingTexture;
  }

  /**
   * @return true if the player is moving, false otherwise.
   */
  private boolean isMoving() {
    return Math.abs(body.getLinearVelocity().x) > 0.01 || Math.abs(body.getLinearVelocity().y) > 0.01;
  }

  /**
   * Applies a linear impulse upwards to body if on ground.
   */
  private void jump() {
    short groundBits = (short) (LayerType.PLATFORM.getBit() | LayerType.STATIC.getBit());
    for (Contact c : world.getContactList()) {
      if (!c.isTouching())
        continue;

      Fixture a = c.getFixtureA(), b = c.getFixtureB();
      boolean aIsMe = a.getUserData() == this, bIsMe = b.getUserData() == this;
      if (!aIsMe && !bIsMe)
        continue;

      Fixture other = aIsMe ? b : a;
      if ((other.getFilterData().categoryBits & groundBits) != 0) {
        body.applyLinearImpulse(new Vector2(0, jumpSpeed), body.getWorldCenter(), true);
      }
    }
  }
  
   /**
   * Creates the body of the player in the given world.
   * 
   * @param world The world in which the player will be created
   * @param pos The position of the player in the world
   */
  private void createBody(World world, Vector2 pos) {
    BodyDef bdef = new BodyDef();
    bdef.position.set(pos);
    bdef.type = BodyDef.BodyType.DynamicBody;
    bdef.fixedRotation = true;
    bdef.linearDamping = 0;
    this.body = world.createBody(bdef);

    PolygonShape bodyShape = new PolygonShape();
    bodyShape.setAsBox(width / 2, height / 2);
    FixtureDef bodyfDef = new FixtureDef();
    bodyfDef.shape = bodyShape;
    bodyfDef.density = 0.5f;
    bodyfDef.restitution = 0f;
    bodyfDef.friction = 0f;
    bodyfDef.filter.categoryBits = LayerType.PLAYER.getBit();
    bodyfDef.filter.maskBits = (short) (LayerType.PLATFORM.getBit() | LayerType.STATIC.getBit());
    body.createFixture(bodyfDef).setUserData("BodyOutline");
    bodyShape.dispose();

    PolygonShape coreShape = new PolygonShape();
    coreShape.setAsBox(
        width * 0.4f,
        height * 0.425f,
        new Vector2(0, -height * 0.075f),
        0);
    FixtureDef corefDef = new FixtureDef();
    corefDef.shape = coreShape;
    corefDef.restitution = 0f;

    corefDef.filter.categoryBits = LayerType.PLAYER.getBit();
    corefDef.filter.maskBits = (short) (LayerType.COLLECTABLE.getBit() |
        LayerType.ELEMENT.getBit() |
        LayerType.FINISH.getBit() |
        LayerType.PLATFORM.getBit() |
        LayerType.STATIC.getBit());

    body.createFixture(corefDef).setUserData(this);
    coreShape.dispose();
  }
}
