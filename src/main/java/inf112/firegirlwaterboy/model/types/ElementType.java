package inf112.firegirlwaterboy.model.types;

/**
 * Enum for the different element types in the game.
 */
public enum ElementType {
  LAVA("assets/element/lava.png"),
  WATER("assets/element/water.png"),
  ACID("assets/element/acid.png"),
  NEUTRAL("assets/element/platform.png");

  private final String texturePath;

  ElementType(String texturePath) {
    this.texturePath = texturePath;
  }

  /**
   * Returns the path to the texture for this element type.
   * 
   * @return the path to the texture
   */
  public String getTexturePath() {
    return texturePath;
  }

}
