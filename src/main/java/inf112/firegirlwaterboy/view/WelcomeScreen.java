package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.*;
import inf112.firegirlwaterboy.controller.Controller;
import inf112.firegirlwaterboy.model.entity.PlayerType;

public class WelcomeScreen implements Screen {

    private final Stage stage;
    private final Viewport viewport;
    private final SpriteBatch batch;
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
        batch = new SpriteBatch();

        logo = new Texture("logo.png");

        setupUI();
    }

    private void setupUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.bottom().padBottom(200);

        controller.attachWelcomeScreenListeners(fireGirlButtonP1, waterBoyButtonP1, fireGirlButtonP2, waterBoyButtonP2,
                startButton, helpButton);

        // Player selection
        Table playerSelectionTable = new Table();

        // Player 1 selection
        Table p1Table = new Table();
        Label player1Label = new Label("Player 1 (ueses arrows) choose your character:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        p1Table.add(player1Label).colspan(2).center().padBottom(10);
        p1Table.row();
        p1Table.add(fireGirlButtonP1).size(150, 50).pad(10);
        p1Table.add(waterBoyButtonP1).size(150, 50).pad(10);
        playerSelectionTable.add(p1Table).expandX().left().pad(20);

        // Player 2 selection
        Table p2Table = new Table();
        Label player2Label = new Label("Player 2 (uses) choose your character:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        p2Table.add(player2Label).colspan(2).center().padBottom(10);
        p2Table.row();
        p2Table.add(fireGirlButtonP2).size(150, 50).pad(10);
        p2Table.add(waterBoyButtonP2).size(150, 50).pad(10);
        playerSelectionTable.add(p2Table).expandX().right().pad(20);

        table.add(playerSelectionTable).colspan(2);

        table.row();
        table.add(startButton).colspan(2).center().padTop(20);

        stage.addActor(table);




        // Help button
        Table helpTable = new Table();
        helpTable.setFillParent(true);
        helpTable.top().right().pad(20);
        helpTable.add(helpButton).size(100, 50);
        stage.addActor(helpTable);
    }

    private Button createButton(String text, Color color) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.fontColor = Color.WHITE;

        // Legg til bakgrunnsfarge på knappene
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGB888);
        pixmap.setColor(color);
        pixmap.fill();
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new Texture(pixmap));
        pixmap.dispose();

        style.up = backgroundDrawable;
        style.down = backgroundDrawable.tint(Color.GRAY);

        return new TextButton(text, style);
    }

    public void updateButtonStates(PlayerType selectedType, int playerNum) {
        if (playerNum == 1) {
            fireGirlButtonP2.setDisabled(selectedType == PlayerType.FIREGIRL);
            fireGirlButtonP2.setColor(selectedType == PlayerType.FIREGIRL ? Color.GRAY : Color.RED);

            waterBoyButtonP2.setDisabled(selectedType == PlayerType.WATERBOY);
            waterBoyButtonP2.setColor(selectedType == PlayerType.WATERBOY ? Color.GRAY : Color.BLUE);
        } else if (playerNum == 2) {
            fireGirlButtonP1.setDisabled(selectedType == PlayerType.FIREGIRL);
            fireGirlButtonP1.setColor(selectedType == PlayerType.FIREGIRL ? Color.GRAY : Color.RED);

            waterBoyButtonP1.setDisabled(selectedType == PlayerType.WATERBOY);
            waterBoyButtonP1.setColor(selectedType == PlayerType.WATERBOY ? Color.GRAY : Color.BLUE);
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.getInputProcessor() != stage) {
            System.out.println("Input processor er IKKE stage. Setter den nå.");
            Gdx.input.setInputProcessor(stage);
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // batch.draw(background, 0, 0, viewport.getWorldWidth(),
        // viewport.getWorldHeight());
        batch.draw(logo, (viewport.getWorldWidth() - logo.getWidth()) / 2,
                (viewport.getWorldHeight() - logo.getHeight()) * 0.75f);
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
        batch.dispose();
        //background.dispose();
        logo.dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
