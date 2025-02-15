package inf112.firegirlwaterboy.app;

import org.lwjgl.system.Configuration;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.utils.Os;
import com.badlogic.gdx.utils.SharedLibraryLoader;

/**
 * Main class for the game FireGirl & WaterBoy.
 * This class sets up the game window and starts the game.
 */
public class Main {
	public static void main(String[] args) {
		if (SharedLibraryLoader.os == Os.MacOsX) {
			Configuration.GLFW_LIBRARY_NAME.set("glfw_async");
		}
		Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
		cfg.setTitle("FireGirl & WaterBoy");
		cfg.setWindowedMode(960, 960);
		
		new Lwjgl3Application(new FireGirlWaterBoy(), cfg);
	}
}
