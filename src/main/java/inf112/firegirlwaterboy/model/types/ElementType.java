package inf112.firegirlwaterboy.model.types;

/**
 * Enum for the different element types in the game.
 */
public enum ElementType {
  LAVA("element/lava.png"),
  WATER("element/water.png"),
  NEUTRAL("element/platform.png"); // Legg inn bilde senere

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
