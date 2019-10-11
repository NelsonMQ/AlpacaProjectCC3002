package model;

import controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TacticianTest {
    private GameController controller;

    @BeforeEach
    public void setUp() {
        controller = new GameController(4,7);
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
    public void equiItemToSelectUnitTest() {
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
    public void someObserverMethodsTest() {
        controller.initGame(5);
        controller.assignSwordMasterToActualPlayer(0,0);
        controller.assignSorcererToActualPlayer(0,2);
        controller.getTurnOwner().selectUnitIn(0,2);
        assertNull(controller.getGameMap().getCell(0,1).getUnit());
        controller.getTurnOwner().moveSelectedUnitTo(0,1);
        assertNotNull(controller.getGameMap().getCell(0,1).getUnit());

        controller.addLightToSelectedUnit();
        controller.getTurnOwner().selectItem(0);
        assertEquals("Light",controller.getSelectedItem().getName());

        controller.selectUnitIn(0,0);
        assertEquals(0,controller.getSelectedUnit().getItems().size());
        controller.selectUnitIn(0,1);
        controller.getTurnOwner().giveItemTo(0,0);
        assertEquals(0,controller.getSelectedUnit().getItems().size());
        controller.selectUnitIn(0,0);
        assertEquals(1,controller.getSelectedUnit().getItems().size());
    }

    @Test
    public void endTurnTest() {
        controller.initGame(5);
        controller.assignSorcererToActualPlayer(0,0);
        controller.getTurnOwner().endTurn();
        assertEquals(0,controller.getTurnOwner().getUnits().size());
        controller.getTurnOwner().endTurn();
        assertEquals(0,controller.getTurnOwner().getUnits().size());
        controller.getTurnOwner().endTurn();
        assertEquals(0,controller.getTurnOwner().getUnits().size());
        controller.getTurnOwner().endTurn();
        assertEquals(2,controller.getRoundNumber());
        assertEquals(1,controller.getTurnOwner().getUnits().size());
    }

}
