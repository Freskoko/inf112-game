package inf112.firegirlwaterboy.model;

import javax.annotation.processing.Generated;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import inf112.firegirlwaterboy.model.entity.Collectable;
import inf112.firegirlwaterboy.model.entity.Element;
import inf112.firegirlwaterboy.model.entity.IEntity;
import inf112.firegirlwaterboy.model.entity.IPlatform;
import inf112.firegirlwaterboy.model.entity.Platform;
import inf112.firegirlwaterboy.model.entity.Player;
import inf112.firegirlwaterboy.model.maps.LayerType;

/**
 * GameContactListener listens for contact events in the physics world.
 */
public class GameContactListener implements ContactListener {

  @Override
  public void beginContact(Contact contact) {
    hanldePlayerContact(contact, true);
    handleplatformCollision(contact);
  }

  @Override
  public void endContact(Contact contact) {
    hanldePlayerContact(contact, false);
  }

  /**
   * Handles collision between platforms and other objects.
   * 
   * @param contact The contact between the two fixtures
   */
  private void handleplatformCollision(Contact contact) {
    Object a = contact.getFixtureA().getUserData();
    Object b = contact.getFixtureB().getUserData();

    IPlatform platform = getEntity(a, b, Platform.class);
    if (platform != null && isLayerType(a, b, LayerType.STATIC)) {
      platform.collision();
    }
  }

  /**
   * Handles collision between player and other objects.
   * 
   * @param contact       The contact between the two fixtures
   * @param contactStatus True if the contact is beginning, false if it is ending
   */
  private void hanldePlayerContact(Contact contact, Boolean contactStatus) {
    Object a = contact.getFixtureA().getUserData();
    Object b = contact.getFixtureB().getUserData();

    Player player = getEntity(a, b, Player.class);
    if (player == null)
      return;

    Collectable collectable = getEntity(a, b, Collectable.class);
    if (collectable != null) {
      if (contactStatus) {
        player.interactWithCollectable(collectable);
      }
    }

    Element element = getEntity(a, b, Element.class);
    if (element != null) {
      if (contactStatus) {
        player.interactWithElement(element);
      }
    }

    if (isLayerType(a, b, LayerType.FINISH)) {
      player.setFinished(contactStatus);
    }
  }

  /**
   * Checks if either of the given objects is of the specified layer type.
   */
  private boolean isLayerType(Object a, Object b, LayerType layerType) {
    return a == layerType || b == layerType;
  }

  /**
   * Retrieves an entity of the specified type from two given objects.
   *
   * @param <T>   The type of entity to retrieve, extending IEntity<?>
   * @param a     The first object to check
   * @param b     The second object to check
   * @param clazz The class type of the entity to retrieve
   * @return An instance of T if one of the objects matches the specified type, otherwise null
   */
  private <T extends IEntity<?>> T getEntity(Object a, Object b, Class<T> clazz) {
    if (clazz.isInstance(a)) {
      return clazz.cast(a);
    }
    if (clazz.isInstance(b)) {
      return clazz.cast(b);
    }
    return null;
  }

  @Override
  @Generated("interface-stub")
  public void postSolve(Contact arg0, ContactImpulse arg1) {}

  @Override
  @Generated("interface-stub")
  public void preSolve(Contact arg0, Manifold arg1) {}
}