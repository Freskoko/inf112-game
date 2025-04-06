package inf112.firegirlwaterboy.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.types.PlayerType;

/**
 * Controller class for the game FireGirl & WaterBoy.
 * Handles keyboard input and delegates button logic via ScreenButtonHandler.
 */
public class Controller implements InputProcessor {

  private IControllableModel model;
  private PlayerType playerOne; // Controlled by Arrow keys
  private PlayerType playerTwo; // Controlled by WASD keys
  private ButtonHandler buttonHandler;

  public Controller(IControllableModel model) {
    this.model = model;
    this.playerOne = null;
    this.playerTwo = null;
    this.buttonHandler = new ButtonHandler(this, model);
  }

  /**
   * Returns the model.
   * 
   * @return
   */
  public IControllableModel getModel() {
    return model;
  }

  /**
   * Selects a playerType for a player.
   * 
   * @param playerType  The playerType to select.
   * @param isPlayerOne True if player 1, false if player 2.
   */
  public void selectPlayer(PlayerType playerType, boolean isPlayerOne) {
    if (isPlayerOne) {
      if (playerTwo != null && playerType == playerTwo) {
        System.out.println("Player 1 cant choose the same as Player 2!");
        return;
      }
      playerOne = playerType;
    } else {
      if (playerOne != null && playerType == playerOne) {
        System.out.println("Player 2 cant choose the same as Player 1!");
        return;
      }
      playerTwo = playerType;
    }
  }

  /**
   * Starts the game if both players have selected a playerType.
   */
  public void startGameIfPlayersSelected() {
    if (playerOne != null && playerTwo != null) {
      model.setGameState(GameState.CHOOSE_MAP);
      model.addPlayer(playerOne);
      model.addPlayer(playerTwo);
    } else {
      System.out.println("Please select playerType for both players.");
    }
  }

  // Attach buttons from WelcomeScreen
  public void attachWelcomeScreenListeners(Button fgP1, Button wbP1, Button fgP2, Button wbP2, Button start,
      Button help) {
    fgP1.addListener(buttonHandler.selectPlayerListener(1, PlayerType.FIREGIRL));
    wbP1.addListener(buttonHandler.selectPlayerListener(1, PlayerType.WATERBOY));
    fgP2.addListener(buttonHandler.selectPlayerListener(2, PlayerType.FIREGIRL));
    wbP2.addListener(buttonHandler.selectPlayerListener(2, PlayerType.WATERBOY));
    start.addListener(buttonHandler.startButtonListener());
    help.addListener(buttonHandler.helpButtonListener());
  }

  // Attach HelpScreen buttons
  public void attachHelpScreenListeners(Button back) {
    back.addListener(buttonHandler.backButtonListener());
  }

  // Attach ChooseMapScreen buttons
  public void setupPlayButtonListener(Button playButton) {
    playButton.addListener(buttonHandler.playButtonListener());
  }

  /**
   * Attaches listeners to the buttons on the GameOverScreen.
   * 
   * @param backToWelcomeScreenButton The button used to go back to the
   *                                  WelcomeScreen.
   */
  public void attachGameOverScreenListeners(Button backToChooseMapScreen) {
    backToChooseMapScreen.addListener(buttonHandler.toMapScreenListener());
  }

  /**
   * Attaches listeners to the buttons on the ChooseMapScreen.
   * 
   * @param map1Button The button used to select map 1.
   * @param map2Button The button used to select map 2.
   * @param playButton The button used to start the game.
   */
  public void attachChooseMapScreenListeners(Button map1Button, Button map2Button, Button playButton) {

    map1Button.addListener(buttonHandler.selectMapListener("map1", playButton, map1Button, map2Button));
  
    if (model.isComplete("map1")) {
      System.out.println("Map 1 is complete!");
      map2Button.addListener(buttonHandler.selectMapListener("map2", playButton, map1Button, map2Button));
    }
    playButton.addListener(buttonHandler.playButtonListener());
  }

  /**
   * Attaches listeners to the buttons on the CompletedMapScreen.
   * 
   * @param backToChooseMapScreenButton The button uesd to go back to the
   *                                    ChooseMapScreen.
   */
  public void attachCompletedMapScreenListeners(Button backToChooseMapScreenButton) {
    backToChooseMapScreenButton.addListener(buttonHandler.toMapScreenListener());
  }

  @Override
  public boolean keyDown(int keycode) {
    if (!model.getGameState().equals(GameState.ACTIVE_GAME)) {
      return false;
    }
    PlayerType player = getPlayer(keycode);

    switch (keycode) {
      case Keys.UP, Keys.W -> model.changeDir(player, MovementType.UP);
      case Keys.LEFT, Keys.A -> model.changeDir(player, MovementType.LEFT);
      case Keys.RIGHT, Keys.D -> model.changeDir(player, MovementType.RIGHT);
    }
    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    if (model.getGameState().equals(GameState.ACTIVE_GAME)) {
      PlayerType player = getPlayer(keycode);

      switch (keycode) {
        case Keys.LEFT:
        case Keys.RIGHT:
        case Keys.A:
        case Keys.D:
          model.changeDir(player, MovementType.STOP);
          return true;
        default:
          return false;
      }
    }
    return false;
  }

  /**
   * Returns the player assigned to the given key input.
   * WASD controls Player 2, Arrows control Player 1.
   */
  private PlayerType getPlayer(int keycode) {
    return switch (keycode) {
      case Keys.W, Keys.A, Keys.D -> playerTwo;
      case Keys.UP, Keys.LEFT, Keys.RIGHT -> playerOne;
      default -> null;
    };
  }

  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  @Override
  public boolean scrolled(float amountX, float amountY) {
    return false;
  }
}
