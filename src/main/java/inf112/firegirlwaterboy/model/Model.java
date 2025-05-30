package inf112.firegirlwaterboy.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;

import inf112.firegirlwaterboy.controller.IControllableModel;
import inf112.firegirlwaterboy.controller.MovementType;
import inf112.firegirlwaterboy.model.entity.Element;
import inf112.firegirlwaterboy.model.entity.Platform;
import inf112.firegirlwaterboy.model.entity.Player;
import inf112.firegirlwaterboy.model.entity.managers.CollectableSet;
import inf112.firegirlwaterboy.model.entity.managers.EntitySet;
import inf112.firegirlwaterboy.model.entity.managers.PlayerSet;
import inf112.firegirlwaterboy.model.entity.types.PlayerType;
import inf112.firegirlwaterboy.model.maps.IMapManager;
import inf112.firegirlwaterboy.model.maps.MapManager;
import inf112.firegirlwaterboy.model.maps.factories.WorldFactory;
import inf112.firegirlwaterboy.model.sound.ISoundManager;
import inf112.firegirlwaterboy.model.maps.factories.IWorldFactory;
import inf112.firegirlwaterboy.model.maps.factories.StandardGameObjectsFactory;
import inf112.firegirlwaterboy.view.IViewModel;

/**
 * Model class represents the game model.
 * The model contains the game state, the players and the maps.
 */
public class Model implements IControllableModel, IViewModel {

  private PlayerSet players;
  private EntitySet<Platform> platforms;
  private CollectableSet collectables;
  private EntitySet<Element> elements;
  private GameState gameState;
  private IWorldFactory worldFactory;
  private String mapName;
  private World world;
  private ISoundManager soundManager;
  private IMapManager mapManager;

  public Model() {
    this.players = new PlayerSet();
    this.mapName = "map1";
    this.mapManager = new MapManager();
    this.worldFactory = new WorldFactory(new StandardGameObjectsFactory(), mapManager);
    this.gameState = GameState.WELCOME;
  }

  @Override
  public void restartGame() {
    TiledMap map = mapManager.getMap(mapName);
    world = worldFactory.createWorld(map);
    platforms = worldFactory.createPlatforms(world, map);
    collectables = worldFactory.createCollectables(world, map);
    elements = worldFactory.createElements(world, map);
    players.forEach(player -> player.spawn(world, mapManager.getSpawnPos(map, player.getType())));
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
    collectables.update();
    players.update();
    platforms.update();
    elements.update();

    if (!players.areAlive()) {
      gameState = GameState.GAME_OVER;
    }

    if (players.areFinished()) {
      Timer.schedule(new Timer.Task() {
        @Override
        public void run() {
          gameState = GameState.COMPLETED_MAP;
        }
      }, 1);
      mapManager.complete(mapName);
      soundManager.playMapCompleteSound();
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
    elements.draw(batch);
    collectables.draw(batch);
  }

  @Override
  public void dispose() {
    players.dispose();
    platforms.dispose();
    elements.dispose();
    collectables.dispose();
    soundManager.dispose();
  }

  @Override
  public TiledMap getMap() {
    return mapManager.getMap(mapName);
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
  public int getScore() {
    return players.getScore();
  }

  @Override
  public void setMap(String mapName) {
    this.mapName = mapName;
  }

  @Override
  public boolean isComplete(String mapName) {
    return mapManager.isComplete(mapName);
  }

  @Override
  public void setSoundManager(ISoundManager soundManager) {
    this.soundManager = soundManager;
  }
}