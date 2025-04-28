package inf112.firegirlwaterboy.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class PlayerSoundManager implements IPlayerSoundManager {

    private Sound deathSound;
    private Sound diamondSound;

    public PlayerSoundManager() {
        diamondSound = Gdx.audio.newSound(Gdx.files.internal("assets/sound/Diamond.ogg"));
        deathSound = Gdx.audio.newSound(Gdx.files.internal("assets/sound/Death.ogg"));
    }

    @Override
    public void playDiamondSound() {
        diamondSound.play(1.0f);
    }

    @Override
    public void playDeathSound() {
        deathSound.play(1.0f);
    }

    @Override
    public void dispose() {
        diamondSound.dispose();
        deathSound.dispose();
    }
}
