package inf112.firegirlwaterboy.model;

import javax.annotation.processing.Generated;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import inf112.firegirlwaterboy.model.entity.Collectable;
import inf112.firegirlwaterboy.model.entity.Element;
import inf112.firegirlwaterboy.model.entity.IEntity;
import inf112.firegirlwaterboy.model.entity.Platform;
import inf112.firegirlwaterboy.model.entity.Player;

/**
 * MyContactListener listens for contacts events in the physics world.
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
   * @param contact the contact between two fixtures
   */
  private void handleplatformCollision(Contact contact) {
    Object a = contact.getFixtureA().getUserData();
    Object b = contact.getFixtureB().getUserData();

    Platform platform = getEntity(a, b, Platform.class);
    if (platform != null && isLayerType(a, b, LayerType.STATIC)) {
      platform.collision();
    }
  }

  /**
   * Handles collision between player and other objects.
   * 
   * @param contact       the contact between two fixtures
   * @param contactStatus true if the contact is beginning, false if it is ending
   */
  private void hanldePlayerContact(Contact contact, Boolean contactStatus) {
    Object a = contact.getFixtureA().getUserData();
    Object b = contact.getFixtureB().getUserData();

    Player player = getEntity(a, b, Player.class);
    if (player == null)
      return;

    Collectable collectable = getEntity(a, b, Collectable.class);
    if (collectable != null && contactStatus) {
      player.interactWithCollectable(collectable);
      return;
    }

    Element element = getEntity(a, b, Element.class);
    if (element != null && contactStatus) {
      player.interactWithElement(element);
      return;
    }

    if (isLayerType(a, b, LayerType.FINISH)) {
      player.setFinished(contactStatus);
    }

    boolean isGround = isLayerType(a, b, LayerType.STATIC) || getEntity(a, b, Platform.class) != null;
    if (isGround) {
      player.setGroundStatus(contactStatus);
    }
  }

  private boolean isLayerType(Object a, Object b, LayerType layerType) {
    return a == layerType || b == layerType;
  }

  @Override
  @Generated("interface-stub")
  public void postSolve(Contact arg0, ContactImpulse arg1) {
  }

  @Override
  @Generated("interface-stub")
  public void preSolve(Contact arg0, Manifold arg1) {
  }

  /**
   * Retrieves an entity of the specified type from two given objects.
   * This method checks whether either of the objects is an instance of the given
   * class and returns the first matching entity.
   *
   * @param <T>   the type of entity to retrieve, extending {@code IEntity<?>}
   * @param a     the first object to check
   * @param b     the second object to check
   * @param clazz the class type of the entity to retrieve
   * @return an instance of {@code T} if one of the objects matches the specified
   *         type, otherwise {@code null}
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
}
