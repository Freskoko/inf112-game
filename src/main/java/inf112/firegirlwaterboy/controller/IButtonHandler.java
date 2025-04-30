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
     * Creates a ClickListener for selecting a player.
     * If the selection is valid (not already taken), the button is marked as
     * selected.
     *
     * @param isPlayerOne Boolean indicating if the player is player one or two
     * @param type        The type of player (FIREGIRL or WATERBOY)
     * @return a clicklistener for handling player selection
     */
    public ClickListener getSelectPlayerListener(boolean isPlayerOne, PlayerType type);

    /**
     * If both players have selected a playerType, gamestate will change to
     * CHOOSE_MAP
     *
     * @return a clicklistener for handlig map selection
     */
    public ClickListener getToChooseMapListener();

    /**
     * Changes the gamestate to HELP
     *
     * @return a clicklistener that changes the gamestate to HELP
     */
    public ClickListener getToHelpListener();

    /**
     * Changes the gamestate to WELCOME
     *
     * @return a clicklistener that changes the gamestate to WELCOME
     */
    public ClickListener getToWelcomeListener();

    /**
     * Buttons used to select map
     * Updates the button visuals to reflect which map is selected.
     *
     * @param mapName    The name of the map to set
     * @param map1button The button used to select map 1
     * @param map2button The button used to select map 2
     * @return the clicklistener for the buttons
     */
    public ClickListener getSelectMapListener(String mapName, Button map1button, Button map2button);

    /**
     * Sets the gamestate to ACTIVE_GAME and starts the game (only if a map has been
     * selected).
     *
     * @return a clicklistener that starts the game
     */
    public ClickListener getToActiveListener();
}
