package model.units;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test set for the alpaca unit
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public class AlpacaTest extends AbstractTestUnit {

  private Alpaca alpaca;

  @Override
  public void setTestUnit() {
    alpaca = new Alpaca(50, 2, field.getCell(0, 0));
  }

  @Override
  public Alpaca getTestUnit() {
    return alpaca;
  }

  @Override
  @Test
  public void setItemTest(){
    assertEquals(getTestUnit().getItems().size(),0);
    getTestUnit().setItem(axe);
    assertTrue(getTestUnit().getItems().contains(axe));
    getTestUnit().setItem(bow);
    getTestUnit().setItem(sword);
    assertEquals(getTestUnit().getItems().size(),3);
    getTestUnit().setItem(staff);
    assertTrue(getTestUnit().getItems().contains(staff));
    assertEquals(getTestUnit().getItems().size(),4);
  }
}