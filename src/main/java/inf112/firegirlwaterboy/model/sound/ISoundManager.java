package inf112.firegirlwaterboy.model.sound;

/**
 * SoundManager class for the game FireGirl & WaterBoy.
 * Handles loading, playing and disposing of game sounds.
 */
public interface ISoundManager {

  /**
   * Plays the map complete sound.
   */
  void playMapCompleteSound();

  /**
   * Disposes all sound resources.
   */
  void dispose();
}