package model.items;

import model.units.*;

/**
 * This class represents a sword type item.
 * <p>
 * Swords are strong against axes and magic weapons, and weak against spears.
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public class Sword extends AbstractAttackItem {

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

  public void attack(IUnit unit) {
    if(unit.getEquippedItem()==null){
      normalAttack(unit);
    }
    else {
      unit.getEquippedItem().receiveSwordDamage(this);
    }
  }

  public void receiveAxeDamage(Axe axe) {
    weakAttack(this.getOwner());
  }

  public void receiveBowDamage(Bow bow) {
    normalAttack(this.getOwner());
  }

  public void receiveSpearDamage(Spear spear) {
    strongAttack(this.getOwner());
  }

  public void receiveSwordDamage(Sword sword) {
    normalAttack(this.getOwner());
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

  @Override
  public void equipToSwordMaster(SwordMaster swordMaster) {
    this.equipTo(swordMaster);
  }
}
