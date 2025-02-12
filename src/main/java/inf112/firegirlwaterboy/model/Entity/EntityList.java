package inf112.firegirlwaterboy.model.Entity;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.Batch;

public class EntityList<T, E extends IEntity> implements Iterable<E> {

  // Kan kun ha en spiller av hver type, hashmap fikser dette
  private HashMap<T, E> players;

  public EntityList() {
    players = new HashMap<T, E>();
  }

  public void addPlayer(T playerType, E player) {
    if (players.containsKey(playerType)) {
      throw new IllegalArgumentException("Player of type:" + playerType +  "already exists");
    }
    players.put(playerType, player);
  }

  
  public E getPlayer(T playerType) {
    return players.get(playerType);
  }

  public void draw(Batch batch) {
    for (E player : players.values()) {
      player.draw(batch);
    }
  }

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
}
