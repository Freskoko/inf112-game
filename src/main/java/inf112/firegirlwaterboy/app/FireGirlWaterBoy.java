package inf112.firegirlwaterboy.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import inf112.firegirlwaterboy.controller.Controller;
import inf112.firegirlwaterboy.model.Model;
import inf112.firegirlwaterboy.view.GameScreen;

public class FireGirlWaterBoy extends Game{

  private Model model;
  private Controller controller;

  public FireGirlWaterBoy(){
    this.model = new Model(); 
    this.controller = new Controller(model);
  }

  @Override
  public void create() { 

    setScreen(new GameScreen(model, controller)); 
    Gdx.input.setInputProcessor(controller); 
  }
}