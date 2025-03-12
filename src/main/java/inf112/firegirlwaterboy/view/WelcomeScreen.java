package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.firegirlwaterboy.controller.Controller;
import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.entity.PlayerType;
import inf112.firegirlwaterboy.controller.IControllableModel;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class WelcomeScreen implements Screen {

    private IControllableModel model;
    private Controller controller;
    private Stage stage;
    private Viewport viewport;
    private BitmapFont font;
    private Button startButton;
    private TextButton fireGirlButton, waterBoyButton;
    private PlayerType playerOne;
    private PlayerType playerTwo;
    private SpriteBatch batch;

    public WelcomeScreen(IControllableModel model, Controller controller) {
        this.model = model;
        this.controller = controller;
        this.playerOne = null;

        // denne må fikses, er for bred for gamescreen
        this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.stage = new Stage(viewport);

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2f);

        batch = new SpriteBatch();

        setupUi();
    }

    /**
     * Sets up the UI elements of the welcome screen.
     * The UI elements include buttons for selecting players and starting the game.
     * When Player 1 (using arrows) selects a playertype (firegirl or waterboy),
     * player 2 (using WASD) gets the other playertype.
     */
    private void setupUi() {
        startButton = new Button(new TextureRegionDrawable(new Texture("start.png")));
        fireGirlButton = new TextButton("Select FireGirl", createButtonStyle());
        waterBoyButton = new TextButton("Select WaterBoy", createButtonStyle());

        fireGirlButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.selectPlayer(PlayerType.FIREGIRL);
                updateButtonStyles();
            }
        });

        waterBoyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.selectPlayer(PlayerType.WATERBOY);
                updateButtonStyles();
            }
        });

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.startGameIfPlayersSelected();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        
        table.add(fireGirlButton).size(200, 80).padBottom(20).row();
        table.add(waterBoyButton).size(200, 80).row();
        table.add(startButton).size(200, 80).padBottom(20).row();

        stage.addActor(table);
    }

    private TextButtonStyle createButtonStyle() {
        TextButtonStyle style = new TextButtonStyle();
        style.font = font;
        return style;
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

        batch.begin();
        font.draw(batch, "Select character for player using arrows", 250, 680);
        font.draw(batch, "The other character uses WASD", 250, 650);
        batch.end();

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
        font.dispose();
        batch.dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    /**
     * Updates the styles of the button. If player one chooses firegirl, waterboy
     * buttons turns grey and vica verca.
     */
    private void updateButtonStyles() {
        PlayerType selectedPlayer = controller.getPlayerOne();

        fireGirlButton.getStyle().fontColor = (selectedPlayer == PlayerType.FIREGIRL) ? Color.RED : Color.GRAY;
        waterBoyButton.getStyle().fontColor = (selectedPlayer == PlayerType.WATERBOY) ? Color.BLUE : Color.GRAY;
    }
}
