package inf112.firegirlwaterboy.model.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.controller.MovementType;

public interface IPlayer {

  void collect(Collectable collectable);

  PlayerType getPlayerType();

  void setOnGround(boolean onGround);

  void setTouchingWall(boolean touchingWall);

  void jump();

  /**
   * Sets the velocity of the player.
   * 
   * @param dir The velocity in x direction
   */
  void move(MovementType dir);

  /**
   * Spawns a player with a given sprite and initial position.
   *
   * @param world The world representing the player
   * @param pos   The initial position of the player
   */
  void spawn(World world, Vector2 pos);

  int getCountCollected();

}
