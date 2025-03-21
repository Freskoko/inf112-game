package inf112.firegirlwaterboy.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.entity.PlayerType;

/**
 * Controller class for the game FireGirl & WaterBoy.
 * This class is responsible for handling input from the player and updating the
 * model accordingly.
 */
public class Controller implements InputProcessor {

  private IControllableModel model;
  private PlayerType playerOne; // Controlled by WASD keys
  private PlayerType playerTwo; // Controlled by Arrow keys

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
      case Keys.UP, Keys.W -> model.changeDir(player, MovementType.UP);
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
    } else if (playerType != playerOne && playerTwo == null) {
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
      case Keys.W, Keys.A, Keys.D -> playerOne;
      case Keys.UP, Keys.LEFT, Keys.RIGHT -> playerTwo;
      default -> null;
    };
  }

  /**
   * Starts the game if both players are selected, otherwise prompts selection.
   */
  private void startGameIfPlayersSelected() {
    if (playerOne != null || playerTwo != null) {
      model.setGameState(GameState.ACTIVE_GAME);
      model.addPlayer(playerOne);
      model.addPlayer(playerTwo);
    } else {
      System.out.println("Please select playerType for both players.");
    }
  }
}
