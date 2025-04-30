package inf112.firegirlwaterboy.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import inf112.firegirlwaterboy.model.maps.LayerType;
import inf112.firegirlwaterboy.model.maps.MapUtils;
import inf112.firegirlwaterboy.model.types.ElementType;

/**
 * Element class represents an element in the game.
 * Elements are static objects that can be interacted with by players.
 */
public class Element implements IEntity<ElementType> {
  private Animation<TextureRegion> animation;
  private float stateTime = 0f;
  private ElementType type;
  private Body body;
  private float x, y, width, height;
  private Vector2 pos;

  public Element(World world, MapObject element) {
    this.type = ElementType.valueOf(MapUtils.getProperty(element, "type"));
    this.animation = createAnimation(type);

    this.width = MapUtils.getWidth(element);
    this.height = MapUtils.getHeight(element);
    this.x = MapUtils.getX(element);
    this.y = MapUtils.getY(element);
    this.pos = new Vector2(MapUtils.getCX(element), MapUtils.getCY(element));

    createBody(world, pos);
  }

  @Override
  public void update() {
    stateTime += Gdx.graphics.getDeltaTime();
  }

  @Override
  public void draw(Batch batch) {
    TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
    batch.draw(currentFrame, x, y, width, height);
  }

  @Override
  public void dispose() {
    for (TextureRegion frame : animation.getKeyFrames()) {
      frame.getTexture().dispose();
    }
  }

  @Override
  public ElementType getType() {
    return type;
  }

  /**
   * Creates an animation for the element based on its type.
   *
   * @param type The type of the element.
   * @return The animation for the element.
   */
  private Animation<TextureRegion> createAnimation(ElementType type) {
    String[] paths = type.getTexturePaths();
    Array<TextureRegion> frames = new Array<>();
    for (int i = 0; i < paths.length; i++) {
      Texture texture = new Texture(Gdx.files.internal(paths[i]));
      TextureRegion region = new TextureRegion(texture);
      frames.add(new TextureRegion(region));
    }
    return new Animation<TextureRegion>(0.3f, frames, Animation.PlayMode.LOOP);
  }

  /**
   * Creates the body of the element in the given world.
   *
   * @param world The world in which the element will be created
   * @param pos The position of the element in the world
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
    fdef.filter.categoryBits = LayerType.ELEMENT.getBit();
    fdef.filter.maskBits = LayerType.PLAYER.getBit();

    Fixture fixture = body.createFixture(fdef);
    fixture.setSensor(true);
    fixture.setUserData(this);

    shape.dispose();
  }
}
