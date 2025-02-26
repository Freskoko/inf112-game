package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import inf112.firegirlwaterboy.controller.Controller;
import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.controller.IControllableModel;

public class WelcomeScreen implements Screen {

    private SpriteBatch batch;
    private BitmapFont font;
    private IControllableModel model;
    private Controller controller;

    public WelcomeScreen(IControllableModel model, Controller controller) {
        this.model = model;
        this.controller = controller;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        // Scale the font size (e.g., 2 times bigger)
        font.getData().setScale(2f);

        model.setGameState(GameState.WELCOME);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Create GlyphLayout to get text dimensions
        GlyphLayout layout = new GlyphLayout();

        // Center the title
        layout.setText(font, "WELCOME TO FIREGIRL & WATERBOY");
        float titleWidth = layout.width;
        float titleHeight = layout.height;
        float titleX = (screenWidth - titleWidth) / 2;
        float titleY = (screenHeight + titleHeight) / 2 + 50; 

        // Center the start instruction
        layout.setText(font, "Press 'P' to Start");
        float instructionWidth = layout.width;
        float instructionHeight = layout.height;
        float instructionX = (screenWidth - instructionWidth) / 2;
        float instructionY = (screenHeight - instructionHeight) / 2;

        batch.begin();
        font.draw(batch, "WELCOME TO FIREGIRL AND WATERBOY", titleX, titleY);
        font.draw(batch, "Press 'P' to start", instructionX, instructionY);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}
