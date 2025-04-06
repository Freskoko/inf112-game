package inf112.firegirlwaterboy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;

import inf112.firegirlwaterboy.app.FireGirlWaterBoy;
import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.Model;
import inf112.firegirlwaterboy.model.types.PlayerType;

public class ControllerTest {
    private Model model;
    private Controller controller;

    @BeforeAll
    private static void setUp() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        HeadlessApplication application = new HeadlessApplication(new FireGirlWaterBoy(), config);
        // even if not used this is required

        // mocking
        GL20 gl20 = mock(GL20.class);
        Gdx.gl = gl20;
        Gdx.gl20 = gl20;

    }

    /*
     * Helper method
     */
    @BeforeEach
    private void initTest() {
        // Create a mock Model instead of a real one
        this.model = mock(Model.class);
        this.controller = new Controller(this.model);

        // Set default behavior for getGameState
        when(model.getGameState()).thenReturn(GameState.WELCOME);
    }

    @Test
    void testPlayersCanMove() {
        // Mock behaviors for adding players
        when(model.containsPlayer(PlayerType.WATERBOY)).thenReturn(false, true);
        when(model.containsPlayer(PlayerType.FIREGIRL)).thenReturn(false, true);

        // add players
        this.controller.keyDown(Keys.W); // w
        this.controller.keyDown(Keys.F); // f

        assertFalse(this.controller.keyUp(Keys.A));
        assertFalse(this.controller.keyUp(Keys.D));

        when(model.getGameState()).thenReturn(GameState.ACTIVE_GAME);

        // mock movement
        when(model.changeDir(eq(PlayerType.WATERBOY), any(MovementType.class))).thenReturn(true);
        when(model.changeDir(eq(PlayerType.FIREGIRL), any(MovementType.class))).thenReturn(true);

        // start the world
        this.controller.keyDown(Keys.P); // p

        // Test movement after game starts
        assertTrue(this.controller.keyUp(Keys.A));
        assertTrue(this.controller.keyUp(Keys.D));
        assertFalse(this.controller.keyUp(Keys.I));
        assertFalse(this.controller.keyUp(Keys.U));
    }

    /*
     * Tests that an unknown player cannot be added to the game
     */
    @Test
    void testKeyUpUnknownPlayer() {
        assertFalse(this.controller.keyUp(Integer.MAX_VALUE));
        assertFalse(this.controller.keyUp(9999));
    }

    /*
     * Tests that the game cannot start if no players are selected
     */
    @Test
    void testKeyUpNoPlayer() {
        assertFalse(this.controller.keyUp(Keys.P));
    }

    /*
     * Tests that starting the game does happen if both players are selected
     */
    @Test
    void testStartGameWithBothPlayersSelected() {
        // Select both players
        controller.selectPlayer(PlayerType.FIREGIRL, true);
        controller.selectPlayer(PlayerType.WATERBOY, false);

        // Attempt to start game
        controller.startGameIfPlayersSelected();

        // Verify game state changes and players are added
        verify(model).setGameState(GameState.CHOOSE_MAP);
        verify(model).addPlayer(PlayerType.FIREGIRL);
        verify(model).addPlayer(PlayerType.WATERBOY);
    }

    /*
     * Tests that starting the game does not happen if both players are not selected
     */
    @Test
    void testStartGameFailsWithoutBothPlayersSelected() {
        controller.selectPlayer(PlayerType.FIREGIRL, true);

        controller.startGameIfPlayersSelected();

        verify(model, never()).setGameState(any());
        verify(model, never()).addPlayer(any());
    }

    /*
     * Tests that pressing R when the game is over, restarts the game
     */
    // @Test
    // void testHandleGameOverStateRestart() {
        // when(model.getGameState()).thenReturn(GameState.GAME_OVER);

        // controller.keyDown(Keys.R);

        // keys logic is removed from controller

        // verify(model).setGameState(GameState.ACTIVE_GAME);
        // verify(model).restartGame();

    // }
}