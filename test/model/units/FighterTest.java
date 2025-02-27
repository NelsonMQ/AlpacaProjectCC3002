package model.units;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * @author Ignacio Slater Muñoz
 */
public class FighterTest extends AbstractTestUnit {

  private Fighter fighter;

  /**
   * Set up the main unit that's going to be tested in the test set
   */
  @Override
  public void setTestUnit() {
    fighter = new Fighter(50, 2, field.getCell(0, 0));
  }

  /**
   * @return the current unit being tested
   */
  @Override
  public IUnit getTestUnit() {
    return fighter;
  }

  /**
   * Checks if the axe is equipped correctly to the unit
   */
  @Test
  @Override
  public void equipAxeTest() {
    assertNull(fighter.getEquippedItem());
    fighter.addItem(axe);
    fighter.equipItem(axe);
    assertEquals(axe, fighter.getEquippedItem());
  }

  @Override
  @Test
  public void combatTest() {
    SwordMaster swordMaster = new SwordMaster(20,2,field.getCell(1, 0));
    fighter.addItem(axe);
    fighter.equipItem(axe);
    fighter.useItemOn(swordMaster);
    assertEquals(10,swordMaster.getCurrentHitPoints());

    swordMaster.addItem(sword);
    swordMaster.equipItem(sword);
    fighter.useItemOn(swordMaster);
    assertEquals(10,swordMaster.getCurrentHitPoints());
    assertEquals(35,fighter.getCurrentHitPoints());

  }
}