package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.firegirlwaterboy.controller.Controller;

/**
 * ChooseMapScreen class represents the screen where the player can choose a
 * map.
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

        viewport = new FitViewport(800, 600);
        stage = new Stage(viewport);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2f);

        setupUI();
    }

    // Set up the UI for the choose map screen
    private void setupUI() {
        Table table = new Table();
        table.setFillParent(true);

        Button playButton = createButton("Play", Color.DARK_GRAY);

        // Sending button logic to controller
        controller.setupPlayButtonListener(playButton);

        table.add(playButton).size(200, 60).center();
        stage.addActor(table);
    }

    // Method for creating a button with a given text and color
    private Button createButton(String text, Color color) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.fontColor = Color.WHITE;

        TextButton button = new TextButton(text, style);
        button.setColor(color);
        return button;
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
        font.draw(batch, "Choose Level:", 320, 550);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

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
