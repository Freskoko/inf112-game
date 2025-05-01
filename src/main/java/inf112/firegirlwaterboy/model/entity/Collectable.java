package inf112.firegirlwaterboy.model.entity;

import java.util.Set;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.model.entity.types.CollectableType;
import inf112.firegirlwaterboy.model.entity.types.PlayerType;
import inf112.firegirlwaterboy.model.maps.LayerType;
import inf112.firegirlwaterboy.model.maps.MapUtils;

/**
 * Collectable class represents a collectable item in the game.
 * The collectable is a static body that can be collected by players.
 */
public class Collectable implements IEntity<CollectableType>, ICollectable {

  private Set<PlayerType> requiredPlayers;
  private Body body;
  private World world;
  private CollectableType type;
  private Texture texture;
  private float x, y, width, height;
  private boolean isCollected;
  private Vector2 pos;

  public Collectable(World world, MapObject collectable) {
    this.type = CollectableType.valueOf(MapUtils.getProperty(collectable, "type"));
    this.requiredPlayers = type.getRequiredPlayers();
    this.texture = type.getTexture();
    this.world = world;
    this.isCollected = false;

    this.width = MapUtils.getWidth(collectable);
    this.height = MapUtils.getHeight(collectable);
    this.x = MapUtils.getX(collectable);
    this.y = MapUtils.getY(collectable);
    this.pos = new Vector2(MapUtils.getCX(collectable), MapUtils.getCY(collectable));

    createBody(world, pos);
  }

  @Override
  public Set<PlayerType> getRequiredPlayers() {
    return requiredPlayers;
  }

  @Override
  public String toString() {
    return "Collectable for " + requiredPlayers;
  }

  @Override
  public void update() {
    if (isCollected)
      world.destroyBody(body);
  }

  @Override
  public void draw(Batch batch) {
    batch.draw(texture, x, y, width, height);
  }

  @Override
  public void dispose() {
    texture.dispose();
  }

  @Override
  public CollectableType getType() {
    return type;
  }

  @Override
  public boolean isCollected() {
    return isCollected;
  }

  @Override
  public void collect() {
    this.isCollected = true;
  }

  /**
   * Creates the body of the collectable in the given world.
   *
   * @param world The world in which the collectable will be created
   * @param pos   The position of the collectable in the world
   */
  private void createBody(World world, Vector2 pos) {
    BodyDef bdef = new BodyDef();
    bdef.position.set(pos);
    bdef.type = BodyDef.BodyType.StaticBody;
    this.body = world.createBody(bdef);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(width / 2, height / 2);

    FixtureDef fdef = new FixtureDef();
    fdef.shape = shape;
    fdef.filter.categoryBits = LayerType.COLLECTABLE.getBit();
    fdef.filter.maskBits = LayerType.PLAYER.getBit();

    Fixture fixture = body.createFixture(fdef);
    fixture.setSensor(true);
    fixture.setUserData(this);

    shape.dispose();
  }
}