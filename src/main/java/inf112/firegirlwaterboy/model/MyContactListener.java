package inf112.firegirlwaterboy.model;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import inf112.firegirlwaterboy.model.Entity.EntityList;
import inf112.firegirlwaterboy.model.Entity.Player;
import inf112.firegirlwaterboy.model.Entity.PlayerType;

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
      if (isPlayerFootSensor(a, b, player)) {
        if (isGround(a, b)) {
          player.setOnGround(contactStatus);
        }
      }
      if (isWall(a, b)) {
        player.setTouchingWall(contactStatus);
      }
    }
  }

  private boolean isPlayerFootSensor(Fixture a, Fixture b, Player player) {
    return ("FOOT_SENSOR".equals(a.getUserData()) && a.getBody() == player.getBody()) ||
        ("FOOT_SENSOR".equals(b.getUserData()) && b.getBody() == player.getBody());
  }

  private boolean isWall(Fixture a, Fixture b) {
    return "WALL".equals(a.getUserData()) || "WALL".equals(b.getUserData());
  }

  private boolean isGround(Fixture a, Fixture b) {
    return "GROUND".equals(a.getUserData()) || "GROUND".equals(b.getUserData());
  }

  @Override
  public void postSolve(Contact arg0, ContactImpulse arg1) {
  }

  @Override
  public void preSolve(Contact arg0, Manifold arg1) {
  }

}
