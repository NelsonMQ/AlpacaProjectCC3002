package model.items;

import model.units.IUnit;

/**
 * This interface represents the <i>weapons</i> that the units of the game can use.
 * <p>
 * The signature for all the common methods of the weapons are defined here. Every weapon have a
 * base damage and is strong or weak against other type of weapons.
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public interface IEquipableItem {

  /**
   * Equips this item to a unit.
   *
   * @param unit
   *     the unit that will be quipped with the item
   */
  void equipTo(IUnit unit);

  /**
   * @return the unit that has currently equipped this item
   */
  IUnit getOwner();

  /**
   * @return the name of the item
   */
  String getName();

  /**
   * @return the power of the item
   */
  int getPower();

  /**
   * @return the minimum range of the item
   */
  int getMinRange();

  /**
   * @return the maximum range of the item
   */
  int getMaxRange();
  
  /**
   * the unit that has an equipable item receive an attack from sword
   * @param sword
   *      the item whose is attacking
   */
  void receiveSwordDamage(Sword sword);

  /**
   *the unit that has an equipable item receive an attack from axe
   * @param axe
   *      the item whose is attacking
   */
  void receiveAxeDamage(Axe axe);

  /**
   *the unit that has an equipable item receive an attack from bow
   * @param bow
   *      the item whose is attacking
   */
  void receiveBowDamage(Bow bow);

  /**
   *the unit that has an equipable item receive an attack from darkness
   * @param darkness
   *      the item whose is attacking
   */
  void receiveDarknessDamage(Darkness darkness);

  /**
   *the unit that has an equipable item receive an attack from light
   * @param light
   *      the item whose is attacking
   */
  void receiveLightDamage(Light light);

  /**
   *the unit that has an equipable item receive an attack from spear
   * @param spear
   *      the item whose is attacking
   */
  void receiveSpearDamage(Spear spear);

  /**
   *the unit that has an equipable item receive an attack from spirit
   * @param spirit
   *      the item whose is attacking
   */
  void receiveSpiritDamage(Spirit spirit);

  boolean canAttack();
}
