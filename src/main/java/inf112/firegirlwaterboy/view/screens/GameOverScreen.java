package inf112.firegirlwaterboy.view.screens;

import javax.annotation.processing.Generated;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;

import inf112.firegirlwaterboy.controller.Controller;
import inf112.firegirlwaterboy.view.ButtonDesigner;

/**
 * GameOverScreen class represents the screen displayed when the game is over.
 * It shows a "Game Over" message and a button to return to the Choose Maps
 * screen.
 */
public class GameOverScreen implements Screen {

  private Controller controller;

  private SpriteBatch batch;
  private Texture backgroundTexture;
  private Stage stage;
  private Viewport viewport;
  private Button chooseMapScreenButton = ButtonDesigner.createButton("Return", Color.GRAY);

  public GameOverScreen(Controller controller) {
    this.controller = controller;
    viewport = new ScreenViewport();
    stage = new Stage(viewport);
    Gdx.input.setInputProcessor(stage);
    batch = new SpriteBatch();
    backgroundTexture = new Texture(Gdx.files.internal("assets/pages/GameOverScreen.png"));

    setupUI();
  }

  private void setupUI() {
    Table table = new Table();
    table.setFillParent(true);
    table.bottom().padBottom(90); 

    controller.attachToChooseMapsListener(chooseMapScreenButton);
    table.add(chooseMapScreenButton).center();

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
    batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
    stage.dispose();

  }

}