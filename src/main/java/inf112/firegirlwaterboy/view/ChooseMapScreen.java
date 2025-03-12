package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.firegirlwaterboy.controller.Controller;
//import inf112.firegirlwaterboy.controller.IControllableModel;

/**
 * Class for the screen where the player chooses a map/level.
 * The player must have completed the level before to choose the next one.
 */
public class ChooseMapScreen implements Screen {
    private IViewModel model;
    private Controller controller;
    private Stage stage;
    private Viewport viewport;
    private BitmapFont font;
    private SpriteBatch batch;

    public ChooseMapScreen(IViewModel model, Controller controller) {
        this.model = model;
        this.controller = controller;

        this.viewport = new FitViewport(800, 600);
        this.stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2f);

        // Kaller controller for å sette opp UI og håndtere logikk mtp buttons
        controller.setupChooseMapUI(stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Choose Level:", 300, 550);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        batch.dispose();
    }
}
