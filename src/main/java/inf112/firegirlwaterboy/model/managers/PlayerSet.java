package inf112.firegirlwaterboy.model.managers;

import inf112.firegirlwaterboy.model.entity.Player;
import inf112.firegirlwaterboy.model.types.PlayerType;

/**
 * A specialized set for managing Player entities in the game.
 *
 * Enforces a maximum of two players, and provides utility methods
 * for querying player status and score.
 */
public class PlayerSet extends EntitySet<Player> implements IPlayerSet {

  public static final int MAX_PLAYERS = 2;

  @Override
  public void add(Player entity) {
    if (entities.size() >= MAX_PLAYERS) {
      throw new IllegalStateException("Maximum number of players reached!");
    }
    super.add(entity);
  }

  @Override
  public Player getPlayer(PlayerType playerType) {
    for (Player player : this) {
      if (player.getType().equals(playerType)) {
        return player;
      }
    }
    throw new IllegalArgumentException("PlayerSet does not contain a Player of type: " + playerType);
  }

  @Override
  public boolean contains(PlayerType playerType) {
    return super.contains(new Player(playerType));
  }

  @Override
  public boolean areFinished() {
    return entities.stream().allMatch(Player::isFinished);
  }

  @Override
  public int getScore() {
    return entities.stream().mapToInt(Player::getCollectedCount).sum();
  }

  @Override
  public boolean areAlive() {
    return entities.stream().allMatch(Player::isAlive);
  }
}
