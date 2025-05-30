package inf112.firegirlwaterboy.view.screens;

import javax.annotation.processing.Generated;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.*;

import inf112.firegirlwaterboy.controller.Controller;
import inf112.firegirlwaterboy.model.entity.types.PlayerType;
import inf112.firegirlwaterboy.view.ButtonDesigner;

/**
 * WelcomeScreen class represents the welcome screen.
 * Here the player can choose their character and start the game, and press help
 * button for instructions.
 */
public class WelcomeScreen implements Screen {

  private final Stage stage;
  private final Viewport viewport;
  private final Texture logo;
  private final Controller controller;

  private Texture backgroundTexture;
  private SpriteBatch batch;

  private Button fireGirlButtonP1 = ButtonDesigner.createButton("FireGirl", Color.valueOf("#f23800"));
  private Button waterBoyButtonP1 = ButtonDesigner.createButton("WaterBoy", Color.valueOf("#18beeb"));
  private Button fireGirlButtonP2 = ButtonDesigner.createButton("FireGirl", Color.valueOf("#f23800"));
  private Button waterBoyButtonP2 = ButtonDesigner.createButton("WaterBoy", Color.valueOf("#18beeb"));
  private Button startButton = ButtonDesigner.createButton("Start", Color.valueOf("#607d4d"));
  private Button helpButton = ButtonDesigner.createButton("Help", Color.valueOf("#607d4d"));

  public WelcomeScreen(Controller controller) {
    this.controller = controller;

    viewport = new ExtendViewport(960, 960);
    stage = new Stage(viewport);
    logo = new Texture("assets/pages/GameLogo.png");
    backgroundTexture = new Texture("assets/pages/screens/BackgroundScreen.png");
    batch = new SpriteBatch();

    setupUI();
  }

  private void setupUI() {
    Table table = new Table();
    table.setFillParent(true);
    table.bottom().padBottom(200);

    controller.attachSelectPlayerListener(fireGirlButtonP1, true, PlayerType.FIREGIRL);
    controller.attachSelectPlayerListener(waterBoyButtonP1, true, PlayerType.WATERBOY);
    controller.attachSelectPlayerListener(fireGirlButtonP2, false, PlayerType.FIREGIRL);
    controller.attachSelectPlayerListener(waterBoyButtonP2, false, PlayerType.WATERBOY);
    controller.attachReturnToChooseMapsListener(startButton);
    controller.attachToHelpListener(helpButton);

    Table playerSelectionTable = new Table();
    playerSelectionTable.top().padTop(20);

    Image logoImage = new Image(new TextureRegionDrawable(new TextureRegion(logo)));
    float logoWidth = logo.getWidth();
    float logoHeight = logo.getHeight();
    logoImage.setSize(logoWidth, logoHeight);
    playerSelectionTable.add(logoImage).colspan(2).center().padBottom(30);
    playerSelectionTable.row();

    Table p1Table = new Table();
    Label player1Label = new Label("Player 1 choose your character:",
        new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    p1Table.add(player1Label).colspan(2).center().padBottom(10);
    p1Table.row();
    p1Table.add(fireGirlButtonP1).size(150, 50).pad(10);
    p1Table.add(waterBoyButtonP1).size(150, 50).pad(10);
    playerSelectionTable.add(p1Table).expandX().left().pad(20);

    Table p2Table = new Table();
    Label player2Label = new Label("Player 2 choose your character:",
        new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    p2Table.add(player2Label).colspan(2).center().padBottom(10);
    p2Table.row();
    p2Table.add(fireGirlButtonP2).size(150, 50).pad(10);
    p2Table.add(waterBoyButtonP2).size(150, 50).pad(10);
    playerSelectionTable.add(p2Table).expandX().right().pad(20);

    table.add(playerSelectionTable).colspan(2);
    table.row();
    table.add(startButton).colspan(2).size(150, 50).center().padTop(20);

    stage.addActor(table);

    Table helpTable = new Table();
    helpTable.setFillParent(true);
    helpTable.top().right().pad(20);
    helpTable.add(helpButton).size(100, 50);
    stage.addActor(helpTable);
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void render(float delta) {
    if (Gdx.input.getInputProcessor() != stage) {
      Gdx.input.setInputProcessor(stage);
    }

    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    batch.setProjectionMatrix(stage.getCamera().combined);
    batch.begin();
    batch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
    batch.end();

    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    viewport.update(width, height, true);
  }

  @Override
  public void hide() {
    Gdx.input.setInputProcessor(null);
  }

  @Override
  public void dispose() {
    stage.dispose();
    logo.dispose();
    backgroundTexture.dispose();
    batch.dispose();
  }

  @Override
  @Generated("interface-stub")
  public void pause() {}

  @Override
  @Generated("interface-stub")
  public void resume() {}
}