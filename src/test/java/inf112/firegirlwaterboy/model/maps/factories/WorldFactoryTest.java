package inf112.firegirlwaterboy.model.maps.factories;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;

import inf112.firegirlwaterboy.app.FireGirlWaterBoy;

public class WorldFactoryTest {
  //private WorldFactory maps;

  /*
   * Sets up a headless version of the app, reuqired to load map/ images files
   */
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
    //IMapManager manager = new MapManager();
    //this.maps = new WorldFactory(new StandardGameObjectsFactory(), manager);
  }


  // meotde fra maps facotry flyttet til world factory, testene er kopiert inn hit


  // @Test
  // public void testEmptyBodyDoesNotCreateBodies() throws Exception {
  //   java.lang.reflect.Method method = WorldFactory.class.getDeclaredMethod(
  //       "createObjectsFromLayer", World.class, MapLayer.class);
  //   method.setAccessible(true);

  //   World mockWorld = mock(World.class);
  //   MapLayer mockLayer = mock(MapLayer.class);
  //   MapObjects mockObjects = mock(MapObjects.class);

  //   when(mockLayer.getObjects()).thenReturn(mockObjects);
  //   when(mockObjects.iterator()).thenReturn(java.util.Collections.emptyIterator());

  //   Body mockBody = mock(Body.class);
  //   when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);

  //   method.invoke(maps, mockWorld, mockLayer);

  //   verify(mockWorld, never()).createBody(any(BodyDef.class));
  // }

  // @Test
  // public void testEmptyBodyDoesCreateBodies() throws Exception {
  //   java.lang.reflect.Method method = WorldFactory.class.getDeclaredMethod(
  //       "createObjectsFromLayer", World.class, MapLayer.class);
  //   method.setAccessible(true);

  //   World mockWorld = mock(World.class);
  //   MapLayer mockLayer = mock(MapLayer.class);
  //   MapObjects mockObjects = mock(MapObjects.class);
  //   MapObject mockObject = mock(MapObject.class);
  //   MapProperties mockProperties = mock(MapProperties.class);

  //   Body mockBody = mock(Body.class);
  //   Fixture mockFixture = mock(Fixture.class);
  //   when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);
  //   when(mockBody.createFixture(any(FixtureDef.class))).thenReturn(mockFixture);

  //   // setting up a layer with one object
  //   when(mockLayer.getObjects()).thenReturn(mockObjects);
  //   when(mockLayer.getName()).thenReturn("TestLayer"); 
  //   ArrayList<MapObject> objectList = new ArrayList<>();
  //   objectList.add(mockObject);
  //   when(mockObjects.iterator()).thenReturn(objectList.iterator());

  //   // setting up properties of layer
  //   when(mockObject.getProperties()).thenReturn(mockProperties);
  //   when(mockProperties.get("x", Float.class)).thenReturn(100f);
  //   when(mockProperties.get("y", Float.class)).thenReturn(200f);
  //   when(mockProperties.get("width", Float.class)).thenReturn(50f);
  //   when(mockProperties.get("height", Float.class)).thenReturn(30f);

  //   method.invoke(maps, mockWorld, mockLayer);

  //   verify(mockWorld, times(1)).createBody(any(BodyDef.class));

  //   verify(mockBody, times(1)).createFixture(any(FixtureDef.class));

  //   verify(mockFixture).setUserData("TestLayer");
  // }

 

}