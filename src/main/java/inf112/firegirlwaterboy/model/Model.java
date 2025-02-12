package inf112.firegirlwaterboy.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import inf112.firegirlwaterboy.controller.IControllableModel;
import inf112.firegirlwaterboy.model.Entity.EntityList;
import inf112.firegirlwaterboy.model.Entity.Player;
import inf112.firegirlwaterboy.model.Entity.PlayerType;
import inf112.firegirlwaterboy.view.IViewModel;

// skal kanskje denne her extende Game fra gdx?
public class Model implements IControllableModel, IViewModel { 

  private EntityList<PlayerType, Player> players;
  private GameState gameState;
  private Maps maps;


  @Override
  public boolean changeVelocity(PlayerType playerType, int deltaX, int deltaY) {
    Player player =  players.getPlayer(playerType);
    player.setVelosity(deltaX, deltaY);
    updatePlayer(player, deltaY);
    return true;
  }


  @Override
	public void update(float deltaTime) {
    for (Player player : players) {
      updatePlayer(player, deltaTime);
    }
	}

  private void updatePlayer(Player player, float deltaTime) {
    player.update(deltaTime);
    TiledMapTileLayer collisionLayer = maps.getLayer("map", "Border");

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

    
  }

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


	public void dispose() {
		players.dispose();
	}

  @Override
  public TiledMap getMap() {
    return maps.getMap("map");
  }

  @Override
  public void init() {
    maps = new Maps();
    players = new EntityList<PlayerType, Player>();
    players.addPlayer(PlayerType.FIREGIRL, new Player());
  }  
}
