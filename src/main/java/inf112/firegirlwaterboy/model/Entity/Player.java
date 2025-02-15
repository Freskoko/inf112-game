package inf112.firegirlwaterboy.model.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

// Må denne være public?

/**
 * Player class represents a player character in the game.
 * The player is a sprite that can move, has velocity and is affected by gravity.
 */
public class Player extends Sprite implements IEntity {

  // Endring i x og y retning
  private Vector2 velocity;
  private float speed = 60 * 2, gravity = 60 * 1.8f;

  /**
   * Constructs a player with a given sprite and initial position.
   * @param sprite The sprite representing the player
   * @param pos The initial position of the player
   */
  private Player(Sprite sprite, Vector2 pos) {
    super(sprite);
    velocity = new Vector2();
    setPosition(pos.x, pos.y);
  }

  /**
   * Constructs a player with a default sprite and initial position.
   */
  public Player() {
    this(new Sprite(new Texture("src/main/resources/figur.png")), new Vector2(100, 200));
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
    // Graviditasjon kraften
    velocity.y -= gravity * deltaTime;

    if (velocity.y < speed) {
      velocity.y = -speed;
    } else if (velocity.y > speed) {
      velocity.y = speed;
    }
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

}
