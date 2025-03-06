package inf112.firegirlwaterboy.model.entity;

import java.util.HashSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.model.maps.Maps;

// Må denne være public?

/**
 * Player class represents a player character in the game.
 * The player is a sprite that can move, has velocity and is affected by
 * gravity.
 */
public class Player extends Sprite implements IEntity {

  // Add state enum?? 
  private float speed = 7;// , gravity = 60 * 1.8f;
  private World world;
  private Body body;
  private boolean onGround;
  private boolean touchingWall;
  private PlayerType playerType;
  private ImmunityComponent immunities; 

  /**
    * Initalizes a player, giving them a type and texture
   */
  public Player(PlayerType playerType) {
    super(getTextureForType(playerType));
    this.playerType = playerType;
    setUpImmunities(playerType);
  }

  /**
   * Spawns a player with a given sprite and initial position.
   *
   * @param world The world representing the player
   * @param pos   The initial position of the player
   */
  public void spawn(World world, Vector2 pos) {
    setSize(getTexture().getWidth() / Maps.PPM, getTexture().getHeight() / Maps.PPM);
    this.world = world;
    this.onGround = false;
    this.touchingWall = false;
    createBody(pos);
    setPosition(pos.x, pos.y);
  }

  private static TextureRegion getTextureForType(PlayerType type) {
    Texture texture;
    try {
      switch (type) {
        case FIREGIRL:
          texture = new Texture(Gdx.files.internal("FIREGIRL.png"));
          //System.out.println("firegirl chosen");
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
    bdef.fixedRotation = true;

    bdef.linearDamping = 0;
    this.body = world.createBody(bdef);

    // Main player body (rectangle shape)
    PolygonShape bodyShape = new PolygonShape();
    bodyShape.setAsBox(16 / Maps.PPM, 32 / Maps.PPM); // Adjust width & height based on player sprite


    FixtureDef fdef = new FixtureDef();
    fdef.shape = bodyShape;
    fdef.density = 0.5f;
    fdef.friction = 0;
    fdef.restitution = 0;
    this.body.createFixture(fdef).setUserData("PLAYER");
    bodyShape.dispose();

    // Foot sensor (used for detecting ground contact)
    PolygonShape footShape = new PolygonShape();
    footShape.setAsBox(12 / Maps.PPM, 2 / Maps.PPM, new Vector2(0, -16 / Maps.PPM), 0);     // Positioned at the bottom

    FixtureDef footFdef = new FixtureDef();
    footFdef.shape = footShape;
    footFdef.isSensor = true; // Sensor means it detects but does not physically collide
    //this.body.createFixture(footFdef).setUserData("FOOT_SENSOR");
    Fixture footSensor = body.createFixture(footFdef);
    footSensor.setUserData("FOOT_SENSOR");

    //footShape.dispose();
  }

  @Override
  public void update(float deltaTime) {
    Vector2 position = body.getPosition();
    this.setPosition(position.x - getWidth() / 2, position.y - getHeight() / 2);
    // velocity.x * deltatime
  }

  /**
   * Sets the velocity of the player.
   * 
   * @param dir The velocity in x direction
   */
  public void move(String dir) {
    if (dir.equals("left")) {
      body.setLinearVelocity(-speed, body.getLinearVelocity().y);
    }
    else if (dir.equals("right")) {
      body.setLinearVelocity(speed, body.getLinearVelocity().y);
    }
    else if (dir.equals("stop")) {
      body.setLinearVelocity(0, body.getLinearVelocity().y);
    }
  }

  public void jump() {
    if (onGround) {
      body.applyLinearImpulse(new Vector2(0, 10.5f), body.getWorldCenter(), true);
      //setOnGround(false);
    }
  }

  public void setOnGround(boolean onGround) {
    //System.out.println("Player on ground: " + onGround);
    this.onGround = onGround;
  }

  public void setTouchingWall(boolean touchingWall) {
    this.touchingWall = touchingWall;
  }

  private void setUpImmunities(PlayerType playerType) {
    HashSet<String> immunitiesSet = new HashSet<>();
    switch (playerType) {
      case FIREGIRL:
        immunitiesSet.add("lava");
        break;
      case WATERBOY:
        immunitiesSet.add("water");
        break;
    }
    this.immunities = new ImmunityComponent(immunitiesSet);
  }

  public boolean isImmuneTo(String hazard) {
    return immunities.isImmuneTo(hazard);
  }
}
