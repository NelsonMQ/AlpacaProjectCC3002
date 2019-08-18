package model.items;

import model.units.IUnit;

/**
 * This class represents a sword type item.
 * <p>
 * Swords are strong against axes and magic weapons, and weak against spears.
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public class Sword extends AttackItem {

  /**
   * Creates a new Sword.
   *
   * @param name
   *     the name that identifies the weapon
   * @param power
   *     the base damage pf the weapon
   * @param minRange
   *     the minimum range of the weapon
   * @param maxRange
   *     the maximum range of the weapon
   */
  public Sword(final String name, final int power, final int minRange, final int maxRange) {
    super(name, power, minRange, maxRange);
  }

  @Override
  public void attack(IUnit unit) {
    if(unit.getCurrentHitPoints()>0) {
      if(unit.getEquippedItem() instanceof Axe ||
              unit.getEquippedItem() instanceof Light ||
              unit.getEquippedItem() instanceof Spirit ||
              unit.getEquippedItem() instanceof Darkness) {
        unit.setCurrentHitPoints(unit.getCurrentHitPoints() - (int)(1.5*this.getPower()));
      }
      else if (unit.getEquippedItem() instanceof Spear){
        if( this.getPower()-20>0)
          unit.setCurrentHitPoints(unit.getCurrentHitPoints() - (this.getPower() - 20));
      }
      else{
        unit.setCurrentHitPoints(unit.getCurrentHitPoints() - this.getPower());
      }
    }
  }
}
