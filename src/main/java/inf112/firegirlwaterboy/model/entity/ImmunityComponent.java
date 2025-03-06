package inf112.firegirlwaterboy.model.entity;

import java.util.HashSet;

public class ImmunityComponent {
  private HashSet<String> immunities;

  public ImmunityComponent(HashSet<String> immunities) {
    this.immunities = new HashSet<>(immunities);
  }

  public boolean isImmuneTo(String hazard) {
    return immunities.contains(hazard);
  }

  public void addImmunity(String hazard) {
    immunities.add(hazard);
  }

  public void removeImmunity(String hazard) {
    immunities.remove(hazard);
  }
}
