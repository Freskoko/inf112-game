package inf112.firegirlwaterboy.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager implements ISoundManager {

    private Sound mapCompleteSound;
    private Music backgroundMusic;

    public SoundManager() {
        mapCompleteSound = Gdx.audio.newSound(Gdx.files.internal("assets/sound/GameWin.ogg"));
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/sound/BackgroundMusic.ogg"));

        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.3f);
        backgroundMusic.play();
    }

    @Override
    public void playMapCompleteSound() {
        mapCompleteSound.play(1.0f);
    }

    @Override
    public void dispose() {
        backgroundMusic.dispose();
        mapCompleteSound.dispose();
    }
}
