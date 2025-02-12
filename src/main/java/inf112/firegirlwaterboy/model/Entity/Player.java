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
  private TiledMapTileLayer collisionLayer;
  private float speed = 60 * 2, gravity = 60 * 1.8f;
  
  public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
    super(sprite);
    velocity = new Vector2();
    this.collisionLayer = collisionLayer;
    setPosition(collisionLayer.getWidth()*4, collisionLayer.getHeight()*20);
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

    checkCollision(deltaTime);

  }

    
  private void checkCollision(float deltaTime) {
    // Save old position
    float oldX = getX(), oldY = getY();
    float tileWidth = collisionLayer.getTileWidth();
    float tileHeight = collisionLayer.getTileHeight();
    boolean collisionX = false, collisionY = false;

    float tileX, tileY;
  
    if (velocity.x < 0) { // Left
      // Top left
      tileX = getX();
      tileY = getY() + getHeight();
      if (isCellBlocked(tileX, tileY)) {
        collisionX = true;
      }
      // Middle left
      tileY = getY() + getHeight()/2;
      if (isCellBlocked(tileX, tileY)) {
        collisionX = true;
      }
      // Bottom left
      tileY = getY();
      if (isCellBlocked(tileX, tileY)) {
        collisionX = true;
      }
    } else if (velocity.x > 0) { // Right
      // Top right
      tileX = getX() + getWidth();
      tileY = getY() + getHeight();

      if (isCellBlocked(tileX, tileY)) {
        collisionX = true;
      }
      // Middle right
      tileY = getY() + (getHeight()/2) ;
      if (isCellBlocked(tileX, tileY)) {
        collisionX = true;
      }
      // Bottom right
      tileY = getY();
      if (isCellBlocked(tileX, tileY)) {
        collisionX = true;
      }  
    }

    if (velocity.y < 0) { // Up
      // Bottom left
      tileX = getX();
      tileY = getY();
      if (isCellBlocked(tileX, tileY)) {
        collisionY = true;
      }
      // Bottom middle
      tileX = getX() + getWidth()/2;
      if (isCellBlocked(tileX, tileY)) {
        collisionY = true;
      }
      // Bottom right
      tileX = getX() + getWidth();
      if (isCellBlocked(tileX, tileY)) {
        collisionY = true;
      }
    } else if (velocity.y > 0) { // Down
      // Top left
      tileX = getX();
      tileY = getY() + getHeight();
      if (isCellBlocked(tileX, tileY)) {
        collisionY = true;
      }
      // Top middle
      tileX = getX() + getWidth()/2;
      if (isCellBlocked(tileX, tileY)) {
        collisionY = true;
      }
      // Top right
      tileX = getX() + getWidth();
      if (isCellBlocked(tileX, tileY)) {
        collisionY = true;
      }
    }
    if (!collisionX) {
      setX(getX() + velocity.x * deltaTime);
    } else {
      setX(oldX);
      velocity.x = 0;
    }
    if (!collisionY) {
      setY(getY() + velocity.y * deltaTime);
    } else {
      setY(oldY);
      velocity.y = 0;
    }
  }


  private boolean isCellBlocked(float x, float y) {
    float tileWidth = collisionLayer.getTileWidth();
    float tileHeight = collisionLayer.getTileHeight();
    //return collisionLayer.getCell(0, 0).getTile().getProperties().containsKey("blocked");
    return collisionLayer.getCell((int) (x / tileWidth), (int) (y / tileHeight)).getTile().getProperties().containsKey("blocked");
  }


  
}
