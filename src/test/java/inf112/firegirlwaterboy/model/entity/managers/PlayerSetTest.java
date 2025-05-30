package inf112.firegirlwaterboy.model.entity.managers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;

import inf112.firegirlwaterboy.app.FireGirlWaterBoy;
import inf112.firegirlwaterboy.model.entity.Player;
import inf112.firegirlwaterboy.model.entity.types.PlayerType;

public class PlayerSetTest {

  private PlayerSet playerSet;

  @BeforeAll
  private static void setUp() {
    HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
    new HeadlessApplication(new FireGirlWaterBoy(), config);

    GL20 gl20 = mock(GL20.class);
    Gdx.gl = gl20;
    Gdx.gl20 = gl20;
  }

  @BeforeEach
  private void setUpEachTest() {
    this.playerSet = new PlayerSet();
  }

  @Test
  void testAddEntity() {
    assertTrue(this.playerSet.isEmpty());
    playerSet.add(new Player(PlayerType.FIREGIRL));
    assertFalse(playerSet.isEmpty());
  }

  @Test
  void testContainsPlayer() {
    playerSet.add(new Player(PlayerType.FIREGIRL));
    assertTrue(playerSet.contains(PlayerType.FIREGIRL));
    assertFalse(playerSet.contains(PlayerType.WATERBOY));
  }

  @Test
  void testGetPlayer() {
    playerSet.add(new Player(PlayerType.FIREGIRL));
    Player player1 = playerSet.getPlayer(PlayerType.FIREGIRL);
    assertEquals(player1.getType(), PlayerType.FIREGIRL);

    assertThrows(IllegalArgumentException.class, () -> playerSet.getPlayer(PlayerType.WATERBOY));
  }

  @Test
  void testIsEmpty() {
    assertTrue(playerSet.isEmpty());
    playerSet.add(new Player(PlayerType.FIREGIRL));
    assertFalse(playerSet.isEmpty());
  }

  @Test
  void testToString() {
    playerSet.add(new Player(PlayerType.FIREGIRL));
    assertEquals("FIREGIRL", playerSet.toString());
  }

  @Test
  void testPlayersAreNotFinishedAtStart() {
    assertTrue(playerSet.areFinished()); 
    playerSet.add(new Player(PlayerType.FIREGIRL));
    assertFalse(playerSet.areFinished());
  }
}