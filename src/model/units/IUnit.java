package model.units;

import java.util.List;
import model.items.IEquipableItem;
import model.map.Location;

/**
 * This interface represents all units in the game.
 * <p>
 * The signature of all the common methods that a unit can execute are defined here. All units
 * except some special ones can carry at most 3 weapons.
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public interface IUnit {

  /**
   * Sets the currently equipped item of this unit.
   *
   * @param item
   *     the item to equip
   */
  void equipItem(IEquipableItem item);

  /**
   * @return hit points of the unit
   */
  int getCurrentHitPoints();

  /**
   * Return the maxHP of the unit
   *
   * @return the maxHP of the unit
   */
  int getMaxHP();

  /**
   * @return the items carried by this unit
   */
  List<IEquipableItem> getItems();

  /**
   * @return the currently equipped item
   */
  IEquipableItem getEquippedItem();

  /**
   * @param item
   *     the item to be equipped
   */
  void setEquippedItem(IEquipableItem item);

  /**
   * Add a new item to the unit's items
   *
   * @param item
   *     the item to add
   */
  void addItem(IEquipableItem item);

  /**
   * Removes a item from the list
   * @param item
   *    the item to remove
   */
  void removeItem(IEquipableItem item);

  /**
   * @return the current location of the unit
   */
  Location getLocation();

  /**
   * Sets a new location for this unit
   *
   * @param location
   *     the location to be set
   */
  void setLocation(final Location location);

  /**
   * @return the number of cells this unit can move
   */
  int getMovement();

  /**
   *
   * @return the maximum number of items that the unit can have
   */
  int getMaxItems();

  /**
   * Moves this unit to another location.
   * <p>
   * If the other location is out of this unit's movement range, the unit doesn't move.
   */
  void moveTo(Location targetLocation);

  /**
   * Gives the item to another unit, the distance between units must be 1.
   * <p>
   * If the unit doesn't have the item, nothing happens.
   *
   * @param unit
   *      the unit that will give the item.
   *
   * @param item
   *     the item that will be given.
   */
  void giveItemTo(IUnit unit, IEquipableItem item);

  /**
   * Checks if the unit can attack to the other one
   *
   * @param unit the unit to attack
   * @return true if can attack
   */
  boolean canAttack(IUnit unit);

  /**
   * Attacks to another unit
   *
   * @param unit
   *    the unit to attack.
   */
  void attackTo(IUnit unit);

  /**
   * The units will fight
   * @param unit
   *    the unit to fight
   */
  void combat(IUnit unit);

  /**
   * Sets the current hit points of the unit
   */
  void setCurrentHitPoints(int hitPoints);

  /**
   * Use the item in a unit, it can attack or heal
   * @param unit the unit to use the item
   */
  void useItem(IUnit unit);

  /**
   * Use the item in a unit
   * @param unit the unit to use the item
   */
  void useItemOn(IUnit unit);
}
