package inf112.firegirlwaterboy.model.managers;

import inf112.firegirlwaterboy.model.entity.Player;
import inf112.firegirlwaterboy.model.types.PlayerType;

public class PlayerSet extends EntitySet<Player> implements IPlayerSet {

  public static final int MAX_PLAYERS = 2;

  @Override
  public void add(Player entity) {
    if (entities.size() >= MAX_PLAYERS) {
      throw new IllegalStateException("Maximum number of players reached!");
    }
    super.add(entity);
  }

  public Player getPlayer(PlayerType playerType) {
    for (Player player : entities) {
      if (player.getType().equals(playerType)) {
        return player;
      }
    }
    throw new IllegalArgumentException("PlayerSet does not contain a Player of type: " + playerType);
  }

  public boolean contains(PlayerType playerType) {
    return super.contains(new Player(playerType));
  }

  public boolean areFinished() {
    for (Player player : entities) {
      if (!player.isFinished()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getTotalCollectedScore() {
    int total = 0;
    for (Player player : this) {
      total += player.getCollectedCount();
    }
    return total;
  }
}
