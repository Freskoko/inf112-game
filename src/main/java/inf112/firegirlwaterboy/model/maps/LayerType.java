package inf112.firegirlwaterboy.model.maps;

/**
 * Enum representing the different layer types in the game.
 * Each layer type has a corresponding bit value.
 */
public enum LayerType {
  PLAYER(0x0001),
  PLATFORM(0x0002),
  STATIC(0x00004),
  COLLECTABLE(0x0008),
  ELEMENT(0x0010),
  FINISH(0x0020),
  BACKGROUND(null),
  SPAWN(null);

  private final Integer bit;

  LayerType(Integer bit) {
    this.bit = bit;
  }

  /**
   * Returns the bit value associated with this layer type.
   * 
   * @return The bit value as a short.
   */
  public short getBit() {
    return (short) (int) this.bit;
  }
}