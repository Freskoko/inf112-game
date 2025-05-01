package inf112.firegirlwaterboy.model.entity;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Interface that represents a platform in the game.
 * Platforms are static objects that can be interacted with by players.
 */
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