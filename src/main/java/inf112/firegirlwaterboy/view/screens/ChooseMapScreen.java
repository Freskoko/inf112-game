package inf112.firegirlwaterboy.view.screens;

import javax.annotation.processing.Generated;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.firegirlwaterboy.controller.Controller;

/**
 * ChooseMapScreen class represents the screen where the player can choose a
 * map.
 */
public class ChooseMapScreen implements Screen {

    private Controller controller;
    private Stage stage;
    private Viewport viewport;
    private BitmapFont font;
    private SpriteBatch batch;
    private Texture chooseMapTextTexture;
    private Button playButton;

    public ChooseMapScreen(Controller controller) {
        this.controller = controller;
        viewport = new ScreenViewport();
        stage = new Stage(viewport);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2f);

        chooseMapTextTexture = new Texture(
                Gdx.files.internal("assets/pages/ChooseMapText.png"));

        setupUI();
    }

    private void setupUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.center().top().padTop(300);

        Button map1Button = createButton("Map 1", Color.DARK_GRAY);
        Button map2Button = createButton("Map 2", Color.DARK_GRAY);
        map1Button.setName("Map1");
        playButton = createImageButton("assets/pages/PlayButton.png", 300, 100);
        playButton.setDisabled(true);

        // Koble knappene til controller
        controller.attachChooseMapListeners(map1Button, map2Button);
        controller.attachToActiveListener(playButton);

        // Legg map-knappene på samme rad
        table.add(map1Button).size(200, 60).padRight(20);
        table.add(map2Button).size(200, 60).padLeft(20);
        table.row().padTop(300);

        // Play-knappen under
        table.add(playButton).colspan(2).size(360, 120).center();

        stage.addActor(table);
    }

    private Button createButton(String text, Color color) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        BitmapFont buttonFont = new BitmapFont();
        buttonFont.getData().setScale(2f);
        style.font = buttonFont;
        style.fontColor = Color.WHITE;

        Pixmap pixmapUp = new Pixmap(300, 80, Pixmap.Format.RGBA8888);
        pixmapUp.setColor(color);
        pixmapUp.fill();

        style.up = new TextureRegionDrawable(new Texture(pixmapUp));
        pixmapUp.dispose();

        return new TextButton(text, style);
    }

    private Button createImageButton(String imagePath, int width, int height) {
        Texture texture = new Texture(Gdx.files.internal(imagePath));
        TextureRegionDrawable drawable = new TextureRegionDrawable(texture);

        ImageButtonStyle style = new ImageButtonStyle();
        style.imageUp = drawable;
        style.imageDown = drawable;

    ImageButton button = new ImageButton(style);
    button.setSize(360,120); 

    return button;
}

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0x91 / 255f, 0x8b / 255f, 0x75 / 255f, 1f);  // Hex color #918b75

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(
                chooseMapTextTexture,
                Gdx.graphics.getWidth() / 2f - chooseMapTextTexture.getWidth() / 2f,
                Gdx.graphics.getHeight() - chooseMapTextTexture.getHeight() - 20);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    @Generated("interface-stub")
    public void pause() {
    }

    @Override
    @Generated("interface-stub")
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
        chooseMapTextTexture.dispose();

    }
}
