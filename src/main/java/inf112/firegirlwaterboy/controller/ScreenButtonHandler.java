package inf112.firegirlwaterboy.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.entity.PlayerType;

/**
 * ScreenButtonHandler class handles button logic for the different screens in
 * the game.
 */

public class ScreenButtonHandler {

  private final Controller controller;

  public ScreenButtonHandler(Controller controller) {
    this.controller = controller;
  }

  // WELCOME SCREEN BUTTONS

  /**
   * Returns a ClickListener for selecting a player on welcomescreen.
   */
  public ClickListener selectPlayerListener(int playerNum, PlayerType type) {
    return new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        boolean isPlayerOne = (playerNum == 1);
        controller.selectPlayer(type, isPlayerOne);
        e.getListenerActor().setColor(Color.GRAY);
      }
    };
  }

  /**
   * Returns a ClickListener for the start button on welcomescreen.
   */
  public ClickListener startButtonListener() {
    return new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        controller.startGameIfPlayersSelected();
        System.out.println("Start clicked");
      }
    };
  }

  /**
   * Returns a ClickListener for the help button on welcomescreen.
   */
  public ClickListener helpButtonListener() {
    return new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        controller.getModel().setGameState(GameState.HELP);
      }
    };
  }

  // HELP SCREEN BUTTON

  /**
   * Returns a ClickListener for the back button on helpscreen.
   */
  public ClickListener backButtonListener() {
    return new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        controller.getModel().setGameState(GameState.WELCOME);
        System.out.println("Back clicked");
      }
    };
  }

  // CHOOSE MAP SCREEN BUTTON

  /**
   * Returns a ClickListener for the play button on choosemapscreen.
   */
  public ClickListener playButtonListener() {
    return new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        controller.getModel().setGameState(GameState.ACTIVE_GAME);
      }
    };
  }
}
