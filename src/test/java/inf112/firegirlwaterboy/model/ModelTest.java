package inf112.firegirlwaterboy.model;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import inf112.firegirlwaterboy.app.FireGirlWaterBoy;
import inf112.firegirlwaterboy.controller.MovementType;
import inf112.firegirlwaterboy.model.entity.Player;
import inf112.firegirlwaterboy.model.entity.PlayerSet;
import inf112.firegirlwaterboy.model.entity.PlayerType;

public class ModelTest {
    private Model testModel;

    @BeforeAll
    static void setUpHeadless() {
        // Set up headless application for tests
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        HeadlessApplication application = new HeadlessApplication(new FireGirlWaterBoy(), config);
        // Mock GL context
        GL20 gl20 = mock(GL20.class);
        Gdx.gl = gl20;
        Gdx.gl20 = gl20;
    }

    @BeforeEach
    void setUp() {
        testModel = new Model();
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
    }

    @Test
    void testAddPlayer2() {
        testModel.addPlayer(PlayerType.WATERBOY);
        testModel.addPlayer(PlayerType.FIREGIRL);
        assertTrue(testModel.containsPlayer(PlayerType.FIREGIRL));
        assertTrue(testModel.containsPlayer(PlayerType.WATERBOY));
    }

    @Test
    void testChangeDirection() {
        Model mockModel = spy(new Model());

        // Mock player
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

        assertTrue(mockModel.changeDir(PlayerType.FIREGIRL, MovementType.LEFT));
        verify(mockPlayer).move(MovementType.LEFT);

        assertTrue(mockModel.changeDir(PlayerType.FIREGIRL, MovementType.UP));
        verify(mockPlayer).move(MovementType.UP);

        assertTrue(mockModel.changeDir(PlayerType.FIREGIRL, MovementType.STOP));
        verify(mockPlayer).move(MovementType.STOP);

        // test with nonexistant player
        when(mockPlayers.getPlayer(PlayerType.WATERBOY)).thenThrow(new IllegalArgumentException("Player not found"));
        assertFalse(mockModel.changeDir(PlayerType.WATERBOY, MovementType.RIGHT));
    }
}