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
import inf112.firegirlwaterboy.view.CompletedLevelScreen;

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

  // Opprett referanser til skjermene. Legger til flere ettersom vi lager flere
  // screens
  private WelcomeScreen welcomeScreen;
  private GameScreen gameScreen;
  private HelpScreen helpScreen;
  private ChooseMapScreen chooseMapScreen;
  private GameOverScreen gameOverScreen;
  private CompletedLevelScreen completedLevelScreen;

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
    switch (gameState) {
      case WELCOME:
        if (welcomeScreen == null)
          welcomeScreen = new WelcomeScreen(controller);
        return welcomeScreen;

      case ACTIVE_GAME:
        model.restartGame(); // restart game hver gang det blir aktivt
        gameScreen = new GameScreen(model, controller); // eventuelt også cache denne hvis ønsket
        return gameScreen;

      case HELP:
        if (helpScreen == null)
          helpScreen = new HelpScreen(controller);
        return helpScreen;

      case CHOOSE_MAP:
        if (chooseMapScreen == null)
          chooseMapScreen = new ChooseMapScreen(controller);
        return chooseMapScreen;

      case GAME_OVER:
        if (gameOverScreen == null)
          gameOverScreen = new GameOverScreen(controller);
        return gameOverScreen;

      case COMPLETED_LEVEL:
        if (completedLevelScreen == null)
          completedLevelScreen = new CompletedLevelScreen(controller);
        return completedLevelScreen;
        
      default:
        System.out.println("Ukjent GameState: " + gameState);
        if (welcomeScreen == null)
          welcomeScreen = new WelcomeScreen(controller);
        return welcomeScreen;
    }
  }

}