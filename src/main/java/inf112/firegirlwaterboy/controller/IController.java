package inf112.firegirlwaterboy.controller;

import com.badlogic.gdx.scenes.scene2d.ui.Button;

import inf112.firegirlwaterboy.model.types.PlayerType;

/**
 * IController interface defines the methods for handling game logic and button
 * interactions.
 */
public interface IController {

  /**
   * This method assigns a PlayerType to either
   * Player One or Player Two.
   * A player type can only be assigned if it is not already taken by the other
   * player.
   * 
   * @param playerType  The type of player to assign.
   * @param isPlayerOne A boolean indicating whether the player is Player One
   *                    (true) or Player Two (false).
   * @return {@code true} if the player type was successfully assigned;
   *         {@code false} otherwise.
   */
  boolean selectPlayer(PlayerType playerType, boolean isPlayerOne);

  /**
   * Starts the game if both players have selected a playerType.
   */
  void continueIfPlayersSelected();

  /**
   * Attaches listener to the button that starts the active game.
   * 
   * @param play Button used to start the game, sets gameState to ACTIVE_GAME
   */
  void attachToActiveListener(Button play);

  /**
   * Attaches listeners to the map buttons on the ChooseMapScreen.
   * 
   * @param map1Button The button used to select map 1.
   * @param map2Button The button used to select map 2.
   */
  void attachChooseMapListeners(Button map1Button, Button map2Button);

  /**
   * Attaches listeners to the buttons that returns to WelcomeScreen.
   * 
   * @param back Button used to go back to the
   *             WelcomeScreen.
   */
  void attachToWelcomeListeners(Button back);

  /**
   * Attaches a listener to buttons that returns to the ChooseMapScreen.
   * 
   * @param back The button uesd to go to the
   *             ChooseMapScreen.
   */
  void attachReturnToChooseMapsListener(Button back);

  /**
   * Attaches listeners to the buttons on the WelcomeScreen where you can choose
   * playerType.
   * 
   * @param button      The button for choosing PlayerType
   * @param isPlayerOne Boolean that is true for player one and false for player
   *                    two.
   * @param type        the PlayerType to be assigned to the player.
   */
  void attachSelectPlayerListener(Button button, boolean isPlayerOne, PlayerType type);

  /**
   * Attaches listeners to the button that returns to helpScreen
   * 
   * @param help The button used to go to the help screen.
   */
  void attachToHelpListener(Button help);

  /**
   * 
   * @param mapName The name of the map to check.
   * @return Boolean if the map is complete or not.
   */
  boolean isMapComplete(String mapName);

}
