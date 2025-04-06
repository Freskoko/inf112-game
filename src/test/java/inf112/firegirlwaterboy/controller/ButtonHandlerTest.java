package inf112.firegirlwaterboy.controller;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import inf112.firegirlwaterboy.model.Model;
import inf112.firegirlwaterboy.model.types.PlayerType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ButtonHandlerTest {

    private ButtonHandler buttonHandler;
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller(null); // no model
        buttonHandler = new ButtonHandler(controller, new Model());
    }

    @Test
    void testBackButtonListener() {
        ClickListener listener = buttonHandler.backButtonListener();
        assertNotNull(listener, "Back button listener should not be null");
    }

    @Test
    void testHelpButtonListener() {
        ClickListener listener = buttonHandler.helpButtonListener();
        assertNotNull(listener, "Help button listener should not be null");
    }

    @Test
    void testPlayButtonListener() {
        ClickListener listener = buttonHandler.playButtonListener();
        assertNotNull(listener, "Play button listener should not be null");
    }

    @Test
    void testSelectPlayerListener() {
        ClickListener listener = buttonHandler.selectPlayerListener(1, PlayerType.FIREGIRL);
        assertNotNull(listener, "Select player listener should not be null");
    }

    @Test
    void testStartButtonListener() {
        ClickListener listener = buttonHandler.startButtonListener();
        assertNotNull(listener, "Start button listener should not be null");
    }
}