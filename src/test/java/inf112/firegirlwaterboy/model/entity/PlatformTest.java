package inf112.firegirlwaterboy.model.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import inf112.firegirlwaterboy.controller.MovementType;
import inf112.firegirlwaterboy.model.maps.MapUtils;
import inf112.firegirlwaterboy.model.types.ElementType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import inf112.firegirlwaterboy.app.FireGirlWaterBoy;

public class PlatformTest {

    private Platform platform;
    private World world;
    private MapObject mockMapObject;
    private MapProperties mockProperties;
    private Texture mockTexture;

    /*
     * Sets up a headless version of the app, required to load map/ images files
     */
    @BeforeAll
    static void setUp() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new FireGirlWaterBoy(), config);
        // even if not used this is required

        // mocking necessary Gdx classes
        GL20 gl20 = mock(GL20.class);
        Gdx.gl = gl20;
        Gdx.gl20 = gl20;
        Gdx.files = mock(com.badlogic.gdx.Files.class);
        FileHandle mockFileHandle = mock(FileHandle.class);
        when(Gdx.files.internal(anyString())).thenReturn(mockFileHandle);
        when(mockFileHandle.exists()).thenReturn(true);
        when(mockFileHandle.readBytes()).thenReturn(new byte[0]); // simulate empty file
    }

    @BeforeEach
    void setUpEachTest() {
        world = new World(new Vector2(0, -9.8f), true);

        mockMapObject = mock(MapObject.class);
        mockProperties = mock(MapProperties.class);
        mockTexture = mock(Texture.class);

        when(mockMapObject.getProperties()).thenReturn(mockProperties);
        when(mockProperties.get("x", Float.class)).thenReturn(100f / MapUtils.PPM);
        when(mockProperties.get("y", Float.class)).thenReturn(100f / MapUtils.PPM);
        when(mockProperties.get("width", Float.class)).thenReturn(32f / MapUtils.PPM);
        when(mockProperties.get("height", Float.class)).thenReturn(32f / MapUtils.PPM);
        when(mockProperties.get("type", String.class)).thenReturn(ElementType.LAVA.toString());
        when(mockProperties.get("dir", String.class)).thenReturn(MovementType.LEFT.toString());

        try (MockedConstruction<Texture> mockedTexture = mockConstruction(Texture.class, (mock, context) -> {
            mockTexture = mock;
        })) {
            platform = new Platform(world, mockMapObject);
        }
    }

    @Test
    void testConstructorSetsUpBodyCorrectly() {
        assertNotNull(platform.getBody());
        Body body = platform.getBody();
        assertEquals(BodyDef.BodyType.DynamicBody, body.getType());
        assertEquals(0.11328125, body.getPosition().x);
        assertEquals(0.11328125, body.getPosition().y);
        assertEquals(0, body.getGravityScale());

        Fixture fixture = body.getFixtureList().first();
        assertNotNull(fixture);
        assertTrue(fixture.getShape() instanceof PolygonShape);
        assertEquals(platform, fixture.getUserData());
        assertEquals(0.1f, fixture.getFriction());
    }

    @Test
    void testConstructorSetsUpTypeAndDirection() {
        assertEquals(ElementType.LAVA, platform.getType());
    }

    @Test
    void collidesSwapsVelocity() {
        platform.update();
        Vector2 initialVelocity = platform.getBody().getLinearVelocity().cpy();

        platform.collision();

        platform.update();
        Vector2 newVelocity = platform.getBody().getLinearVelocity();

        assertEquals(-initialVelocity.x, newVelocity.x);
    }

    @Test
    void testUpdateMovesLeftInitially() {
        platform.update();
        assertEquals(new Vector2(3, 0), platform.getBody().getLinearVelocity());
    }

    @Test
    void testUpdateMovesRight() {
        // Set initial direction to RIGHT
        when(mockProperties.get("dir", String.class)).thenReturn(MovementType.RIGHT.toString());
        try (MockedConstruction<Texture> mockedTexture = mockConstruction(Texture.class, (mock, context) -> {
            mockTexture = mock;
        })) {
            platform = new Platform(world, mockMapObject);
        }

        platform.update();
        assertEquals(new Vector2(-3, 0), platform.getBody().getLinearVelocity());
    }

    @Test
    void testUpdateMovesUp() {
        // Set initial direction to UP
        when(mockProperties.get("dir", String.class)).thenReturn(MovementType.UP.toString());
        try (MockedConstruction<Texture> mockedTexture = mockConstruction(Texture.class, (mock, context) -> {
            mockTexture = mock;
        })) {
            platform = new Platform(world, mockMapObject);
        }

        platform.update();
        assertEquals(new Vector2(0, -3), platform.getBody().getLinearVelocity());
    }

    @Test
    void testUpdateMovesDown() {
        // Set initial direction to DOWN
        when(mockProperties.get("dir", String.class)).thenReturn(MovementType.DOWN.toString());
        try (MockedConstruction<Texture> mockedTexture = mockConstruction(Texture.class, (mock, context) -> {
            mockTexture = mock;
        })) {
            platform = new Platform(world, mockMapObject);
        }

        platform.update();
        assertEquals(new Vector2(0, 3), platform.getBody().getLinearVelocity());
    }

    @Test
    void testDraw() {
        Batch mockBatch = mock(Batch.class);
        when(mockTexture.getWidth()).thenReturn(64);
        when(mockTexture.getHeight()).thenReturn(64);

        platform.draw(mockBatch);
        MapUtils.getX(mockMapObject);

        float x = MapUtils.getX(mockMapObject);
        float y = MapUtils.getY(mockMapObject);
        float width = MapUtils.getWidth(mockMapObject);
        float height = MapUtils.getHeight(mockMapObject);

        verify(mockBatch).draw(mockTexture, x, y, width, height);
    }

    @Test
    void testDispose() {
        assertFalse(world.isLocked());
        platform.dispose();
    }
}