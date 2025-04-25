package inf112.firegirlwaterboy.model.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.physics.box2d.*;

import inf112.firegirlwaterboy.app.FireGirlWaterBoy;
import inf112.firegirlwaterboy.model.types.ElementType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;

public class ElementTest {

  private World mockWorld;
  private MapObject mockMapObject;
  private MapProperties mockProperties;
  private Texture mockTexture;
  private Element element;
  private Body mockBody;

  @BeforeAll
  static void setUp() {
    HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
    new HeadlessApplication(new FireGirlWaterBoy(), config);

    GL20 gl20 = mock(GL20.class);
    Gdx.gl = gl20;
    Gdx.gl20 = gl20;
    Gdx.files = mock(com.badlogic.gdx.Files.class);
    FileHandle mockFileHandle = mock(FileHandle.class);
    when(Gdx.files.internal(anyString())).thenReturn(mockFileHandle);
    when(mockFileHandle.exists()).thenReturn(true);
    when(mockFileHandle.readBytes()).thenReturn(new byte[0]);
    when(mockFileHandle.name()).thenReturn("mockTexture.png");
  }

  @BeforeEach
  void setUpEachTest() {
    mockWorld = mock(World.class);
    mockMapObject = mock(MapObject.class);
    mockProperties = mock(MapProperties.class);
    mockTexture = mock(Texture.class);
    mockBody = mock(Body.class);
    Fixture mockFixture = mock(Fixture.class);

    when(mockMapObject.getProperties()).thenReturn(mockProperties);
    when(mockProperties.get("type", String.class)).thenReturn(ElementType.LAVA.toString());
    when(mockProperties.get("x", Float.class)).thenReturn(100f);
    when(mockProperties.get("y", Float.class)).thenReturn(100f);
    when(mockProperties.get("width", Float.class)).thenReturn(32f);
    when(mockProperties.get("height", Float.class)).thenReturn(32f);

    when(mockWorld.createBody(any())).thenReturn(mockBody);
    when(mockBody.createFixture(any())).thenReturn(mockFixture);

    try (MockedConstruction<Texture> mockedTexture = mockConstruction(Texture.class, (mock, context) -> {
      mockTexture = mock;
    })) {
      element = new Element(mockWorld, mockMapObject);
    }
  }

  @Test
  void constructorShouldLoadTextureBasedOnElementType() {
    for (String path : ElementType.LAVA.getTexturePaths()) {
      verify(Gdx.files, times(2)).internal(path);
    }
  }

    @Test
    void constructorShouldInitializeElementType() {
      assertEquals(ElementType.LAVA, element.getType());
    }

  @Test
  void constructorShouldCreateFixtureWithPolygonShape() {
    ArgumentCaptor<FixtureDef> fixtureDefCaptor = ArgumentCaptor.forClass(FixtureDef.class);
    verify(mockBody).createFixture(fixtureDefCaptor.capture());
    FixtureDef fixtureDef = fixtureDefCaptor.getValue();

    assertNotNull(fixtureDef.shape);
    assertTrue(fixtureDef.shape instanceof PolygonShape);
  }

  @Test
  void constructorShouldDisposeOfShape() {
    ArgumentCaptor<FixtureDef> fixtureDefCaptor = ArgumentCaptor.forClass(FixtureDef.class);
    verify(mockBody).createFixture(fixtureDefCaptor.capture());
    FixtureDef fixtureDef = fixtureDefCaptor.getValue();
    assertNotNull(fixtureDef.shape);
  }

  @Test
  void getTypeShouldReturnCorrectType() {
    assertEquals(ElementType.LAVA, element.getType());
  }

  @Test
  void testDraw() {
    Batch mockBatch = mock(Batch.class);
    element.draw(mockBatch);
    verify(mockBatch).draw(any(TextureRegion.class), anyFloat(), anyFloat(), eq(1f), eq(1f));
  }

  @Test
  void disposeShouldDisposeTexture() throws Exception {
    @SuppressWarnings("unchecked")
    Animation<TextureRegion> mockAnimation = mock(Animation.class);
    TextureRegion mockRegion = mock(TextureRegion.class);
    when(mockRegion.getTexture()).thenReturn(mockTexture);
    TextureRegion[] regions = new TextureRegion[]{mockRegion};
    when(mockAnimation.getKeyFrames()).thenReturn(regions);
    Field animationField = Element.class.getDeclaredField("animation");
    animationField.setAccessible(true);
    animationField.set(element, mockAnimation);

    element.dispose();
    verify(mockTexture, atLeastOnce()).dispose();
  }
}