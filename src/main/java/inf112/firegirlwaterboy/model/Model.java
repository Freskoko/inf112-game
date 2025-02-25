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
  
  public static final float PPM = 100;

  public Model() {
    world = new World(new Vector2(0, -9.8f), true); // Gravity
    world.setContactListener(new MyContactListener(players));
    //debugRenderer = new Box2DDebugRenderer();

  }
  
  @Override
  public boolean changeVelocity(PlayerType playerType, int deltaX, int deltaY) {

    //henter spiller fra player på pos 1 i array
    Player player =  players.getPlayer(playerType);
    player.setVelosity(deltaX, deltaY);
    //updatePlayer(player, deltaY);
    return true;
  }

  @Override
  public void update(float deltaTime) {
    for (Player player : players) {
      //updatePlayer(player, deltaTime);
      player.update(deltaTime);
      //checkCollisions(player);
    }
  }
  
  /*public void checkCollisions(Player player) {
    //System.out.println("CheckCollisions kjører");
    Rectangle playerRect = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());

    for (InteractiveObject obj : interactiveObjects) {
      //System.out.println("Objekt posisjon og størrelse: " + obj.getBounds());

      if (playerRect.overlaps(obj.getBounds())) {
        System.out.println("Overlap: " + obj.getValue());
        handleCollision(player, obj);
      }
    }
  }
  
  private void handleCollision(Player player, InteractiveObject obj) {
    String value = obj.getValue();
    System.out.println("Kolliderer med: " + value);
    
    if (value == null) {
      System.out.println("Object with no value encountered.");
      return;
    }
    
    switch (value) {
      case "wall":
        System.out.println("Player hit wall");
        player.setVelosity(0, 0);
        break;
      case "lava":
        System.out.println("Player hit lava");
        break;
      case "water":
        System.out.println("Player hit water");
        break;
      case "border":
        System.out.println("Player hit border");
        player.setVelosity(0, 0);
      default:
        System.out.println("Foreign object: " + value);
    }
    if (obj.isBlocked()) {
      System.out.println("obj is blocked");
      player.setVelosity(0, 0);
    }
  }*/


  /** 
   * 
   * @param player The player to update
   * @param deltaTime The time elapsed since the last update
   */
  /*private void updatePlayer(Player player, float deltaTime) {
    player.update(deltaTime);
    TiledMapTileLayer collisionLayer = maps.getLayer(this.currentMapName, "Border");


    float oldX = player.getX(), oldY= player.getY();

    Vector2 velocity = player.getVelocity();
    float x = player.getX(), y= player.getY();
    float width = player.getWidth(), height = player.getHeight();

    player.setX(player.getX() + velocity.x * deltaTime);
    player.setY(player.getY() + velocity.y * deltaTime);
  
    boolean collisionX = false, collisionY = false;
    float[] xOffsets = {0, width / 2, width}; // Left, Middle, Right
    float[] yOffsets = {0, height / 3, height * 2/3, height}; // Bottom, Body, Head, Top


    // Check X-axis collision
    if (velocity.x != 0) {
        float checkX = (velocity.x < 0) ? x : x + width;
        for (float offsetY : yOffsets) {
            if (isCellBlocked(collisionLayer, checkX, y + offsetY)) {
                collisionX = true;
                break;
            }
        }
    }

    if (collisionX) {
      velocity.x = 0;
      player.setX(oldX);
      player.setVelosity(velocity);
    } 

  
    // Check Y-axis collision
    if (velocity.y != 0) {
        float checkY = (velocity.y < 0) ? y : y + height;
        for (float offsetX : xOffsets) {
            if (isCellBlocked(collisionLayer, x + offsetX, checkY)) {
                collisionY = true;
                break;
            }
        }
    }

    
   
    if (collisionY) {
      velocity.y = 0;
      player.setY(oldY);
    } 

    
  }*/


  /**
   * Checks if the cell at the given position is blocked.
   * @param collisionLayer The collision layer
   * @param x The x position
   * @param y The y position
   * @return True if the cell is blocked, false otherwise
   */
  private boolean isCellBlocked(TiledMapTileLayer collisionLayer, float x, float y) {
    float tileWidth = collisionLayer.getTileWidth();
    float tileHeight = collisionLayer.getTileHeight();
    return collisionLayer.getCell((int) (x / tileWidth), (int) (y / tileHeight)).getTile().getProperties().containsKey("blocked");
  }

  // Kanskje vi ikke trenger gameState enum hvis vi bruker Game gdx klassen (?)
  @Override
  public GameState getGameState() {
    return gameState;
  }

  @Override // Usikker om denne er nødvendig
  public void setGameState(GameState gameState) {
    this.gameState = gameState;
  }

  @Override
  public int getTimerDelay() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getTimerDelay'");
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

  @Override
  public void init() {
    maps = new Maps();
    this.setMap(this.currentMapName);
    Texture texture = new Texture(Gdx.files.internal("src/main/resources/figur.png"));
    TextureRegion textureRegion = new TextureRegion(texture);
    players = new EntityList<PlayerType, Player>();
    players.addPlayer(PlayerType.FIREGIRL, new Player(world, maps.getPlayerSpawn(), PlayerType.FIREGIRL));
    
  }

  // for tests
  public Model (String mapName) {
    this.setMap(mapName);
  }

  private void setMap(String mapName) {
    this.currentMapName = mapName;
  }
}
