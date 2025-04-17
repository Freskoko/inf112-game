package inf112.firegirlwaterboy.model.entity;

import java.util.Set;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.model.LayerType;
import inf112.firegirlwaterboy.model.maps.MapUtils;
import inf112.firegirlwaterboy.model.types.CollectableType;
import inf112.firegirlwaterboy.model.types.PlayerType;

public class Collectable implements IEntity<CollectableType>, ICollectable {

  private Set<PlayerType> requiredPlayer;
  private Body body;
  private World world;
  private boolean powerUp;
  private CollectableType type;
  private Texture texture;
  private float x, y, width, height;
  private boolean isCollected;

  public Collectable(World world, MapObject collectable) {
    this.type = CollectableType.valueOf(MapUtils.getProperty(collectable, "type"));
    this.powerUp = type.isPowerUp();
    this.requiredPlayer = type.getRequiredPlayers();
    this.texture = type.getTexture();
    this.world = world;
    this.isCollected = false;

    this.width = MapUtils.getWidth(collectable);
    this.height = MapUtils.getHeight(collectable);
    this.x = MapUtils.getX(collectable);
    this.y = MapUtils.getY(collectable);

    BodyDef bdef = new BodyDef();
    bdef.position.set(MapUtils.getCX(collectable), MapUtils.getCY(collectable));
    bdef.type = BodyDef.BodyType.StaticBody;
    body = world.createBody(bdef);
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

  @Override
  public Set<PlayerType> getRequiredPlayer() {
    return requiredPlayer;
  }

  @Override
  public String toString() {
    return "Collectable for " + requiredPlayer;
  }

  @Override
  public void update() {
    if (isCollected) {
      world.destroyBody(body);
      body.setUserData(null);
    }
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
  public Body getBody() {
    return body;
  }

  @Override
  public CollectableType getType() {
    return type;
  }

  public boolean isPowerUp() {
    return powerUp;
  }

  public boolean isCollected() {
    return isCollected;
  }

  public void collect() {
    this.isCollected = true;
  }
}
