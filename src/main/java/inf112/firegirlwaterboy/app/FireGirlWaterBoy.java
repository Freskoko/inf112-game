package inf112.firegirlwaterboy.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import inf112.firegirlwaterboy.controller.Controller;
import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.Model;
import inf112.firegirlwaterboy.view.GameScreen;
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

  // Opprett referanser til skjermene. Legger til flere ettersom vi lager flere screens
  private WelcomeScreen welcomeScreen;
  private GameScreen gameScreen;

  // Holder styr på current game state
  private GameState currentGameState;


  public FireGirlWaterBoy() {
    this.model = new Model();
    this.controller = new Controller(model);
    this.currentGameState = GameState.WELCOME;

  }

  @Override
  public void create() {
    // Setter Welcome som start screen
    
    setScreen(getScreenByGameState(currentGameState));
    Gdx.input.setInputProcessor(controller);
  }

  @Override
  public void render() {
    super.render();

    // Sjekker hva den nye game state er hvis den skiftes
    GameState newGameState = model.getGameState();

    // Hvis gamestate har endret seg, så skiftes skjermen
    if (newGameState != currentGameState) {
      setScreen(getScreenByGameState(newGameState));
      currentGameState = newGameState;
    }

  }

  //Legger til flere cases ettersom vi får flere screens
  //Endrer screen basert på state
  private Screen getScreenByGameState(GameState gameState) {
    switch (gameState) {
      case WELCOME:
        if (welcomeScreen == null) {
          welcomeScreen = new WelcomeScreen(model, controller);
        }
        return welcomeScreen;

      case ACTIVE_GAME:
        if (gameScreen == null) {
          gameScreen = new GameScreen(model, controller);
        }
        return gameScreen;

      default:
      System.out.println("Ukjent GameState: " + gameState);
      return welcomeScreen; //default å returnere til welcomeScreen hvis ukjent? Vet ikke hva som er nødvendig. 

    }

  }
}