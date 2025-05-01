package inf112.firegirlwaterboy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MovementTypeTest {
  @Test
  void testGetOppositeDir() {
    assertEquals(MovementType.getOppositeDir(MovementType.DOWN), MovementType.UP);
    assertEquals(MovementType.getOppositeDir(MovementType.UP), MovementType.DOWN);
    assertEquals(MovementType.getOppositeDir(MovementType.RIGHT), MovementType.LEFT);
    assertEquals(MovementType.getOppositeDir(MovementType.LEFT), MovementType.RIGHT);
    assertEquals(MovementType.getOppositeDir(MovementType.STOP), MovementType.STOP);
  }
}