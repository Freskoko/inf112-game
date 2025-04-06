package inf112.firegirlwaterboy.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.controller.MovementType;
import inf112.firegirlwaterboy.model.maps.MapsFactory;
import inf112.firegirlwaterboy.model.types.ElementType;

public class Platform implements IEntity<ElementType>{

  private ElementType type;
  private int speed = 3;
  private World world;
  private Body body;
  private MovementType dir;
  private Texture texture;

  public Platform(World world, MapObject platform) {
    float x = MapsFactory.getCX(platform);
    float y = MapsFactory.getCY(platform);
    float width = MapsFactory.getWidth(platform);
    float height = MapsFactory.getHeight(platform);

    this.type = ElementType.valueOf(MapsFactory.getProperty(platform, "type"));
    this.texture = new Texture(Gdx.files.internal(type.getTexturePath()));
    this.dir = MovementType.valueOf(MapsFactory.getProperty(platform, "dir"));
    this.world = world;
    BodyDef bdef = new BodyDef();
    bdef.position.set(x, y);
    bdef.type = BodyDef.BodyType.DynamicBody;
    body = world.createBody(bdef);
    body.setGravityScale(0);

    
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(width / 2, height / 2);
    FixtureDef fdef = new FixtureDef();
    fdef.shape = shape;
    fdef.friction = 0.1f;
    body.createFixture(fdef).setUserData(this);
    shape.dispose();
  }

  @Override
  public void update() {

    if (body.getLinearVelocity().equals(new Vector2(0, 0))) {
      collision();
    }
    switch (dir) {
      case UP -> body.setLinearVelocity(0, speed);
      case DOWN -> body.setLinearVelocity(0, -speed);
      case LEFT -> body.setLinearVelocity(-speed, 0);
      case RIGHT -> body.setLinearVelocity(speed, 0);
      default -> body.setLinearVelocity(0, 0);
    }
  }

  @Override
  public void draw(Batch batch) {
    float x = body.getPosition().x;
    float y = body.getPosition().y;
    float width = texture.getWidth() / MapsFactory.PPM;
    float height = texture.getHeight() / MapsFactory.PPM;

    batch.draw(texture, x - width / 2, y - height / 2, width, height);
  }

  @Override
  public void dispose() {
    world.destroyBody(body);
  }

  @Override
  public Body getBody() {
    return body;
  }

  @Override
  public ElementType getType() {
    return type;
  }

  /**
   * Handles collision by changing the movement direction of the platform.
   */
  public void collision(){
    dir = dir.getOppositeDir(dir);
  }

  
}
