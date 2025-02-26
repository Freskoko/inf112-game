package inf112.firegirlwaterboy.model;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;

public class InteractiveObject {
  private Rectangle bounds;
  private String value;

  public InteractiveObject(RectangleMapObject mapObject) {
    this.bounds = mapObject.getRectangle();
    this.value = mapObject.getProperties().get("value", String.class);

  }

  public Rectangle getBounds() {
    return bounds;
  }

  public String getValue() {
    return value;
  }

  public boolean isBlocked() {
    return "border".equals(value);
  }

  /*
   * public static Array<InteractiveObject> loadObjects(MapObjects mapObjects) {
   * Array<InteractiveObject> objects = new Array<>();
   * 
   * for (MapObject object : mapObjects) {
   * if (object instanceof RectangleMapObject) {
   * Rectangle rect = ((RectangleMapObject) object).getRectangle();
   * 
   * String type = object.getProperties().get("type", String.class);
   * boolean blocked = object.getProperties().get("blocked", false,
   * Boolean.class);
   * 
   * objects.add(new InteractiveObject(rect, type, blocked));
   * }
   * }
   * return objects;
   * }
   */
}
