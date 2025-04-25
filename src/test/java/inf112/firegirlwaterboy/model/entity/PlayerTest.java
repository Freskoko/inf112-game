package inf112.firegirlwaterboy.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.app.FireGirlWaterBoy;
import inf112.firegirlwaterboy.controller.MovementType;
import inf112.firegirlwaterboy.model.types.CollectableType;
import inf112.firegirlwaterboy.model.types.ElementType;
import inf112.firegirlwaterboy.model.types.PlayerType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    Player player;
    World mockWorld;
    Body mockBody;
    Batch mockBatch;
    Texture mockTexture;
    IPlatform mockPlatform;

    /*
     * Sets up a headless version of the app, required to load map/ images files
     */
    @BeforeAll
    static void setUp() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();

        new HeadlessApplication(new FireGirlWaterBoy(), config);

        // mocking
        GL20 gl20 = mock(GL20.class);
        Gdx.gl = gl20;
        Gdx.gl20 = gl20;
    }

    @BeforeEach
    private void setUpEachTest() {
        this.player = new Player(PlayerType.FIREGIRL);
        mockWorld = mock(World.class);
        mockBody = mock(Body.class);
        mockBatch = mock(Batch.class);
        mockTexture = mock(Texture.class);
        mockPlatform = mock(Platform.class);
        Fixture mockFixture = mock(Fixture.class);

        when(mockWorld.createBody(any())).thenReturn(mockBody);
        when(mockBody.getPosition()).thenReturn(new Vector2(0, 0));
        when(mockBody.getLinearVelocity()).thenReturn(new Vector2(0, 0));
        when(mockBody.getWorldCenter()).thenReturn(new Vector2(0, 0));
        when(mockBody.createFixture(any())).thenReturn(mockFixture);
    }

    @Test
    void testPlayerTouchesDeadly() {
        // spawn in a empty world
        this.player.spawn(mockWorld, new Vector2(0, 0));
        Element mockElement = mock(Element.class);
        when(mockElement.getType()).thenReturn(ElementType.LAVA);
        assertTrue(player.isAlive());
        player.interactWithElement(mockElement);
        assertTrue(player.isAlive());
        when(mockElement.getType()).thenReturn(ElementType.WATER);
        player.interactWithElement(mockElement);
        assertFalse(player.isAlive());
    }

    @Test
    void testPlayerSpawns() {
        this.player.spawn(mockWorld, new Vector2(100, 100));
        assertEquals(100, player.getY());
        assertEquals(100, player.getX());
    }

    @Test
    void testFinished() {
        assertFalse(player.isFinished());
        player.setFinished(true);
        assertTrue(player.isFinished());
    }

    @Test
    void testEqualsAndHashCode() {
        Player player2 = new Player(PlayerType.FIREGIRL);
        Player player3 = new Player(PlayerType.WATERBOY);

        assertEquals(player, player2);
        assertEquals(player.hashCode(), player2.hashCode());
        assertNotEquals(player, player3);
        assertNotEquals(player.hashCode(), player3.hashCode());
    }

    @Test
    void testDispose() {
        Player spyPlayer = spy(player);
        when(spyPlayer.getTexture()).thenReturn(mockTexture);
        spyPlayer.dispose();
        verify(mockTexture).dispose();
    }

    @Test
    void testUpdateCollectable() {
        player.spawn(mockWorld, new Vector2(0, 0));
        Collectable mockCollectable = mock(Collectable.class);
        CollectableType type = mock(CollectableType.class);
        Set<PlayerType> requiredPlayer = new HashSet<>();
        requiredPlayer.add(player.getType());

        when(mockCollectable.getRequiredPlayers()).thenReturn(requiredPlayer);
        when(mockCollectable.getType()).thenReturn(type);
        when(type.isPowerUp()).thenReturn(true);

        player.interactWithCollectable(mockCollectable);
        player.update();
        verify(mockCollectable).collect();
        assertEquals(1, player.getCollectedCount());
    }

    @Test
    void testUpdateNoCollectable() {
        player.spawn(mockWorld, new Vector2(0, 0));
        player.update();
        assertEquals(0, player.getCollectedCount());
    }

    @Test
    void testMoveLeft() {
        player.spawn(mockWorld, new Vector2(0, 0));
        player.move(MovementType.LEFT);
        verify(mockBody).setLinearVelocity(eq(-7f), eq(0f));
    }

    @Test
    void testMoveRight() {
        player.spawn(mockWorld, new Vector2(0, 0));
        player.move(MovementType.RIGHT);
        verify(mockBody).setLinearVelocity(eq(7f), eq(0f));
    }

    @Test
    void testMoveStop() {
        player.spawn(mockWorld, new Vector2(0, 0));
        player.move(MovementType.STOP);
        verify(mockBody).setLinearVelocity(eq(0f), eq(0f));
    }

    @Test
    void testMoveDown() {
        player.spawn(mockWorld, new Vector2(0, 0));
        assertThrows( IllegalArgumentException.class, () -> player.move(MovementType.DOWN) );
    } 

    @Test
    void testGetCollectedCount() {
        player.spawn(mockWorld, new Vector2(0, 0));
        assertEquals(0, player.getCollectedCount());
        Collectable mockCollectable = mock(Collectable.class);
        
        CollectableType mockType = mock(CollectableType.class);
        Set<PlayerType> requiredPlayerSet = new HashSet<>();
        requiredPlayerSet.add(player.getType());
        when(mockCollectable.getRequiredPlayers()).thenReturn(requiredPlayerSet);

        when(mockCollectable.getType()).thenReturn(mockType);
        when(mockType.isPowerUp()).thenReturn(true);
        player.interactWithCollectable(mockCollectable);
        assertEquals(1, player.getCollectedCount());
        player.update();
        assertEquals(1, player.getCollectedCount());
    }

    @Test
    void testInteractWithCollectableCorrectType() {
        player.spawn(mockWorld, new Vector2(0, 0));
        Collectable mockCollectable = mock(Collectable.class);
        CollectableType type = mock(CollectableType.class);
        when(mockCollectable.getType()).thenReturn(type);
        when(type.isPowerUp()).thenReturn(true);

        Set<PlayerType> requiredPlayerSet = new HashSet<>();
        requiredPlayerSet.add(player.getType());
        when(mockCollectable.getRequiredPlayers()).thenReturn(requiredPlayerSet);
        player.interactWithCollectable(mockCollectable);
        player.update();
        assertEquals(1, player.getCollectedCount());
    }

    @Test
    void testInteractWithCollectableWrongType() {
        player.spawn(mockWorld, new Vector2(0, 0));
        Collectable mockCollectable = mock(Collectable.class);
        Set<PlayerType> requiredPlayerSet = new HashSet<>();
        CollectableType type = mock(CollectableType.class);
        when(mockCollectable.getType()).thenReturn(type);
        when(type.isPowerUp()).thenReturn(true);
        when(mockCollectable.getRequiredPlayers()).thenReturn(requiredPlayerSet);
        player.interactWithCollectable(mockCollectable);
        player.update();
        assertEquals(0, player.getCollectedCount());
    }

    @Test
    void testToString() {
        assertEquals("FIREGIRL", player.toString());
        Player waterBoy = new Player(PlayerType.WATERBOY);
        assertEquals("WATERBOY", waterBoy.toString());
    }
}