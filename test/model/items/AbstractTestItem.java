package model.items;

import model.map.Location;
import model.units.Fighter;
import model.units.IUnit;
import model.units.Sorcerer;
import model.units.SwordMaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Defines some common methods for all the items tests
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public abstract class AbstractTestItem {

  protected String expectedName;
  protected int expectedPower;
  protected short expectedMinRange;
  protected short expectedMaxRange;

  /**
   * Sets up the items to be tested
   */
  @BeforeEach
  public void setUp() {
    setTestItem();
    setWrongRangeItem();
    setTestUnit();
  }

  /**
   * Sets up a correctly implemented item that's going to be tested
   */
  public abstract void setTestItem();

  /**
   * Sets up an item with wrong ranges setted.
   */
  public abstract void setWrongRangeItem();

  /**
   * Sets the unit that will be equipped with the test item
   */
  public abstract void setTestUnit();

  /**
   * Checks that the tested item cannot have ranges outside of certain bounds.
   */
  @Test
  public void incorrectRangeTest() {
    assertTrue(getWrongTestItem().getMinRange() >= 0);
    assertTrue(getWrongTestItem().getMaxRange() >= getWrongTestItem().getMinRange());
  }

  public abstract IEquipableItem getWrongTestItem();

  /**
   * Tests that the constructor for the tested item works properly
   */
  @Test
  public void constructorTest() {
    assertEquals(getExpectedName(), getTestItem().getName());
    assertEquals(getExpectedBasePower(), getTestItem().getPower());
    assertEquals(getExpectedMinRange(), getTestItem().getMinRange());
    assertEquals(getExpectedMaxRange(), getTestItem().getMaxRange());
  }

  /**
   * @return the expected name of the item
   */
  public String getExpectedName() {
    return expectedName;
  }

  /**
   * @return the item being tested
   */
  public abstract IEquipableItem getTestItem();

  /**
   * @return the expected power of the Item
   */
  public int getExpectedBasePower() {
    return expectedPower;
  }

  /**
   * @return the expected minimum range of the item
   */
  public int getExpectedMinRange() {
    return expectedMinRange;
  }

  /**
   * @return the expected maximum range of the item
   */
  public int getExpectedMaxRange() {
    return expectedMaxRange;
  }

  /**
   * Checks that the Item can be correctly equipped to a unit
   */
  @Test
  public void equippedToTest() {
    assertNull(getTestItem().getOwner());
    IUnit unit = getTestUnit();
    getTestItem().equipTo(unit);
    assertEquals(unit, getTestItem().getOwner());
  }

  /**
   * @return a unit that can equip the item being tested
   */
  public abstract IUnit getTestUnit();

  /**
   * Checks if canAttack works correctly
   */
  @Test
  public void  canAttackTest() {
    Axe axe = new Axe("Axe",10,1,2);
    assertTrue(axe.canAttack());

    Staff staff = new Staff("Staff", 10,1,2);
    assertFalse(staff.canAttack());
  }

  /**
   * Checks if normal, strong and weak attacks works correctly
   */
  @Test
  public void attacksTest() {
    Sword sword = new Sword("Sword", 10, 1, 2);
    Sorcerer sorcerer = new Sorcerer(50,2,new Location(0, 0));
    SwordMaster swordMaster = new SwordMaster(50,2,new Location(0, 1));
    sword.equipTo(swordMaster);

    sword.normalAttack(sorcerer);
    sword.strongAttack(sorcerer);
    sword.weakAttack(sorcerer);

    assertEquals(25,sorcerer.getCurrentHitPoints());
  }

  /**
   * Checks if attack(IUnit unit) method works correctly
   */
  @Test
  public void attackTest() {
    Axe axe = new Axe("Axe", 10, 1, 2);
    Sword sword = new Sword("Sword", 10, 1, 2);
    Spear spear = new Spear("Spear", 10, 1, 2);
    Bow bow = new Bow("Bow", 10, 2, 3);
    Light light = new Light("Light", 10, 1, 2);
    Darkness darkness = new Darkness("Darkness", 10, 1, 2);
    Spirit spirit = new Spirit("Spirit", 10, 1, 2);

    axe.attack(getTestUnit());
    sword.attack(getTestUnit());
    spear.attack(getTestUnit());
    bow.attack(getTestUnit());
    light.attack(getTestUnit());
    darkness.attack(getTestUnit());
    spirit.attack(getTestUnit());
    assertEquals(-20,getTestUnit().getCurrentHitPoints());
  }
}
