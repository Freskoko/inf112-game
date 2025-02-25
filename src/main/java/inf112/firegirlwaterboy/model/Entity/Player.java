package inf112.firegirlwaterboy.model.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.model.Model;

// Må denne være public?

/**
 * Player class represents a player character in the game.
 * The player is a sprite that can move, has velocity and is affected by gravity.
 */
public class Player extends Sprite implements IEntity {

  // Endring i x og y retning
  private Vector2 velocity;
  private float speed = 60 * 2, gravity = 60 * 1.8f;
  private World world;
  private Body body;
  private boolean onGround;

  private PlayerType playerType;

  /**
   * Constructs a player with a given sprite and initial position.
   * @param world The world representing the player
   * @param pos The initial position of the player
   */
  public Player(World world, Vector2 pos, PlayerType playerType) {
    super();
    this.world = world;
    this.playerType = playerType;
    this.onGround = false;
    createBody(pos.x, pos.y);
    setPosition(pos.x, pos.y);
  }


  @Override
  public Texture getTexture() {
    return super.getTexture();
  }

  @Override
  public void draw(Batch batch) {
    update(Gdx.graphics.getDeltaTime());
    super.draw(batch);
  }

  // Maybe this should be a package private method??
  @Override
  public void update(float deltaTime) {
    setPosition(body.getPosition().x * Model.PPM, body.getPosition().y * Model.PPM);
  }

  /**
   * Sets the velocity of the player.
   * @param deltaX The velocity in x direction
   * @param deltaY The velocity in y direction
   */
  public void setVelosity(int deltaX, int deltaY) {
    velocity.set(deltaX * speed, deltaY * speed);
  }

  /**
   * Sets the velocity of the player.
   * @param velocity The new velocity of the player
   */
  public void setVelosity(Vector2 velocity) {
    this.velocity = velocity;
  }

  /**
   * Gets the velocity of the player.
   * @return The velocity of the player
   */
  public Vector2 getVelocity() {
    return velocity;
  }

  // Utforske for kollisjon (Get upsi)
  @Override
  public void setBounds(float x, float y, float width, float height) {
    // TODO Auto-generated method stub
    super.setBounds(x, y, width, height);
  }

   public Body getBody() {
    return body;
  }
    

  private void createBody(float x, float y) {
    BodyDef bdef = new BodyDef();
    bdef.type = BodyDef.BodyType.DynamicBody;
    bdef.position.set(x / Model.PPM, y / Model.PPM);

    body = world.createBody(bdef);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(16 / Model.PPM, 16 / Model.PPM);

    FixtureDef fdef = new FixtureDef();
    fdef.shape = shape;
    fdef.density = 1f;
    fdef.friction = 0.6f;
    fdef.restitution = 0.1f; // Small bounce

    body.createFixture(fdef).setUserData(this);
    shape.dispose();
  }

  public PlayerType getPlayerType() {
    return this.playerType;
  }

  public void jump() {
    if (onGround) {
      body.applyLinearImpulse(new Vector2(0, 5f), body.getWorldCenter(), true);
      onGround = false; 
    }
  }
  

  public void setOnGround(boolean onGround) {
    this.onGround = onGround;
  }

}
