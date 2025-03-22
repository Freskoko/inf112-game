package inf112.firegirlwaterboy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.entity.PlayerType;
import inf112.firegirlwaterboy.view.WelcomeScreen;

/**
 * Controller class for the game FireGirl & WaterBoy.
 * This class is responsible for handling input from the player and updating the
 * model accordingly.
 */
public class Controller implements InputProcessor {

  private IControllableModel model;
  private PlayerType playerOne; // Controlled by Arrow keys
  private PlayerType playerTwo; // Controlled by WASD keys
  TextButton playButton;

  /**
   * Constructor for the Controller class.
   * 
   * @param model the model to be controlled.
   */
  public Controller(IControllableModel model) {
    this.model = model;
    this.playerOne = null;
    this.playerTwo = null;
  }

  @Override
  public boolean keyDown(int keycode) {
    switch (model.getGameState()) {
      case ACTIVE_GAME -> handleActiveGameState(keycode);
    }
    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    PlayerType player = getPlayer(keycode);
    switch (keycode) {
      case Keys.LEFT, Keys.RIGHT, Keys.A, Keys.D -> model.changeDir(player, MovementType.STOP);
    }
    return true;
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

  /**
   * Handles key inputs in the ACTIVE_GAME state.
   * Moves the corresponding player based on the key pressed.
   *
   * @param keycode the key pressed
   */
  private void handleActiveGameState(int keycode) {
    PlayerType player = getPlayer(keycode);

    switch (keycode) {
      case Keys.UP, Keys.W -> model.changeDir(player, MovementType.JUMP);
      case Keys.LEFT, Keys.A -> model.changeDir(player, MovementType.LEFT);
      case Keys.RIGHT, Keys.D -> model.changeDir(player, MovementType.RIGHT);
    }
  }

  /**
 * Assigns a player type to Player 1 (WASD) or Player 2 (Arrows).
 * Ensures Player 1 and Player 2 select different types.
 *
 * @param playerType the selected player type
 * @param isPlayerOne true if selecting for Player 1, false for Player 2
 */
private void selectPlayer(PlayerType playerType, boolean isPlayerOne) {
  if (isPlayerOne) {
    if (playerTwo != null && playerType == playerTwo) {
      System.out.println("Player 1 kan ikke velge samme figur som Player 2!");
      return;
    }
    playerOne = playerType;
  } else {
    if (playerOne != null && playerType == playerOne) {
      System.out.println("Player 2 kan ikke velge samme figur som Player 1!");
      return;
    }
    playerTwo = playerType;
  }
}


  /**
   * Returns the player associated with the given key press.
   * WASD controls Player 1, Arrows control Player 2.
   *
   * @param keycode the key pressed
   * @return the corresponding player, or null if invalid key
   */
  private PlayerType getPlayer(int keycode) {
    return switch (keycode) {
      case Keys.W, Keys.A, Keys.D -> playerTwo;
      case Keys.UP, Keys.LEFT, Keys.RIGHT -> playerOne;
      default -> null;
    };
  }

  /**
   * Starts the game if both players are selected, otherwise prompts selection.
   */
  private void startGameIfPlayersSelected() {
    if (playerOne != null || playerTwo != null) {
      model.setGameState(GameState.CHOOSE_MAP);
      model.addPlayer(playerOne);
      model.addPlayer(playerTwo);
    } else {
      System.out.println("Please select playerType for both players.");
    }

  }

  // WELCOME SCREEN BUTTONS
  // WelcomeScreen, Attach listeners to buttons on WelcomeScreen
  public void attachWelcomeScreenListeners(Button fgP1, Button wbP1, Button fgP2, Button wbP2, Button start,
      Button help) {
    fgP1.addListener(selectPlayerListener(1, PlayerType.FIREGIRL));
    wbP1.addListener(selectPlayerListener(1, PlayerType.WATERBOY));
    fgP2.addListener(selectPlayerListener(2, PlayerType.FIREGIRL));
    wbP2.addListener(selectPlayerListener(2, PlayerType.WATERBOY));
    start.addListener(startButtonListener());
    help.addListener(helpButtonListener());
  }

  // WelcomeScreen, Help button on welcomeScreen, directs to help screen
  private ClickListener helpButtonListener() {
    return new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        model.setGameState(GameState.HELP);
      }
    };
  }

  /* WelcomeScreen, Handling button on WelcomeScreen where user select player */
private ClickListener selectPlayerListener(int playerNum, PlayerType type) {
  return new ClickListener() {
    @Override
    public void clicked(InputEvent e, float x, float y) {
      boolean isPlayerOne = (playerNum == 1);
      selectPlayer(type, isPlayerOne);
    }
  };
}

  /*
   * WelcomeScreen,
   * Handling start button on WelcomeScreen. Start buttons sends the user to
   * ChooseMapsScreen
   */
  private ClickListener startButtonListener() {
    return new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        startGameIfPlayersSelected();
      }
    };
  }

  // HELP SCREEN BUTTONS

  // HelpScreen, Back button on helpScreen, directs to welcome screen
  public void attachHelpScreenListeners(Button back) {
    back.addListener(backButtonListener());
  }

  // HelpScreen, Handles click from back button on HelpScreen
  private ClickListener backButtonListener() {
    return new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        model.setGameState(GameState.WELCOME);
        System.out.println("back trykket");
      }
    };
  }

  // CHOOSE MAP SCREEN BUTTONS

  /**
   * ChooseMapScreen,
   * Handles button clicks in the ChooseMapScreen.
   * 
   * @param playButton the play button
   */
  public void setupPlayButtonListener(Button playButton) {
    playButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        handlePlayButtonClicked();
      }
    });
  }

  /* ChooseMapScreen, Handles play button in ChooseMapScreen */
  private void handlePlayButtonClicked() {
    model.setGameState(GameState.ACTIVE_GAME);
  }

}
