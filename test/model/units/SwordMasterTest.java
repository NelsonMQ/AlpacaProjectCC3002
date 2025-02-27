package model.units;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Ignacio Slater Muñoz
 */
public class SwordMasterTest extends AbstractTestUnit {

  private SwordMaster swordMaster;

  /**
   * Set up the main unit that's going to be tested in the test set
   */
  @Override
  public void setTestUnit() {
    swordMaster = new SwordMaster(50, 2, field.getCell(0, 0));
  }

  /**
   * @return the current unit being tested
   */
  @Override
  public IUnit getTestUnit() {
    return swordMaster;
  }

  @Test
  @Override
  public void equipSwordTest() {
    assertNull(swordMaster.getEquippedItem());
    swordMaster.addItem(sword);
    swordMaster.equipItem(sword);
    assertEquals(sword, swordMaster.getEquippedItem());
  }

  @Override
  @Test
  public void combatTest() {
    Fighter fighter = new Fighter(20,2,field.getCell(1, 0));
    swordMaster.addItem(sword);
    swordMaster.equipItem(sword);
    swordMaster.useItemOn(fighter);
    assertEquals(10,fighter.getCurrentHitPoints());

    fighter.addItem(axe);
    fighter.equipItem(axe);
    swordMaster.useItemOn(fighter);
    assertEquals(-5,fighter.getCurrentHitPoints());
    assertEquals(50,swordMaster.getCurrentHitPoints());
  }
}