package model.items;

import model.units.*;

/**
 * This class represents an Axe.
 * <p>
 * Axes are strong against spears and magic weapons, but weak against swords.
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public class Axe extends AbstractAttackItem {

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

  public void attack(IUnit unit) {
    if(unit.getEquippedItem()==null){
      normalAttack(unit);
    }
    else {
      unit.getEquippedItem().receiveAxeDamage(this);
    }
  }

  @Override
  public void receiveAxeDamage(Axe axe) {
    normalAttack(this.getOwner());
  }

  @Override
  public void receiveBowDamage(Bow bow) {
    normalAttack(this.getOwner());
  }

  @Override
  public void receiveSpearDamage(Spear spear) {
    weakAttack(this.getOwner());
  }

  @Override
  public void receiveSwordDamage(Sword sword) {
    strongAttack(this.getOwner());
  }

  @Override
  public void receiveLightDamage(Light light) {
    strongAttack(this.getOwner());
  }

  @Override
  public void receiveDarknessDamage(Darkness darkness) {
    strongAttack(this.getOwner());
  }

  @Override
  public void receiveSpiritDamage(Spirit spirit) {
    strongAttack(this.getOwner());
  }

  @Override
  public void equipToFighter(Fighter fighter) {
    this.equipTo(fighter);
  }
}
