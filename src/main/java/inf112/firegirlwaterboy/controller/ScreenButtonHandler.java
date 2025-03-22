package inf112.firegirlwaterboy.controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.entity.PlayerType;

public class ScreenButtonHandler {

    private final Controller controller;
  
    public ScreenButtonHandler(Controller controller) {
      this.controller = controller;
    }
  
    // WELCOME SCREEN BUTTONS
  
    public ClickListener selectPlayerListener(int playerNum, PlayerType type) {
      return new ClickListener() {
        @Override
        public void clicked(InputEvent e, float x, float y) {
          boolean isPlayerOne = (playerNum == 1);
          controller.selectPlayer(type, isPlayerOne);
        }
      };
    }
  
    public ClickListener startButtonListener() {
      return new ClickListener() {
        @Override
        public void clicked(InputEvent e, float x, float y) {
          controller.startGameIfPlayersSelected();
        }
      };
    }
  
    public ClickListener helpButtonListener() {
      return new ClickListener() {
        @Override
        public void clicked(InputEvent e, float x, float y) {
          controller.getModel().setGameState(GameState.HELP);
        }
      };
    }
  
    // HELP SCREEN BUTTON
  
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
  
    public ClickListener playButtonListener() {
      return new ClickListener() {
        @Override
        public void clicked(InputEvent e, float x, float y) {
          controller.getModel().setGameState(GameState.ACTIVE_GAME);
        }
      };
    }
  }
  
