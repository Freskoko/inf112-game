package inf112.firegirlwaterboy.sound;

/**
 * SoundManager class for the game FireGirl & WaterBoy.
 * Handles loading, playing and disposing of game sounds. Diamond sound is not used yet. 
 */
public interface ISoundManager {

    /**
     * Plays the death sound.
     */
    void playDeathSound();

    /**
     * Plays the diamond collection sound.
     */
    void playDiamondSound();

    /**
     * Plays the map complete sound.
     */
    void playMapCompleteSound();

    /**
     * Disposes all sound resources.
     */
    void dispose();

}