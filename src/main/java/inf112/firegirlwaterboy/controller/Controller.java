package inf112.firegirlwaterboy.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.entity.PlayerType;

/**
 * Controller class for the game FireGirl & WaterBoy.
 * This class is responsible for handling input from the player and updating the
 * model accordingly.
 */
public class Controller implements InputProcessor {

  private IControllableModel model;
  private PlayerType playerOne; // Controlled by Arrow keys
  private PlayerType playerTwo; // Controlled by WASD keys
  Button startButton;
  TextButton fireGirlButton, waterBoyButton, playButton;
  

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
      case WELCOME -> handleWelcomeState(keycode);
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
   * Handles key inputs in the WELCOME state.
   * Allows player selection or game start if both players are chosen.
   *
   * @param keycode the key pressed
   */
  private void handleWelcomeState(int keycode) {
    switch (keycode) {
      case Keys.P -> startGameIfPlayersSelected();
      case Keys.W -> selectPlayer(PlayerType.WATERBOY);
      case Keys.F -> selectPlayer(PlayerType.FIREGIRL);
    }
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
   * Ensures Player 2 is different from Player 1.
   *
   * @param playerType the selected player type
   */
  private void selectPlayer(PlayerType playerType) {
    if (playerOne == null) {
      playerOne = playerType;
      // Setter playerTwo automatisk til motsatt spiller
      playerTwo = (playerOne == PlayerType.FIREGIRL) ? PlayerType.WATERBOY : PlayerType.FIREGIRL;
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

  // endret for welcomescreen

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


  /**
   * Returns the player type for Player 1.
   *
   * @return the player type for Player 1
   */
  public PlayerType getPlayerOne() {
    return playerOne;
  }


//Må fikse med levler, at den forrige må være fullført før neste kan starte
/**
 * Sets up the UI for ChooseMapScreen.java
 * @param stage
 */
public void setupChooseMapUI(Stage stage) {
    playButton = new TextButton("Play", createButtonStyle());
    playButton.setPosition(350, 250);
    playButton.setSize(100, 50);

    playButton.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            model.setGameState(GameState.ACTIVE_GAME);
        }
    });

    stage.addActor(playButton);
}

/**
 * Creates a TextButtonStyle for the buttons.
 * @return the TextButtonStyle
 */
private TextButton.TextButtonStyle createButtonStyle() {
    TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
    textButtonStyle.font = new BitmapFont();
    textButtonStyle.fontColor = Color.WHITE;
    return textButtonStyle;
}

  /**
   * Sets up the UI for the WelcomeScreen.java.
   *
   * @param stage the stage to add the UI elements to
   */
public void setUWelcomeScreenUI(Stage stage) {
    startButton = new TextButton("Start Game", createButtonStyle());
    fireGirlButton = new TextButton("Select FireGirl", createButtonStyle());
    waterBoyButton = new TextButton("Select WaterBoy", createButtonStyle());

    fireGirlButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        selectPlayer(PlayerType.FIREGIRL);
        updateButtonStylesWelcomeScreen();
      }
    });

    waterBoyButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        selectPlayer(PlayerType.WATERBOY);
        updateButtonStylesWelcomeScreen();
      }
    });

    startButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        startGameIfPlayersSelected();
      }
    });

    Table table = new Table();
    table.setFillParent(true);
    table.center();

    table.add(fireGirlButton).size(200, 80).padBottom(20).row();
    table.add(waterBoyButton).size(200, 80).row();
    table.add(startButton).size(200, 80).padBottom(20).row();

    stage.addActor(table);
  }

  /**
   * Updates the button styles to reflect the selected player in WelcomeScreen.
   */
  private void updateButtonStylesWelcomeScreen() {
    fireGirlButton.getStyle().fontColor = (playerOne == PlayerType.FIREGIRL) ? Color.RED : Color.GRAY;
    waterBoyButton.getStyle().fontColor = (playerOne == PlayerType.WATERBOY) ? Color.BLUE : Color.GRAY;
  }

}
