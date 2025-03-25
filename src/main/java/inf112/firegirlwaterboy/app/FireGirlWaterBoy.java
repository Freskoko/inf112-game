package inf112.firegirlwaterboy.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import inf112.firegirlwaterboy.controller.Controller;
import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.Model;
import inf112.firegirlwaterboy.view.ChooseMapScreen;
import inf112.firegirlwaterboy.view.GameScreen;
import inf112.firegirlwaterboy.view.HelpScreen;
import inf112.firegirlwaterboy.view.WelcomeScreen;

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
  private GameState currentGameState;
  

  public FireGirlWaterBoy() {
    this.model = new Model();
    this.controller = new Controller(model);
    this.currentGameState = GameState.WELCOME;

  }

  @Override
  public void create() {
    setScreen(getScreenByGameState(currentGameState));
    Gdx.input.setInputProcessor(controller);
  }

  @Override
  public void render() {
    super.render();

    GameState newGameState = model.getGameState();
    if (!newGameState.equals(currentGameState)) {
      setScreen(getScreenByGameState(newGameState));
      currentGameState = newGameState;
    }
  }

  /**
   * Returns the appropriate screen based on the current game state.
   * 
   * This method ensures that the correct screen is returned for each game state.
   * If the screen instance does not already exist, it creates and initializes it.
   * 
   * @param gameState The current state of the game.
   * @return The corresponding Screen instance for the given game state.
   */
  private Screen getScreenByGameState(GameState gameState) {
    Screen screen;
    switch (gameState) {
      case WELCOME:
        screen = new WelcomeScreen(controller);
        break;
      case ACTIVE_GAME:
        model.restartGame();
        screen = new GameScreen(model, controller);
        break;
      case GAME_OVER:
        // screen = new GameOverScreen(model, controller); 
        screen = new GameScreen(model, controller);
        break;
      case HELP:
        screen = new HelpScreen(controller);
        break;
      case CHOOSE_MAP:
        screen = new ChooseMapScreen(model, controller);
        break;
      default:

      
        System.out.println("Ukjent GameState: " + gameState);
        screen = new WelcomeScreen(controller);
    }
    return screen; //default å returnere til welcomeScreen hvis ukjent? Vet ikke hva som er nødvendig.
  }
}