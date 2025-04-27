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
import inf112.firegirlwaterboy.view.ButtonDesigner;

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
        viewport = new ExtendViewport(960, 960);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture("assets/pages/background.png");

        backButton = ButtonDesigner.createButton("Back", Color.valueOf("#607d4d"));

        setupUI();
        controller.attachToWelcomeListeners(backButton);
    }

    private void setupUI() {
        Image backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));
        backgroundImage.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        stage.addActor(backgroundImage);

        Pixmap whitePixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        whitePixmap.setColor(Color.valueOf("#607d4d"));
        whitePixmap.fill();
        boxTexture = new Texture(whitePixmap);
        whitePixmap.dispose();
        TextureRegionDrawable whiteBackground = new TextureRegionDrawable(new TextureRegion(boxTexture));

        String instructionsText = "How to Play:\n\n" +
                "Choose character for player 1 and player 2. Player 1 uses arrow keys and player 2 uses WASD.\n\n" +
                "Work together to collect all the diamonds and lead both characters to the exit door.\n\n" +
                "White diamonds grant an extra life - grab them when you can!\n\n" +
                "Avoid hazards: FireGirl can't touch water, WaterBoy can't touch fire, and no one can touch acid!";

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
