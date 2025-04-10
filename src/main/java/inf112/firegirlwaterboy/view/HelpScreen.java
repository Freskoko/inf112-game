package inf112.firegirlwaterboy.view;

import javax.annotation.processing.Generated;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.*;

import inf112.firegirlwaterboy.controller.Controller;

/**
 * HelpScreen class represents the help screen.
 * The help screen is responsible for displaying the instructions for the game.
 */
public class HelpScreen implements Screen {

    private final Stage stage;
    private final Viewport viewport;
    private final Button backButton;
    private final Texture backgroundTexture;
    private Texture boxTexture;

    public HelpScreen(Controller controller) {
        viewport = new ScreenViewport();
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture("assets/pages/background.png");

        backButton = createButton("Back", Color.valueOf("#cab558"));

        setupUI();
        controller.attachToWelcomeListeners(backButton);
    }

    private void setupUI() {
        Image backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));
        backgroundImage.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        stage.addActor(backgroundImage);

        Pixmap whitePixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        whitePixmap.setColor(Color.valueOf("#cab558"));
        whitePixmap.fill();
        boxTexture = new Texture(whitePixmap);
        whitePixmap.dispose();
        TextureRegionDrawable whiteBackground = new TextureRegionDrawable(new TextureRegion(boxTexture));

        String instructionsText = "How to play:\n\n" +
                "Select which player is should use WASD, and which player should use the arrowkeys\n\n" +
                "Collect all diamonds and guide both characters to the door.\n\n" +
                "Avoid obstacles - FireGirl can't touch water, and WaterBoy can't touch fire!";

        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.BLACK);
        Label instructionsLabel = new Label(instructionsText, labelStyle);
        instructionsLabel.setWrap(true);

        Table instructionTable = new Table();
        instructionTable.setFillParent(true);
        instructionTable.center();

        Table innerTable = new Table();
        innerTable.background(whiteBackground);
        innerTable.add(instructionsLabel).width(600).pad(30);

        instructionTable.add(innerTable);

        stage.addActor(instructionTable);

        Table backTable = new Table();
        backTable.setFillParent(true);
        backTable.top().right().pad(20);
        backTable.add(backButton).size(100, 50);
        stage.addActor(backTable);
    }

    // Method for creating a button with a given text and color
    private Button createButton(String text, Color fillColor) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.fontColor = Color.BLACK;

        int width = 150;
        int height = 50;
        int borderWidth = 3;

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(0, 0, width, height);
        pixmap.setColor(fillColor);
        pixmap.fillRectangle(borderWidth, borderWidth, width - 2 * borderWidth, height - 2 * borderWidth);

        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new Texture(pixmap));
        pixmap.dispose();

        style.up = backgroundDrawable;
        style.down = backgroundDrawable.tint(Color.GRAY);

        return new TextButton(text, style);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        backgroundTexture.dispose();
    }
}
