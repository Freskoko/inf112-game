package inf112.firegirlwaterboy.model.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.model.maps.Maps;

public class Collectable {

  private boolean collected;
  private PlayerType requiredPlayer;
  private Body body;
  private World world;

  public Collectable(PlayerType requiredPlayer, World world, float x, float y) {
    this.requiredPlayer = requiredPlayer;
    this.collected = false;

    BodyDef bdef = new BodyDef();
    bdef.position.set(x, y);
    bdef.type = BodyDef.BodyType.StaticBody;
    body = world.createBody(bdef);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(8 / Maps.PPM, 8 / Maps.PPM); 
    FixtureDef fdef = new FixtureDef();
    fdef.shape = shape;
    Fixture fixture = body.createFixture(fdef);
    fixture.setUserData(this); 
    shape.dispose();
  }

  public PlayerType getRequiredPlayer() {
    return requiredPlayer;
  }

  public void collect() {
    if (!collected) {
      collected = true;
      this.world.destroyBody(body); // Remove from the world
      body.setUserData(null);
    }
  }

}
