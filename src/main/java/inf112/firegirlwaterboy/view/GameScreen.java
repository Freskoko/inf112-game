package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import inf112.firegirlwaterboy.model.Entity.Player;
import inf112.firegirlwaterboy.model.Model;
import inf112.firegirlwaterboy.model.Entity.EntityList;
import inf112.firegirlwaterboy.model.Entity.PlayerType;

// Tilemap example : https://github.com/libgdx/libgdx/blob/master/tests/gdx-tests/src/com/badlogic/gdx/tests/superkoalio/SuperKoalio.java

public class GameScreen implements Screen {
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMap map;
    private EntityList<Player> playerList;
    private Model model; //usikker på hvordan vi skal løse dette.
   
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

        // Use OrthogonalTiledMapRenderer for 2D orthogonal maps. // mulig å legge til unit scale her senere
        renderer = new OrthogonalTiledMapRenderer(map);

        // Set up the camera
        camera = new OrthographicCamera();
        // camera.update(); resize() blir kalt etter show()


        // dette bør kanskje gjøres i modelen?
        playerList = new EntityList<Player>();
        TiledMapTileLayer collisionLayer =  (TiledMapTileLayer) map.getLayers().get("Border");
        playerList.addPlayer(PlayerType.FIREGIRL, new Player(new Sprite(new Texture("src/main/resources/figur.png")), collisionLayer));
    }

    @Override
    public void render(float delta) {
        // Added background color (unsure if this is necessary)
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ////////////////////////////////////////

        // Render the map
        renderer.setView(camera);
        renderer.render();  

        // Oppdater spiller pos
		float deltaTime = Gdx.graphics.getDeltaTime();
        playerList.update(deltaTime);

        // Om vi senere vil at kamera skal flytte seg etter spilleren:
        // camera.position = PlayerList.getPlayerPositions();
        
        renderer.getBatch().begin();
        playerList.draw(renderer.getBatch());
        renderer.getBatch().end();
    
        
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
        map.dispose();
        renderer.dispose();
        playerList.dispose();
    }
}
