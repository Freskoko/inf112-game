package inf112.firegirlwaterboy.model.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public interface IEntity {

   /**
    * 
   * @param deltaTime
   */
  void update(float deltaTime);

  /**
   * 
   * @param batch
   */
  void draw(Batch batch);

  Texture getTexture();
  
}
