package inf112.firegirlwaterboy.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * SoundManager class for the game FireGirl & WaterBoy.
 * Handles loading, playing and disposing of game sounds. Diamond sound is not used yet. 
 */
public class SoundManager {

    private Sound deathSound;
    private Music backgroundMusic;
    private Sound diamondSound;

    public SoundManager() {
        deathSound = Gdx.audio.newSound(Gdx.files.internal("assets/sound/Death.ogg"));
        diamondSound = Gdx.audio.newSound(Gdx.files.internal("assets/sound/Diamond.ogg"));
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/sound/BackgroundMusic.ogg"));

        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.3f);
        backgroundMusic.play();
    }

    /**
     * Plays the death sound.
     */
    public void playDeathSound() {
        deathSound.play(1.0f);
    }

    /**
     * Plays the diamond collection sound.
     */
    public void playDiamondSound() {
        diamondSound.play(1.0f);
    }

    /**
     * Disposes all sound resources.
     */
    public void dispose() {
        deathSound.dispose();
        diamondSound.dispose();
        backgroundMusic.dispose();
    }
}
