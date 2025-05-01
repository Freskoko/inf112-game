package inf112.firegirlwaterboy.model.entity.types;

/**
 * Enum for the different element types in the game.
 */
public enum ElementType {
  LAVA(new String[] {"assets/element/lava1.png", "assets/element/lava2.png"}),
  WATER(new String[] {"assets/element/water1.png", "assets/element/water2.png"}),
  ACID(new String[] {"assets/element/acid1.png", "assets/element/acid2.png"}),
  NEUTRAL(new String[] {"assets/element/platform.png"});

  private final String[] texturePaths;

  ElementType(String[] texturePaths) {
    this.texturePaths = texturePaths;
  }

  /**
   * Returns the path to the texture for this element type.
   *
   * @return the path to the texture
   */
  public String[] getTexturePaths() {
    return texturePaths;
  }
}