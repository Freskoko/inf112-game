package inf112.firegirlwaterboy.view.screens;

import javax.annotation.processing.Generated;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.*;

import inf112.firegirlwaterboy.controller.Controller;
import inf112.firegirlwaterboy.view.ButtonFactory;

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

        backButton = ButtonFactory.createButton("Back", Color.valueOf("#cab558"));

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
                "The white diamond gives one extra life.\n\n" +
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
