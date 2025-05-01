package inf112.firegirlwaterboy.model.maps.factories;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.model.entity.Collectable;
import inf112.firegirlwaterboy.model.entity.Element;
import inf112.firegirlwaterboy.model.entity.Platform;

/**
 * Standard implementation of the IGameObjectFactory interface.
 * This factory creates game objects such as platforms, elements, and collectables.
 */
public class StandardGameObjectsFactory implements IGameObjectFactory {

  @Override
  public Platform createPlatform(World world, MapObject object) {
    return new Platform(world, object);
  }

  @Override
  public Element createElement(World world, MapObject object) {
    return new Element(world, object);
  }

  @Override
  public Collectable createCollectable(World world, MapObject object) {
    return new Collectable(world, object);
  }
}