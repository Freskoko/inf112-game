package inf112.firegirlwaterboy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import inf112.firegirlwaterboy.app.FireGirlWaterBoy;
import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.Model;
import inf112.firegirlwaterboy.model.types.PlayerType;

public class ControllerTest {
    private Model model;
    private Controller controller;
    private ButtonHandler buttonHandler;

    @BeforeAll
    private static void setUp() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        // HeadlessApplication application =
        new HeadlessApplication(new FireGirlWaterBoy(), config);

        GL20 gl20 = mock(GL20.class);
        Gdx.gl = gl20;
        Gdx.gl20 = gl20;
    }

    @BeforeEach
    private void initTest() {
        this.model = mock(Model.class);
        this.buttonHandler = mock(ButtonHandler.class);
        this.controller = new Controller(this.model, this.buttonHandler); // Pass the mock buttonHandler
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

        // Attempt to start game
        controller.continueIfPlayersSelected();

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

        controller.continueIfPlayersSelected();

        verify(model, never()).setGameState(any());
        verify(model, only()).addPlayer(any());
    }

    @Test
    void testWelcomeScreenButtonSelection() {
        ClickListener mockClickListener = mock(ClickListener.class);
        when(buttonHandler.getSelectPlayerListener(true, PlayerType.FIREGIRL))
                .thenReturn(mockClickListener);

        Button fgP1 = mock(Button.class);

        controller.attachSelectPlayerListener(fgP1, true, PlayerType.FIREGIRL);

        verify(fgP1).addListener(mockClickListener);
    }

    @Test
    void testStartButtonTriggersGameStart() {
        ClickListener mockClickListener = mock(ClickListener.class);
        when(buttonHandler.getToChooseMapListener())
                .thenReturn(mockClickListener);

        Button start = mock(Button.class);

        controller.attachToChooseMapsListener(start);

        verify(start).addListener(mockClickListener);
    }

    @Test
    void testPlayButtonChangesGameState() {
        Button playButton = mock(Button.class);
        ClickListener mockClickListener = mock(ClickListener.class);
        when(buttonHandler.getToActiveListener()).thenReturn(mockClickListener);

        controller.attachToActiveListener(playButton);

        verify(playButton).addListener(mockClickListener);
        verify(buttonHandler).getToActiveListener();
    }

    @Test
    void testAttachWelcomeScreenListeners() {
        Button fgP1 = mock(Button.class);
        Button wbP1 = mock(Button.class);
        Button fgP2 = mock(Button.class);
        Button wbP2 = mock(Button.class);
        Button start = mock(Button.class);
        Button help = mock(Button.class);

        ClickListener mockClickListener1FG = mock(ClickListener.class);
        ClickListener mockClickListener1WB = mock(ClickListener.class);
        ClickListener mockClickListener2FG = mock(ClickListener.class);
        ClickListener mockClickListener2WB = mock(ClickListener.class);
        ClickListener mockClickListenerStart = mock(ClickListener.class);
        ClickListener mockClickListenerHelp = mock(ClickListener.class);

        when(buttonHandler.getSelectPlayerListener(true, PlayerType.FIREGIRL)).thenReturn(mockClickListener1FG);
        when(buttonHandler.getSelectPlayerListener(true, PlayerType.WATERBOY)).thenReturn(mockClickListener1WB);
        when(buttonHandler.getSelectPlayerListener(false, PlayerType.FIREGIRL)).thenReturn(mockClickListener2FG);
        when(buttonHandler.getSelectPlayerListener(false, PlayerType.WATERBOY)).thenReturn(mockClickListener2WB);
        when(buttonHandler.getToChooseMapListener()).thenReturn(mockClickListenerStart);
        when(buttonHandler.getToHelpListener()).thenReturn(mockClickListenerHelp);

        controller.attachSelectPlayerListener(fgP1, true, PlayerType.FIREGIRL);
        controller.attachSelectPlayerListener(wbP1, true, PlayerType.WATERBOY);
        controller.attachSelectPlayerListener(fgP2, false, PlayerType.FIREGIRL);
        controller.attachSelectPlayerListener(wbP2, false, PlayerType.WATERBOY);
        controller.attachToChooseMapsListener(start);
        controller.attachToHelpListener(help);

        verify(fgP1).addListener(mockClickListener1FG);
        verify(wbP1).addListener(mockClickListener1WB);
        verify(fgP2).addListener(mockClickListener2FG);
        verify(wbP2).addListener(mockClickListener2WB);
        verify(start).addListener(mockClickListenerStart);
        verify(help).addListener(mockClickListenerHelp);

        verify(buttonHandler).getSelectPlayerListener(true, PlayerType.FIREGIRL);
        verify(buttonHandler).getSelectPlayerListener(true, PlayerType.WATERBOY);
        verify(buttonHandler).getSelectPlayerListener(false, PlayerType.FIREGIRL);
        verify(buttonHandler).getSelectPlayerListener(false, PlayerType.WATERBOY);
        verify(buttonHandler).getToChooseMapListener();
        verify(buttonHandler).getToHelpListener();
    }

    @Test
    void testAttachHelpScreenListeners() {
        Button back = mock(Button.class);
        ClickListener mockClickListener = mock(ClickListener.class);
        when(buttonHandler.getToWelcomeListener()).thenReturn(mockClickListener);

        controller.attachToWelcomeListeners(back);

        verify(back).addListener(mockClickListener);
        verify(buttonHandler).getToWelcomeListener();
    }

    @Test
    void testSetupPlayButtonListener() {
        Button playButton = mock(Button.class);
        ClickListener mockClickListener = mock(ClickListener.class);
        when(buttonHandler.getToActiveListener()).thenReturn(mockClickListener);

        controller.attachToActiveListener(playButton);

        verify(playButton).addListener(mockClickListener);
        verify(buttonHandler).getToActiveListener();
    }

    @Test
    void testAttachGameOverScreenListeners() {
        Button backToWelcomeScreenButton = mock(Button.class);
        ClickListener mockClickListener = mock(ClickListener.class);
        when(buttonHandler.getToChooseMapListener()).thenReturn(mockClickListener);

        controller.attachToChooseMapsListener(backToWelcomeScreenButton);

        verify(backToWelcomeScreenButton).addListener(mockClickListener);
        verify(buttonHandler).getToChooseMapListener();
    }
}
