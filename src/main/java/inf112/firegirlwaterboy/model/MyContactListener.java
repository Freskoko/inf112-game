package inf112.firegirlwaterboy.model;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import inf112.firegirlwaterboy.model.entity.Collectable;
import inf112.firegirlwaterboy.model.entity.Player;

public class MyContactListener implements ContactListener {

  @Override
  public void beginContact(Contact contact) {
    playerCollision(contact, true);
  }

  @Override
  public void endContact(Contact contact) {
    playerCollision(contact, false);
  }

  private void playerCollision(Contact contact, Boolean contactStatus) {
    Fixture a = contact.getFixtureA();
    Fixture b = contact.getFixtureB();
    Player player = getPlayer(a, b);

    if (player != null) {
      if (isHorizontal(a, b)) {
        player.setOnGround(contactStatus);
      } else if (isVertical(a, b)) {
        player.setTouchingWall(contactStatus);
      } else {
        Collectable collectable = getCollectable(a, b);
        if (collectable != null && canCollect(player, collectable)) {
          player.collect(collectable);
        }
      }
    }
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

  private boolean isVertical(Fixture a, Fixture b) {
    return "Vertical".equals(a.getUserData()) || "Vertical".equals(b.getUserData());
  }

  private boolean isHorizontal(Fixture a, Fixture b) {
    return "Horizontal".equals(a.getUserData()) || "Horizontal".equals(b.getUserData());
  }

  @Override
  public void postSolve(Contact arg0, ContactImpulse arg1) {
  }

  @Override
  public void preSolve(Contact arg0, Manifold arg1) {
  }
}
