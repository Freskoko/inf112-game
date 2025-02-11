package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

// Tilemap example : https://github.com/libgdx/libgdx/blob/master/tests/gdx-tests/src/com/badlogic/gdx/tests/superkoalio/SuperKoalio.java

public class GameScreen implements Screen {
    private OrthographicCamera camera;
    private TiledMap map;
    private TiledMapRenderer renderer;
    //private PlayerList playerList;

   
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
        map = new TmxMapLoader().load("src/main/resources/map.tmx");

        // Use OrthogonalTiledMapRenderer for 2D orthogonal maps. 
        renderer = new OrthogonalTiledMapRenderer(map);

        // Set up the camera
        camera = new OrthographicCamera();
        camera.update();  
    }

    @Override
    public void render(float delta) {
        // Oppdater spiller pos her
        // get the delta time
		// float deltaTime = Gdx.graphics.getDeltaTime();
        // PlayerList.updatePlayers(deltatime);

        // Om vi senere vil at kamera skal flytte seg etter spilleren:
        // camera.position = PlayerList.getPlayerPositions();
    
        // Render the map
        renderer.setView(camera);
        renderer.render();  
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pause'");
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resume'");
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hide'");
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dispose'");
    }
}
