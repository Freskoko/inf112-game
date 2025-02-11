package inf112.firegirlwaterboy.app;

import com.badlogic.gdx.Game;

import inf112.firegirlwaterboy.view.GameScreen;

public class FireGirlWaterBoy extends Game{

  @Override
  public void create() {
    setScreen(new GameScreen());
  }

}