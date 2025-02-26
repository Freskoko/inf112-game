package inf112.firegirlwaterboy.model.Entity;

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

import inf112.firegirlwaterboy.model.Maps;

// Må denne være public?

/**
 * Player class represents a player character in the game.
 * The player is a sprite that can move, has velocity and is affected by
 * gravity.
 */
public class Player extends Sprite implements IEntity {

  private float speed = Maps.PPM;// , gravity = 60 * 1.8f;
  private World world;
  private Body body;
  private boolean onGround;
  private boolean touchingWall;
  private PlayerType playerType;

  /**
   * Constructs a player with a given sprite and initial position.
   * 
   * @param world The world representing the player
   * @param pos   The initial position of the player
   */
  public Player(World world, Vector2 pos, PlayerType playerType) {
    super(getTextureForType(playerType));
    this.world = world;
    this.playerType = playerType;
    this.onGround = false;
    this.touchingWall = false;
    createBody(pos);
    setPosition(pos.x, pos.y);
  }

  private static TextureRegion getTextureForType(PlayerType type) {
    switch (type) {
      case FIREGIRL:
        return new TextureRegion(new Texture(Gdx.files.internal("figur.png")));
      case WATERBOY:
        return new TextureRegion(new Texture(Gdx.files.internal("textures/waterboy.png")));
      default:
        return new TextureRegion(new Texture(Gdx.files.internal("textures/default.png")));

    }
  }

  @Override
  public Texture getTexture() {
    return super.getTexture();
  }

  public Body getBody() {
    return body;
  }

  @Override
  public void draw(Batch batch) {
    update(Gdx.graphics.getDeltaTime());
    super.draw(batch);
  }

  public void createBody(Vector2 position) {
    BodyDef bdef = new BodyDef();
    bdef.position.set(position.x / Maps.PPM, position.y / Maps.PPM);
    bdef.type = BodyDef.BodyType.DynamicBody;

    this.body = world.createBody(bdef);

    // Main player body (rectangle shape)
    PolygonShape bodyShape = new PolygonShape();
    bodyShape.setAsBox(16 / Maps.PPM, 32 / Maps.PPM); // Adjust width & height based on player sprite

    FixtureDef fdef = new FixtureDef();
    fdef.shape = bodyShape;
    fdef.density = 1.0f;
    fdef.friction = 0.5f;
    fdef.restitution = 0.0f;
    this.body.createFixture(fdef).setUserData("PLAYER");
    body.setLinearDamping(0f);
    bodyShape.dispose();

    // Foot sensor (used for detecting ground contact)
    PolygonShape footShape = new PolygonShape();
    footShape.setAsBox(12 / Maps.PPM, 4 / Maps.PPM, new Vector2(0, -34 / Maps.PPM), 0); // Positioned at the bottom

    FixtureDef footFdef = new FixtureDef();
    footFdef.shape = footShape;
    footFdef.isSensor = true; // Sensor means it detects but does not physically collide
    this.body.createFixture(footFdef).setUserData("FOOT_SENSOR");

    footShape.dispose();
  }

  @Override
  public void update(float deltaTime) {
    this.setPosition(body.getPosition().x * Maps.PPM - getWidth() / 2,
        body.getPosition().y * Maps.PPM - getHeight() / 2);

    // Prevent falling through ground if onGround
    if (onGround && body.getLinearVelocity().y < 0) {
      body.setLinearVelocity(body.getLinearVelocity().x, 0);
    }

  }

  /**
   * Sets the velocity of the player.
   * 
   * @param dir The velocity in x direction
   */
  public void move(String dir) {
    if (!touchingWall) {
      if (dir.equals("left")) {
        body.setLinearVelocity(-speed, body.getLinearVelocity().y);
      }
      if (dir.equals("right")) {
        body.setLinearVelocity(speed, body.getLinearVelocity().y);
      }
      if (dir.equals("stop")) {
        body.setLinearVelocity(0, body.getLinearVelocity().y);
      }
    } else {
      body.setLinearVelocity(0, body.getLinearVelocity().y); // Stop movement into wall
    }

  }

  public void jump() {
    if (onGround) {
      body.applyLinearImpulse(new Vector2(0, Maps.PPM), body.getWorldCenter(), true);
      onGround = false;
    }
  }

  public void setOnGround(boolean onGround) {
    this.onGround = onGround;
    // if (onGround) {
    // body.setLinearVelocity(body.getLinearVelocity().x, 0); // Stop downward
    // movement
    // }
    System.out.println("Player on ground: " + onGround);
  }

  public void setTouchingWall(boolean touchingWall) {
    this.touchingWall = touchingWall;
  }

}
