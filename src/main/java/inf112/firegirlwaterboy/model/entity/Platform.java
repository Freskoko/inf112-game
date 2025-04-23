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
import inf112.firegirlwaterboy.model.maps.LayerType;
import inf112.firegirlwaterboy.model.maps.MapUtils;
import inf112.firegirlwaterboy.model.types.ElementType;

public class Platform implements IEntity<ElementType> {

  private ElementType type;
  private int speed = 3;
  private World world;
  private Body body;
  private MovementType dir;
  private Texture texture;
  private float width, height;

  public Platform(World world, MapObject platform) {
    this.width = MapUtils.getWidth(platform);
    this.height = MapUtils.getHeight(platform);
    this.type = ElementType.valueOf(MapUtils.getProperty(platform, "type"));
    this.texture = new Texture(Gdx.files.internal(type.getTexturePath()));
    this.dir = MovementType.valueOf(MapUtils.getProperty(platform, "dir"));
    this.world = world;

    BodyDef bdef = new BodyDef();
    bdef.position.set(MapUtils.getCX(platform), MapUtils.getCY(platform));
    bdef.type = BodyDef.BodyType.DynamicBody;
    bdef.fixedRotation = true;
    body = world.createBody(bdef);
    body.setGravityScale(0);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(width / 2, height / 2);
    FixtureDef fdef = new FixtureDef();
    fdef.shape = shape;
    fdef.filter.categoryBits = LayerType.PLATFORM.getBit();
    fdef.filter.maskBits = (short) (LayerType.STATIC.getBit() | LayerType.PLAYER.getBit());
    fdef.friction = 0.5f;
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
    float x = body.getPosition().x - width / 2;
    float y = body.getPosition().y - height / 2;
    batch.draw(texture, x, y, width, height);
  }

  @Override
  public void dispose() {
    world.destroyBody(body);
  }

  /**
   * Returns the body of the platform.
   * 
   * @return The body of the platform.
   */
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
  public void collision() {
    dir = MovementType.getOppositeDir(dir);
  }

}
