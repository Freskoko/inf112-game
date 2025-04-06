package inf112.firegirlwaterboy.model.maps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import java.lang.IllegalArgumentException;
import java.util.ArrayList;

import inf112.firegirlwaterboy.app.FireGirlWaterBoy;

public class MapsTest {
    private Maps maps;

    /*
     * Sets up a headless version of the app, reuqired to load map/ images files
     */
    @BeforeAll
    static void setUp() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        HeadlessApplication application = new HeadlessApplication(new FireGirlWaterBoy(), config);
        // even if not used this is required

        // mocking
        GL20 gl20 = mock(GL20.class);
        Gdx.gl = gl20;
        Gdx.gl20 = gl20;

    }

    @BeforeEach
    private void initTest() {
        this.maps = new Maps();
        // this.maps.init();
    }

    /*
     * Tests that inital map loading works
     * Empty on purpose, just runs the BeforeEach initTest()
     */
    @Test
    void testMapLoadsAsExpected() {
    }

    /*
     * Tests that loading a layer from a map works
     */
    @Test
    void testGetLayer() {
        MapLayer layer = this.maps.getLayer("map", "Spawn");
        assertEquals(layer.getName(), "Spawn");
    }

    /*
     * Tests that loading a nonexistent layer from a map throws an exception
     */
    @Test
    void testGetLayerFails() {
        assertThrows(IllegalArgumentException.class, () -> this.maps.getLayer("map", "idontexist!"));
    }

    @Test
    void testGetPlayerSpawn() {
        assertEquals(maps.getSpawnPos("map"), new Vector2(2, 2));
    }

    @Test
    public void testEmptyBodyDoesNotCreateBodies() throws Exception {
        // Update method name to match the actual implementation
        java.lang.reflect.Method method = Maps.class.getDeclaredMethod(
                "createObjectsFromLayer", World.class, MapLayer.class);
        method.setAccessible(true);

        World mockWorld = mock(World.class);
        MapLayer mockLayer = mock(MapLayer.class);
        MapObjects mockObjects = mock(MapObjects.class);

        when(mockLayer.getObjects()).thenReturn(mockObjects);
        when(mockObjects.iterator()).thenReturn(java.util.Collections.emptyIterator());

        Body mockBody = mock(Body.class);
        when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);

        method.invoke(maps, mockWorld, mockLayer);

        verify(mockWorld, never()).createBody(any(BodyDef.class));
    }

    @Test
    public void testEmptyBodyDoesCreateBodies() throws Exception {
        java.lang.reflect.Method method = Maps.class.getDeclaredMethod(
                "createObjectsFromLayer", World.class, MapLayer.class);
        method.setAccessible(true);

        World mockWorld = mock(World.class);
        MapLayer mockLayer = mock(MapLayer.class);
        MapObjects mockObjects = mock(MapObjects.class);
        MapObject mockObject = mock(MapObject.class);
        MapProperties mockProperties = mock(MapProperties.class);

        Body mockBody = mock(Body.class);
        Fixture mockFixture = mock(Fixture.class);
        when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);
        when(mockBody.createFixture(any(FixtureDef.class))).thenReturn(mockFixture);

        // setting up a layer with one object
        when(mockLayer.getObjects()).thenReturn(mockObjects);
        when(mockLayer.getName()).thenReturn("TestLayer");
        ArrayList<MapObject> objectList = new ArrayList<>();
        objectList.add(mockObject);
        when(mockObjects.iterator()).thenReturn(objectList.iterator());

        // setting up properties of layer
        when(mockObject.getProperties()).thenReturn(mockProperties);
        when(mockProperties.get("x", Float.class)).thenReturn(100f);
        when(mockProperties.get("y", Float.class)).thenReturn(200f);
        when(mockProperties.get("width", Float.class)).thenReturn(50f);
        when(mockProperties.get("height", Float.class)).thenReturn(30f);

        // call the private method directly
        method.invoke(maps, mockWorld, mockLayer);

        // verify createBody was called once
        verify(mockWorld, times(1)).createBody(any(BodyDef.class));

        // verify createFixture was called once
        verify(mockBody, times(1)).createFixture(any(FixtureDef.class));

        // verify setUserData was called with the layer name
        verify(mockFixture).setUserData("TestLayer");
    }
}
