package inf112.firegirlwaterboy.model.entity;

import java.util.HashSet;

/**
 * Component that stores immunities of an entity.
 */
public class ImmunityComponent {
  private HashSet<String> immunities;

  public ImmunityComponent(HashSet<String> immunities) {
    this.immunities = new HashSet<>(immunities);
  }

  /**
   * Checks if the entity is immune to a hazard.
   * 
   * @param hazard to check
   * @return true if the entity is immune to the hazard
   */
  public boolean isImmuneTo(String hazard) {
    return immunities.contains(hazard);
  }

  /**
   * Adds an immunity to the entity.
   * 
   * @param hazard to add
   */
  public void addImmunity(String hazard) {
    immunities.add(hazard);
  }

  /**
   * Removes an immunity from the entity.
   * 
   * @param hazard to remove
   */
  public void removeImmunity(String hazard) {
    immunities.remove(hazard);
  }
}
