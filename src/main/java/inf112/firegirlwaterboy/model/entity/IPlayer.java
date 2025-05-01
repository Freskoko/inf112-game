package inf112.firegirlwaterboy.model.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.controller.MovementType;

/**
 * Interface representing a player in the game.
 * Provides methods for player interaction, movement, and state management.
 */
public interface IPlayer {

  /**
   * Adds a collectable item to the player's collection queue and increments the
   * collected count if collectable can be collected by the player.
   *
   * @param collectable The collectable object the player interacts with.
   */
  void interactWithCollectable(Collectable collectable);

  /**
   * Sets whether the player has finished the map.
   *
   * @param finished True if the player has finished; false otherwise.
   */
  void setFinished(boolean finished);

  /**
   * Checks if the player has finished the map.
   *
   * @return True if the player has finished; false otherwise.
   */
  boolean isFinished();

  /**
   * Moves the player in the specified direction by modifying its body's linear
   * velocity.
   *
   * @param dir The direction in which the player should move (UP, LEFT, RIGHT, STOP).
   * @throws IllegalArgumentException if an unexpected MovementType is provided.
   */
  void move(MovementType dir);

  /**
   * Spawns the player in the specified world at the given position, initializing
   * its attributes and creating its physics body.
   *
   * @param world The Box2D world where the player will be spawned.
   * @param pos   The position to spawn the player at.
   */
  void spawn(World world, Vector2 pos);

  /**
   * Returns the number of collected items by the player.
   * 
   * @return The number of items collected by the player.
   */
  int getCollectedCount();

  /**
   * Checks if the player is alive.
   * 
   * @return true if the player is alive; false otherwise.
   */
  boolean isAlive();

  /**
   * Determines the player's alive status based on interaction with a specific
   * element.
   * The player survives only if its immunity matches the given element.
   *
   * @param element The element the player interacts with.
   */
  void interactWithElement(Element element);
}