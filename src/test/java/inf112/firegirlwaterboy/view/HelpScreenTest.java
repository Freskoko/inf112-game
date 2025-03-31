package inf112.firegirlwaterboy.view;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class HelpScreenTest {
    @Test
    @ManualTest
    @Disabled // Currently, the help screen hard-codes movement controls,
                // while movement is actually chosen by players
    void getInformation() {
        /*
         * Upon entering the HelpScreen, information regarding the game is presented.
         * Information such as:
         * - The game's goal
         * - Aspects about the players
         * - Controls FIXME!
         */
        assertFalse(true);
    }

    @Test
    @ManualTest
    @Disabled // TODO IMPLEMENT ME, Widgets move unsually.
    void testResize() {
        /*
         * Resizing the HelpScreen causes on screen widgets move accordingly, nicely taking up place.
         */
        assertTrue(true);
    }

    @Test
    @ManualTest
    void testBack() {
        /*
         * Selecting the "Back" button causes the WelcomeScreen to show
         */
        assertTrue(true);
    }
}
