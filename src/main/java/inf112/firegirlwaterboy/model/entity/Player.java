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
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import inf112.firegirlwaterboy.controller.MovementType;
import inf112.firegirlwaterboy.model.LayerType;
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
  private boolean onGround;

  private Animation<TextureRegion> runningAnimation;
  private TextureRegion standingTexture, headTexture;
  private float stateTime;

  private final float bodyWidth = 1.0f;
  private final float bodyHeight = 2.0f;
  private final float bodyHeightPlacement = -0.45f;

  /**
   * Initalizes a player, giving them a type and texture
   */
  public Player(PlayerType playerType) {
    super(new Texture(Gdx.files.internal("assets/players/" + playerType.name() + "-body.png")));

    this.playerType = playerType;
    this.standingTexture = new TextureRegion(
        new Texture(Gdx.files.internal("assets/players/" + playerType.name() + "-body.png")));
    this.headTexture = new TextureRegion(
        new Texture(Gdx.files.internal("assets/players/" + playerType.name() + "-head.png")));
    
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
    Vector2 position = body.getPosition();
    batch.draw(headTexture, position.x - bodyWidth, position.y + bodyHeightPlacement, headTexture.getRegionWidth() / 38,
        headTexture.getRegionHeight() / 38);
  }

  @Override
  public void update() {
    setCurrentTexture();
  }

  @Override
  public PlayerType getType() {
    return playerType;
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
  public void spawn(World world, Vector2 pos) {
    setSize(bodyWidth, bodyHeight);
    onGround = true;
    isAlive = true;
    finished = false;
    onGround = true;
    collectedCount = 0;
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
    if (collectable.getRequiredPlayer().contains(playerType)) {
      collectedCount++;
      collectable.collect();
    }
    powerUp |= collectable.isPowerUp();
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

  //////////////////////////
  //// PRIVATE METHODS /////
  //////////////////////////

  /**
   * Sets the current texture of the player based on its state (running or standing).
   * It also flips the texture if the player is moving left or right.
   */
  private void setCurrentTexture() {
    if (!isAlive) {
      return;
    }
    stateTime += Gdx.graphics.getDeltaTime();
    TextureRegion currentFrame = getCurrentFrame();

    setRegion(currentFrame);

    if (body.getLinearVelocity().x < 0 && !currentFrame.isFlipX()) {
      currentFrame.flip(true, false);
    } else if (body.getLinearVelocity().x > 0 && currentFrame.isFlipX()) {
      currentFrame.flip(true, false);
    }

    Vector2 position = body.getPosition();
    setPosition(position.x - bodyWidth / 2, position.y - bodyHeight / 2 + bodyHeightPlacement);
  }

  /**
   * Initializes the animations for the player.
   */
  private void initializeAnimations() {
    standingTexture = new TextureRegion(
        new Texture(Gdx.files.internal("assets/players/" + playerType.name() + "-body.png")));

    Array<TextureRegion> runningFrames = new Array<>();
    for (int i = 1; i <= 8; i++) {
      Texture texture = new Texture(Gdx.files.internal("assets/players/" + playerType.name() + "-run" + i + ".png"));
      TextureRegion region = new TextureRegion(texture);
      runningFrames.add(new TextureRegion(region));
    }
    runningAnimation = new Animation<>(0.1f, runningFrames, Animation.PlayMode.LOOP);
    stateTime = 0f;
  }
  
  /**
   * Returns the current frame of the player based on their state (moving or standing).
   * If the player is not alive, returns an empty TextureRegion.
   */
  private TextureRegion getCurrentFrame() {
    if (!isAlive) {
      return new TextureRegion();
    }

    return isMoving() ? runningAnimation.getKeyFrame(stateTime, true) : standingTexture;
  }

  /**
   * Checks if the player is moving based on their linear velocity.
   */
  private boolean isMoving() {
    return Math.abs(body.getLinearVelocity().x) > 0.01 || Math.abs(body.getLinearVelocity().y) > 0.01;
  }

  /**
   * Creates the body for the player in the Box2D world.
   * 
   * @param world The Box2D world where the player will be created.
   * @param pos   The position where the player will be created.
   */
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
        width * 0.45f,
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

  /**
   * Applies a jump impulse to the player if they are on the ground or on a
   * platform.  
   */
  private void jump() {
    if (onGround) {
      body.applyLinearImpulse(new Vector2(0, jumpSpeed), body.getWorldCenter(), true);
    }
  }

  public void setGroundStatus(boolean onGround) {
    this.onGround = onGround;
  }
}
