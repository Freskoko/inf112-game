package inf112.firegirlwaterboy.model.Entity;

import java.util.HashMap;
import java.util.Iterator;

public class  PlayerList<T extends Player> implements Iterable<T> {

  // Kan kun ha en spiller av hver type, hashmap fikser dette
  HashMap<PlayerType, Player> players = new HashMap<>();

  @Override
  public Iterator<T> iterator() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'iterator'");
  }
}
