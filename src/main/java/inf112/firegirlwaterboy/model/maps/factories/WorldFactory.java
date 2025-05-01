package inf112.firegirlwaterboy.model.maps.factories;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.model.GameContactListener;
import inf112.firegirlwaterboy.model.entity.Element;
import inf112.firegirlwaterboy.model.maps.IMapManager;
import inf112.firegirlwaterboy.model.maps.LayerType;
import inf112.firegirlwaterboy.model.maps.MapUtils;
import inf112.firegirlwaterboy.model.entity.Platform;
import inf112.firegirlwaterboy.model.entity.managers.CollectableSet;
import inf112.firegirlwaterboy.model.entity.managers.EntitySet;

/**
 * Manages loading and interacting with tiled maps in the game.
 * Handles map retrieval, layer access, and object creation in a Box2D world.
 */
public class WorldFactory implements IWorldFactory {

  private IGameObjectFactory gameObjectFactory;
  private IMapManager manager;

  public WorldFactory(IGameObjectFactory gameObjectFactory, IMapManager manager) {
    this.gameObjectFactory = gameObjectFactory;
    this.manager = manager;
  }

  @Override
  public World createWorld(TiledMap map) {
    World world = new World(new Vector2(0, -9.8f), true);
    world.setContactListener(new GameContactListener());
    createObjectsInWorld(world, map);
    return world;
  }

  @Override
  public EntitySet<Platform> createPlatforms(World world, TiledMap map) {
    EntitySet<Platform> platforms = new EntitySet<>();
    LayerType layerType = LayerType.PLATFORM;
    MapLayer layer = manager.getLayer(map, layerType);
    for (MapObject object : layer.getObjects()) {
      platforms.add(gameObjectFactory.createPlatform(world, object));
    }
    return platforms;
  }

  @Override
  public EntitySet<Element> createElements(World world, TiledMap map) {
    EntitySet<Element> elements = new EntitySet<>();
    LayerType layerType = LayerType.ELEMENT;
    MapLayer layer = manager.getLayer(map, layerType);
    for (MapObject object : layer.getObjects()) {
      elements.add(gameObjectFactory.createElement(world, object));
    }
    return elements;
  }

  @Override
  public CollectableSet createCollectables(World world, TiledMap map) {
    CollectableSet collectables = new CollectableSet();
    LayerType layerType = LayerType.COLLECTABLE;
    MapLayer layer = manager.getLayer(map, layerType);
    for (MapObject object : layer.getObjects()) {
      collectables.add(gameObjectFactory.createCollectable(world, object));
    }
    return collectables;
  }

  private void createObjectsInWorld(World world, TiledMap map) {
    for (MapLayer layer : map.getLayers()) {
      switch (LayerType.valueOf(layer.getName())) {
        case FINISH -> createFinishObjects(world, layer);
        case STATIC -> createStaticObjects(world, layer);
        default -> {
        }
      }
    }
  }

  /**
   * Creates finish zone objects in the world from a given map layer.
   *
   * @param world The Box2D world where objects should be created.
   * @param layer The map layer containing finish zone objects.
   */
  private void createFinishObjects(World world, MapLayer layer) {
    LayerType layerType = LayerType.valueOf(layer.getName());
    for (MapObject object : layer.getObjects()) {
      BodyDef bdef = new BodyDef();
      bdef.position.set(MapUtils.getCX(object), MapUtils.getCY(object));
      bdef.type = BodyDef.BodyType.StaticBody;
      Body body = world.createBody(bdef);

      PolygonShape shape = new PolygonShape();
      shape.setAsBox(MapUtils.getWidth(object) / 2, MapUtils.getHeight(object) / 2);

      FixtureDef fdef = new FixtureDef();
      fdef.shape = shape;
      fdef.filter.categoryBits = layerType.getBit();
      fdef.filter.maskBits = LayerType.PLAYER.getBit();
      Fixture fixture = body.createFixture(fdef);
      fixture.setSensor(true);
      fixture.setUserData(layerType);
      shape.dispose();
    }
  }

  /**
   * Creates physics objects in the world from a given map layer.
   *
   * @param world The Box2D world where objects should be created.
   * @param layer The map layer containing objects.
   */
  private void createStaticObjects(World world, MapLayer layer) {
    LayerType layerType = LayerType.valueOf(layer.getName());
    for (MapObject object : layer.getObjects()) {
      if (object instanceof PolygonMapObject) {
        createPolygonObject(world, (PolygonMapObject) object, layerType);
      } else {
        createRectangleObject(world, object, layerType);
      }
    }
  }

  /**
   * Creates a polygon object in the world from a given map object.
   *
   * @param world     The Box2D world where the object should be created.
   * @param polygon   The map object to create a polygon from.
   * @param layerType The type of the layer the object belongs to.
   */
  private void createPolygonObject(World world, PolygonMapObject polygon, LayerType layerType) {
    BodyDef bdef = new BodyDef();
    bdef.type = BodyDef.BodyType.StaticBody;

    Body body = world.createBody(bdef);

     float[] vertices = polygon.getPolygon().getTransformedVertices();
    Vector2[] worldVertices = new Vector2[vertices.length / 2];

    for (int i = 0; i < worldVertices.length; i++) {
      worldVertices[i] = new Vector2(vertices[i * 2] / MapUtils.PPM, vertices[i * 2 + 1] / MapUtils.PPM);
    }

    ChainShape shape = new ChainShape();
    shape.createLoop(worldVertices);

    FixtureDef fdef = new FixtureDef();
    fdef.shape = shape;
    fdef.filter.categoryBits = layerType.getBit();
    fdef.filter.maskBits = (short) (LayerType.PLAYER.getBit() | LayerType.PLATFORM.getBit());
    fdef.friction = 0f;
    Fixture fixture = body.createFixture(fdef);
    fixture.setUserData(layerType);
    shape.dispose();
  }

  /**
   * Creates a rectangle object in the world from a given map object.
   *
   * @param world     The Box2D world where the object should be created.
   * @param object    The map object to create a rectangle from.
   * @param layerType The type of the layer the object belongs to.
   */
  private void createRectangleObject(World world, MapObject object, LayerType layerType) {
    BodyDef bdef = new BodyDef();
    bdef.type = BodyDef.BodyType.StaticBody;

    float width = MapUtils.getWidth(object);
    float height = MapUtils.getHeight(object);
    float x = MapUtils.getCX(object);
    float y = MapUtils.getCY(object);

    bdef.position.set(x, y);
    Body body = world.createBody(bdef);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(width / 2, height / 2);

    FixtureDef fdef = new FixtureDef();
    fdef.shape = shape;
    fdef.friction = 0f;
    fdef.filter.categoryBits = layerType.getBit();
    fdef.filter.maskBits = (short) (LayerType.PLAYER.getBit() | LayerType.PLATFORM.getBit());
    Fixture fixture = body.createFixture(fdef);
    fixture.setUserData(layerType);
    shape.dispose();
  }
}
