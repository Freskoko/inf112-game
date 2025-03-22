package inf112.firegirlwaterboy.model;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import inf112.firegirlwaterboy.model.entity.ElementType;
import inf112.firegirlwaterboy.model.entity.Collectable;
import inf112.firegirlwaterboy.model.entity.Player;

/**
 * MyContactListener is a class that listens for contacts between fixtures in
 * the game world.
 */
public class MyContactListener implements ContactListener {

  @Override
  public void beginContact(Contact contact) {
    playerCollision(contact, true);
  }

  @Override
  public void endContact(Contact contact) {
    playerCollision(contact, false);
  }

  /**
   * Handles collision between player and other objects.
   * 
   * @param contact the contact between two fixtures
   * @param contactStatus true if the contact is beginning, false if it is ending
   */
  private void playerCollision(Contact contact, Boolean contactStatus) {
    Fixture a = contact.getFixtureA();
    Fixture b = contact.getFixtureB();
    Player player = getPlayer(a, b);

    if (player != null) {
      if (isHorizontal(a, b)) {
        player.setOnGround(contactStatus);
      }
      if (isVertical(a, b)) {
        player.setTouchingWall(contactStatus);
      }

       {
        Collectable collectable = getCollectable(a, b);
        if (collectable != null && canCollect(player, collectable)) {
          player.collect(collectable);
        }
      

      ElementType elementType = getElementType(a, b);
      if (elementType != null && contactStatus) {
        player.interactWithElement(elementType);
      }
    }
  }

  /**
   * Returns the element type of the fixture that is in contact with the player.
   * 
   * @param a the first fixture
   * @param b the second fixture
   * @return the element type of the fixture in contact with the player
   */
  private ElementType getElementType(Fixture a, Fixture b) {
    if (a.getUserData().equals(ElementType.LAVA) || a.getUserData().equals(ElementType.WATER)) {
      return (ElementType) a.getUserData();
    } else if (b.getUserData().equals(ElementType.LAVA) || b.getUserData().equals(ElementType.WATER)) {
      return (ElementType) b.getUserData();
    }
    return null;
  }

  /**
   * Returns true if one of the given fixtures is a player.
   * 
   * @param a the first fixture
   * @param b the second fixture
   * @param player the player to compare with the fixtures
   * @return true if one of the fixtures is a player
   */
  private boolean isPlayer(Fixture a, Fixture b, Player player) {
    return (a.getBody().equals(player.getBody()) || b.getBody().equals(player.getBody()));
  }

  /**
   * Returns true if one of the given fixtures is a horizontal fixture.
   * 
   * @param a the first fixture
   * @param b the second fixture
   * @return true if one of the fixtures is a horizontal fixture
   */
  private boolean isVertical(Fixture a, Fixture b) {
    return "Vertical".equals(a.getUserData()) || "Vertical".equals(b.getUserData());
  }

  /**
   * Returns true if one of the given fixtures is a vertical fixture.
   * 
   * @param a the first fixture
   * @param b the second fixture
   * @return true if one of the fixtures is a vertical fixture
   */
  private boolean isHorizontal(Fixture a, Fixture b) {
    return "Horizontal".equals(a.getUserData()) || "Horizontal".equals(b.getUserData());
  }

  @Override
  public void postSolve(Contact arg0, ContactImpulse arg1) {
  }

  @Override
  public void preSolve(Contact arg0, Manifold arg1) {
  }

  
  /**
   * Determines if a player can collect a given collectable item based on their type.
   *
   * @param player the player attempting to collect the item
   * @param collectable the collectable item
   * @return true if the player's type matches the collectable's required type; false otherwise
   */
  private boolean canCollect(Player player, Collectable collectable) {
    return player.getPlayerType() == collectable.getRequiredPlayer();
  }

   /**
   * Retrieves the Collectable object involved in the collision between two fixtures.
   *
   * @return the Collectable object if found; null otherwise
   */
  private Collectable getCollectable(Fixture a, Fixture b) {
    if (a.getUserData() instanceof Collectable collectable) {
      return collectable;
    } else if (b.getUserData() instanceof Collectable collectable) {
      return collectable;
    }
    return null;
  }

  private Player getPlayer(Fixture a, Fixture b) {
    if (a.getUserData() instanceof Player player) {
      return player;
    }
    if (b.getUserData() instanceof Player player) {
      return player;
    }
    return null;
  }
}
