package model;

import controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TacticianTest {
    private GameController controller;
    private long randomSeed;
    private long randomSeed2;

    @BeforeEach
    public void setUp() {
        randomSeed = 10;
        randomSeed2 = 5;
        controller = new GameController(4,7);
        controller.reRollMap(new Random(randomSeed),7,new Random(randomSeed2));
    }

    @Test
    public void getUnitDataTest() {
        controller.assignSwordMasterToActualPlayer(0,0);
        controller.selectUnitIn(0,0);
        assertEquals(50,controller.getTurnOwner().getSelectedUnitMaxHP());
        assertEquals(50,controller.getTurnOwner().getSelectedUnitCurrentHitPoints());
        assertEquals(1,controller.getTurnOwner().getSelectedUnitMovement());
    }

    @Test
    public void getItemDataTest() {
        controller.assignSwordMasterToActualPlayer(0,0);
        controller.selectUnitIn(0,0);
        controller.addSwordToSelectedUnit();
        controller.selectItem(0);
        assertEquals("Sword",controller.getTurnOwner().getSelectedItemName());
        assertEquals(10,controller.getTurnOwner().getSelectedItemPower());
        assertEquals(1,controller.getTurnOwner().getSelectedItemMinRange());
        assertEquals(1,controller.getTurnOwner().getSelectedItemMaxRange());
    }

    @Test
    public void selectUnitTest() {
        controller.initGame(5);
        controller.assignSorcererToActualPlayer(0,0);
        controller.getTurnOwner().selectUnitIn(0,0);
        assertNotNull(controller.getTurnOwner().getSelectedUnit());
    }

    @Test
    public void equipItemToSelectUnitTest() {
        controller.initGame(5);
        controller.assignSorcererToActualPlayer(0,0);
        controller.getTurnOwner().selectUnitIn(0,0);
        controller.addLightToSelectedUnit();
        assertNull(controller.getSelectedUnit().getEquippedItem());
        controller.getTurnOwner().equipItemToSelectedUnit(0);
        assertNotNull(controller.getTurnOwner().getSelectedUnit().getEquippedItem());
        assertEquals("Light",controller.getTurnOwner().getSelectedUnit().getEquippedItem().getName());
    }

    @Test
    public void giveItemToTest() {
        controller.assignSorcererToActualPlayer(0,0);
        controller.assignSorcererToActualPlayer(0,1);
        controller.selectUnitIn(0,0);
        controller.addLightToSelectedUnit();
        assertEquals(1,controller.getSelectedUnit().getItems().size());
        controller.selectUnitIn(0,1);
        assertEquals(0,controller.getSelectedUnit().getItems().size());
        controller.selectUnitIn(0,0);
        controller.selectItem(0);
        controller.getTurnOwner().giveItemTo(0,1);
        assertEquals(0,controller.getSelectedUnit().getItems().size());
        controller.getTurnOwner().selectUnitIn(0,1);
        assertEquals(1,controller.getSelectedUnit().getItems().size());
    }

    @Test
    public void useItemOnTest() {
        controller.assignSorcererToActualPlayer(0,0);
        controller.selectUnitIn(0,0);
        controller.addLightToSelectedUnit();
        controller.equipItem(0);
        controller.initGame(5);
        controller.endTurn();
        controller.assignSorcererToActualPlayer(0,1);
        controller.selectUnitIn(0,1);
        controller.addSpiritToSelectedUnit();
        controller.equipItem(0);

        assertEquals(50,controller.getGameMap().getCell(0,0).getUnit().getCurrentHitPoints());
        assertEquals(50,controller.getSelectedUnit().getCurrentHitPoints());
        controller.getTurnOwner().useItemOn(0,0);
        assertEquals(35,controller.getGameMap().getCell(0,0).getUnit().getCurrentHitPoints());
        assertEquals(50,controller.getSelectedUnit().getCurrentHitPoints());
    }

    @Test
    public void moveUnitToTest() {
        controller.initGame(5);
        controller.assignSorcererToActualPlayer(0,0);
        assertNotNull(controller.getGameMap().getCell(0,0).getUnit());
        controller.selectUnitIn(0,0);
        controller.getTurnOwner().moveSelectedUnitTo(0,2);
        assertNull(controller.getGameMap().getCell(0,2).getUnit());
        assertNotNull(controller.getGameMap().getCell(0,0).getUnit());
        controller.selectUnitIn(0,2);
        controller.getTurnOwner().moveSelectedUnitTo(0,1);
        //No lo dejara moverse, ya que ya la movio una vez en el turno
        assertNotNull(controller.getGameMap().getCell(0,0).getUnit());
        assertNull(controller.getGameMap().getCell(0,1).getUnit());
    }

    @Test
    public void assignUnitsTest() {
        controller.initGame(5);
        assertNull(controller.getGameMap().getCell(0,0).getUnit());
        assertNull(controller.getGameMap().getCell(0,1).getUnit());
        controller.getTurnOwner().assignSorcerer(0,0);
        controller.getTurnOwner().assignFighter(0,1);
        assertNotNull(controller.getGameMap().getCell(0,0).getUnit());
        assertNotNull(controller.getGameMap().getCell(0,1).getUnit());
    }

    @Test
    public void addItemsTest() {
        controller.assignSwordMasterToActualPlayer(0,0);
        controller.selectUnitIn(0,0);
        controller.getTurnOwner().addSwordToSelectedUnit();
        controller.getTurnOwner().addSpiritToSelectedUnit();
        assertEquals(2,controller.getSelectedUnit().getItems().size());
        controller.selectItem(0);
        assertEquals("Sword",controller.getSelectedItemName());
        controller.selectItem(1);
        assertEquals("Spirit",controller.getSelectedItemName());
    }

}
