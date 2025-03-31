package inf112.firegirlwaterboy.model.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.controller.MovementType;

public interface IPlayer {

  /**
   * Adds a collectable item to the player's collection queue and increments the
   * collected count.
   *
   * @param collectable The collectable item to be added.
   */
  void collect(Collectable collectable);

  /**
   * Sets whether the player is currently on the ground.
   *
   * @param groundStatus true if the player is on the ground; false otherwise.
   */
  void setOnGround(boolean groundStatus);

  /**
   * Sets whether the player is touching an edge.
   *
   * @param edgeStatus true if the player is touching an edge; false otherwise.
   */
  void setTouchingEdge(boolean edgeStatus);

  /**
   * Moves the player in the specified direction by modifying its body's linear
   * velocity.
   *
   * @param dir The direction in which the player should move (UP, LEFT, RIGHT,
   *            STOP).
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
   *
   * @return The number of items collected by the player.
   */
  int getCountCollected();

  /**
   *
   * @return true if the player is alive; false otherwise.
   */
  boolean isAlive();

  /**
   * Determines the player's alive status based on interaction with a specific
   * element type.
   * The player survives only if its immunity matches the given element type.
   *
   * @param elementType The type of element the player interacts with.
   */
  void interactWithElement(ElementType elementType);

  /**
   * @return true if the player is on the ground; false otherwise.
   */
  boolean isOnGround();

  /**
   * @return true if the player is touching an edge; false otherwise.
   */
  boolean isTouchingEdge();

}
