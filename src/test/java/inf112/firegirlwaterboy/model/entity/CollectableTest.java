package inf112.firegirlwaterboy.model.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import inf112.firegirlwaterboy.app.FireGirlWaterBoy;
import inf112.firegirlwaterboy.model.entity.types.PlayerType;

public class CollectableTest {

  private Collectable collectable;
  private World world;
  private MapObject mockMapObject;
  private MapProperties mockProperties;

  @BeforeAll
  static void setUpBeforeAll() {
    HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
    new HeadlessApplication(new FireGirlWaterBoy(), config);

    GL20 gl20 = mock(GL20.class);
    Gdx.gl = gl20;
    Gdx.gl20 = gl20;
  }

  @BeforeEach
  void setUpEachTest() {
    world = new World(new Vector2(0, -9.8f), false);

    mockMapObject = mock(MapObject.class);
    mockProperties = mock(MapProperties.class);

    when(mockMapObject.getProperties()).thenReturn(mockProperties);
    when(mockProperties.get("x", Float.class)).thenReturn(100f);
    when(mockProperties.get("y", Float.class)).thenReturn(100f);
    when(mockProperties.get("width", Float.class)).thenReturn(32f);
    when(mockProperties.get("height", Float.class)).thenReturn(32f);
    when(mockProperties.get("type", String.class)).thenReturn("RED");

    this.collectable = new Collectable(world, mockMapObject);
  }

  /*
   * Verify collectable creation is correct
   */
  @Test
  void testCollectableCreation() {
    assertNotNull(collectable);
  }

  /*
   * Verify collecting a collectable sets the user data to null
   */
  @Test
  void testCollect() {
    assertFalse(collectable.isCollected());
    collectable.collect();
    assertTrue(collectable.isCollected());
  }

  /*
   * Verify collectable creation is correct
   */
  @Test
  void testGetRequiredPlayer() {
    assertTrue(collectable.getRequiredPlayers().contains(PlayerType.FIREGIRL));

    // Test with a different collectable type
    when(mockProperties.get("type", String.class)).thenReturn("blue");
    Collectable waterboyCollectable = new Collectable(world, mockMapObject);
    assertTrue(waterboyCollectable.getRequiredPlayers().contains(PlayerType.WATERBOY));
  }

  /*
   * Test that the toString method works
   */
  @Test
  void testToString() {
    assertEquals("Collectable for [FIREGIRL]", collectable.toString());
  }
}