package inf112.firegirlwaterboy.model.entity.types;

/**
 * Enum for the two different player types in the game.
 */
public enum PlayerType {
  FIREGIRL(ElementType.LAVA),
  WATERBOY(ElementType.WATER);

  private final ElementType immuneTo;

  PlayerType(ElementType immuneTo) {
    this.immuneTo = immuneTo;
  }

  /**
   * Returns the element type that this player type is immune to.
   * 
   * @return The element type that this player type is immune to
   */
  public ElementType getImmunity() {
    return immuneTo;
  }
}