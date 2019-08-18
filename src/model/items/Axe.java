package model.items;

import model.units.IUnit;

/**
 * This class represents an Axe.
 * <p>
 * Axes are strong against spears and magic weapons, but weak against swords.
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public class Axe extends AttackItem {

  /**
   * Creates a new Axe item
   *
   * @param name
   *     the name of the Axe
   * @param power
   *     the damage of the axe
   * @param minRange
   *     the minimum range of the axe
   * @param maxRange
   *     the maximum range of the axe
   */
  public Axe(final String name, final int power, final int minRange, final int maxRange) {
    super(name, power, minRange, maxRange);
  }

  @Override
  public void attack(IUnit unit) {
    if(unit.getCurrentHitPoints()>0) {
      if(unit.getEquippedItem() instanceof Spear ||
          unit.getEquippedItem() instanceof Light ||
          unit.getEquippedItem() instanceof Spirit ||
          unit.getEquippedItem() instanceof Darkness) {
        unit.setCurrentHitPoints(unit.getCurrentHitPoints() - (int)(1.5*this.getPower()));
      }
      else if (unit.getEquippedItem() instanceof Sword){
        if( this.getPower()-20>0)
          unit.setCurrentHitPoints(unit.getCurrentHitPoints() - (this.getPower() - 20));
      }
      else{
        unit.setCurrentHitPoints(unit.getCurrentHitPoints() - this.getPower());
      }
    }
  }
}
