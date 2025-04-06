package inf112.firegirlwaterboy.model.types;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PlayerTypeTest {
    @Test
    void testGetWaterboyImmunity() {
        assertEquals(PlayerType.WATERBOY.getImmunity(), ElementType.WATER);
    }

    @Test
    void testGetFiregirlImmunity() {
        assertEquals(PlayerType.FIREGIRL.getImmunity(), ElementType.LAVA);
    }
}
