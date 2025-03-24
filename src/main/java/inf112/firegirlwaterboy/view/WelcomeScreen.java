package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.*;
import inf112.firegirlwaterboy.controller.Controller;

/**
 * WelcomeScreen class represents the welcome screen.
 * Here the player can choose their character and start the game, and press help
 * button for instructions.
 */
public class WelcomeScreen implements Screen {

    private final Stage stage;
    private final Viewport viewport;
    private final Texture logo;
    private final Controller controller;
    private Button fireGirlButtonP1 = createButton("FireGirl", Color.RED);
    private Button waterBoyButtonP1 = createButton("WaterBoy", Color.BLUE);
    private Button fireGirlButtonP2 = createButton("FireGirl", Color.RED);
    private Button waterBoyButtonP2 = createButton("WaterBoy", Color.BLUE);
    private Button startButton = createButton("Start", Color.DARK_GRAY);
    private Button helpButton = createButton("Help", Color.DARK_GRAY);

    public WelcomeScreen(Controller controller) {
        this.controller = controller;

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);

        logo = new Texture("logo.png");

        setupUI();
    }

    private void setupUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.bottom().padBottom(200);

        // Button listeners sent to controller
        controller.attachWelcomeScreenListeners(fireGirlButtonP1, waterBoyButtonP1, fireGirlButtonP2, waterBoyButtonP2,
                startButton, helpButton);

        // Player selection
        Table playerSelectionTable = new Table();

        // Player 1 selection
        Table p1Table = new Table();
        Label player1Label = new Label("Player 1 (ueses arrows) choose your character:",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        p1Table.add(player1Label).colspan(2).center().padBottom(10);
        p1Table.row();
        p1Table.add(fireGirlButtonP1).size(150, 50).pad(10);
        p1Table.add(waterBoyButtonP1).size(150, 50).pad(10);
        playerSelectionTable.add(p1Table).expandX().left().pad(20);

        // Player 2 selection
        Table p2Table = new Table();
        Label player2Label = new Label("Player 2 (uses WASD) choose your character:",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        p2Table.add(player2Label).colspan(2).center().padBottom(10);
        p2Table.row();
        p2Table.add(fireGirlButtonP2).size(150, 50).pad(10);
        p2Table.add(waterBoyButtonP2).size(150, 50).pad(10);
        playerSelectionTable.add(p2Table).expandX().right().pad(20);

        table.add(playerSelectionTable).colspan(2);

        // Start button
        table.row();
        table.add(startButton).colspan(2).size(150, 50).center().padTop(20);

        stage.addActor(table);

        // Help button
        Table helpTable = new Table();
        helpTable.setFillParent(true);
        helpTable.top().right().pad(20);
        helpTable.add(helpButton).size(100, 50);
        stage.addActor(helpTable);

        // Logo image
        Image logoImage = new Image(new TextureRegionDrawable(new TextureRegion(logo)));

        // Set logo size and position
        float logoWidth = logo.getWidth();
        float logoHeight = logo.getHeight();

        logoImage.setSize(logoWidth, logoHeight);
        logoImage.setPosition((viewport.getWorldWidth() - logoWidth) / 2, viewport.getWorldHeight() * 0.45f);

        stage.addActor(logoImage);
    }

    // Method to create buttons with text and color
    private Button createButton(String text, Color color) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.fontColor = Color.WHITE;

        // Background color on buttons
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGB888);
        pixmap.setColor(color);
        pixmap.fill();
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
        if (Gdx.input.getInputProcessor() != stage) {
            Gdx.input.setInputProcessor(stage);
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        logo.dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
