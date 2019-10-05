package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

import model.Tactician;
import model.factory.GameMapFactory.FieldFactory;
import model.factory.IEquipableItemFactory.IEquipableItemFactory;
import model.factory.IUnitFactory.IUnitFactory;
import model.items.IEquipableItem;
import model.map.Field;
import model.map.Location;
import model.units.Cleric;
import model.units.IUnit;

/**
 * Controller of the game.
 * The controller manages all the input received from de game's GUI.
 *
 * @author Ignacio Slater Muñoz
 * @version 2.0
 * @since 2.0
 */
public class GameController {
  private List<Tactician> tacticians;
  private Field map;
  private Tactician actualPlayer;
  private int roundNumber;
  private int maxRounds;
  private IEquipableItemFactory itemFactory;
  private IUnitFactory unitFactory;
  private List<Tactician> roundOrder;
  private List<Tactician> nextRoundOrder;
  private Random randomSeed;
  private List<String> winners;
  private int tacticiansNum;

  /**
   * Creates the controller for a new game.
   *
   * @param numberOfPlayers
   *     the number of players for this game
   * @param mapSize
   *     the dimensions of the map, for simplicity, all maps are squares
   */
  public GameController(int numberOfPlayers, int mapSize) {
    tacticiansNum = numberOfPlayers;
    FieldFactory fieldFactory = new FieldFactory();
    this.map=fieldFactory.create(mapSize);
    tacticians = createPlayers(numberOfPlayers);
    nextRoundOrder = new ArrayList<>();
    nextRoundOrder.addAll(tacticians);
    randomSeed = new Random(10);
    Collections.shuffle(nextRoundOrder, randomSeed);
    roundOrder = new ArrayList<>();
    prepareRoundOrder();
    this.actualPlayer = roundOrder.get(0);
  }

  /**
   * @return the list of all the tacticians participating in the game.
   */
  public List<Tactician> getTacticians() {
    return tacticians;
  }

  /**
   * @return the map of the current game
   */
  public Field getGameMap() {
    return map;
  }

  /**
   * @return the tactician that's currently playing
   */
  public Tactician getTurnOwner() {
    return actualPlayer;
  }

  /**
   * @return the number of rounds since the start of the game.
   */
  public int getRoundNumber() {
    return roundNumber;
  }

  /**
   * @return the maximum number of rounds a match can last
   */
  public int getMaxRounds() {
    return maxRounds;
  }

  /**
   * Finishes the current player's turn.
   */
  public void endTurn() {
    if(roundOrder.size()==1){
      if(roundNumber==maxRounds){
        roundOrder.remove(0);
        endGame();
      }
      else{
        roundOrder.remove(0);
        prepareRoundOrder();
        actualPlayer = roundOrder.get(0);
        roundNumber += 1;
      }
    }
    else {
      roundOrder.remove(0);
      actualPlayer = roundOrder.get(0);
    }
  }

  /**
   * Removes a tactician and all of it's units from the game.
   *
   * @param tactician
   *     the player to be removed
   */
  public void removeTactician(String tactician) {
    for(int i = 0; i<tacticians.size();i++){
      if(tacticians.get(i).getName().equals(tactician)) {
        List<IUnit> units = tacticians.get(i).getUnits();
        for(int j = 0; j<units.size();j++){
          units.get(j).getLocation().setUnit(null);
        }
        tacticians.remove(i);
        roundOrder.remove(tacticians.get(i));
      }
    }
    if(tacticians.size()==1)
      endGame();
  }

  /**
   * Starts the game.
   * @param maxTurns
   *  the maximum number of turns the game can last
   */
  public void initGame(final int maxTurns) {
    if(roundOrder.size()<tacticiansNum){
      prepareRoundOrder();
      this.actualPlayer = roundOrder.get(0);
    }
    if(tacticians.size()<tacticiansNum){
      tacticians = createPlayers(tacticiansNum);
    }
    this.winners = null;
    this.maxRounds = maxTurns;
    this.roundNumber = 1;
  }

  /**
   * Starts a game without a limit of turns.
   */
  public void initEndlessGame() {
    initGame(-1);
  }

  /**
   * @return the winner of this game, if the match ends in a draw returns a list of all the winners
   */
  public List<String> getWinners() {
    return winners;
  }

  /**
   * @return the current player's selected unit
   */
  public IUnit getSelectedUnit() {
    return actualPlayer.getSelectedUnit();
  }

  /**
   * Selects a unit in the game map
   *
   * @param x
   *     horizontal position of the unit
   * @param y
   *     vertical position of the unit
   */
  public void selectUnitIn(int x, int y) {
    actualPlayer.setSelectedUnit(map.getCell(x,y).getUnit());
  }

  /**
   * @return the inventory of the currently selected unit.
   */
  public List<IEquipableItem> getItems() {
    return getSelectedUnit().getItems();
  }

  /**
   * Equips an item from the inventory to the currently selected unit.
   *
   * @param index
   *     the location of the item in the inventory.
   */
  public void equipItem(int index) {
    getSelectedUnit().equipItem(getItems().get(index));
  }

  /**
   * Uses the equipped item on a target
   *
   * @param x
   *     horizontal position of the target
   * @param y
   *     vertical position of the target
   */
  public void useItemOn(int x, int y) {
    IUnit unit = map.getCell(x,y).getUnit();
    getSelectedUnit().useItemOn(unit);
  }

  /**
   * Selects an item from the selected unit's inventory.
   *
   * @param index
   *     the location of the item in the inventory.
   */
  public void selectItem(int index) {
    IEquipableItem item = actualPlayer.getSelectedUnit().getItems().get(index);
    actualPlayer.setSelectedItem(item);
  }

  /**
   * Gives the selected item to a target unit.
   *
   * @param x
   *     horizontal position of the target
   * @param y
   *     vertical position of the target
   */
  public void giveItemTo(int x, int y) {
    IUnit unit = map.getCell(x,y).getUnit();
    IEquipableItem item = actualPlayer.getSelectedItem();
    actualPlayer.getSelectedUnit().giveItemTo(unit,item);
  }

  public void assignUnit(IUnit unit) {
    actualPlayer.getUnits().add(unit);
  }

  public void prepareRoundOrder() {
    roundOrder.addAll(nextRoundOrder);
    Collections.shuffle(nextRoundOrder,randomSeed);
    while(nextRoundOrder.get(0)==roundOrder.get(tacticians.size()-1))
      Collections.shuffle(nextRoundOrder, randomSeed);
  }

  public void endGame() {
    winners = new ArrayList<>();
    if(tacticians.size()==1)
      winners.add(tacticians.get(0).getName());
    else{
      int maxUnitsQuantity = tacticianMaxUnitQuantity();
      for (Tactician tactician : tacticians) {
        if (tactician.getUnits().size() == maxUnitsQuantity)
          winners.add(tacticians.get(0).getName());
      }
    }
  }

  public int tacticianMaxUnitQuantity() {
    int max = 0;
    for (Tactician tactician : tacticians) {
      if(tactician.getUnits().size() > max)
        max = tactician.getUnits().size();
    }
    return max;
  }

  public List<Tactician> createPlayers(int numberOfPlayers) {
    List<Tactician> players = new ArrayList<>();
    for(int i = 0; i<numberOfPlayers; i++){
      Tactician tactician = new Tactician("Player "+i,this.map);
      players.add(tactician);
    }
    return players;
  }

  public void moveSelectedUnitTo(int x,int y) {
    Location targetLocation = map.getCell(x,y);
    getSelectedUnit().moveTo(targetLocation);
  }

  public void putSelectedUnitOn(int x, int y) {
    Location cell = map.getCell(x,y);
    getSelectedUnit().setLocation(cell);
    cell.setUnit(getSelectedUnit());
  }
}
