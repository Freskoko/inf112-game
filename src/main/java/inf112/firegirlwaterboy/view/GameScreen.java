package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import inf112.firegirlwaterboy.controller.Controller;
import inf112.firegirlwaterboy.model.maps.Maps;

// Tilemap example : https://github.com/libgdx/libgdx/blob/master/tests/gdx-tests/src/com/badlogic/gdx/tests/superkoalio/SuperKoalio.java

/**
 * GameScreen class represents the game screen.
 * The game screen is responsible for rendering the game and updating the game
 * state.
 */
public class GameScreen implements Screen {
  private OrthographicCamera camera;
  private OrthogonalTiledMapRenderer renderer;
  private Box2DDebugRenderer debugRenderer;
  private TiledMap map;
  private IViewModel model;
  private Controller controller; // Må være her

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
    camera.viewportWidth = width / Maps.PPM;
    camera.viewportHeight = height/ Maps.PPM;
    camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    camera.update();
  }

  @Override
  public void show() {
    float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();

    // Set up the camera
    camera = new OrthographicCamera(w / Maps.PPM, h / Maps.PPM);
    camera.position.set(w / 2 / Maps.PPM, h / 2 / Maps.PPM, 0);
    camera.update();

    // Load map
    map = model.getMap();
    renderer = new OrthogonalTiledMapRenderer(map, 1 / Maps.PPM);
    debugRenderer = new Box2DDebugRenderer();
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
    
  }

  @Override
  public void resume() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {
    map.dispose();
    renderer.dispose();
    debugRenderer.dispose();
    model.dispose();
  }
}
