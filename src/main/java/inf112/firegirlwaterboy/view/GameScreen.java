package inf112.firegirlwaterboy.view;

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
  private Controller controller; // Må være her
  private Viewport viewport;
  private Hud hud;
  private SpriteBatch batch;

  /**
   * Constructs a GameScreen with a given view model and controller.
   * 
   * @param model      The view model of the game
   * @param controller The controller of the game
   */
  public GameScreen(IViewModel model, Controller controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public void resize(int width, int height) {
    float scale = 1.0f;

    float newWorldWidth = (width / MapUtils.PPM) * scale;
    float newWorldHeight = (height / MapUtils.PPM) * scale;

    viewport.setWorldSize(newWorldWidth, newWorldHeight);
    viewport.update(width, height, true);
  }

  @Override
  public void show() {
    float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();

    camera = new OrthographicCamera();
    viewport = new FitViewport(w / MapUtils.PPM, h / MapUtils.PPM, camera);
    viewport.apply(true);

    camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    camera.update();

    batch = new SpriteBatch();
    hud = new Hud(batch, model);

    // Load map
    map = model.getMap();
    renderer = new OrthogonalTiledMapRenderer(map, 1 / MapUtils.PPM);
    debugRenderer = new Box2DDebugRenderer();

    Gdx.input.setInputProcessor(controller);
  }

  @Override
  public void render(float delta) {
    // Added background color (usikker på om dette er nødvendig)
    // Gdx.gl.glClearColor(0, 0, 0, 1);
    // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    ////////////////////////////////////////

    // Om vi senere vil at kamera skal flytte seg etter spilleren:
    // camera.position = model.getPlayerPositions();

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    camera.update();

    // Render the map
    renderer.setView(camera);
    renderer.render();

    debugRenderer.render(model.getWorld(), camera.combined);

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
  public void resume() {

  }

  @Override
  @Generated("interface-stub")
  public void pause() {

  }

  @Override
  @Generated("interface-stub")
  public void hide() {

  }

  @Override
  public void dispose() {
    map.dispose();
    renderer.dispose();
    debugRenderer.dispose();
    model.dispose();
    batch.dispose();
  }
}
