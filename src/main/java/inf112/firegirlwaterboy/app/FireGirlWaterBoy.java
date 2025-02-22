package inf112.firegirlwaterboy.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import inf112.firegirlwaterboy.controller.Controller;
import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.Model;
import inf112.firegirlwaterboy.view.GameScreen;
import inf112.firegirlwaterboy.view.WelcomeScreen;

public class FireGirlWaterBoy extends Game{

  private Model model;
  private Controller controller;

  public FireGirlWaterBoy(){
    this.model = new Model(); 
    this.controller = new Controller(model);
  }

  @Override
  public void create() { 
    model.setGameState(GameState.WELCOME);
    setScreen(new WelcomeScreen(model, controller)); 
    Gdx.input.setInputProcessor(controller); 
  }
}