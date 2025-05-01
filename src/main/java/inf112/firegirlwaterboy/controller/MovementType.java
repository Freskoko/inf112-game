package inf112.firegirlwaterboy.controller;

/**
 * Enum for the different movement states in the game.
 */
public enum MovementType {
  UP(),
  LEFT(),
  RIGHT(),
  STOP(),
  DOWN();

  /**
   * Get the opposite direction of the given movement type.
   * 
   * @param movementType  The movement type to get the opposite of
   * @return The opposite movement type
   */
  static public MovementType getOppositeDir(MovementType movementType) {
    switch (movementType) {
      case LEFT:
        return MovementType.RIGHT;
      case RIGHT:
        return MovementType.LEFT;
      case UP:
        return MovementType.DOWN;
      case DOWN:
        return MovementType.UP;
      default:
        return MovementType.STOP;
    }
  }
}