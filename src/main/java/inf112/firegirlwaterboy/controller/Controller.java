package inf112.firegirlwaterboy.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input;
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

  /**
   * Constructor for the Controller class.
   * 
   * @param model the model to be controlled.
   */
  public Controller(IControllableModel model) {
    this.model = model;
  }

  @Override
  public boolean keyDown(int keycode) {
    // Sjekk om spillet er i WELCOME-modus først. Usikker på om dette er den beste
    // måten å gjøre det på?.
    // Evt en hjelpemetode?
    if (model.getGameState() == GameState.WELCOME) {
        if (keycode == Input.Keys.P) {
            model.setGameState(GameState.ACTIVE_GAME); // Start spillet
            return true;
        }
    }

    switch (keycode) {
      case Keys.UP:
        model.changeDir(PlayerType.FIREGIRL, "jump");
        break;
      case Keys.LEFT:
        model.changeDir(PlayerType.FIREGIRL, "left");
        break;
      case Keys.RIGHT:
        model.changeDir(PlayerType.FIREGIRL, "right");
        break;
    }
    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    switch (keycode) {
      case Keys.LEFT:
        model.changeDir(PlayerType.FIREGIRL, "stop");
        break;
      case Keys.RIGHT:
        model.changeDir(PlayerType.FIREGIRL, "stop");
        break;
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
}
