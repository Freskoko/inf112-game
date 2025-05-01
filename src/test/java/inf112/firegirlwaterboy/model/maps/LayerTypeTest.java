package inf112.firegirlwaterboy.model.maps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LayerTypeTest {
  @Test
  void testGetBit() {
    assertEquals((short) 0x0001, LayerType.PLAYER.getBit());
    assertEquals((short) 0x0002, LayerType.PLATFORM.getBit());
    assertEquals((short) 0x0004, LayerType.STATIC.getBit());
    assertEquals((short) 0x0008, LayerType.COLLECTABLE.getBit());
    assertEquals((short) 0x0010, LayerType.ELEMENT.getBit());
    assertEquals((short) 0x0020, LayerType.FINISH.getBit());

    assertThrows(NullPointerException.class, () -> LayerType.BACKGROUND.getBit());
    assertThrows(NullPointerException.class, () -> LayerType.SPAWN.getBit());
  }
}