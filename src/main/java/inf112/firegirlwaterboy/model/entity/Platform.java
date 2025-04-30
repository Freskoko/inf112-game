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

/**
 * Platform class represents a moving platform in the game.
 * The platform is a dynamic body that moves in a specified direction.
 */
public class Platform implements IEntity<ElementType>, IPlatform {

  private ElementType type;
  private int speed = 3;
  private World world;
  private Body body;
  private MovementType dir;
  private Texture texture;
  private float width, height;
  private Vector2 pos;

  public Platform(World world, MapObject platform) {
    this.type = ElementType.valueOf(MapUtils.getProperty(platform, "type"));
    this.texture = new Texture(Gdx.files.internal(type.getTexturePaths()[0]));
    this.dir = MovementType.valueOf(MapUtils.getProperty(platform, "dir"));
    this.world = world;

    this.width = MapUtils.getWidth(platform);
    this.height = MapUtils.getHeight(platform);
    this.pos = new Vector2(MapUtils.getCX(platform), MapUtils.getCY(platform));

    createBody(world, pos);
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

  @Override
  public ElementType getType() {
    return type;
  }

  @Override
  public Body getBody() {
    return body;
  }

  @Override
  public void collision() {
    dir = MovementType.getOppositeDir(dir);
  }

  /**
   * Creates the body of the platform in the given world.
   *
   * @param world The world in which the platform will be created
   * @param pos   The position of the platform in the world
   */
  private void createBody(World world, Vector2 pos) {
    BodyDef bdef = new BodyDef();
    bdef.position.set(pos);
    bdef.type = BodyDef.BodyType.DynamicBody;
    bdef.fixedRotation = true;
    this.body = world.createBody(bdef);
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
}