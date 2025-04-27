package inf112.firegirlwaterboy.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.types.PlayerType;

/**
 * ScreenButtonHandler class handles button logic for the different screens in
 * the game.
 */

public class ButtonHandler implements IButtonHandler {

  private final Controller controller;
  private final IControllableModel model;
  private String selectedMapName = null;

  public ButtonHandler(Controller controller, IControllableModel model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public ClickListener getSelectPlayerListener(boolean isPlayerOne, PlayerType type) {
    return new ClickListener() {
      @Override

      public void clicked(InputEvent e, float x, float y) {
        if (controller.selectPlayer(type, isPlayerOne)) {
          e.getListenerActor().setColor(Color.GRAY);
        }
      }
    };
  }

  @Override
  public ClickListener getToChooseMapListener() {
    return new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        controller.continueIfPlayersSelected();
      }
    };
  }

  @Override
  public ClickListener getToHelpListener() {
    return new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        model.setGameState(GameState.HELP);
      }
    };
  }

  @Override
  public ClickListener getToWelcomeListener() {
    return new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        model.setGameState(GameState.WELCOME);
      }
    };
  }

  @Override
  public ClickListener getSelectMapListener(String mapName, Button map1button, Button map2button) {
    return new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        selectedMapName = mapName;

        map1button.setColor(Color.WHITE);
        map2button.setColor(Color.WHITE);

      }
    };
  }

  @Override
  public ClickListener getToActiveListener() {
    return new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        if (selectedMapName != null) {
          model.setMap(selectedMapName);
          model.setGameState(GameState.ACTIVE_GAME);
          selectedMapName = null;
        }
      }
    };
  }
}
