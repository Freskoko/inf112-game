package inf112.firegirlwaterboy.model.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.model.maps.Maps;
import inf112.firegirlwaterboy.model.types.PlayerType;

public class Collectable implements IEntity<String>, ICollectable {

  private PlayerType requiredPlayer;
  private Body body;
  private World world;

  public Collectable(World world, MapObject collectable) {
    float width = Maps.getWidth(collectable);
    float height = Maps.getHeight(collectable);
    this.requiredPlayer = PlayerType.valueOf(Maps.getProperty(collectable, "PlayerType"));
    this.world = world;

    BodyDef bdef = new BodyDef();
    bdef.position.set(Maps.getCX(collectable), Maps.getCY(collectable));
    bdef.type = BodyDef.BodyType.StaticBody;
    body = world.createBody(bdef);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(width / 2, height / 2);
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
    return "Collectable for " + requiredPlayer;
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
  public void dispose() {
    // TODO Auto-generated method stub
  }

  @Override
  public Body getBody() {
    return body;
  }

  @Override
  public String getType() {

    return "Collectable";
    // In future multiple collectable types are added
  }
}
