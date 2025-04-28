package inf112.firegirlwaterboy.view.screens;

import javax.annotation.processing.Generated;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import inf112.firegirlwaterboy.controller.Controller;
import inf112.firegirlwaterboy.model.maps.MapUtils;
import inf112.firegirlwaterboy.view.Hud;
import inf112.firegirlwaterboy.view.IViewModel;

/**
 * GameScreen class represents the game screen.
 * The game screen is responsible for rendering the game.
 */
public class GameScreen implements Screen {
  private OrthographicCamera camera;
  private OrthogonalTiledMapRenderer renderer;
  private Box2DDebugRenderer debugRenderer;
  private TiledMap map;
  private IViewModel model;
  private Controller controller; 
  private Viewport viewport;
  private Hud hud;
  private SpriteBatch batch;

  /**
   * Constructs a GameScreen with a given view model and controller.
   * 
   * @param model      The model of the game
   * @param controller The controller of the game
   */
  public GameScreen(IViewModel model, Controller controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public void resize(int width, int height) {
    float mapWidth = map.getProperties().get("width", Integer.class);
    float mapHeight = map.getProperties().get("height", Integer.class);

    camera.viewportHeight = mapHeight;
    camera.viewportWidth = mapWidth;

    camera.position.set(mapWidth / 2, mapHeight / 2, 0);
    camera.update();

    viewport.setWorldSize(camera.viewportWidth, camera.viewportHeight);
    viewport.update(width, height, true);
  }

  @Override
  public void show() {
    float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();

    camera = new OrthographicCamera();
    viewport = new FitViewport(w / MapUtils.PPM, h / MapUtils.PPM, camera);
    viewport.apply(true);

    batch = new SpriteBatch();
    hud = new Hud(batch, model);

    map = model.getMap();
    renderer = new OrthogonalTiledMapRenderer(map, 1 / MapUtils.PPM);
    debugRenderer = new Box2DDebugRenderer();

    Gdx.input.setInputProcessor(controller);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(31 / 255f, 58 / 255f, 26 / 255f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    renderer.setView(camera);
    renderer.render();

    //debugRenderer.render(model.getWorld(), camera.combined);

    renderer.getBatch().setProjectionMatrix(camera.combined);
    renderer.getBatch().begin();
    model.draw(renderer.getBatch());
    renderer.getBatch().end();
    model.update();

    batch.setProjectionMatrix(hud.getStage().getCamera().combined);
    hud.update(delta);
    hud.draw();
  }

  @Override
  @Generated("interface-stub")
  public void resume() {}

  @Override
  @Generated("interface-stub")
  public void pause() {}

  @Override
  @Generated("interface-stub")
  public void hide() {}

  @Override
  public void dispose() {
    map.dispose();
    renderer.dispose();
    debugRenderer.dispose();
    model.dispose();
    batch.dispose();
  }
}