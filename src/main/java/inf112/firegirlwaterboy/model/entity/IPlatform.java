package inf112.firegirlwaterboy.model.entity;

import com.badlogic.gdx.physics.box2d.Body;

public interface IPlatform {

  /**
   * Returns the body of the platform.
   *
   * @return The body of the platform.
   */
  Body getBody();

  /**
   * Handles collision by changing the movement direction of the platform.
   */
  void collision();

}