package inf112.firegirlwaterboy.model.Entity;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class  EntityList<T extends Sprite> implements Iterable<T> {

  // Kan kun ha en spiller av hver type, hashmap fikser dette
  private HashMap<PlayerType, T> players;

  public EntityList() {
    players = new HashMap<PlayerType, T>();
  }

  public void addPlayer(PlayerType playerType, T player) {
    players.put(playerType, player);
  }

  
  public T getPlayer(PlayerType playerType) {
    return players.get(playerType);
  }


  @Override
  public Iterator<T> iterator() {
    return players.values().iterator();
    // Kan hende vi må løse dette ulikt slik vi får med player Type?
    // Evt legge til Type i player klassen. 
  }
}
