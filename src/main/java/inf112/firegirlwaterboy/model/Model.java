package inf112.firegirlwaterboy.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.controller.IControllableModel;
import inf112.firegirlwaterboy.model.Entity.EntityList;
import inf112.firegirlwaterboy.model.Entity.Player;
import inf112.firegirlwaterboy.model.Entity.PlayerType;
import inf112.firegirlwaterboy.view.IViewModel;

// skal kanskje denne her extende Game fra gdx?

/**
 * Model class represents the game model.
 * The model contains the game state, the players and the maps.
 */
public class Model implements IControllableModel, IViewModel { 

  private EntityList<PlayerType, Player> players;
  private GameState gameState;
  private Maps maps;
  private String currentMapName = "map";
  private World world;

  public Model() {
    this.world = new World(new Vector2(0, -9.8f), true); // Gravity
    this.maps = new Maps();  // Only initialize once
    this.players = new EntityList<>();
    world.setContactListener(new MyContactListener(players));
  }

  @Override
  public void init() {
      maps.init(); // Load maps
      this.setMap(this.currentMapName);
      maps.createObjectsInWorld(world, currentMapName);

      // Ensure we only create the player when needed
      if (players.isEmpty()) {
          Player player =  new Player(world, maps.getPlayerSpawn(), PlayerType.FIREGIRL);
          players.addPlayer(PlayerType.FIREGIRL,player);
      }
  }
  
  @Override
  public boolean changeDir(PlayerType playerType, String dir) {
    Player player =  players.getPlayer(playerType);
    if (dir.equals("jump")) {
      player.jump();
    } else {
      player.move(dir);
    }
    return true;
  }

  @Override
  public void update(float deltaTime) {
    world.step(1 / 60f, 3, 2); //?

    for (Player player : players) {
      player.update(deltaTime);
    }
  }
  
  @Override
  public GameState getGameState() {
    return gameState;
  }

  @Override 
  public void setGameState(GameState gameState) {
    this.gameState = gameState;
  }

 
  @Override
  public void draw(Batch batch) {
    players.draw(batch);
  }

  /** Cleans up allocated resources */
	public void dispose() {
		players.dispose();
	}

  @Override
  public TiledMap getMap() {
    return maps.getMap(this.currentMapName);
  }

 

  private void setMap(String mapName) {
    this.currentMapName = mapName;
  }

   // for tests
   public Model(String mapName) {
    this.currentMapName = mapName;
  }


}
