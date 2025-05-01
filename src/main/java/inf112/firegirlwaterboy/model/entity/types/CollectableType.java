package inf112.firegirlwaterboy.model.entity.types;

import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Enum for the different collectable types in the game.
 */
public enum CollectableType {
  RED("assets/collectable/red.png", false, Set.of(PlayerType.FIREGIRL)),
  BLUE("assets/collectable/blue.png", false, Set.of(PlayerType.WATERBOY)),
  DUO("assets/collectable/duo.png", false, Set.of(PlayerType.FIREGIRL, PlayerType.WATERBOY)),
  POWERUP("assets/collectable/powerUp.png", true, Set.of(PlayerType.FIREGIRL, PlayerType.WATERBOY));

  private Texture texture;
  private final boolean powerUp;
  private final Set<PlayerType> requiredPlayers;

  CollectableType(String path, boolean powerUp, Set<PlayerType> requiredPlayers) {
    this.texture = new Texture(Gdx.files.internal(path));
    this.powerUp = powerUp;
    this.requiredPlayers = requiredPlayers;
  }

  /**
   * Returns the texture for this collectable type.
   * 
   * @return The texture
   */
  public Texture getTexture() {
    return texture;
  }

  /**
   * Checks if this collectable type is a power-up.
   * 
   * @return True if it is a power-up, false otherwise
   */
  public boolean isPowerUp() {
    return powerUp;
  }

  /**
   * Returns the set of player types required to collect this collectable type.
   * 
   * @return The set of required player types
   */
  public Set<PlayerType> getRequiredPlayers() {
    return requiredPlayers;
  }
}