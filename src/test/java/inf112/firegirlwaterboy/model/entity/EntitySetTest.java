package inf112.firegirlwaterboy.model.entity;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;

import inf112.firegirlwaterboy.app.FireGirlWaterBoy;

public class EntitySetTest {

    private EntitySet<Player, PlayerType> entitySet;

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

    @BeforeEach
    private void setUpEachTest() {
        this.entitySet = new EntitySet<>();
    }

    @Test
    void testaddEntity() {
        assertTrue(this.entitySet.isEmpty());
        entitySet.addEntity(new Player(PlayerType.FIREGIRL));
        assertFalse(entitySet.isEmpty());
    }

    @Test
    void testContainsKey() {
        entitySet.addEntity(new Player(PlayerType.FIREGIRL));
        assertTrue(entitySet.containsEntity(PlayerType.FIREGIRL));
        assertFalse(entitySet.containsEntity(PlayerType.WATERBOY));
    }

    @Test
    void testGetPlayer() {
        entitySet.addEntity(new Player(PlayerType.FIREGIRL));
        Player player1 = entitySet.getEntity(PlayerType.FIREGIRL);
        assertEquals(player1.getEntityType(), PlayerType.FIREGIRL);

        // cannot grab player which is not there
        assertThrows(IllegalArgumentException.class, () -> entitySet.getEntity(PlayerType.WATERBOY));
    }

    @Test
    void testIsEmpty() {
        assertTrue(entitySet.isEmpty());
        entitySet.addEntity(new Player(PlayerType.FIREGIRL));
        assertFalse(entitySet.isEmpty());
    }

    @Test
    void testToString() {
        entitySet.addEntity(new Player(PlayerType.FIREGIRL));
        assertEquals("FIREGIRL", entitySet.toString());
    }
}
