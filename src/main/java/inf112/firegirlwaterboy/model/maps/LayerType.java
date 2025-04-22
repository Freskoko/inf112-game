package inf112.firegirlwaterboy.model.maps;

public enum LayerType {
  PLAYER(0x0001),
  PLATFORM(0x0002),
  STATIC(0x00004),
  COLLECTABLE(0x0008),
  ELEMENT(0x0010),
  FINISH(0x0020),
  BACKGROUND(null),
  SPAWN(null);

  private final Integer bit;

  LayerType(Integer bit) {
    this.bit = bit;
  }

  public short getBit() {
    return (short) (int) this.bit;
  }
}
