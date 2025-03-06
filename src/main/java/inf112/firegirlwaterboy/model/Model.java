package inf112.firegirlwaterboy.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.controller.IControllableModel;
import inf112.firegirlwaterboy.model.entity.EntityList;
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

  private EntityList<PlayerType, Player> players;
  private GameState gameState;
  private IMaps maps;
  private String currentMapName;
  private World world;

  public Model() {
    this.world = new World(new Vector2(0, -9.8f), true); // Gravity
    this.players = new EntityList<>();
    world.setContactListener(new MyContactListener(players));
    this.currentMapName = "map";
  }

  @Override
  public void init() {
    this.maps = new Maps();
    maps.init();
    maps.createObjectsInWorld(world, currentMapName);

    for (Player player : players) {
        player.spawn(world, maps.getPlayerSpawn());
    }
  }

  @Override
  public boolean changeDir(PlayerType playerType, String dir) {
    Player player = players.getPlayer(playerType);
    if (dir.equals("jump")) {
      player.jump();
    } else {
      player.move(dir);
    }
    return true;
  }

  @Override
  public void update(float deltaTime) {
    world.step(1 / 60f, 6, 2); // Usikker hva parameter gjør?
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
  public void addPlayer(PlayerType playerType) {
    Player player1 = new Player(playerType);
    players.addPlayer(playerType, player1);
  }

  @Override
  public World getWorld() {
    return this.world;
  }

  public void removePlayer(PlayerType player) {
    players.removePlayer(player);
  }

  @Override
  public boolean containsPlayer(PlayerType playerType) {
    return this.players.containsKey(playerType);
  }

  @Override
  public String getPlayersAsString() {
    return this.players.toString();
  }
}
