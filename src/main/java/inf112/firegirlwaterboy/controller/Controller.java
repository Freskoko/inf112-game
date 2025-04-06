package inf112.firegirlwaterboy.controller;

import com.badlogic.gdx.Input.Keys;

import javax.annotation.processing.Generated;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.types.PlayerType;

/**
 * Controller class for the game FireGirl & WaterBoy.
 * Handles keyboard input and delegates button logic via ScreenButtonHandler.
 */
public class Controller implements IController, InputProcessor {

  private IControllableModel model;
  private PlayerType playerOne; // Controlled by Arrow keys
  private PlayerType playerTwo; // Controlled by WASD keys
  private ButtonHandler bh;

  public Controller(IControllableModel model) {
    this.model = model;
    this.playerOne = null;
    this.playerTwo = null;
    this.bh = new ButtonHandler(this, model);
  }

  public Controller(IControllableModel model, ButtonHandler buttonHandler) {
    this.model = model;
    this.playerOne = null;
    this.playerTwo = null;
    this.bh = buttonHandler;
  }

  @Override
  public boolean selectPlayer(PlayerType playerType, boolean isPlayerOne) {
    if (isPlayerOne) {
      if (playerOne == null && playerType != playerTwo) {
        playerOne = playerType;
        model.addPlayer(playerType);
        return true;
      }
    } else {
      if (playerTwo == null && playerType != playerOne) {
        playerTwo = playerType;
        model.addPlayer(playerType);
        return true;
      }
    }
    return false;
  }

  @Override
  public void continueIfPlayersSelected() {
    if (playerOne != null && playerTwo != null) {
      model.setGameState(GameState.CHOOSE_MAP);
    }
  }

  @Override
  public void attachSelectPlayerListener(Button button, boolean isPlayerOne, PlayerType type) {
    button.addListener(bh.getSelectPlayerListener(isPlayerOne, type));
  }

  @Override
  public void attachToHelpListener(Button help) {
    help.addListener(bh.getToHelpListener());
  }

  @Override
  public void attachToWelcomeListeners(Button button) {
    button.addListener(bh.getToWelcomeListener());
  }

  @Override
  public void attachToActiveListener(Button button) {
    button.addListener(bh.getToActiveListener());
  }

  @Override
  public void attachToChooseMapsListener(Button button) {
    button.addListener(bh.getToChooseMapListener());
  }

  @Override
  public void attachChooseMapListeners(Button map1Button, Button map2Button) {
    map1Button.addListener(bh.getSelectMapListener("map1", map1Button, map2Button));
    if (model.isComplete("map1")) {
      map2Button.addListener(bh.getSelectMapListener("map2", map1Button, map2Button));
    }
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
  @Generated("interface-stub")
  public boolean keyTyped(char character) {
    return false;
  }

  @Override
  @Generated("interface-stub")
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  @Generated("interface-stub")
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  @Generated("interface-stub")
  public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  @Generated("interface-stub")
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  @Override
  @Generated("interface-stub")
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  @Override
  @Generated("interface-stub")
  public boolean scrolled(float amountX, float amountY) {
    return false;
  }
}
