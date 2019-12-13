package controller;

import java.util.*;
import java.util.stream.IntStream;
import model.Tactician;
import model.factory.GameMapFactory.FieldFactory;
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
  private long randomSeed = new Random().nextLong();
  private long randomSeed1 = new Random().nextLong();
  private List<String> testTacticians;

  /**
   * Sets up the controller of the game
   */
  @BeforeEach
  void setUp() {
    long seed = 10;
    controller = new GameController(4, 7);
    testTacticians = List.of("Player 0", "Player 1", "Player 2", "Player 3");
    //A seed is set to connect the map to help the moveUnitTest, useItemTest, etc.
    long randomSeed2 = 5;
    controller.reRollMap(new Random(seed),7,new Random(randomSeed2));
    //A seed is set to select the roundOrder to help some tests methods.
    controller.setRandomSeed(new Random(seed));
    controller.prepareFirstRound();
  }

  /**
   * Checks if getTacticians is working correctly.
   */
  @Test
  void getTacticians() {
    List<Tactician> tacticians = controller.getTacticians();
    assertEquals(4, tacticians.size());
    for (int i = 0; i < tacticians.size(); i++) {
      assertEquals(testTacticians.get(i), tacticians.get(i).getName());
    }
  }

  /**
   * Checks if the map is created correctly, and if getGameMap works correctly.
   */
  @Test
  void getGameMap() {
    //Now, we reRoll the controller map and create a new Field to compare the maps (Both with the same seeds)
    Random random = new Random(randomSeed);
    Random random2 = new Random(randomSeed1);
    Random random3 = new Random(randomSeed);
    Random random4 = new Random(randomSeed1);
    controller.reRollMap(random,7,random2);
    FieldFactory factory = new FieldFactory();
    factory.setRandomSeed(random3);
    factory.setRandomSeed2(random4);
    Field map = factory.create(7);
    assertEquals(7, controller.getGameMap().getSize());
    assertTrue(controller.getGameMap().isConnected());
    assertEquals(7, map.getSize());
    assertTrue(map.isConnected());
    //We check the locations in the map
    for(int i =0;i<7;i++){
      for(int j=0;j<7;j++){
        assertEquals(map.getCell(i,j),controller.getGameMap().getCell(i,j));
        List<Location> controllerNeighbours = new ArrayList<>(controller.getGameMap().getCell(i,j).getNeighbours());
        List<Location> mapNeighbours = new ArrayList<>(map.getCell(i,j).getNeighbours());
        //Here we check the connections are the same.
        controllerNeighbours.containsAll(mapNeighbours);
        mapNeighbours.containsAll(controllerNeighbours);
        for (Location location : mapNeighbours) {
          assertTrue(controllerNeighbours.contains(location));
        }
      }
    }
  }

  /**
   * Checks if getTurnOwner is working correctly.
   */
  @Test
  void getTurnOwner() {
    Random random = new Random(randomSeed);
    Random random2 = new Random(randomSeed);
    controller.setRandomSeed(random);
    controller.prepareFirstRound();
    controller.initGame(2);

    List<Tactician> players = new ArrayList<>();
    players.addAll(controller.getTacticians());
    Collections.shuffle(players,random2);

    for(int i = 0; i<players.size();i++){
      assertEquals(players.get(i).getName(), controller.getTurnOwner().getName());
      controller.endTurn();
    }
  }

  /**
   * Checks if getRoundNumber works correctly
   */
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

  /**
   * Checks if getMaxRound works correctly.
   */
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

  /**
   * Checks if endTurn is working correctly.
   */
  @Test
  void endTurn() {
    Tactician firstPlayer = controller.getTurnOwner();
    Tactician secondPlayer = new Tactician("Player 3",controller.getGameMap());
    assertNotEquals(secondPlayer.getName(), firstPlayer.getName());

    controller.endTurn();
    assertNotEquals(firstPlayer.getName(), controller.getTurnOwner().getName());
    assertEquals(secondPlayer.getName(), controller.getTurnOwner().getName());
  }

  /**
   * Checks if removeTactician works correctly.
   */
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

  /**
   * Checks if the winners are the corrects
   */
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

  /**
   * Checks the getSelectedUnit method.
   */
  @Test
  void getSelectedUnit() {
    assertNull(controller.getSelectedUnit());
    controller.assignFighterToActualPlayer(1,2);
    IUnit unit = controller.getTurnOwner().getUnits().get(0);
    controller.getTurnOwner().setSelectedUnit(unit);
    assertEquals(controller.getTurnOwner().getSelectedUnit(),controller.getSelectedUnit());
  }

  /**
   * Checks if selectedUnit works correctly.
   */
  @Test
  void selectUnitIn() {
    controller.selectUnitIn(0,0);
    assertNull(controller.getSelectedUnit());
    controller.assignSorcererToActualPlayer(0,0);
    IUnit unit = controller.getTurnOwner().getUnits().get(0);
    controller.selectUnitIn(0,0);
    assertEquals(unit,controller.getSelectedUnit());
  }

  /**
   * Checks if getItems is working correctly.
   */
  @Test
  void getItems() {
    controller.assignSwordMasterToActualPlayer(0,0);
    controller.selectUnitIn(0,0);
    assertEquals(0,controller.getItems().size());

    controller.addAxeToSelectedUnit();
    assertEquals(1,controller.getItems().size());
    assertEquals(controller.getSelectedUnit().getItems().get(0),controller.getItems().get(0));
  }

  /**
   * Checks if equipItem is working correctly.
   */
  @Test
  void equipItem() {
    controller.assignArcherToActualPlayer(0,0);
    controller.selectUnitIn(0,0);
    controller.addBowToSelectedUnit();
    assertNull(controller.getSelectedUnit().getEquippedItem());
    controller.equipItem(0);
    assertNotNull(controller.getSelectedUnit().getEquippedItem());
  }

  /**
   * Checks if useItem works correctly.
   */
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

  /**
   * Checks if selectedItem works correctly.
   */
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

  /**
   * Checks if giveItemTo works correctly.
   */
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

  /**
   * Checks if removeUnit works correctly.
   */
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

  /**
   * Checks if moveSelectedUnit works correctly.
   */
  @Test
  void moveSelectedUnitTo() {
    controller.assignSwordMasterToActualPlayer(0,0);
    controller.selectUnitIn(0,0);
    controller.moveSelectedUnitTo(0,1);
    assertEquals(controller.getSelectedUnit(),controller.getGameMap().getCell(0,1).getUnit());
  }

  /**
   * Checks if addItemToSelectedUnit works correctly.
   */
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

  /**
   * Checks if checkHeroes works, and the winners conditions.
   */
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

  /**
   * Tests a tie game
   */
  @Test
  void tieGameTest() {
    controller.assignSorcererToActualPlayer(0,0);
    controller.endTurn();
    controller.assignSwordMasterToActualPlayer(0,1);
    controller.endTurn();
    controller.endTurn();
    controller.endTurn();
    controller.initGame(1);
    controller.endTurn();
    controller.endTurn();
    controller.endTurn();
    controller.endTurn();
    assertEquals("Player 1",controller.getWinners().get(0));
    assertEquals("Player 3",controller.getWinners().get(1));
    assertEquals(2,controller.getWinners().size());
  }

  /**
   * Checks if the controller removes the tacticians without units.
   */
  @Test
  void tacticianWithoutUnitsTest() {
    controller.assignSwordMasterToActualPlayer(0,0);
    controller.endTurn();
    controller.assignSorcererToActualPlayer(0,1);
    controller.endTurn();
    controller.assignClericToActualPlayer(0,2);
    controller.endTurn();
    controller.assignAlpacaToActualPlayer(1,0);
    String winnerName = controller.getTurnOwner().getName();
    controller.endTurn();
    controller.initGame(8);
    Location cell = controller.getGameMap().getCell(0,1);
    controller.removeUnit(cell.getUnit());
    assertEquals(3,controller.getTacticians().size());
    Location cell2 = controller.getGameMap().getCell(0,0);
    String name = controller.getTurnOwner().getName();
    controller.removeUnit(cell2.getUnit());
    assertNotEquals(name,controller.getTurnOwner().getName());
    Location cell3 = controller.getGameMap().getCell(0,2);
    controller.removeUnit(cell3.getUnit());
    assertEquals(1,controller.getWinners().size());
    assertEquals(controller.getWinners().get(0),winnerName);
  }
}