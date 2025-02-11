package inf112.firegirlwaterboy.model;

import inf112.firegirlwaterboy.controller.IControllableModel;
import inf112.firegirlwaterboy.model.Entity.PlayerType;
import inf112.firegirlwaterboy.view.IViewModel;

public class Model implements IControllableModel, IViewModel {


  @Override
  public GameState getGameState() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getGameState'");
  }

  @Override
  public void setGameState(GameState gameState) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setGameState'");
  }

  @Override
  public boolean move(PlayerType player, int deltaRow, int deltaCol) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'move'");
  }

  @Override
  public int getTimerDelay() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getTimerDelay'");
  }
  
}
