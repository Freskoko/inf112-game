package inf112.firegirlwaterboy.model;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import inf112.firegirlwaterboy.app.FireGirlWaterBoy;
import inf112.firegirlwaterboy.controller.MovementType;
import inf112.firegirlwaterboy.model.entity.Platform;
import inf112.firegirlwaterboy.model.entity.Player;
import inf112.firegirlwaterboy.model.managers.EntitySet;
import inf112.firegirlwaterboy.model.managers.PlayerSet;
import inf112.firegirlwaterboy.model.maps.MapsFactory;
import inf112.firegirlwaterboy.model.types.PlayerType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;

import java.util.HashSet;
import java.util.Set;

public class ModelTest {
    private Model testModel;
    private World mockWorld;
    private MapsFactory mockMaps;
    private PlayerSet mockPlayers;
    private EntitySet<Platform> mockPlatforms;
    private Set<PlayerType> addedPlayers;

    @BeforeAll
    static void setUpHeadless() {
        // Set up headless application for tests
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
     
        new HeadlessApplication(new FireGirlWaterBoy(), config);
        // Mock GL context
        GL20 gl20 = mock(GL20.class);
        Gdx.gl = gl20;
        Gdx.gl20 = gl20;
    }

    @BeforeEach
    void setUp() {
        testModel = new Model();
        mockWorld = mock(World.class);
        mockMaps = mock(MapsFactory.class);
        mockPlayers = mock(PlayerSet.class);
        mockPlatforms = mock(EntitySet.class);
        addedPlayers = new HashSet<>();

        try {
            java.lang.reflect.Field worldField = Model.class.getDeclaredField("world");
            worldField.setAccessible(true);
            worldField.set(testModel, mockWorld);

            java.lang.reflect.Field mapsField = Model.class.getDeclaredField("maps");
            mapsField.setAccessible(true);
            mapsField.set(testModel, mockMaps);

            java.lang.reflect.Field playersField = Model.class.getDeclaredField("players");
            playersField.setAccessible(true);
            playersField.set(testModel, mockPlayers);

            java.lang.reflect.Field platformsField = Model.class.getDeclaredField("platforms");
            platformsField.setAccessible(true);
            platformsField.set(testModel, mockPlatforms);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set mock dependencies: " + e.getMessage());
        }

        when(mockPlayers.toString()).thenReturn("");
        doAnswer(invocation -> {
            Player player = invocation.getArgument(0);
            addedPlayers.add(player.getType());
            return null;
        }).when(mockPlayers).add(any(Player.class));
        when(mockPlayers.contains(any(PlayerType.class))).thenAnswer(invocation -> {
            PlayerType type = invocation.getArgument(0);
            return addedPlayers.contains(type);
        });
    }

    @Test
    void testEmptyPlayers() {
        assertEquals("", testModel.toString());
    }

    @Test
    void testAddPlayer() {
        assertFalse(testModel.containsPlayer(PlayerType.WATERBOY));
        testModel.addPlayer(PlayerType.WATERBOY);
        assertTrue(testModel.containsPlayer(PlayerType.WATERBOY));
        assertFalse(testModel.containsPlayer(PlayerType.FIREGIRL));
        verify(mockPlayers).add(argThat(player -> player.getType() == PlayerType.WATERBOY));
    }

    @Test
    void testAddPlayer2() {
        testModel.addPlayer(PlayerType.WATERBOY);
        testModel.addPlayer(PlayerType.FIREGIRL);
        assertTrue(testModel.containsPlayer(PlayerType.FIREGIRL));
        assertTrue(testModel.containsPlayer(PlayerType.WATERBOY));
        verify(mockPlayers).add(argThat(player -> player.getType() == PlayerType.FIREGIRL));
        verify(mockPlayers).add(argThat(player -> player.getType() == PlayerType.WATERBOY));
    }

    @Test
    void testChangeDirection() {
        Model mockModel = spy(new Model());
        Player mockPlayer = mock(Player.class);
        PlayerSet mockPlayers = mock(PlayerSet.class);

        when(mockPlayers.contains(PlayerType.FIREGIRL)).thenReturn(true);
        when(mockPlayers.getPlayer(PlayerType.FIREGIRL)).thenReturn(mockPlayer);

        try {
            java.lang.reflect.Field playersField = Model.class.getDeclaredField("players");
            playersField.setAccessible(true);
            playersField.set(mockModel, mockPlayers);
        } catch (Exception e) {
            fail("Failed to set mock players: " + e.getMessage());
        }

        assertTrue(mockModel.changeDir(PlayerType.FIREGIRL, MovementType.RIGHT));
        verify(mockPlayer).move(MovementType.RIGHT);

        assertFalse(mockModel.changeDir(PlayerType.WATERBOY, MovementType.RIGHT));
    }

    @Test
    void testRestartGame() {
        Vector2 spawnPos = new Vector2(1, 1);
        when(mockMaps.getSpawnPos(anyString())).thenReturn(spawnPos);
        when(mockMaps.getPlatforms(anyString())).thenReturn(mockPlatforms);

        Player mockPlayer1 = mock(Player.class);
        Player mockPlayer2 = mock(Player.class);
        when(mockPlayers.iterator()).thenReturn(java.util.Arrays.asList(mockPlayer1, mockPlayer2).iterator());

        testModel.restartGame();

        verify(mockMaps).getPlatforms("map1");
    }

    @Test
    void testUpdate() {
        Player mockPlayer1 = mock(Player.class);
        Player mockPlayer2 = mock(Player.class);
        when(mockPlayers.iterator()).thenReturn(java.util.Arrays.asList(mockPlayer1, mockPlayer2).iterator());
        when(mockPlayer1.isAlive()).thenReturn(true);
        when(mockPlayer2.isAlive()).thenReturn(true);
        when(mockPlayers.areFinished()).thenReturn(false);

        testModel.update();

        verify(mockWorld).step(1 / 60f, 6, 2);
        verify(mockPlayers).update();
        verify(mockPlatforms).update();
        assertEquals(GameState.WELCOME, testModel.getGameState()); // Game state should remain WELCOME
    }

    @Test
    void testUpdate_GameOver() {
        Player mockPlayer1 = mock(Player.class);
        when(mockPlayers.iterator()).thenReturn(java.util.Collections.singletonList(mockPlayer1).iterator());
        when(mockPlayer1.isAlive()).thenReturn(false);

        testModel.update();

        assertEquals(GameState.GAME_OVER, testModel.getGameState());
    }

    @Test
    void testUpdate_LevelCompleted() {
        Player mockPlayer1 = mock(Player.class);
        when(mockPlayers.iterator()).thenReturn(java.util.Collections.singletonList(mockPlayer1).iterator());
        when(mockPlayer1.isAlive()).thenReturn(true);
        when(mockPlayers.areFinished()).thenReturn(true);

        testModel.update();

        assertEquals(GameState.COMPLETED_MAP, testModel.getGameState());
    }

    @Test
    void testGetSetGameState() {
        assertEquals(GameState.WELCOME, testModel.getGameState());
        testModel.setGameState(GameState.WELCOME);
        assertEquals(GameState.WELCOME, testModel.getGameState());
        testModel.setGameState(GameState.CHOOSE_MAP);
        assertEquals(GameState.CHOOSE_MAP, testModel.getGameState());
        testModel.setGameState(GameState.GAME_OVER);
        assertEquals(GameState.GAME_OVER, testModel.getGameState());
        testModel.setGameState(GameState.COMPLETED_MAP);
        assertEquals(GameState.COMPLETED_MAP, testModel.getGameState());
    }

    @Test
    void testDraw() {
        Batch mockBatch = mock(Batch.class);
        testModel.draw(mockBatch);
        verify(mockPlayers).draw(mockBatch);
        verify(mockPlatforms).draw(mockBatch);
    }

    @Test
    void testDispose() {
        testModel.dispose();
        verify(mockPlayers).dispose();
    }

    @Test
    void testGetMap() {
        TiledMap mockTiledMap = mock(TiledMap.class);
        when(mockMaps.getMap("map1")).thenReturn(mockTiledMap);
        assertEquals(mockTiledMap, testModel.getMap());
        verify(mockMaps).getMap("map1");
    }

    @Test
    void testGetWorld() {
        assertEquals(mockWorld, testModel.getWorld());
    }
}