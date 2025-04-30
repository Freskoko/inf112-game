package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Interface for the HUD (Heads-Up Display) of the game.
 * This interface defines the methods that the HUD should implement.
 */
public interface IHud {

    /**
     * Draws the HUD on the screen.
     */
    void draw();

    /**
     * Dispose of the stage.
     */
    public void dispose();

    /**
     * Returns the stage.
     *
     * @return stage
     */
    public Stage getStage();

    /**
     * Update the score and time.
     *
     * @param deltaTime
     */
    public void update(float deltaTime);

    /**
     * Reset the time to 0 and update the timerLabel
     */
    public void resetTime();

    /**
     * Update the score and scoreLabel.
     *
     * @param newScore
     */
    public void updateScore(int newScore);

}
