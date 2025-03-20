package inf112.firegirlwaterboy.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.model.maps.Maps;

public class Element implements IEntity {
  private Texture texture;
  private ElementType type;
  //private Vector2 position;
  private Body body;
  private World world;
  private Vector2 position, size;

  
  public Element(ElementType type, World world, Vector2 position, Vector2 size) {
    this.type = type;
    this.texture = new Texture(Gdx.files.internal(type.getTexturePath()));
    this.world = world;
    this.position = position;
    this.size = size;

    float width = size.x / 2 / Maps.PPM;
    float height = size.y / 2 / Maps.PPM;


    BodyDef bdef = new BodyDef();
    bdef.position.set(position.x, position.y);
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
    batch.draw(texture, position.x, position.y);
  }

  @Override
  public Texture getTexture() {
    return texture;
  }

  public ElementType getType() {
    return type;
  }
}

