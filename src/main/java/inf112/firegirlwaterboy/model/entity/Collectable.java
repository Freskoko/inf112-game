package inf112.firegirlwaterboy.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.model.maps.Maps;

public class Collectable implements IEntity<String>, ICollectable {

  private PlayerType requiredPlayer;
  private Body body;
  private World world;

  public Collectable(World world, MapObject object) {
    float x = Maps.getCX(object);
    float y = Maps.getCY(object);
    float with = Maps.getWidth(object);
    float height = Maps.getHeight(object);

    this.requiredPlayer = PlayerType.valueOf(object.getProperties().get("PlayerType", String.class).toUpperCase());
    this.world = world;

    BodyDef bdef = new BodyDef();
    bdef.position.set(x, y);
    bdef.type = BodyDef.BodyType.StaticBody;
    body = world.createBody(bdef);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox( 8 / Maps.PPM, 8/ Maps.PPM);
    FixtureDef fdef = new FixtureDef();
    fdef.shape = shape;

    Fixture fixture = body.createFixture(fdef);
    fixture.setSensor(true);
    fixture.setUserData(this);
    shape.dispose();
  }

  @Override
  public PlayerType getRequiredPlayer() {
    return requiredPlayer;
  }

  @Override
  public void collect() {
    this.world.destroyBody(body);
    body.setUserData(null);
  }

  @Override
  public String toString() {
    return "collectable for " + requiredPlayer;
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void draw(Batch batch) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'draw'");
  }

  @Override
  public Texture getTexture() {
    return new Texture(Gdx.files.internal("FIREGIRL.png")); // Midlertidig
  }

  @Override
  public Body getBody() {
    return body;
  }

  @Override
  public String getEntityType() {
    return "Collectable";
    // In future multiple collectable types are added
  }
}
