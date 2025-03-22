package inf112.firegirlwaterboy.model.entity;

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

  public ElementType getImmunity() {
      return immuneTo;
  }
}
