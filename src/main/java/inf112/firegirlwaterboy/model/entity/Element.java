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

import inf112.firegirlwaterboy.model.maps.MapsFactory;
import inf112.firegirlwaterboy.model.types.ElementType;

/**
 * Element class represents an element in the game.
 * Elements are static objects that can be interacted with by players.
 */
public class Element implements IEntity<ElementType> {
  private Texture texture;
  private ElementType type;
  private Body body;
  private float x, y;

  public Element(World world, MapObject element) {
    this.type = ElementType.valueOf(MapsFactory.getProperty(element, "type"));
    this.texture = new Texture(Gdx.files.internal(type.getTexturePath()));

    float width = MapsFactory.getWidth(element);
    float height = MapsFactory.getHeight(element);

    this.x = MapsFactory.getCX(element);
    this.y = MapsFactory.getCY(element);

    BodyDef bdef = new BodyDef();
    bdef.position.set(x, y);
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
  public void update() {
  }

  @Override
  public void draw(Batch batch) {
    batch.draw(texture, x, y);
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
  public ElementType getType() {
    return type;
  }

  public Texture getTexture() {
    return texture;
  }
}
