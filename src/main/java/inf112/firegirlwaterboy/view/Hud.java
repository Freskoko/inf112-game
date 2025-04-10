package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Hud class represents the heads-up display for the game.
 * The HUD displays the score and time of the game.
 */
public class Hud {
    private Stage stage;
    private Viewport viewport;
    private Label scoreLabel;
    private Integer score;
    private Label timerLabel;
    private float time;
    private IViewModel model;

    /**
     * Constructor for the Hud class.
     * 
     * @param batch
     */
    public Hud(SpriteBatch batch, IViewModel model) {
        this.model = model;
        score = 0;

        viewport = new ScreenViewport();
        stage = new Stage(viewport, batch);

        Table mainTable = new Table();
        mainTable.top();
        mainTable.setFillParent(true);

        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);

        scoreLabel = new Label("Score: " + score, labelStyle);
        time = 0f;
        timerLabel = new Label("Time: 0", labelStyle);

        Table hudTable = new Table();
        // Usikker på farge på tekst og evt bakgrunnsfarge på HUD, så kommenterer ut for
        // nå
        // hudTable.setBackground(createBackgroundDrawable(Color.LIGHT_GRAY, 0.5f));
        hudTable.pad(10);

        hudTable.add(timerLabel).expandX().padRight(20);
        hudTable.add(scoreLabel).expandX();

        mainTable.add(hudTable).expandX().padTop(10);

        stage.addActor(mainTable);
    }

    /**
     * Draw the stage.
     */
    public void draw() {
        stage.draw();
    }

    /**
     * Resize the viewport.
     * 
     * @param width
     * @param height
     */
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    /**
     * Dispose of the stage.
     */
    public void dispose() {
        stage.dispose();
    }

    /**
     * Get the stage.
     * 
     * @return stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Update the score and time.
     * 
     * @param deltaTime
     */
    public void update(float deltaTime) {
        time += deltaTime;
        timerLabel.setText("Time: " + (int) time);
        scoreLabel.setText("Score: " + model.getTotalCollectedScore());
    }

    /**
     * Reset the time to 0 and update the timerLabel
     */
    public void resetTime() {
        time = 0;
        timerLabel.setText("Time: 0");
    }

    /**
     * Update the score and scoreLabel.
     * 
     * @param newScore
     */
    public void updateScore(int newScore) {
        score = newScore;
        scoreLabel.setText("Score: " + score);
    }

    // private Drawable createBackgroundDrawable(Color color, float alpha) {
    // Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
    // color.a = alpha;
    // pixmap.setColor(color);
    // pixmap.fill();
    // Texture texture = new Texture(pixmap);
    // pixmap.dispose();
    // return new TextureRegionDrawable(texture);
    // }

}