package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.Screen;

import inf112.firegirlwaterboy.controller.Controller;

public class GameOverScreen implements Screen {

  private IViewModel model;
  private Controller controller;

  public GameOverScreen(IViewModel model, Controller controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public void show() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'show'");
  }

  @Override
  public void render(float delta) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'render'");
  }

  @Override
  public void resize(int width, int height) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'resize'");
  }

  @Override
  public void pause() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'pause'");
  }

  @Override
  public void resume() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'resume'");
  }

  @Override
  public void hide() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'hide'");
  }

  @Override
  public void dispose() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'dispose'");
  }
}