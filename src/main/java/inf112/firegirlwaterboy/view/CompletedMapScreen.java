package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;

import inf112.firegirlwaterboy.controller.Controller;


public class CompletedMapScreen implements Screen{
  
  private Controller controller;
  private SpriteBatch batch;
  private BitmapFont font;
  private Stage stage;
  private Viewport viewport;
  private Button welcomeScreenButton = createButton("Back to Choose Maps Screen", Color.DARK_GRAY);

  public CompletedMapScreen(Controller controller) {
    this.controller = controller;
    viewport = new ScreenViewport();
    stage = new Stage(viewport);
    Gdx.input.setInputProcessor(stage);
    batch = new SpriteBatch();

    font = new BitmapFont();
    font.setColor(Color.BLUE);
    font.getData().setScale(3);

    setupUI();
  }

  // Set up the UI for the completed level screen
  private void setupUI() {
    Table table = new Table();
    table.setFillParent(true);
    table.bottom().padBottom(200);
    table.center();


    controller.attachCompletedLevelScreenListeners(welcomeScreenButton);

    table.add(welcomeScreenButton);
    stage.addActor(table);

  }

  // Method to create buttons with text and color
  private Button createButton(String text, Color color) {
    TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

    // Font
    BitmapFont buttonFont = new BitmapFont();
    buttonFont.getData().setScale(2);
    style.font = buttonFont;
    style.fontColor = Color.WHITE;

    // Background
    int width = 300;
    int height = 80;
    Pixmap pixmapUp = new Pixmap(width, height, Pixmap.Format.RGBA8888);
    pixmapUp.setColor(color);
    pixmapUp.fill();

    style.up = new TextureRegionDrawable(new Texture(pixmapUp));

    pixmapUp.dispose();

    TextButton button = new TextButton(text, style);
    button.pad(10); 
    return button;
}


  @Override
  public void show() {
    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    batch.begin();
    font.draw(batch, "Level completed!", Gdx.graphics.getWidth() / 2f - 150, Gdx.graphics.getHeight() / 2f + 20);

    batch.end();

    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    viewport.update(width, height, true);
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void hide() {
    Gdx.input.setInputProcessor(null);
  }

  @Override
  public void dispose() {
    batch.dispose();
    font.dispose();
    stage.dispose();
  }
}
