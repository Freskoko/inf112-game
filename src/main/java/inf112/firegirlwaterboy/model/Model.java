package inf112.firegirlwaterboy.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.controller.IControllableModel;
import inf112.firegirlwaterboy.controller.MovementType;
import inf112.firegirlwaterboy.model.entity.EntitySet;
import inf112.firegirlwaterboy.model.entity.Player;
import inf112.firegirlwaterboy.model.entity.PlayerType;
import inf112.firegirlwaterboy.model.maps.IMaps;
import inf112.firegirlwaterboy.model.maps.Maps;
import inf112.firegirlwaterboy.view.IViewModel;

/**
 * Model class represents the game model.
 * The model contains the game state, the players and the maps.
 */
public class Model implements IControllableModel, IViewModel {

  private EntitySet<Player> players;
  private GameState gameState;
  private IMaps maps;
  private String currentMapName;
  private World world;

  public Model() {
    this.players = new EntitySet<>();
    this.currentMapName = "map";
    this.maps = new Maps();
    this.world = new World(new Vector2(0, -9.8f), true);
    world.setContactListener(new MyContactListener());
  }

  @Override
  public void init() {
    maps.init();
    maps.createObjectsInWorld(world, currentMapName);
    for (Player player : players) {
      player.spawn(world, maps.getPlayerSpawn());
    }
  }

  @Override
  public boolean changeDir(PlayerType playerType, MovementType dir) {
    try {
      Player player = players.getEntity(playerType);
      player.move(dir);
      return true;
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  @Override
  public void update() {
    world.step(1 / 60f, 6, 2);
    for (Player player : players) {
      player.update();
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

  @Override
  public void dispose() {
    players.dispose();
  }

  @Override
  public TiledMap getMap() {
    return maps.getMap(currentMapName);
  }

  @Override
  public void addPlayer(PlayerType playerType) {
    Player player1 = new Player(playerType);
    players.addEntity(player1);
  }

  @Override
  public World getWorld() {
    return world;
  }

  @Override
  public String toString() {
    return players.toString();
  }
}
