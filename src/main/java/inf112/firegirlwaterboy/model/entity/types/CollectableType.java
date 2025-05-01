package inf112.firegirlwaterboy.model.entity.types;

import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

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
    ;
    this.powerUp = powerUp;
    this.requiredPlayers = requiredPlayers;
  }

  public Texture getTexture() {
    return texture;
  }

  public boolean isPowerUp() {
    return powerUp;
  }

  public Set<PlayerType> getRequiredPlayers() {
    return requiredPlayers;
  }

}
