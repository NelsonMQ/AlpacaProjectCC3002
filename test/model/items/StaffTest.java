package model.items;

import model.map.Location;
import model.units.Cleric;
import model.units.IUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test set for staffs
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public class StaffTest extends AbstractTestItem {

  private Staff staff;
  private Staff wrongStaff;
  private Cleric cleric;

  /**
   * Sets which item is going to be tested
   */
  @Override
  public void setTestItem() {
    expectedName = "Common staff";
    expectedPower = 5;
    expectedMinRange = 1;
    expectedMaxRange = 1;
    staff = new Staff(expectedName, expectedPower, expectedMinRange, expectedMaxRange);
  }

  /**
   * Sets up an item with wrong ranges setted.
   */
  @Override
  public void setWrongRangeItem() {
    wrongStaff = new Staff("Wrong staff", 0, -1, -2);
  }

  /**
   * Sets the unit that will be equipped with the test item
   */
  @Override
  public void setTestUnit() {
    cleric = new Cleric(50, 5, new Location(0, 0));
  }

  @Override
  public IEquipableItem getWrongTestItem() {
    return wrongStaff;
  }

  /**
   * @return the item being tested
   */
  @Override
  public IEquipableItem getTestItem() {
    return staff;
  }

  /**
   * @return a unit that can equip the item being tested
   */
  @Override
  public IUnit getTestUnit() {
    return cleric;
  }

  /**
   * Checks if receive"Item"Damage works correctly
   */
  @Test
  public void receiveDamageTest() {
    Axe axe = new Axe("Axe", 10, 1, 2);
    Sword sword = new Sword("Sword", 10, 1, 2);
    Spear spear = new Spear("Spear", 10, 1, 2);
    Bow bow = new Bow("Bow", 10, 2, 3);
    Light light = new Light("Light", 10, 1, 2);
    Darkness darkness = new Darkness("Darkness", 10, 1, 2);
    Spirit spirit = new Spirit("Spirit", 10, 1, 2);

    getTestItem().equipTo(getTestUnit());
    getTestItem().receiveAxeDamage(axe);
    assertEquals(40,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveBowDamage(bow);
    assertEquals(30,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveDarknessDamage(darkness);
    assertEquals(15,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveLightDamage(light);
    assertEquals(0,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveSpearDamage(spear);
    assertEquals(-10,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveSpiritDamage(spirit);
    assertEquals(-25,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveSwordDamage(sword);
    assertEquals(-35,getTestUnit().getCurrentHitPoints());
  }
}
