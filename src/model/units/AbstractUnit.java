package model.units;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.items.IAttackItem;
import model.items.IEquipableItem;
import model.map.Location;

/**
 * This class represents an abstract unit.
 * <p>
 * An abstract unit is a unit that cannot be used in the
 * game, but that contains the implementation of some of the methods that are common for most
 * units.
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public abstract class AbstractUnit implements IUnit {

  private final List<IEquipableItem> items = new ArrayList<>();
  private int currentHitPoints;
  private final int movement;
  private IEquipableItem equippedItem;
  private Location location;
  private int maxItems;
  private int maxHP;

  /**
   * Creates a new Unit.
   *
   * @param hitPoints
   *     the maximum amount of damage a unit can sustain
   * @param movement
   *     the number of panels a unit can move
   * @param location
   *     the current position of this unit on the map
   * @param maxItems
   *     maximum amount of items this unit can carry
   */
  protected AbstractUnit(final int hitPoints, final int movement,
      final Location location, final int maxItems, final IEquipableItem... items) {
    this.currentHitPoints = hitPoints;
    this.movement = movement;
    this.location = location;
    this.items.addAll(Arrays.asList(items).subList(0, min(maxItems, items.length)));
    this.maxItems = maxItems;
    this.maxHP = hitPoints;
  }

  @Override
  public int getCurrentHitPoints() {
    return currentHitPoints;
  }

  @Override
  public int getMaxHP() {
    return maxHP;
  }

  @Override
  public List<IEquipableItem> getItems() {
    return List.copyOf(items);
  }

  @Override
  public IEquipableItem getEquippedItem() {
    return equippedItem;
  }

  @Override
  public void setEquippedItem(final IEquipableItem item) {
    this.equippedItem = item;
  }

  @Override
  public void addItem(IEquipableItem item){
    if(this.items.size()<this.getMaxItems()){
      this.items.add(item);
      item.setOwner(this);
    }
  }

  @Override
  public void removeItem(IEquipableItem item){
    if(this.items.contains(item)){
      this.items.remove(item);
    }
  }

  @Override
  public Location getLocation() {
    return location;
  }

  @Override
  public void setLocation(final Location location) {
    this.location = location;
  }

  @Override
  public int getMovement() {
    return movement;
  }

  @Override
  public int getMaxItems() { return maxItems; }

  @Override
  public void moveTo(final Location targetLocation) {
    if (getLocation().distanceTo(targetLocation) <= getMovement()
        && targetLocation.getUnit() == null) {
      setLocation(targetLocation);
      targetLocation.setUnit(this);
    }
  }

  @Override
  public void giveItemTo(IUnit unit, IEquipableItem item){
    if(this.getLocation().distanceTo(unit.getLocation())==1
        && this.getItems().contains(item)
        && unit.getItems().size()<unit.getMaxItems()){
      this.removeItem(item);
      unit.addItem(item);
    }
  }

  @Override
  public boolean canAttack(IUnit unit) {
    double distance = getLocation().distanceTo(unit.getLocation());
    return getEquippedItem()!= null &&
            getEquippedItem().canAttack() &&
            distance <= getEquippedItem().getMaxRange() &&
            distance >= getEquippedItem().getMinRange() &&
            getCurrentHitPoints()>0;
  }

  @Override
  public void attackTo(IUnit unit) {
    if(this.canAttack(unit)){
      ((IAttackItem) getEquippedItem()).attack(unit);
    }
  }

  @Override
  public void combat(IUnit unit) {
    this.attackTo(unit);
    if(unit.canAttack(this)){
      unit.attackTo(this);
    }
  }

  @Override
  public void useItemOn(IUnit unit) {
    this.useItem(unit);
  }

  @Override
  public abstract void useItem(IUnit unit);

  @Override
  public void setCurrentHitPoints(int hitPoints) {
    this.currentHitPoints = hitPoints;
  }
}
