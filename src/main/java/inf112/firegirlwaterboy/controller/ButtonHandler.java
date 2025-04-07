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

public class ButtonHandler {

  private final Controller controller;
  private final IControllableModel model;
  private String selectedMapName = null;

  public ButtonHandler(Controller controller, IControllableModel model) {
    this.controller = controller;
    this.model = model;
  }

  /**
   * Returns a ClickListener for selecting a player
   */
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

  /**
   * Returns a ClickListener that changes gameState to CHOOSE_MAP
   */
  public ClickListener getToChooseMapListener() {
    return new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        controller.continueIfPlayersSelected();
      }
    };
  }

  /**
   * Returns a ClickListener that changes gameStare to HELP
   */
  public ClickListener getToHelpListener() {
    return new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        model.setGameState(GameState.HELP);
      }
    };
  }

  /**
   * Returns a ClickListener that changes gameState to WELCOME
   */
  public ClickListener getToWelcomeListener() {
    return new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        model.setGameState(GameState.WELCOME);
      }
    };
  }

  /**
   * Returns a ClickListener for selecting a map
   */
  public ClickListener getSelectMapListener(String mapName, Button map1button, Button map2button) {
    return new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        selectedMapName = mapName;

        // Reset color
        map1button.setColor(Color.WHITE);
        map2button.setColor(Color.WHITE);

        // Mark the selected button
        e.getListenerActor().setColor(Color.GRAY);
      }
    };
  }

  /**
   * Returns a ClickListener that changes gameState to ACTIVE_GAME if a map is
   * selected
   */
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
