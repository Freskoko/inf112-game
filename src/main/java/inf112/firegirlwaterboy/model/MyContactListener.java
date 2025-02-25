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
    Fixture fixtureA = contact.getFixtureA();
    Fixture fixtureB = contact.getFixtureB();

    Object dataA = fixtureA.getUserData();
    Object dataB = fixtureB.getUserData();

    // Check if Player collided with a Wall
    if ((dataA instanceof Player && "WALL".equals(dataB)) || (dataB instanceof Player && "WALL".equals(dataA))) {
      Player player = dataA instanceof Player ? (Player) dataA : (Player) dataB;
      System.out.println(player.getPlayerType() + " hit a wall!");
      //player.stopMoving(); // Custom method to stop movement
    }

    // Check if two Players collided
    if (dataA instanceof Player && dataB instanceof Player) {
      Player player1 = (Player) dataA;
      Player player2 = (Player) dataB;
      System.out.println(player1.getPlayerType() + " collided with " + player2.getPlayerType());
    }
  }

  @Override
  public void endContact(Contact arg0) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'endContact'");
  }

  @Override
  public void postSolve(Contact arg0, ContactImpulse arg1) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'postSolve'");
  }

  @Override
  public void preSolve(Contact arg0, Manifold arg1) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'preSolve'");
  }
  
}
