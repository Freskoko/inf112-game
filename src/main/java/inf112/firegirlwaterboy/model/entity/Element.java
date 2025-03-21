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

/**
 * Element class represents an element in the game.
 * Elements are static objects that can be interacted with by players.
 */
public class Element implements IEntity {
  private Texture texture;
  private ElementType type;
  private Body body;
  private World world;
  private float x, y;
  
  public Element(ElementType type, World world, MapObject object) {
    this.type = type;
    this.texture = new Texture(Gdx.files.internal(type.getTexturePath()));
    this.world = world;
    this.x = Maps.getX(object);
    this.y = Maps.getY(object);

    float width = Maps.getWidth(object) / 2 / Maps.PPM;
    float height = Maps.getHeight(object) / 2 / Maps.PPM;

    BodyDef bdef = new BodyDef();
    bdef.position.set(x, y);
    bdef.type = BodyDef.BodyType.StaticBody;
    body = world.createBody(bdef);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(width, height);
    
    FixtureDef fdef = new FixtureDef();
    fdef.shape = shape;
    Fixture fixture = body.createFixture(fdef);
    fixture.setSensor(true);
    fixture.setUserData(type); 
    shape.dispose();
  }

  @Override
  public void update(float deltaTime) {
  }

  @Override
  public void draw(Batch batch) {
    batch.draw(texture, x, y);
  }

  @Override
  public Texture getTexture() {
    return texture;
  }

  /**
   * Returns the type of the element.
   * 
   * @return the type of the element
   */
  public ElementType getType() {
    return type;
  }
}