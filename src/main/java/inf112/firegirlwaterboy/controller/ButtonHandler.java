package inf112.firegirlwaterboy.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.entity.types.PlayerType;

/**
 * ScreenButtonHandler class handles button logic for the different screens in
 * the game.
 */

public class ButtonHandler implements IButtonHandler {

  private final Controller controller;
  private final IControllableModel model;
  private String selectedMapName = null;
  private ImageButton selectedButton = null;

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

        ImageButton clickedButton = (ImageButton) e.getListenerActor();

        if (selectedButton != null && selectedButton != clickedButton) {
          TextureRegionDrawable[] textures = (TextureRegionDrawable[]) selectedButton.getUserObject();
          ((ImageButton.ImageButtonStyle) selectedButton.getStyle()).imageUp = textures[0];
        }

        TextureRegionDrawable[] clickedTextures = (TextureRegionDrawable[]) clickedButton.getUserObject();
        ((ImageButton.ImageButtonStyle) clickedButton.getStyle()).imageUp = clickedTextures[1];

        selectedButton = clickedButton;
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
