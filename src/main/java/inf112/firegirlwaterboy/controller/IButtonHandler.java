package inf112.firegirlwaterboy.controller;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import inf112.firegirlwaterboy.model.types.PlayerType;

/**
 * IButtonHandler interface defines the methods for handling buttons in the
 * game.
 */
public interface IButtonHandler {

    /**
     * Returns a ClickListener for selecting a player
     */
    public ClickListener getSelectPlayerListener(boolean isPlayerOne, PlayerType type);

    /**
     * Returns a ClickListener that changes gameState to CHOOSE_MAP
     */
    public ClickListener getToChooseMapListener();

    /**
     * Returns a ClickListener that changes gameState to HELP
     */
    public ClickListener getToHelpListener();

    /**
     * Returns a ClickListener that changes gameState to WELCOME
     */
    public ClickListener getToWelcomeListener();

    /**
     * Returns a ClickListener for selecting a map
     */
    public ClickListener getSelectMapListener(String mapName, Button map1button, Button map2button);

    /**
     * Returns a ClickListener that changes gameState to ACTIVE_GAME if a map is
     * selected
     */
    public ClickListener getToActiveListener();
}
