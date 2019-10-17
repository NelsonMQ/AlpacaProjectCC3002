package controller;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import model.Tactician;
import model.map.Field;
import model.map.Location;
import model.units.IUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ignacio Slater Muñoz
 * @since v2.0
 */
class GameControllerTest {

  private GameController controller;
  private long randomSeed;
  private List<String> testTacticians;
  private long randomSeed2;

  @BeforeEach
  void setUp() {
    // Se define la semilla como un número aleatorio para generar variedad en los tests
    randomSeed = 10;
    randomSeed2 = 5;
    controller = new GameController(4, 7);
    testTacticians = List.of("Player 0", "Player 1", "Player 2", "Player 3");
    controller.reRollMap(new Random(randomSeed),7,new Random(randomSeed2));
  }

  @Test
  void getTacticians() {
    List<Tactician> tacticians = controller.getTacticians();
    assertEquals(4, tacticians.size());
    for (int i = 0; i < tacticians.size(); i++) {
      assertEquals(testTacticians.get(i), tacticians.get(i).getName());
    }
  }

  @Test
  void getGameMap() {
    //Cabe destacar que para saber de que forma sera el mapa, se crea un mapa nuevo con semillas
    // luego de la creacion del controller.
    Field gameMap = controller.getGameMap();
    assertEquals(7, gameMap.getSize());
    assertTrue(controller.getGameMap().isConnected());
    assertTrue(controller.getGameMap().getMap().containsKey("(0, 0)"));
    assertTrue(controller.getGameMap().getMap().containsKey("(0, 1)"));
    assertFalse(controller.getGameMap().getMap().containsKey("(2, 0)"));
  }

  @Test
  void getTurnOwner() {
    controller.initGame(2);
    assertEquals("Player 1", controller.getTurnOwner().getName());
    controller.endTurn();
    assertEquals("Player 3", controller.getTurnOwner().getName());
  }

  @Test
  void getRoundNumber() {
    controller.initGame(10);
    for (int i = 1; i < 10; i++) {
      assertEquals(i, controller.getRoundNumber());
      for (int j = 0; j < 4; j++) {
        controller.endTurn();
      }
    }
  }

  @Test
  void getMaxRounds() {
    Random randomTurnSequence = new Random();
    IntStream.range(0, 50).map(i -> randomTurnSequence.nextInt() & Integer.MAX_VALUE).forEach(nextInt -> {
      controller.initGame(nextInt);
      System.out.println(nextInt);
      assertEquals(nextInt, controller.getMaxRounds());
      System.out.println(nextInt);
    });
    controller.initEndlessGame();
    assertEquals(-1, controller.getMaxRounds());
  }

  @Test
  void endTurn() {
    Tactician firstPlayer = controller.getTurnOwner();
    // Nuevamente, para determinar el orden de los jugadores se debe usar una semilla
    Tactician secondPlayer = new Tactician("Player 3",controller.getGameMap()); // <- Deben cambiar esto (!)
    assertNotEquals(secondPlayer.getName(), firstPlayer.getName());

    controller.endTurn();
    assertNotEquals(firstPlayer.getName(), controller.getTurnOwner().getName());
    assertEquals(secondPlayer.getName(), controller.getTurnOwner().getName());
  }

  @Test
  void removeTactician() {
    assertEquals(4, controller.getTacticians().size());
    controller.getTacticians()
        .forEach(tactician -> Assertions.assertTrue(testTacticians.contains(tactician.getName())));

    controller.removeTactician("Player 0");
    assertEquals(3, controller.getTacticians().size());
    controller.getTacticians().forEach(tactician -> assertNotEquals("Player 0", tactician));
    controller.getTacticians()
        .forEach(tactician -> Assertions.assertTrue(testTacticians.contains(tactician.getName())));

    controller.removeTactician("Player 5");
    assertEquals(3, controller.getTacticians().size());
    controller.getTacticians()
        .forEach(tactician -> Assertions.assertTrue(testTacticians.contains(tactician.getName())));
  }

  @Test
  void getWinners() {
    controller.initGame(2);
    IntStream.range(0, 8).forEach(i -> controller.endTurn());
    assertEquals(4, controller.getWinners().size());
    controller.getWinners()
        .forEach(player -> Assertions.assertTrue(testTacticians.contains(player)));

    controller.initGame(2);
    IntStream.range(0, 4).forEach(i -> controller.endTurn());
    assertNull(controller.getWinners());
    controller.removeTactician("Player 0");
    controller.removeTactician("Player 2");
    IntStream.range(0, 2).forEach(i -> controller.endTurn());
    List<String> winners = controller.getWinners();
    assertEquals(2, winners.size());
    assertTrue(List.of("Player 1", "Player 3").containsAll(winners));

    controller.initEndlessGame();
    for (int i = 0; i < 3; i++) {
      assertNull(controller.getWinners());
      controller.removeTactician("Player " + i);
    }
    assertTrue(List.of("Player 3").containsAll(controller.getWinners()));
  }

  @Test
  void getSelectedUnit() {
    assertNull(controller.getSelectedUnit());
    controller.assignFighterToActualPlayer(1,2);
    IUnit unit = controller.getTurnOwner().getUnits().get(0);
    controller.getTurnOwner().setSelectedUnit(unit);
    assertEquals(controller.getTurnOwner().getSelectedUnit(),controller.getSelectedUnit());
  }

  @Test
  void selectUnitIn() {
    controller.selectUnitIn(0,0);
    assertNull(controller.getSelectedUnit());
    controller.assignSorcererToActualPlayer(0,0);
    IUnit unit = controller.getTurnOwner().getUnits().get(0);
    controller.selectUnitIn(0,0);
    assertEquals(unit,controller.getSelectedUnit());
  }

  @Test
  void getItems() {
    controller.assignSwordMasterToActualPlayer(0,0);
    controller.selectUnitIn(0,0);
    assertEquals(0,controller.getItems().size());

    controller.addAxeToSelectedUnit();
    assertEquals(1,controller.getItems().size());
    assertEquals(controller.getSelectedUnit().getItems().get(0),controller.getItems().get(0));
  }

  @Test
  void equipItem() {
    controller.assignArcherToActualPlayer(0,0);
    controller.selectUnitIn(0,0);
    controller.addBowToSelectedUnit();
    assertNull(controller.getSelectedUnit().getEquippedItem());
    controller.equipItem(0);
    assertNotNull(controller.getSelectedUnit().getEquippedItem());
  }

  @Test
  void useItemOn() {
    controller.assignFighterToActualPlayer(0,0);
    controller.endTurn();
    controller.assignSorcererToActualPlayer(1,0);
    controller.selectUnitIn(1,0);
    controller.addLightToSelectedUnit();
    controller.useItemOn(0,0);
    assertEquals(50,controller.getTacticians().get(1).getUnits().get(0).getCurrentHitPoints());
    controller.equipItem(0);
    controller.useItemOn(0,0);
    assertEquals(40,controller.getTacticians().get(1).getUnits().get(0).getCurrentHitPoints());
    assertEquals(50,controller.getSelectedUnit().getCurrentHitPoints());
  }

  @Test
  void selectItem() {
    controller.assignHeroToActualPlayer(0,0);
    controller.selectUnitIn(0,0);
    controller.addSpearToSelectedUnit();
    controller.selectItem(0);
    assertEquals("Spear", controller.getSelectedItem().getName());
    controller.addBowToSelectedUnit();
    controller.addDarknessToSelectedUnit();
    controller.selectItem(2);
    assertEquals("Darkness", controller.getSelectedItem().getName());
  }

  @Test
  void giveItemTo() {
    controller.assignAlpacaToActualPlayer(0,0);
    controller.assignClericToActualPlayer(1,0);
    controller.selectUnitIn(0,0);
    controller.addDarknessToSelectedUnit();
    controller.selectUnitIn(1,0);
    assertEquals(0,controller.getItems().size());
    controller.selectUnitIn(0,0);
    controller.selectItem(0);
    controller.giveItemTo(1,0);
    controller.selectUnitIn(1,0);
    assertEquals(1,controller.getItems().size());
    assertEquals("Darkness",controller.getItems().get(0).getName());
    controller.selectUnitIn(0,0);
    assertEquals(0,controller.getItems().size());
  }

  @Test
  void removeUnitTest() {
    controller.assignSwordMasterToActualPlayer(0,0);
    controller.assignSwordMasterToActualPlayer(0,1);
    assertEquals(2,controller.getTurnOwner().getUnits().size());
    controller.removeUnit(controller.getTurnOwner().getUnits().get(1));
    assertEquals(1,controller.getTurnOwner().getUnits().size());
    assertNull(controller.getGameMap().getCell(0,1).getUnit());

    controller.endTurn();
    controller.assignSwordMasterToActualPlayer(0,1);
    assertEquals(1,controller.getTurnOwner().getUnits().size());
    controller.removeUnit(controller.getTurnOwner().getUnits().get(0));
    assertEquals(0,controller.getTurnOwner().getUnits().size());
    assertNull(controller.getGameMap().getCell(0,1).getUnit());
  }

  @Test
  void moveSelectedUnitTo() {
    controller.assignSwordMasterToActualPlayer(0,0);
    controller.selectUnitIn(0,0);
    controller.moveSelectedUnitTo(0,1);
    assertEquals(controller.getSelectedUnit(),controller.getGameMap().getCell(0,1).getUnit());
  }

  @Test
  void addItemToSelectedUnitTest() {
    controller.assignSwordMasterToActualPlayer(0,0);
    controller.selectUnitIn(0,0);
    controller.addSpiritToSelectedUnit();
    assertEquals(1,controller.getItems().size());
    controller.addStaffToSelectedUnit();
    controller.addSwordToSelectedUnit();
    assertEquals(3,controller.getItems().size());
  }

  @Test
  void checkHeroesTest() {
    //We assign units (heroes) and items
    controller.assignSorcererToActualPlayer(0,0);
    controller.assignHeroToActualPlayer(0,1);
    controller.selectUnitIn(0,1);
    controller.addSpearToSelectedUnit();
    controller.equipItem(0);
    controller.endTurn();
    controller.assignHeroToActualPlayer(0,2);
    controller.endTurn();
    controller.assignHeroToActualPlayer(0,3);
    controller.selectUnitIn(0,3);
    controller.addSpearToSelectedUnit();
    controller.equipItem(0);
    controller.endTurn();
    controller.assignHeroToActualPlayer(1,0);
    controller.endTurn();

    //Here the game begins
    controller.initGame(5);
    controller.selectUnitIn(0,0);
    controller.addLightToSelectedUnit();
    controller.equipItem(0);
    //We will kill a hero
    controller.useItemOn(1,0);
    controller.useItemOn(1,0);
    controller.useItemOn(1,0);
    controller.useItemOn(1,0);
    controller.useItemOn(1,0);
    //We check the number of tacticians
    assertEquals(3,controller.getTacticians().size());
    //Here we kill another hero
    controller.moveSelectedUnitTo(1,0);
    controller.endTurn();
    controller.selectUnitIn(0,2);
    controller.addSpearToSelectedUnit();
    controller.equipItem(0);
    controller.useItemOn(0,1);
    controller.useItemOn(0,1);
    controller.useItemOn(0,1);
    controller.useItemOn(0,1);
    controller.useItemOn(0,1);
    assertEquals(2,controller.getTacticians().size());
    assertEquals("Player 0",controller.getTacticians().get(0).getName());
    assertEquals("Player 3",controller.getTacticians().get(1).getName());
    //Here we kill our hero
    controller.useItemOn(0,3);
    //So, the winner should be Player 0
    assertEquals("Player 0",controller.getWinners().get(0));

  }
}