package inf112.firegirlwaterboy.view.screens;

import javax.annotation.processing.Generated;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;

import inf112.firegirlwaterboy.controller.Controller;
import inf112.firegirlwaterboy.view.ButtonFactory;

/**
 * CompletedMapScreen class represents the screen displayed when a map is
 * completed. It shows a message indicating that the map is completed and a
 * button to return to the Choose Maps screen.
 */
public class CompletedMapScreen implements Screen {

  private Controller controller;
  private SpriteBatch batch;
  private BitmapFont font;
  private Stage stage;
  private Viewport viewport;
  private Button welcomeScreenButton = ButtonFactory.createButton("Back to Choose Maps Screen", Color.DARK_GRAY);

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

  // Set up the UI for the completed map screen
  private void setupUI() {
    Table table = new Table();
    table.setFillParent(true);
    table.bottom().padBottom(200);
    table.center();

    controller.attachToChooseMapsListener(welcomeScreenButton);

    table.add(welcomeScreenButton);
    stage.addActor(table);

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
    font.draw(batch, "Map completed!", Gdx.graphics.getWidth() / 2f - 150, Gdx.graphics.getHeight() / 2f + 20);

    batch.end();

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
    batch.dispose();
    font.dispose();
    stage.dispose();
  }
}
