package inf112.firegirlwaterboy.controller;

import com.badlogic.gdx.scenes.scene2d.ui.Button;

import inf112.firegirlwaterboy.model.types.PlayerType;

public interface IController {

  /**
   * This method assigns a player type to either
   * Player One or Player Two.
   * A player type can only be assigned if it is not already taken by the other
   * player.
   * 
   * @param playerType  The type of player to assign (FireGirl or WaterBoy).
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
   * Attaches listeners to the buttons on the GameOverScreen.
   * 
   * @param backToWelcomeScreenButton The button used to go back to the
   *                                  WelcomeScreen.
   */

  void attachToActiveListener(Button play);

  void attachToWelcomeListeners(Button back);

  /**
   * Attaches listeners to the buttons on the CompletedMapScreen.
   * 
   * @param back The button uesd to go back to the
   *             ChooseMapScreen.
   */
  void attachToChooseMapsListener(Button back);

  /**
   * Attaches listeners to the buttons on the ChooseMapScreen.
   * 
   * @param map1Button The button used to select map 1.
   * @param map2Button The button used to select map 2.
   * @param playButton The button used to start the game.
   */

  void attachChooseMapListeners(Button map1Button, Button map2Button);

  void attachSelectPlayerListener(Button button, boolean isPlayerOne, PlayerType type);

  void attachToHelpListener(Button help);

}
