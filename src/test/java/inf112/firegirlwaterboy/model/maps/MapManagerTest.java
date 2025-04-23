package inf112.firegirlwaterboy.model.maps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.GL20;

import inf112.firegirlwaterboy.app.FireGirlWaterBoy;
import inf112.firegirlwaterboy.model.types.PlayerType;

public class MapManagerTest {

  private IMapManager mapManager;
  private TiledMap map;

  @BeforeAll
  static void setUp() {
    HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
    new HeadlessApplication(new FireGirlWaterBoy(), config);

    GL20 gl20 = mock(GL20.class);
    Gdx.gl = gl20;
    Gdx.gl20 = gl20;
  }

  @BeforeEach
  private void initTest() {
    this.mapManager = new MapManager();
    this.map = mapManager.getMap("map1");
  }

  @Test
  void testGetLayerFails() {
    assertThrows(NullPointerException.class, () -> mapManager.getLayer(map,
        null));
  }

  @Test
  void testGetMapFails() {
    assertThrows(IllegalArgumentException.class, () -> mapManager.getMap("map"));
  }

  @Test
  void testComplete() {
    mapManager.complete("map1");
    assertTrue(mapManager.isComplete("map1"));
  }

  @Test
  void testGetMap() {
    TiledMap map = mapManager.getMap("map1");
    MapLayer layer = mapManager.getLayer(map, LayerType.SPAWN);
    assertEquals(layer.getName(), "SPAWN");
  }

  @Test
  void testIsComplete() {
    assertFalse(mapManager.isComplete("map1"));
    assertFalse(mapManager.isComplete("map2"));
    mapManager.complete("map1");
    assertTrue(mapManager.isComplete("map1"));
    assertFalse(mapManager.isComplete("map2"));
  }

  @Test
  public void testGetMapNonExistent() {
    assertThrows(IllegalArgumentException.class, () -> mapManager.getMap("non-existent-map"));
  }

  @Test
  public void testGetSpawnPosWithEmptySpawnLayer() {
    TiledMap mockMap = mock(TiledMap.class);
    MapLayers mockLayers = mock(MapLayers.class);
    MapLayer mockLayer = mock(MapLayer.class);
    MapObjects mockObjects = mock(MapObjects.class);

    when(mockMap.getLayers()).thenReturn(mockLayers);
    when(mockLayers.get(LayerType.SPAWN.toString())).thenReturn(mockLayer);
    when(mockLayer.getObjects()).thenReturn(mockObjects);
    when(mockObjects.getCount()).thenReturn(0);

    Vector2 result = mapManager.getSpawnPos(mockMap, null);
    assertEquals(new Vector2(2, 2), result);
  }

  @Test
  public void testGetSpawnPosWithMissingProperties() {
    TiledMap mockMap = mock(TiledMap.class);
    MapLayers mockLayers = mock(MapLayers.class);
    MapLayer mockLayer = mock(MapLayer.class);
    MapObjects mockObjects = mock(MapObjects.class);
    MapObject mockObject = mock(MapObject.class);
    MapProperties mockProperties = mock(MapProperties.class);

    when(mockMap.getLayers()).thenReturn(mockLayers);
    when(mockLayers.get(LayerType.SPAWN.toString())).thenReturn(mockLayer);
    when(mockLayer.getObjects()).thenReturn(mockObjects);
    when(mockObjects.getCount()).thenReturn(1);
    when(mockObjects.get(0)).thenReturn(mockObject);
    when(mockObject.getProperties()).thenReturn(mockProperties);

    // Return 0 instead of null to avoid NullPointerException during float unboxing
    when(mockProperties.get("x", Float.class)).thenReturn(0f);
    when(mockProperties.get("y", Float.class)).thenReturn(null);

    assertThrows(NullPointerException.class, () -> mapManager.getSpawnPos(mockMap, PlayerType.FIREGIRL));
  }

  @Test
  void testGetPlayerSpawn() {
    assertEquals(mapManager.getSpawnPos(map, PlayerType.FIREGIRL), new Vector2(2f, 2.5f));
    assertEquals(mapManager.getSpawnPos(map, PlayerType.WATERBOY), new Vector2(2f, 2.5f));
  }

}
