package inf112.firegirlwaterboy.model.entity;

/**
 * Enum for the different element types in the game.
 */
public enum ElementType {
  LAVA("lava.png"),
  WATER("water.png");

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
