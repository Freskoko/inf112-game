package inf112.firegirlwaterboy.model.entity;

/**
 * Enum for the two different element types in the game.
 */
public enum ElementType {
  LAVA("lava.png"),
  WATER("water.png");

  private final String texturePath;

  ElementType(String texturePath) {
    this.texturePath = texturePath;
  }

  public String getTexturePath() {
    return texturePath;
  }

}
