package inf112.firegirlwaterboy.model;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import inf112.firegirlwaterboy.model.entity.IEntity;
import inf112.firegirlwaterboy.model.entity.Platform;
import inf112.firegirlwaterboy.model.entity.Collectable;
import inf112.firegirlwaterboy.model.entity.Element;
import inf112.firegirlwaterboy.model.entity.Player;

/**
 * MyContactListener is a class that listens for contacts between fixtures in
 * the game world.
 */
public class MyContactListener implements ContactListener {

  @Override
  public void beginContact(Contact contact) {
    playerCollision(contact, true);
    platformCollision(contact);

  }

  @Override
  public void endContact(Contact contact) {
    playerCollision(contact, false);
  }

  /**
   * Handles collision between platforms and other objects.
   * 
   * @param contact the contact between two fixtures
   */
  private void platformCollision(Contact contact) {
    Object a = contact.getFixtureA().getUserData();
    Object b = contact.getFixtureB().getUserData();

    Platform platform = getEntity(a, b, Platform.class);
    if (platform != null) {
      if (isTarget(a, b, "Verticle") || isTarget(a, b, "Horizontal")) {
        platform.collision();
      }
    }
  }

  /**
   * Handles collision between player and other objects.
   * 
   * @param contact       the contact between two fixtures
   * @param contactStatus true if the contact is beginning, false if it is ending
   */
  private void playerCollision(Contact contact, Boolean contactStatus) {
    Object a = contact.getFixtureA().getUserData();
    Object b = contact.getFixtureB().getUserData();

    Player player = getEntity(a, b, Player.class);

    if (player != null) {
      if (isTarget(a, b, "Horizontal")) {
        player.setOnGround(contactStatus);
      }

      if (isTarget(a, b, "Edges")) {
        player.setTouchingEdge(contactStatus);
      }

      if (isTarget(a, b, "Finish")) {
        player.setFinished(contactStatus);
      }

      Collectable collectable = getEntity(a, b, Collectable.class);
      if (collectable != null  && contactStatus) {
        player.interactWithCollectable(collectable);
      }

      Element element = getEntity(a, b, Element.class);
      if (element != null && contactStatus) {
        player.interactWithElement(element.getType());
      }

      Platform platform = getEntity(a, b, Platform.class);
      if (platform != null && contactStatus) {
        player.setOnPlatform(platform);
      }
    }
  }

  /**
   * Checks if either of the given objects matches the specified target string.
   *
   * @param a      the first object
   * @param b      the second object
   * @param target the string to compare against
   * @return true if either object matches the target string
   */
  private boolean isTarget(Object a, Object b, String target) {
    return target.equals(a) || target.equals(b);
  }

  @Override
  public void postSolve(Contact arg0, ContactImpulse arg1) {
  }

  @Override
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
