package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import inf112.firegirlwaterboy.controller.Controller;
import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.controller.IControllableModel;

public class WelcomeScreen implements Screen {

    private SpriteBatch batch;
    private BitmapFont font;
    private IControllableModel model;
    private Controller controller;

    public WelcomeScreen(IControllableModel model, Controller controller) {
        this.model = model;
        this.controller = controller;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        batch.begin();
        font.draw(batch, "WELCOME TO FIREGIRL & WATERBOY", 100, 300);
        font.draw(batch, "Press 'P' to Start", 150, 250);
        batch.end();

        // Check for key press to start game
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            model.setGameState(GameState.ACTIVE_GAME);
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
