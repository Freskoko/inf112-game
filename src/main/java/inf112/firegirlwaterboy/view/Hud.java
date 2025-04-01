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
    private float time; // Time in seconds since start

    /**
     * Constructor for the Hud class.
     * 
     * @param batch
     */
    public Hud(SpriteBatch batch) {
        score = 0;

        viewport = new ScreenViewport();
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        scoreLabel = new Label("Score: " + score, labelStyle);
        time = 0f;

        timerLabel = new Label("Time: 0", labelStyle);
        table.row();
        table.add(timerLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);

        stage.addActor(table);
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
     * @param deltaTime
     */
    public void updateTime(float deltaTime) {
        time += deltaTime;
        timerLabel.setText("Time: " + (int) time);
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

    public void updateScoreFromModel(IViewModel model) {
        //score = model.getCollectedCount();
        scoreLabel.setText("Score: " + score);
    }


}