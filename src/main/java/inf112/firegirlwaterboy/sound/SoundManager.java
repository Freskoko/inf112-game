package inf112.firegirlwaterboy.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager implements ISoundManager {

    private Sound deathSound;
    private Sound diamondSound;
    private Sound mapCompleteSound;
    private Music backgroundMusic;

    public SoundManager() {
        deathSound = Gdx.audio.newSound(Gdx.files.internal("assets/sound/Death.ogg"));
        diamondSound = Gdx.audio.newSound(Gdx.files.internal("assets/sound/Diamond.ogg"));
        mapCompleteSound = Gdx.audio.newSound(Gdx.files.internal("assets/sound/GameWin.ogg"));
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/sound/BackgroundMusic.ogg"));

        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.3f);
        backgroundMusic.play();
    }

    @Override
    public void playDeathSound() {
        deathSound.play(1.0f);
    }

    @Override
    public void playDiamondSound() {
        diamondSound.play(1.0f);
    }

    @Override
    public void playMapCompleteSound() {
        mapCompleteSound.play(1.0f);
    }

    @Override
    public void dispose() {
        deathSound.dispose();
        diamondSound.dispose();
        backgroundMusic.dispose();
        mapCompleteSound.dispose();
    }
}
