package inf112.firegirlwaterboy.model.maps.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.app.FireGirlWaterBoy;
import inf112.firegirlwaterboy.model.entity.Element;
import inf112.firegirlwaterboy.model.entity.Platform;
import inf112.firegirlwaterboy.model.managers.CollectableSet;
import inf112.firegirlwaterboy.model.managers.EntitySet;
import inf112.firegirlwaterboy.model.maps.IMapManager;
import inf112.firegirlwaterboy.model.maps.LayerType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class WorldFactoryTest {
    private WorldFactory worldFactory;
    private IMapManager mapManager;
    private IGameObjectFactory gameObjectFactory;

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
        mapManager = mock(IMapManager.class);
        gameObjectFactory = mock(IGameObjectFactory.class);
        this.worldFactory = new WorldFactory(gameObjectFactory, mapManager);
    }

    @Test
    public void testCreateWorld() {
        TiledMap mockMap = mock(TiledMap.class);
        MapLayers mockLayers = mock(MapLayers.class);
        when(mockMap.getLayers()).thenReturn(mockLayers);

        Iterator<MapLayer> emptyIterator = Collections.emptyIterator();
        when(mockLayers.iterator()).thenReturn(emptyIterator);

        World world = worldFactory.createWorld(mockMap);

        assertNotNull(world);
        assertEquals(new Vector2(0, -9.8f), world.getGravity());
    }

    @Test
    public void testCreatePlatforms() {
        TiledMap mockMap = mock(TiledMap.class);
        World mockWorld = mock(World.class);
        MapLayer mockLayer = mock(MapLayer.class);
        MapObject mockObject = mock(MapObject.class);
        Platform mockPlatform = mock(Platform.class);

        when(mapManager.getLayer(mockMap, LayerType.PLATFORM)).thenReturn(mockLayer);

        MapObjects objects = mock(MapObjects.class);
        ArrayList<MapObject> objectList = new ArrayList<>();
        objectList.add(mockObject);
        when(mockLayer.getObjects()).thenReturn(objects);
        when(objects.iterator()).thenReturn(objectList.iterator());

        when(gameObjectFactory.createPlatform(mockWorld, mockObject)).thenReturn(mockPlatform);

        EntitySet<Platform> platforms = worldFactory.createPlatforms(mockWorld, mockMap);

        assertNotNull(platforms);
        verify(gameObjectFactory).createPlatform(mockWorld, mockObject);
    }

    @Test
    public void testCreateElements() {
        TiledMap mockMap = mock(TiledMap.class);
        World mockWorld = mock(World.class);
        MapLayer mockLayer = mock(MapLayer.class);
        MapObject mockObject = mock(MapObject.class);
        Element mockElement = mock(Element.class);

        when(mapManager.getLayer(mockMap, LayerType.ELEMENT)).thenReturn(mockLayer);

        MapObjects objects = mock(MapObjects.class);
        ArrayList<MapObject> objectList = new ArrayList<>();
        objectList.add(mockObject);
        when(mockLayer.getObjects()).thenReturn(objects);
        when(objects.iterator()).thenReturn(objectList.iterator());

        when(gameObjectFactory.createElement(mockWorld, mockObject)).thenReturn(mockElement);

        EntitySet<Element> elements = worldFactory.createElements(mockWorld, mockMap);

        assertNotNull(elements);
        verify(gameObjectFactory).createElement(mockWorld, mockObject);
    }

    @Test
    public void testCreateCollectables() {
        TiledMap mockMap = mock(TiledMap.class);
        World mockWorld = mock(World.class);
        MapLayer mockLayer = mock(MapLayer.class);
        MapObject mockObject = mock(MapObject.class);

        when(mapManager.getLayer(mockMap, LayerType.COLLECTABLE)).thenReturn(mockLayer);

        MapObjects objects = mock(MapObjects.class);
        ArrayList<MapObject> objectList = new ArrayList<>();
        objectList.add(mockObject);
        when(mockLayer.getObjects()).thenReturn(objects);
        when(objects.iterator()).thenReturn(objectList.iterator());

        CollectableSet collectables = worldFactory.createCollectables(mockWorld, mockMap);

        assertNotNull(collectables);
        verify(gameObjectFactory).createCollectable(mockWorld, mockObject);
    }

    @Test
    public void testCreateStaticObjects() throws Exception {
        Method method = WorldFactory.class.getDeclaredMethod(
                "createStaticObjects", World.class, MapLayer.class);
        method.setAccessible(true);

        World mockWorld = mock(World.class);
        MapLayer mockLayer = mock(MapLayer.class);
        MapObjects mockObjects = mock(MapObjects.class);
        MapObject mockObject = mock(MapObject.class);
        MapProperties mockProperties = mock(MapProperties.class);
        Body mockBody = mock(Body.class);
        Fixture mockFixture = mock(Fixture.class);

        when(mockObject.getProperties()).thenReturn(mockProperties);
        when(mockProperties.get("x", Float.class)).thenReturn(100f);
        when(mockProperties.get("y", Float.class)).thenReturn(200f);
        when(mockProperties.get("width", Float.class)).thenReturn(50f);
        when(mockProperties.get("height", Float.class)).thenReturn(30f);

        when(mockLayer.getName()).thenReturn(LayerType.STATIC.name());
        when(mockLayer.getObjects()).thenReturn(mockObjects);
        ArrayList<MapObject> objectList = new ArrayList<>();
        objectList.add(mockObject);
        when(mockObjects.iterator()).thenReturn(objectList.iterator());
        when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);
        when(mockBody.createFixture(any(FixtureDef.class))).thenReturn(mockFixture);

        method.invoke(worldFactory, mockWorld, mockLayer);

        verify(mockWorld).createBody(any(BodyDef.class));
        verify(mockBody).createFixture(any(FixtureDef.class));
        verify(mockFixture).setUserData(LayerType.STATIC);
    }

    @Test
    public void testCreatePolygonObject() throws Exception {
        Method method = WorldFactory.class.getDeclaredMethod(
                "createPolygonObject", World.class, PolygonMapObject.class, LayerType.class);
        method.setAccessible(true);

        World mockWorld = mock(World.class);
        PolygonMapObject mockPolygon = mock(PolygonMapObject.class);
        Polygon mockPolygonShape = mock(Polygon.class);
        Body mockBody = mock(Body.class);
        Fixture mockFixture = mock(Fixture.class);

        when(mockPolygon.getPolygon()).thenReturn(mockPolygonShape);
        float[] vertices = new float[]{0, 0, 10, 0, 10, 10, 0, 10};
        when(mockPolygonShape.getTransformedVertices()).thenReturn(vertices);
        when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);
        when(mockBody.createFixture(any(FixtureDef.class))).thenReturn(mockFixture);

        method.invoke(worldFactory, mockWorld, mockPolygon, LayerType.STATIC);

        verify(mockWorld).createBody(any(BodyDef.class));
        verify(mockBody).createFixture(any(FixtureDef.class));
        verify(mockFixture).setUserData(LayerType.STATIC);
    }

    @Test
    public void testCreateFinishObjects() throws Exception {
        Method method = WorldFactory.class.getDeclaredMethod(
                "createFinishObjects", World.class, MapLayer.class);
        method.setAccessible(true);

        World mockWorld = mock(World.class);
        MapLayer mockLayer = mock(MapLayer.class);
        MapObjects mockObjects = mock(MapObjects.class);
        MapObject mockObject = mock(MapObject.class);
        Body mockBody = mock(Body.class);
        Fixture mockFixture = mock(Fixture.class);
        MapProperties mockProperties = mock(MapProperties.class);

        when(mockLayer.getName()).thenReturn(LayerType.FINISH.name());
        when(mockLayer.getObjects()).thenReturn(mockObjects);
        ArrayList<MapObject> objectList = new ArrayList<>();
        objectList.add(mockObject);
        when(mockObjects.iterator()).thenReturn(objectList.iterator());
        when(mockObject.getProperties()).thenReturn(mockProperties);
        when(mockProperties.get("x", Float.class)).thenReturn(100f);
        when(mockProperties.get("y", Float.class)).thenReturn(200f);
        when(mockProperties.get("width", Float.class)).thenReturn(50f);
        when(mockProperties.get("height", Float.class)).thenReturn(30f);
        when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);
        when(mockBody.createFixture(any(FixtureDef.class))).thenReturn(mockFixture);

        method.invoke(worldFactory, mockWorld, mockLayer);

        verify(mockWorld).createBody(any(BodyDef.class));
        verify(mockBody).createFixture(any(FixtureDef.class));
        verify(mockFixture).setUserData(LayerType.FINISH);
        verify(mockFixture).setSensor(true);
    }
}