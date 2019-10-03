package model.units;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * @author Ignacio Slater Muñoz
 */
public class ClericTest extends AbstractTestUnit {

  private Cleric cleric;

  /**
   * Set up the main unit that's going to be tested in the test set
   */
  @Override
  public void setTestUnit() {
    cleric = new Cleric(50, 2, field.getCell(0, 0));
  }

  /**
   * @return the current unit being tested
   */
  @Override
  public IUnit getTestUnit() {
    return cleric;
  }

  @Test
  @Override
  public void equipStaffTest() {
    assertNull(cleric.getEquippedItem());
    cleric.addItem(staff);
    cleric.equipItem(staff);
    assertEquals(staff, cleric.getEquippedItem());
  }

  @Override
  @Test
  public void combatTest() {
    Sorcerer sorcerer = new Sorcerer(50,2,field.getCell(1, 0));
    cleric.addItem(staff);
    cleric.equipItem(staff);
    cleric.combat(sorcerer);
    assertEquals(sorcerer.getCurrentHitPoints(),50);
  }

  @Test
  public void healToTest() {
    Sorcerer sorcerer = new Sorcerer(50,2,field.getCell(1, 0));
    cleric.addItem(staff);
    sorcerer.setCurrentHitPoints(0);
    cleric.useItemOn(sorcerer);
    assertEquals(0,sorcerer.getCurrentHitPoints());

    sorcerer.setCurrentHitPoints(20);
    cleric.useItemOn(sorcerer);
    assertEquals(20,sorcerer.getCurrentHitPoints());

    cleric.equipItem(staff);
    cleric.useItemOn(sorcerer);
    assertEquals(30,sorcerer.getCurrentHitPoints());

    sorcerer.setCurrentHitPoints(45);
    cleric.useItemOn(sorcerer);
    assertEquals(50,sorcerer.getCurrentHitPoints());

  }
}