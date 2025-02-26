package inf112.firegirlwaterboy.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import inf112.firegirlwaterboy.controller.Controller;
import inf112.firegirlwaterboy.model.Model;
import inf112.firegirlwaterboy.view.GameScreen;

/**
 * FireGirlWaterBoy is a game where the player controls two characters, FireGirl
 * and WaterBoy, to solve puzzles and complete levels.
 * This class is the main class of the game, it initializes the model and
 * controller, and sets the screen to the GameScreen.
 * this class extends the Game class from libGDX.
 */
public class FireGirlWaterBoy extends Game {

  private Model model;
  private Controller controller;

  /**
   * Constructor for FireGirlWaterBoy, initializes the model and controller.
   */
  public FireGirlWaterBoy() {
    this.model = new Model();
    this.controller = new Controller(model);
  }

  @Override
  public void create() {
    this.model.init();
    setScreen(new GameScreen(model, controller));
    Gdx.input.setInputProcessor(controller);
  }
}