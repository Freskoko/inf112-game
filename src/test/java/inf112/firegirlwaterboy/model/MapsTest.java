package inf112.firegirlwaterboy.model;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import inf112.firegirlwaterboy.app.FireGirlWaterBoy;

public class MapsTest {


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

    /*
     * Tests if all maps can be loaded, and that setting a map does not throw an exception.
     */
    @Test
    void testMapLoadsAsExpected() {
        Model model = new Model("default_map");
        model.init();
    }
}
