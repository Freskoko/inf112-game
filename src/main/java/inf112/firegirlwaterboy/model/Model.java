package inf112.firegirlwaterboy.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.controller.IControllableModel;
import inf112.firegirlwaterboy.controller.MovementType;
import inf112.firegirlwaterboy.model.managers.EntitySet;
import inf112.firegirlwaterboy.model.entity.Platform;
import inf112.firegirlwaterboy.model.entity.Player;
import inf112.firegirlwaterboy.model.managers.PlayerSet;
import inf112.firegirlwaterboy.model.maps.IMaps;
import inf112.firegirlwaterboy.model.maps.Maps;
import inf112.firegirlwaterboy.model.types.PlayerType;
import inf112.firegirlwaterboy.view.IViewModel;

/**
 * Model class represents the game model.
 * The model contains the game state, the players and the maps.
 */
public class Model implements IControllableModel, IViewModel {

  private PlayerSet players;
  private EntitySet<Platform> platforms;
  private GameState gameState;
  private IMaps maps;
  private String mapName;
  private World world;
  private int collectedCount;

  public Model() {
    this.players = new PlayerSet();
    this.collectedCount = 0;
    this.mapName = "map";
    this.maps = new Maps();
    this.gameState = GameState.WELCOME;
  }

  @Override
  public void restartGame(){
    world = new World(new Vector2(0, -9.8f), true);
    world.setContactListener(new MyContactListener());
    maps.createObjectsInWorld(world, mapName);
    platforms = new EntitySet<>();
    platforms = maps.getPlatforms(mapName);
    players.forEach(player -> player.spawn(world, maps.getSpawnPos(mapName)));
  }

  @Override
  public boolean changeDir(PlayerType playerType, MovementType dir) {
    if (players.contains(playerType)) {
      Player player = players.getPlayer(playerType);
      player.move(dir);
      return true;
    }
    return false;
  }

  @Override
  public void update() {
    world.step(1 / 60f, 6, 2);
    players.update();
    platforms.update();

    for (Player player : players) {
      if (!player.isAlive()) {
        gameState = GameState.GAME_OVER;
      }
    }
    if (players.areFinished()) {
      gameState = GameState.COMPLETED_LEVEL;
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
    platforms.draw(batch);
  }

  @Override
  public void dispose() {
    players.dispose();
  }

  @Override
  public TiledMap getMap() {
    return maps.getMap(mapName);
  }

  @Override
  public void addPlayer(PlayerType playerType) {
    players.add(new Player(playerType));
  }

  @Override
  public World getWorld() {
    return world;
  }

  @Override
  public String toString() {
    return players.toString();
  }

  @Override
  public boolean containsPlayer(PlayerType playerType) {
    return players.contains(playerType);
  }
}
