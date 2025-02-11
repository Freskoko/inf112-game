package inf112.firegirlwaterboy.model.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

// Må denne være public?
public class Player extends Sprite implements IEntity {

  // Endring i x og y retning
  private Vector2 velocity;
  private float speed = 60 * 2, gravity = 60 * 1.8f;
  
  public Player(Sprite sprite) {
    super(sprite);
    velocity = new Vector2();
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
    velocity.add(0, - (gravity * deltaTime));

    if (velocity.y < speed) {
      velocity.y = -speed;
    }else if (velocity.y > speed) {
      velocity.y = speed;
    }

    // Oppdaterer posisjonen til spilleren
    setX(getX() + velocity.x * deltaTime);
    setY(getY() + velocity.y * deltaTime);
  }

  
}
