package model.items;

import model.units.IUnit;

/**
 * This class represents a <i>spear</i>.
 * <p>
 * Spears are strong against swords and magic weapons,  and weak against axes
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public class Spear extends AbstractAttackItem {

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
  public Spear(final String name, final int power, final int minRange, final int maxRange) {
    super(name, power, minRange, maxRange);
  }

  public void attack(IUnit unit) {
    if(unit.getEquippedItem()==null){
      normalAttack(unit);
    }
    else {
      unit.getEquippedItem().receiveSpearDamage(this);
    }
  }

  public void receiveAxeDamage(Axe axe) {
    strongAttack(this.getOwner());
  }

  public void receiveBowDamage(Bow bow) {
    normalAttack(this.getOwner());
  }

  public void receiveSpearDamage(Spear spear) {
    normalAttack(this.getOwner());
  }

  public void receiveSwordDamage(Sword sword) {
    weakAttack(this.getOwner());
  }

  public void receiveLightDamage(Light light) {
    strongAttack(this.getOwner());
  }

  public void receiveDarknessDamage(Darkness darkness) {
    strongAttack(this.getOwner());
  }

  public void receiveSpiritDamage(Spirit spirit) {
    strongAttack(this.getOwner());
  }
}
