package inf112.firegirlwaterboy.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.files.FileHandle;

import inf112.firegirlwaterboy.controller.Controller;
import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.Model;
import inf112.firegirlwaterboy.view.screens.GameScreen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class FireGirlWaterBoyTest {

    private FireGirlWaterBoy game;
    private Model mockModel;
    private Controller mockController;

    @BeforeEach
    void setUp() {
        Gdx.graphics = mock(Graphics.class);
        Gdx.input = mock(com.badlogic.gdx.Input.class);

        GL20 mockGL = mock(GL20.class);
        Gdx.gl20 = mockGL;
        Gdx.gl = mockGL;

        com.badlogic.gdx.Files mockFiles = mock(com.badlogic.gdx.Files.class);
        Gdx.files = mockFiles;

        FileHandle mockFileHandle = mock(FileHandle.class);
        when(mockFiles.internal(anyString())).thenReturn(mockFileHandle);
        when(mockFiles.classpath(anyString())).thenReturn(mockFileHandle);

        mockModel = mock(Model.class);
        mockController = mock(Controller.class);

        game = new FireGirlWaterBoy() {
            @Override
            public void create() {
                // prevent actual init
            }

            @Override
            public void setScreen(Screen screen) {
                super.setScreen(screen);
            }
        };

        try {
            var modelField = FireGirlWaterBoy.class.getDeclaredField("model");
            modelField.setAccessible(true);
            modelField.set(game, mockModel);

            var controllerField = FireGirlWaterBoy.class.getDeclaredField("controller");
            controllerField.setAccessible(true);
            controllerField.set(game, mockController);

            var currentGameStateField = FireGirlWaterBoy.class.getDeclaredField("currentGameState");
            currentGameStateField.setAccessible(true);
            currentGameStateField.set(game, GameState.WELCOME);
        } catch (Exception e) {
            fail("Failed to set up test: " + e.getMessage());
        }
    }

    @Test
    void testConstructor() {
        FireGirlWaterBoy realGame = new FireGirlWaterBoy();

        try {
            var modelField = FireGirlWaterBoy.class.getDeclaredField("model");
            modelField.setAccessible(true);
            assertNotNull(modelField.get(realGame));

            var controllerField = FireGirlWaterBoy.class.getDeclaredField("controller");
            controllerField.setAccessible(true);
            assertNotNull(controllerField.get(realGame));

            var currentGameStateField = FireGirlWaterBoy.class.getDeclaredField("currentGameState");
            currentGameStateField.setAccessible(true);
            assertEquals(GameState.WELCOME, currentGameStateField.get(realGame));
        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
    }

    @Test
    void testRenderWithNoStateChange() {
        when(mockModel.getGameState()).thenReturn(GameState.WELCOME);
        try {
            var currentGameStateField = FireGirlWaterBoy.class.getDeclaredField("currentGameState");
            currentGameStateField.setAccessible(true);
            currentGameStateField.set(game, GameState.WELCOME);
        } catch (Exception e) {
            fail("Test setup failed: " + e.getMessage());
        }

        FireGirlWaterBoy gameSpy = spy(game);

        gameSpy.render();

        verify(gameSpy, never()).setScreen(any(Screen.class));
        verify(gameSpy).render();
    }

    @Test
    void testGetScreenByGameStateActiveGame() {
        Screen screen = invokeGetScreen(game, GameState.ACTIVE_GAME);

        assertTrue(screen instanceof GameScreen);

        verify(mockModel).restartGame();
    }


    private Screen invokeGetScreen(FireGirlWaterBoy game, GameState state) {
        try {
            var method = FireGirlWaterBoy.class.getDeclaredMethod("getScreen", GameState.class);
            method.setAccessible(true);
            return (Screen) method.invoke(game, state);
        } catch (Exception e) {
            fail("Exception inside getScreenByGameState: " + e);
            return null;
        }
    }
}