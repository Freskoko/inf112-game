package inf112.firegirlwaterboy.model;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import inf112.firegirlwaterboy.model.entity.ElementType;
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
      /*if (isPlayerFootSensor(a, b, player)) {
        player.setOnGround(contactStatus);
        System.out.println("it is foot");
        if (isHorizontal(a, b)) {
          System.out.println("cs: " + contactStatus);
          player.setOnGround(contactStatus);
        }
      }*/
      if (isHorizontal(a, b)) {
        player.setOnGround(contactStatus);
      } else if (isVertical(a, b)) {
        player.setTouchingWall(contactStatus);
      }

      ElementType elementType = getElementType(a, b);
      //System.out.println("Element type: " + elementType);
      //System.out.println("fix a: " + a.getUserData() + " type: " + a.getUserData().getClass());
      //System.out.println("fix b: " + b.getUserData() + " type: " + b.getUserData().getClass());
      if (elementType != null && contactStatus) {
        player.interactWithElement(elementType);
      }
            

      /*if (isHazard(a, b)) {
        String hazard = getHazardType(a, b);
        System.out.println("Hazard: " + hazard);
        player.handleHazard(hazard);
      }*/
    }
  }

  /*private boolean isPlayerFootSensor(Fixture a, Fixture b, Player player) {
    boolean footA = "FOOT_SENSOR".equals(a.getUserData()) && a.getBody() == player.getBody();
    boolean footB = "FOOT_SENSOR".equals(b.getUserData()) && b.getBody() == player.getBody();
    //return (footA || footB) && (isHorizontal(b, a) || isHorizontal(a, b));
    return footA || footB;
  }*/

  private ElementType getElementType(Fixture a, Fixture b) {
    if (a.getUserData().equals(ElementType.LAVA) || a.getUserData().equals(ElementType.WATER)) {
      return (ElementType) a.getUserData();
    } else if (b.getUserData().equals(ElementType.LAVA) || b.getUserData().equals(ElementType.WATER)) {
      return (ElementType) b.getUserData();
    }
    return null;
  }

  private boolean isPlayer(Fixture a, Fixture b, Player player) {
    return (a.getBody().equals(player.getBody()) || b.getBody().equals(player.getBody()));
  }

  private boolean isVertical(Fixture a, Fixture b) {
    return "Vertical".equals(a.getUserData()) || "Vertical".equals(b.getUserData());
  }

  private boolean isHorizontal(Fixture a, Fixture b) {
    return "Horizontal".equals(a.getUserData()) || "Horizontal".equals(b.getUserData());
  }

  /*private boolean isHazard(Fixture a, Fixture b) {
    return "lava".equals(a.getUserData()) || "water".equals(b.getUserData()) || "lava".equals(b.getUserData()) || "water".equals(a.getUserData());
  }

  private String getHazardType(Fixture a, Fixture b) {
    if ("lava".equals(a.getUserData()) || "water".equals(a.getUserData())) {
      return (String) a.getUserData();
    } else if ("lava".equals(b.getUserData()) || "water".equals(b.getUserData())) {
      return (String) b.getUserData();
    }
    return null;
  }*/

  @Override
  public void postSolve(Contact arg0, ContactImpulse arg1) {
  }

  @Override
  public void preSolve(Contact arg0, Manifold arg1) {
  }

}
