package inf112.firegirlwaterboy.model.maps;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;

public final class MapUtils {

  private MapUtils() {
  }

  static final Vector2 DEFAULT_SPAWN_POS = new Vector2(2, 2);
  public static final float PPM = 32;

  public static float getY(MapObject object) {
    return object.getProperties().get("y", Float.class) / MapUtils.PPM;
  }

  public static float getX(MapObject object) {
    return object.getProperties().get("x", Float.class) / MapUtils.PPM;
  }

  public static String getProperty(MapObject object, String property) {
    return object.getProperties().get(property, String.class).toUpperCase();
  }

  public static float getCY(MapObject object) {
    float y = object.getProperties().get("y", Float.class) / MapUtils.PPM;
    float height = MapUtils.getHeight(object);
    return y + height / 2;
  }

  public static float getCX(MapObject object) {
    float x = object.getProperties().get("x", Float.class) / MapUtils.PPM;
    float width = MapUtils.getWidth(object);
    return x + width / 2;
  }

  public static float getHeight(MapObject object) {
    return object.getProperties().get("height", Float.class) / MapUtils.PPM;
  }

  public static float getWidth(MapObject object) {
    return object.getProperties().get("width", Float.class) / MapUtils.PPM;
  }
}
