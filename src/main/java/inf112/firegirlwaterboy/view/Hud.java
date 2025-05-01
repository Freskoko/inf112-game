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
public class Hud implements IHud {
  
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
    Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

    scoreLabel = new Label("Score: " + score, labelStyle);
    time = 0f;
    timerLabel = new Label("Time: 0", labelStyle);

    Table hudTable = new Table();
    hudTable.pad(10);

    hudTable.add(timerLabel).expandX().padRight(20);
    hudTable.add(scoreLabel).expandX();
    mainTable.add(hudTable).expandX().padTop(10);
    stage.addActor(mainTable);
  }

  @Override
  public void draw() {
    stage.draw();
  }

  @Override
  public void dispose() {
    stage.dispose();
  }

  @Override
  public Stage getStage() {
    return stage;
  }

  @Override
  public void update(float deltaTime) {
    time += deltaTime;
    timerLabel.setText("Time: " + (int) time);
    scoreLabel.setText("Diamonds collected: " + model.getScore());
  }

  @Override
  public void resetTime() {
    time = 0;
    timerLabel.setText("Time: 0");
  }

  @Override
  public void updateScore(int newScore) {
    score = newScore;
    scoreLabel.setText("Score: " + score);
  }
}