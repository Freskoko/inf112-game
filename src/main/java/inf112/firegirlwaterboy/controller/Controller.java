package inf112.firegirlwaterboy.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import inf112.firegirlwaterboy.model.Entity.PlayerType;

public class Controller implements InputProcessor{

  private IControllableModel model;

  public Controller(IControllableModel model) {
    this.model = model;
  }

  @Override
  public boolean keyDown(int keycode) {
    switch ( keycode) {
      case Keys.UP:
        // Må oppdatere logikk slik at den ikke hopper mens figur er i "luften"

        // model.changeVelocity(player 1, 0, -1);
        model.changeVelocity(PlayerType.FIREGIRL, 0, -1);
        break;
      case Keys.LEFT:
        model.changeVelocity(PlayerType.FIREGIRL, -1, 0);
        break;
      case Keys.RIGHT:
        model.changeVelocity(PlayerType.FIREGIRL, 1, 0);
        break;
    }
    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    switch ( keycode) {
      case Keys.LEFT:
        model.changeVelocity(PlayerType.FIREGIRL, 0, 0);
        break;
      case Keys.RIGHT:
        model.changeVelocity(PlayerType.FIREGIRL, 0, 0);
        break;
    }
    return true;
  }

  @Override
  public boolean keyTyped(char character) {return false;}

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {return false;}

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {return false;}

  @Override
  public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {return false;}

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {return false;}

  @Override
  public boolean mouseMoved(int screenX, int screenY) {return false;}

  @Override
  public boolean scrolled(float amountX, float amountY) {return false;}
}
