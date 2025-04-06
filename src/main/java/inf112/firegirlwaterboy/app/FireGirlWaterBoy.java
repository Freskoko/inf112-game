package inf112.firegirlwaterboy.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import inf112.firegirlwaterboy.controller.Controller;
import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.Model;
import inf112.firegirlwaterboy.view.ChooseMapScreen;
import inf112.firegirlwaterboy.view.GameOverScreen;
import inf112.firegirlwaterboy.view.GameScreen;
import inf112.firegirlwaterboy.view.HelpScreen;
import inf112.firegirlwaterboy.view.WelcomeScreen;
import inf112.firegirlwaterboy.view.CompletedMapScreen;

/**
 * FireGirlWaterBoy is a game where the player controls two characters, FireGirl
 * and WaterBoy, to solve puzzles and complete maps.
 * This class is the main class of the game, it initializes the model and
 * controller, and sets the screen to the GameScreen.
 * this class extends the Game class from libGDX.
 */
public class FireGirlWaterBoy extends Game {

  private Model model;
  private Controller controller;
  private GameState currentGameState;
  private WelcomeScreen welcomeScreen;

  public FireGirlWaterBoy() {
    this.model = new Model();
    this.controller = new Controller(model);
    this.currentGameState = model.getGameState();
  }

  @Override
  public void create() {
    setScreen(getScreen(currentGameState));
    Gdx.input.setInputProcessor(controller);
  }

  @Override
  public void render() {
    super.render();

    GameState newGameState = model.getGameState();
    if (!newGameState.equals(currentGameState)) {
      setScreen(getScreen(newGameState));
      currentGameState = newGameState;
    }
  }

  /**
   * Returns appropriate screen based on the current game state.
   * 
   * This method ensures that the correct screen is returned for each game state.
   * 
   * @param gameState The current state of the game.
   * @return a new corresponding Screen instance for the given game state.
   */
  private Screen getScreen(GameState gameState) {
    if (gameState == null){
      throw new IllegalArgumentException("null is not a valid gamestate");
    }

    switch (gameState) {
      case WELCOME:
        if (welcomeScreen == null)
          welcomeScreen = new WelcomeScreen(controller);
        return welcomeScreen;

      case ACTIVE_GAME:
        model.restartGame();
        return new GameScreen(model, controller);
      case HELP:
        return new HelpScreen(controller);
      case CHOOSE_MAP:
        return new ChooseMapScreen(controller);
      case GAME_OVER:
        return new GameOverScreen(controller);
      case COMPLETED_MAP:
        return new CompletedMapScreen(controller);
      default:
        throw new IllegalArgumentException("Unknown game state: " + gameState);
    }
  }

}