package inf112.firegirlwaterboy.model.Entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.app.FireGirlWaterBoy;
import inf112.firegirlwaterboy.model.entity.ElementType;
import inf112.firegirlwaterboy.model.entity.Player;
import inf112.firegirlwaterboy.model.entity.PlayerType;

public class PlayerTest {

    Player player;

    /*
     * Sets up a headless version of the app, reuqired to load map/ images files
     */
    @BeforeAll
    static void setUp() {
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
        this.player = new Player(PlayerType.FIREGIRL);
    }

    @Test
    void testPlayerTouchesDeadly() {
        // spawn in a empty world
        this.player.spawn(new World(new Vector2(0, 0), false), new Vector2(0, 0));

        assertTrue(player.isAlive());
        player.interactWithElement(ElementType.LAVA);
        assertTrue(player.isAlive());
        player.interactWithElement(ElementType.WATER);
        assertFalse(player.isAlive());
    }

    @Test
    void testPlayerSpawns() {
        // spawn in a empty world
        this.player.spawn(new World(new Vector2(0, -9.8f), true), new Vector2(100, 100));
        assertEquals(player.getY(), 100);
        player.update();
        // fall down
        assertEquals(player.getY(), 99.0);
    }

    @Test
    void testEdge() {
        assertFalse(player.isTouchingEdge());
        player.setTouchingEdge(true);
        assertTrue(player.isTouchingEdge());
    }

    @Test
    void testGround() {
        assertFalse(player.isOnGround());
        player.setOnGround(true);
        assertTrue(player.isOnGround());
    }
}
