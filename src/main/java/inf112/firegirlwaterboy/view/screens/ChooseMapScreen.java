package inf112.firegirlwaterboy.view.screens;

import javax.annotation.processing.Generated;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.firegirlwaterboy.controller.Controller;
import inf112.firegirlwaterboy.view.ButtonDesigner;

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
    private Button playButton;
    private Texture backgroundTexture;

    public ChooseMapScreen(Controller controller) {
        this.controller = controller;
        viewport = new ScreenViewport();
        stage = new Stage(viewport);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2f);

        backgroundTexture = new Texture(Gdx.files.internal("assets/pages/ChooseMap.png"));

        setupUI();
    }

    private void setupUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.center().top().padTop(350);

        Button map1Button = createImageButton("assets/pages/map1button.png", 350, 210);
        map1Button.setName("map1");

        Button map2Button;
        if (controller.isMapComplete("map1")) {
            map2Button = createImageButton("assets/pages/map2button.png", 350, 210);
            map2Button.setDisabled(false);
        } else {
            map2Button = createImageButton("assets/pages/map2ButtonDark.png", 350, 210);
            map2Button.setDisabled(true);
        }
        map2Button.setName("map2");

        playButton = ButtonDesigner.createButton("Play", Color.valueOf("#607d4d"));
        playButton.setDisabled(true);

        controller.attachChooseMapListeners(map1Button, map2Button);
        controller.attachToActiveListener(playButton);

        table.add(map1Button).size(350, 210).padRight(20);
        table.add(map2Button).size(350, 210).padLeft(20);

        table.row().padTop(10);
        BitmapFont font = new BitmapFont();
        font.getData().setScale(2f);
        font.setColor(Color.WHITE);

        table.add(new Label("Level 1", new Label.LabelStyle(font, Color.WHITE))).center();
        table.add(new Label("Level 2", new Label.LabelStyle(font, Color.WHITE))).center();

        table.row().padTop(150);
        table.add(playButton).colspan(2).size(210, 60).center();

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

    }

    private Button createImageButton(String imagePath, int width, int height) {
        Texture upTexture = new Texture(Gdx.files.internal(imagePath));
        TextureRegionDrawable drawableUp = new TextureRegionDrawable(upTexture);

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = drawableUp;

        String pressedImagePath = imagePath.replace(".png", "_Pressed.png");
        FileHandle pressedHandle = Gdx.files.internal(pressedImagePath);

        if (pressedHandle.exists()) {
            Texture downTexture = new Texture(Gdx.files.internal(pressedImagePath));
            TextureRegionDrawable drawableDown = new TextureRegionDrawable(downTexture);
            style.imageDown = drawableDown;
        }

        ImageButton button = new ImageButton(style);
        button.setSize(width, height);
        button.setName(imagePath);
        return button;
    }

}
