package inf112.firegirlwaterboy.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import inf112.firegirlwaterboy.app.FireGirlWaterBoy;
import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.types.PlayerType;

public class ControllerTest {
    private IControllableModel model;
    private Controller controller;

    @BeforeAll
    private static void setUp() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new FireGirlWaterBoy(), config);

        GL20 gl20 = mock(GL20.class);
        Gdx.gl = gl20;
        Gdx.gl20 = gl20;
    }

    @BeforeEach
    private void initTest() {
        this.model = mock(IControllableModel.class);
        this.controller = new Controller(this.model);
        when(model.getGameState()).thenReturn(GameState.WELCOME);
    }

    /*
     * Tests that starting the game does happen if both players are selected
     */
    @Test
    void testStartGameWithBothPlayersSelected() {
        // Select both players
        controller.selectPlayer(PlayerType.FIREGIRL, true);
        controller.selectPlayer(PlayerType.WATERBOY, false);
        controller.continueIfPlayersSelected();

        verify(model).setGameState(GameState.CHOOSE_MAP);
        verify(model, times(2)).addPlayer(any(PlayerType.class));
    }

    /*
     * Tests that starting the game does not happen if both players are not selected
     */
    @Test
    void testStartGameFailsWithoutBothPlayersSelected() {
        controller.selectPlayer(PlayerType.FIREGIRL, true);

        controller.continueIfPlayersSelected();

        verify(model, times(0)).setGameState(any(GameState.class));
        verify(model, times(1)).addPlayer(any(PlayerType.class));
    }

    @Test
    void testPlayerSelection() {
        assertTrue(controller.selectPlayer(PlayerType.FIREGIRL, true));
        assertTrue(controller.selectPlayer(PlayerType.WATERBOY, false));

        // cannot select player again
        assertFalse(controller.selectPlayer(PlayerType.FIREGIRL, false));

        assertFalse(controller.selectPlayer(PlayerType.WATERBOY, true));
    }

    @Test
    void testButtonAttachments() {
        Button button = new Button();

        controller.attachSelectPlayerListener(button, true, PlayerType.FIREGIRL);
        controller.attachToHelpListener(button);
        controller.attachToWelcomeListeners(button);
        controller.attachToActiveListener(button);
        controller.attachReturnToChooseMapsListener(button);

        assertTrue(button.getListeners().size > 0);
    }

    @Test
    void testKeyboardInput() {
        when(model.getGameState()).thenReturn(GameState.ACTIVE_GAME);

        controller.selectPlayer(PlayerType.FIREGIRL, true);
        controller.selectPlayer(PlayerType.WATERBOY, false);

        assertTrue(controller.keyDown(com.badlogic.gdx.Input.Keys.UP));
        verify(model).changeDir(PlayerType.FIREGIRL, MovementType.UP);

        assertTrue(controller.keyDown(com.badlogic.gdx.Input.Keys.LEFT));
        verify(model).changeDir(PlayerType.FIREGIRL, MovementType.LEFT);

        assertTrue(controller.keyDown(com.badlogic.gdx.Input.Keys.W));
        verify(model).changeDir(PlayerType.WATERBOY, MovementType.UP);

        assertTrue(controller.keyDown(com.badlogic.gdx.Input.Keys.D));
        verify(model).changeDir(PlayerType.WATERBOY, MovementType.RIGHT);

        assertTrue(controller.keyUp(com.badlogic.gdx.Input.Keys.LEFT));
        verify(model).changeDir(PlayerType.FIREGIRL, MovementType.STOP);
    }

    @Test
    void testMapSelection() {
        Button map1Button = new Button();
        Button map2Button = new Button();

        when(model.isComplete("map1")).thenReturn(true);

        controller.attachChooseMapListeners(map1Button, map2Button);

        assertTrue(map1Button.getListeners().size > 0);
        assertTrue(map2Button.getListeners().size > 0);
    }
}