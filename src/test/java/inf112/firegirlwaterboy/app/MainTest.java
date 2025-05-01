package inf112.firegirlwaterboy.app;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class MainTest {
  @Test
  void testMainCompiles() {
    assertDoesNotThrow(() -> {
      Class<?> clazz = Class.forName("inf112.firegirlwaterboy.app.Main");
      clazz.getMethod("main", String[].class);
    });
  }
}