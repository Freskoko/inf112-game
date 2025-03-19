package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.*;

import inf112.firegirlwaterboy.controller.Controller;

public class HelpScreen implements Screen {

    private final Stage stage;
    private final Viewport viewport;
    private final SpriteBatch batch;
    private final Controller controller;
    private final Button backButton;

    public HelpScreen(Controller controller) {
        this.controller = controller;
        this.viewport = new ScreenViewport();
        this.stage = new Stage(viewport);
        this.batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);

        backButton = createButton("Back", Color.DARK_GRAY);
        
        setupUI();
        controller.attachHelpScreenListeners(backButton);
    }
    
    private void setupUI() {
        
        Table table = new Table();
        table.setFillParent(true);
        
        Label helpText = new Label("How to play:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        
        table.add(helpText).padBottom(20);
        table.row();
        table.add(backButton).padTop(20);
        
        stage.addActor(table);
    }

    private Button createButton(String text, Color color) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.fontColor = Color.WHITE;

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGB888);
        pixmap.setColor(color);
        pixmap.fill();
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new Texture(pixmap));
        pixmap.dispose();

        style.up = backgroundDrawable;
        style.down = backgroundDrawable.tint(Color.GRAY);

        return new TextButton(text, style);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }
}
