package model.items;

import model.units.*;

/**
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public class Bow extends AbstractAttackItem {

  /**
   * Creates a new bow.
   * <p>
   * Bows are weapons that can't attack adjacent units, so it's minimum range must me greater than
   * one.
   *
   * @param name
   *     the name of the bow
   * @param power
   *     the damage power of the bow
   * @param minRange
   *     the minimum range of the bow
   * @param maxRange
   *     the maximum range of the bow
   */
  public Bow(final String name, final int power, final int minRange, final int maxRange) {
    super(name, power, minRange, maxRange);
    setMinRange(Math.max(minRange, 2));
    setMaxRange(Math.max(maxRange, getMinRange()));
  }

  public void attack(IUnit unit) {
    if(unit.getEquippedItem()==null){
      normalAttack(unit);
    }
    else {
      unit.getEquippedItem().receiveBowDamage(this);
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
    normalAttack(this.getOwner());
  }

  @Override
  public void receiveSwordDamage(Sword sword) {
    normalAttack(this.getOwner());
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
  public void equipToArcher(Archer archer) {
    this.equipTo(archer);
  }
}
