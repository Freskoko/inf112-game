package inf112.firegirlwaterboy.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import inf112.firegirlwaterboy.app.FireGirlWaterBoy;
import inf112.firegirlwaterboy.controller.MovementType;
import inf112.firegirlwaterboy.model.entity.Element;
import inf112.firegirlwaterboy.model.entity.Platform;
import inf112.firegirlwaterboy.model.entity.Player;
import inf112.firegirlwaterboy.model.managers.CollectableSet;
import inf112.firegirlwaterboy.model.managers.EntitySet;
import inf112.firegirlwaterboy.model.managers.PlayerSet;
import inf112.firegirlwaterboy.model.maps.MapManager;
import inf112.firegirlwaterboy.model.maps.factories.WorldFactory;
import inf112.firegirlwaterboy.model.types.PlayerType;
import inf112.firegirlwaterboy.sound.SoundManager;

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
    private WorldFactory mockFactory;
    private MapManager mockManager;
    private PlayerSet mockPlayers;
    private EntitySet<Platform> mockPlatforms;
    private Set<PlayerType> addedPlayers;
    private CollectableSet mockCollectables;
    private EntitySet<Element> mockElements;

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

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        testModel = new Model();
        mockWorld = mock(World.class);
        mockPlayers = mock(PlayerSet.class);
        mockPlatforms = mock(EntitySet.class);
        mockCollectables = mock(CollectableSet.class);
        addedPlayers = new HashSet<>();
        mockElements = mock(EntitySet.class);
        SoundManager mockSoundManager = mock(SoundManager.class);
        mockManager = mock(MapManager.class);
        mockFactory = mock(WorldFactory.class);

        try {
            java.lang.reflect.Field worldField = Model.class.getDeclaredField("world");
            worldField.setAccessible(true);
            worldField.set(testModel, mockWorld);

            java.lang.reflect.Field mapsField = Model.class.getDeclaredField("mapManager");
            mapsField.setAccessible(true);
            mapsField.set(testModel, mockManager);

            java.lang.reflect.Field factoryField = Model.class.getDeclaredField("worldFactory");
            factoryField.setAccessible(true);
            factoryField.set(testModel, mockFactory);

            java.lang.reflect.Field playersField = Model.class.getDeclaredField("players");
            playersField.setAccessible(true);
            playersField.set(testModel, mockPlayers);

            java.lang.reflect.Field platformsField = Model.class.getDeclaredField("platforms");
            platformsField.setAccessible(true);
            platformsField.set(testModel, mockPlatforms);

            java.lang.reflect.Field collectablesField = Model.class.getDeclaredField("collectables");
            collectablesField.setAccessible(true);
            collectablesField.set(testModel, mockCollectables);

            java.lang.reflect.Field elementsField = Model.class.getDeclaredField("elements");
            elementsField.setAccessible(true);
            elementsField.set(testModel, mockElements);

            java.lang.reflect.Field soundManagerField = Model.class.getDeclaredField("soundManager");
            soundManagerField.setAccessible(true);
            soundManagerField.set(testModel, mockSoundManager);

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
        // TiledMap mockMap = mock(TiledMap.class);
        // when(mockManager.getMap("map1")).thenReturn(mockMap);
        // testModel.restartGame();
        // verify(mockFactory).createCollectables(mockWorld, mockMap);
        // verify(mockFactory).createPlatforms(mockWorld, mockMap);
        // verify(mockFactory).createElements(mockWorld, mockMap);       
    }

    @Test
    void testUpdate() {
        when(mockPlayers.areAlive()).thenReturn(true);
        when(mockPlayers.areFinished()).thenReturn(false);

        testModel.update();

        verify(mockWorld).step(1 / 60f, 6, 2);
        verify(mockPlayers).update();
        verify(mockPlatforms).update();
        verify(mockCollectables).update();
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

        //assertEquals(GameState.COMPLETED_MAP, testModel.getGameState());
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
        verify(mockCollectables).draw(mockBatch);
        verify(mockElements).draw(mockBatch);
    }

    @Test
    void testDispose() {
        testModel.dispose();
        verify(mockPlayers).dispose();
    }

    @Test
    void testGetMap() {
        TiledMap mockTiledMap = mock(TiledMap.class);
        when(mockManager.getMap("map1")).thenReturn(mockTiledMap);
        assertEquals(mockTiledMap, testModel.getMap());
        verify(mockManager).getMap("map1");
    }

    @Test
    void testGetWorld() {
        assertEquals(mockWorld, testModel.getWorld());
    }
}