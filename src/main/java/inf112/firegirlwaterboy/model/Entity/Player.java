package inf112.firegirlwaterboy.model.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

// Må denne være public?
public class Player extends Sprite implements IEntity {

  // Endring i x og y retning
  private Vector2 velocity;
  private float speed = 60 * 2, gravity = 60 * 1.8f;
  
  private Player(Sprite sprite, Vector2 pos) {
    super(sprite);
    velocity = new Vector2();
    setPosition(pos.x, pos.y);
  }

  public Player(){
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
    }else if (velocity.y > speed) {
      velocity.y = speed;
    }
  }

  public void setVelosity(int deltaX, int deltaY) {
    velocity.set(deltaX *speed, deltaY*speed);

  }  

  public Vector2 getVelocity() {
    return velocity;
  }

  public void setVelosity(Vector2 velocity) {
    this.velocity = velocity;
  }

  

  
  

 
}
