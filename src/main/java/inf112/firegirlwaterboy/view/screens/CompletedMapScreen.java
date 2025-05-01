package inf112.firegirlwaterboy.view.screens;

import javax.annotation.processing.Generated;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;

import inf112.firegirlwaterboy.controller.Controller;
import inf112.firegirlwaterboy.view.ButtonDesigner;

/**
 * CompletedMapScreen class represents the screen displayed when a map is
 * completed. It shows a message indicating that the map is completed and a
 * button to return to the Choose Maps screen.
 */
public class CompletedMapScreen implements Screen {

  private Controller controller;
  private SpriteBatch batch;
  private Stage stage;
  private Viewport viewport;
  private Button ToChooseMapScreenButton = ButtonDesigner.createButton("Return", Color.valueOf("#0583f2"));
  private Texture backgroundTexture;

  public CompletedMapScreen(Controller controller) {
    this.controller = controller;
    viewport = new ExtendViewport(960, 960);
    stage = new Stage(viewport);
    Gdx.input.setInputProcessor(stage);
    batch = new SpriteBatch();
    backgroundTexture = new Texture(Gdx.files.internal("assets/pages/LevelCompletedScreen.png"));

    setupUI();
  }

  // Set up the UI for the completed map screen
  private void setupUI() {
    Table table = new Table();
    table.setFillParent(true);
    table.bottom().padBottom(90);

    controller.attachReturnToChooseMapsListener(ToChooseMapScreenButton);

    table.add(ToChooseMapScreenButton).width(300)
        .height(100)
        .center();
    ;
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

    batch.setProjectionMatrix(stage.getCamera().combined);
    batch.begin();
    batch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
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
    backgroundTexture.dispose();
  }
}