package model;

import controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TacticianTest {
    private GameController controller;
    private List<Tactician> players;

    @BeforeEach
    public void setUp() {
        controller = new GameController(4,7);
        players = controller.createPlayers(4);
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
        controller.assignSorcererToActualPlayer(1,0);
        controller.getTurnOwner().selectUnitIn(1,0);
        assertNotNull(controller.getTurnOwner().getSelectedUnit());
    }
}
