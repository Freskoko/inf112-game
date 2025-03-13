package inf112.firegirlwaterboy.model;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import inf112.firegirlwaterboy.model.entity.Collectable;
import inf112.firegirlwaterboy.model.entity.EntityList;
import inf112.firegirlwaterboy.model.entity.Player;
import inf112.firegirlwaterboy.model.entity.PlayerType;

public class MyContactListener implements ContactListener {

  private EntityList<PlayerType, Player> players;

  public MyContactListener(EntityList<PlayerType, Player> players) {
    this.players = players;
  }

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
    for (Player player : players) {

      if (!isPlayer(a, b, player)) {
        continue;
      }
      // isPlayerFootSensor does not work as intended, might not need it anyway
      /*
       * if (isPlayerFootSensor(a, b, player)) {
       * System.out.println("it is foot");
       * if (isHorizontal(a, b)) {
       * System.out.println("cs: " + contactStatus);
       * player.setOnGround(contactStatus);
       * }
       * }
       */
      if (isHorizontal(a, b)) {
        player.setOnGround(contactStatus);
      } else if (isVertical(a, b)) {
        player.setTouchingWall(contactStatus);
      } else if (isCollectable(a, b, player)) {
        System.out.println("collectable");
      }
    }
  }

  /*
   * private boolean isPlayerFootSensor(Fixture a, Fixture b, Player player) {
   * boolean footA = "FOOT_SENSOR".equals(a.getUserData()) && a.getBody() ==
   * player.getBody();
   * boolean footB = "FOOT_SENSOR".equals(b.getUserData()) && b.getBody() ==
   * player.getBody();
   * return (footA || footB) && (isHorizontal(b, a) || isHorizontal(a, b));
   * }
   */

  private boolean isPlayer(Fixture a, Fixture b, Player player) {
    return (a.getBody() == player.getBody() || b.getBody() == player.getBody());
  }

  private boolean isCollectable(Fixture a, Fixture b, Player player) {
    if (a.getUserData() instanceof Collectable collectable) {
      return canCollect(player, collectable);
    }
    if (b.getUserData() instanceof Collectable collectable) {
      return canCollect(player, collectable);
    }
    return false;
  }

  private boolean canCollect(Player player, Collectable collectable) {
    if (player.getPlayerType() == collectable.getRequiredPlayer()) {
      player.collect();
      collectable.collect();
      return true;
    }
    return false;
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
