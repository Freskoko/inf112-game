package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.GL20; se kommentar i render()
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import inf112.firegirlwaterboy.controller.Controller;

// Tilemap example : https://github.com/libgdx/libgdx/blob/master/tests/gdx-tests/src/com/badlogic/gdx/tests/superkoalio/SuperKoalio.java

/**
 * GameScreen class represents the game screen.
 * The game screen is responsible for rendering the game and updating the game
 * state.
 */
public class GameScreen implements Screen {
  private OrthographicCamera camera;
  private OrthogonalTiledMapRenderer renderer;
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
    camera.viewportWidth = width;
    camera.viewportHeight = height;
    camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
    camera.update();
  }

  @Override
  public void show() {
    // Load map
    model.init();
    map = model.getMap();
    // Use OrthogonalTiledMapRenderer for 2D orthogonal maps. // mulig å legge til
    // unit scale her senere
    renderer = new OrthogonalTiledMapRenderer(map);
    // Set up the camera
    camera = new OrthographicCamera();
    // camera.update(); resize() blir kalt etter show()

    // Oppdater spiller pos
    float deltaTime = Gdx.graphics.getDeltaTime();
    model.update(deltaTime);
  }

  @Override
  public void render(float delta) {
    // Added background color (usikker på om dette er nødvendig)
    // Gdx.gl.glClearColor(0, 0, 0, 1);
    // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    ////////////////////////////////////////

    // Om vi senere vil at kamera skal flytte seg etter spilleren:
    // camera.position = model.getPlayerPositions();

    // Render the map
    renderer.setView(camera);
    renderer.render();

    // Oppdater spiller pos
    float deltaTime = Gdx.graphics.getDeltaTime();
    model.update(deltaTime);

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
    model.dispose();
  }
}
