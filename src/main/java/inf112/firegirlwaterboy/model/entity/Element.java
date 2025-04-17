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

import inf112.firegirlwaterboy.model.LayerType;
import inf112.firegirlwaterboy.model.maps.MapUtils;
import inf112.firegirlwaterboy.model.types.ElementType;

/**
 * Element class represents an element in the game.
 * Elements are static objects that can be interacted with by players.
 */
public class Element implements IEntity<ElementType> {
  private Texture texture;
  private ElementType type;
  private Body body;
  private float x, y, width, height;

  public Element(World world, MapObject element) {
    this.type = ElementType.valueOf(MapUtils.getProperty(element, "type"));
    this.texture = new Texture(Gdx.files.internal(type.getTexturePath()));

    this.width = MapUtils.getWidth(element);
    this.height = MapUtils.getHeight(element);
    this.x = MapUtils.getX(element);
    this.y = MapUtils.getY(element);

    BodyDef bdef = new BodyDef();
    bdef.position.set(MapUtils.getCX(element), MapUtils.getCY(element));
    bdef.type = BodyDef.BodyType.StaticBody;
    body = world.createBody(bdef);
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

  @Override
  public void update() {
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
  public ElementType getType() {
    return type;
  }

}
