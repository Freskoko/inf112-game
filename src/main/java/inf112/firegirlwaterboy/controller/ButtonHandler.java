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
  private String selectedMapName = null;
  private final IControllableModel model;


  public ButtonHandler(Controller controller, IControllableModel model) {
    this.controller = controller;
    this.model = model;
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
        model.setGameState(GameState.HELP);
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
        model.setGameState(GameState.WELCOME);
      }
    };
  }

  // CHOOSE MAP SCREEN BUTTON

  // CHOOSE MAP SCREEN BUTTONS
  public ClickListener selectMapListener(String mapName, Button playButton, Button map1button, Button map2button) {
    return new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        selectedMapName = mapName;
        playButton.setDisabled(false);
        
        // Reset color
        map1button.setColor(Color.WHITE);
        map2button.setColor(Color.WHITE);
  
        // Mark the selected button
        e.getListenerActor().setColor(Color.GRAY);
      }
    };
  }
  

  // PLAY
  public ClickListener playButtonListener() {
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
  

  // GAME OVER SCREEN BUTTON
  public ClickListener toMapScreenListener() {
    return new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        model.setGameState(GameState.CHOOSE_MAP);
      }
    };
  }
}
