package inf112.firegirlwaterboy.model.entity;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * EntityList is a class that holds a list of entities.
 * It is used to draw and dispose of entities.
 * 
 * @param <T> Type of entity
 * @param <E> Entity
 */
public class EntityList<T, E extends IEntity> implements Iterable<E> {

  public static final int maxPlayers = 2;

  // Dette bør være en arrayList
  private HashMap<T, E> players;

  /**
   * Constructor for EntityList
   */
  public EntityList() {
    players = new HashMap<T, E>();
  }

  /**
   * Add player to the list
   * 
   * @param playerType The key representing the entity
   * @param player     The entity to be added
   * @throws IllegalArgumentException if player already exists
   */
  public void addPlayer(T playerType, E player) {

    if (players.size() >= EntityList.maxPlayers) {
      System.out.println("Max players reached!");
      return;
    }
    else {
      if (players.containsKey(playerType)) {
        throw new IllegalArgumentException("Player of type:" + playerType + "already exists");
      }
    }

    players.put(playerType, player);
  }

  public void removePlayer(T playerType) {
    if (players.size() <= 0) {
      System.out.println("Cannot remove player, there are no players to remove!");
      return;
    }
    if (!players.containsKey(playerType)) {
      throw new IllegalArgumentException("Player of type:" + playerType + "does not exist, they cannot be removed!");
    }
    players.remove(playerType);
  }

  /**
   * Retrieve player from the list based on playerType
   * 
   * @param playerType The key representing the entity type
   * @return player The entity associated with the given type
   */
  public E getPlayer(T playerType) {
    return players.get(playerType);
  }

  /**
   * Draws all players contained in the list using the given batch
   * 
   * @param batch The batch used for rendering
   */
  public void draw(Batch batch) {
    for (E player : players.values()) {
      player.draw(batch);
    }
  }

  /**
   * Disposes of all textures used by the players
   */
  public void dispose() {
    for (E player : players.values()) {
      player.getTexture().dispose();
    }
  }

  @Override
  public Iterator<E> iterator() {
    return players.values().iterator();
    // Kan hende vi må løse dette ulikt slik vi får med player Type?
    // Evt legge til Type i player klassen.
  }

  public boolean isEmpty() {
    return players.isEmpty();
  }

  public boolean containsKey(PlayerType playerType) {
    return this.players.containsKey(playerType);
  }

  public String getPlayersAsString() {
    StringBuilder sb = new StringBuilder();

    for (T playerType : players.keySet()) {
        sb.append(playerType.toString()).append(", ");
    }

    // Remove trailing comma if there are any players
    if (sb.length() > 0) {
        sb.setLength(sb.length() - 2);
    }

    return sb.toString();
  }
}
