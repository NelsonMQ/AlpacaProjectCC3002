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
    this.minRange = Math.max(minRange, 2);
    this.maxRange = Math.max(maxRange, this.minRange);
  }

  public void attack(IUnit unit) {
    if(unit.getEquippedItem()==null){
      normalAttack(unit);
    }
    else {
      unit.getEquippedItem().receiveBowDamage(this);
    }
  }

  public void receiveAxeDamage(Axe axe) {
    normalAttack(this.getOwner());
  }

  public void receiveBowDamage(Bow bow) {
    normalAttack(this.getOwner());
  }

  public void receiveSpearDamage(Spear spear) {
    normalAttack(this.getOwner());
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

  public void equipToArcher(Archer archer) {
    this.equipTo(archer);
  }

  public void equipToFighter(Fighter fighter){}

  public void equipToSorcerer(Sorcerer sorcerer){}

  public void equipToHero(Hero hero){}

  public void equipToCleric(Cleric cleric){}

  public void equipToSwordMaster(SwordMaster swordMaster){}
}
