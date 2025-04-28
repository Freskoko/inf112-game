package inf112.firegirlwaterboy.sound;

/**
 * PlayerSoundManager class for the game FireGirl & WaterBoy.
 * Handles loading, playing and disposing of player related sounds.
 */
public interface IPlayerSoundManager {

    /**
     * Plays the death sound.
     */
    void playDeathSound();

    /**
     * Plays the diamond collection sound.
     */
    void playDiamondSound();

    /**
     * Disposes all sound resources.
     */
    void dispose();

}