package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

import controller.handlers.*;
import model.Tactician;
import model.factory.GameMapFactory.FieldFactory;
import model.items.IEquipableItem;
import model.map.Field;
import model.units.IUnit;

/**
 * Controller of the game.
 * The controller manages all the input received from de game's GUI.
 *
 * @author Ignacio Slater Muñoz
 * @version 2.0
 * @since 2.0
 */
public class GameController{
  private List<Tactician> tacticians;
  private Field map;
  private Tactician actualPlayer;
  private int roundNumber;
  private int maxRounds;
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
    this.map = fieldFactory.create(mapSize);
    tacticians = createPlayers(numberOfPlayers);
    prepareFirstRound();
    randomSeed = null;
    this.roundNumber = 0;
    this.maxRounds = -1;
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
        actualPlayer.getUnitsToMove().addAll(actualPlayer.getUnits());
      }
    }
    else {
      roundOrder.remove(0);
      actualPlayer = roundOrder.get(0);
      actualPlayer.getUnitsToMove().addAll(actualPlayer.getUnits());
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
        roundOrder.remove(tacticians.get(i));
        tacticians.remove(i);
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
    setTacticiansHandler();
    this.winners = null;
    this.maxRounds = maxTurns;
    this.roundNumber = 1;
    actualPlayer.getUnitsToMove().addAll(actualPlayer.getUnits());
  }

  /**
   * Set the tactician handlers
   */
  private void setTacticiansHandler() {
    RefreshMapHandler refreshMapHandler = new RefreshMapHandler(this);
    RemoveUnitHandler removeUnitHandler = new RemoveUnitHandler(this);
    RemoveTacticianHandler removeTacticianHandler = new RemoveTacticianHandler(this);
    CheckHeroesHandler checkHeroesHandler = new CheckHeroesHandler(this);
    for (Tactician tactician: tacticians) {
      tactician.addRefreshMapObserver(refreshMapHandler);
      tactician.addRemoveUnitObserver(removeUnitHandler);
      tactician.addRemoveTacticianObserver(removeTacticianHandler);
      tactician.addCheckHeroesObserver(checkHeroesHandler);
    }
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
    actualPlayer.selectUnitIn(x,y);
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
    actualPlayer.equipItemToSelectedUnit(index);
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
    actualPlayer.useItemOn(x,y);
  }

  /**
   * Selects an item from the selected unit's inventory.
   *
   * @param index
   *     the location of the item in the inventory.
   */
  public void selectItem(int index) {
    actualPlayer.selectItem(index);
  }

  /**
   *
   * @return
   *    The selected Item
   */
  public IEquipableItem getSelectedItem() {
    return actualPlayer.getSelectedItem();
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
    actualPlayer.giveItemTo(x,y);
  }

  /**
   * Assign an Alpaca to the actual player
   * @param x
   *      horizontal position where the unit will be set
   * @param y
   *      vertical position where the unit will be set
   */
  public void assignAlpacaToActualPlayer(int x,int y) {
    actualPlayer.assignAlpaca(x,y);
  }

  /**
   * Assign an Archer to the actual player
   * @param x
   *      horizontal position where the unit will be set
   * @param y
   *      vertical position where the unit will be set
   */
  public void assignArcherToActualPlayer(int x, int y) {
    actualPlayer.assignArcher(x,y);
  }

  /**
   * Assign a Cleric to the actual player
   * @param x
   *      horizontal position where the unit will be set
   * @param y
   *      vertical position where the unit will be set
   */
  public void assignClericToActualPlayer(int x, int y) {
    actualPlayer.assignCleric(x,y);
  }

  /**
   * Assign a Fighter to the actual player
   * @param x
   *      horizontal position where the unit will be set
   * @param y
   *      vertical position where the unit will be set
   */
  public void assignFighterToActualPlayer(int x, int y) {
    actualPlayer.assignFighter(x,y);
  }

  /**
   * Assign a Hero to the actual player
   * @param x
   *      horizontal position where the unit will be set
   * @param y
   *      vertical position where the unit will be set
   */
  public void assignHeroToActualPlayer(int x, int y) {
    actualPlayer.assignHero(x,y);
  }

  /**
   * Assign a Sorcerer to the actual player
   * @param x
   *      horizontal position where the unit will be set
   * @param y
   *      vertical position where the unit will be set
   */
  public void assignSorcererToActualPlayer(int x, int y) {
    actualPlayer.assignSorcerer(x,y);
  }

  /**
   * Assign a SwordMaster to the actual player
   * @param x
   *      horizontal position where the unit will be set
   * @param y
   *      vertical position where the unit will be set
   */
  public void assignSwordMasterToActualPlayer(int x, int y) {
    actualPlayer.assignSwordMaster(x,y);
  }

  /**
   * Adds an Axe to the selected unit
   */
  public void addAxeToSelectedUnit() {
    actualPlayer.addAxeToSelectedUnit();
  }

  /**
   * Adds a Bow to the selected unit
   */
  public void addBowToSelectedUnit() {
    actualPlayer.addBowToSelectedUnit();
  }

  /**
   * Adds a Darkness to the selected unit
   */
  public void addDarknessToSelectedUnit() {
    actualPlayer.addDarknessToSelectedUnit();
  }

  /**
   * Adds a Light to the selected unit
   */
  public void addLightToSelectedUnit() {
    actualPlayer.addLightToSelectedUnit();
  }

  /**
   * Adds a Spear to the selected unit
   */
  public void addSpearToSelectedUnit() {
    actualPlayer.addSpearToSelectedUnit();
  }

  /**
   * Adds a Spirit to the selected unit
   */
  public void addSpiritToSelectedUnit() {
    actualPlayer.addSpiritToSelectedUnit();
  }

  /**
   * Adds a Staff to the selected unit
   */
  public void addStaffToSelectedUnit() {
    actualPlayer.addStaffToSelectedUnit();
  }

  /**
   * Adds a Sword to the selected unit
   */
  public void addSwordToSelectedUnit() {
    actualPlayer.addSwordToSelectedUnit();
  }

  /**
   * Prepares the order of the round
   */
  public void prepareRoundOrder() {
    if(roundNumber==0){
      roundOrder.addAll(nextRoundOrder);
    }
    else {
      if(randomSeed==null){
        randomSeed = new Random();
      }
      roundOrder.addAll(tacticians);
      Collections.shuffle(roundOrder, randomSeed);
      while (roundOrder.get(0) == nextRoundOrder.get(tacticians.size() - 1))
        Collections.shuffle(roundOrder, randomSeed);
      nextRoundOrder.addAll(roundOrder);
    }
  }

  /**
   * Prepares the first round
   */
  public void prepareFirstRound(){
    if(randomSeed == null){
      randomSeed = new Random();
    }
    nextRoundOrder = new ArrayList<>();
    nextRoundOrder.addAll(tacticians);
    Collections.shuffle(nextRoundOrder, randomSeed);
    roundOrder = new ArrayList<>();
    roundOrder.addAll(nextRoundOrder);
    actualPlayer = roundOrder.get(0);
  }

  /**
   * Selects the winners of the game
   */
  public void endGame() {
    winners = new ArrayList<>();
    if(tacticians.size()==1)
      winners.add(tacticians.get(0).getName());
    else{
      int maxUnitsQuantity = tacticianMaxUnitQuantity();
      for (Tactician tactician : tacticians) {
        if (tactician.getUnits().size() == maxUnitsQuantity)
          winners.add(tactician.getName());
      }
    }
  }

  /**
   *
   * @return
   *    the number of units that the player with more units has
   */
  public int tacticianMaxUnitQuantity() {
    int max = 0;
    for (Tactician tactician : tacticians) {
      if(tactician.getUnits().size() > max)
        max = tactician.getUnits().size();
    }
    return max;
  }

  /**
   * Creates the players of the game
   * @param numberOfPlayers
   *      The number of players to create
   * @return
   *      A List that contains the players
   */
  public List<Tactician> createPlayers(int numberOfPlayers) {
    List<Tactician> players = new ArrayList<>();
    for(int i = 0; i<numberOfPlayers; i++){
      Tactician tactician = new Tactician("Player "+i,this.map);
      players.add(tactician);
    }
    return players;
  }

  /**
   * Moves the selected unit to (x,y) cell
   * @param x
   *      horizontal position where the unit will be moved
   * @param y
   *      vertical position where the unit will be moved
   */
  public void moveSelectedUnitTo(int x,int y) {
    actualPlayer.moveSelectedUnitTo(x,y);
  }

  /**
   * Removes a unit from the player units List
   * @param unit
   *      The unit to be removed
   */
  public void removeUnit(IUnit unit) {
    unit.getLocation().setUnit(null);
    for (Tactician tactician: tacticians) {
      if(tactician.getUnits().contains(unit)){
        tactician.getUnits().remove(unit);
        if(tactician.getHeroes().contains(unit)){
          tactician.getHeroes().remove(unit);
        }
        if(tactician.getUnits().size()==0){
          if(tactician.getName().equals(actualPlayer.getName()))
            endTurn();
          removeTactician(tactician.getName());
          break;
        }
      }
    }
    refreshMap(map);
  }

  /**
   * Changes the map for a new one (for tests)
   * @param seed
   *      Connections seed
   * @param size
   *      Size of the map
   */
  public void reRollMap(Random seed,int size,Random seed2){
    FieldFactory fieldFactory = new FieldFactory();
    fieldFactory.setRandomSeed(seed);
    fieldFactory.setRandomSeed2(seed2);
    this.map = fieldFactory.create(size);
    refreshMap(map);
  }

  /**
   * The map will be updated in all players
   * @param map the updated map
   */
  public void refreshMap(Field map) {
    this.map = map;
    for (Tactician tactician:tacticians) {
      tactician.setMap(map);
    }
  }

  /**
   * Returns the number of cells the unit can move
   * @return
   *      The number of cells the unit can move
   */
  public int getSelectedUnitMovement() {
    return actualPlayer.getSelectedUnitMovement();
  }

  /**
   * Returns the current hit points of the unit
   * @return
   *      The current hit points of the unit
   */
  public int getSelectedUnitCurrentHitPoints() {
    return actualPlayer.getSelectedUnitCurrentHitPoints();
  }

  /**
   * Returns the max HP of the unit
   * @return
   *      The max HP of the unit
   */
  public int getSelectedUnitMaxHP() {
    return actualPlayer.getSelectedUnitMaxHP();
  }

  /**
   * Returns the power of the selected item
   * @return
   *      The power of the selected item
   */
  public int getSelectedItemPower() {
    return actualPlayer.getSelectedItemPower();
  }

  /**
   * Returns the min range of the selected item
   * @return
   *      The min range of the selected item
   */
  public int getSelectedItemMinRange() {
    return actualPlayer.getSelectedItemMinRange();
  }

  /**
   * Returns the max range of the selected item
   * @return
   *      The max range of the selected item
   */
  public int getSelectedItemMaxRange() {
    return actualPlayer.getSelectedItemMaxRange();
  }

  /**
   * Returns the name of the selected item
   * @return
   *      The name of the selected item
   */
  public String getSelectedItemName() {
    return actualPlayer.getSelectedItemName();
  }

  /**
   * Removes the tacticians who have a death hero.
   */
  public void checkPlayersHeroes() {
    for (Tactician tactician: tacticians) {
      if(tactician.getHeroes().size()<tactician.getHeroesNumber()){
        if(actualPlayer.equals(tactician)){
          endTurn();
          removeTactician(tactician.getName());
          return;
        }
        else {
          removeTactician(tactician.getName());
        }
      }
    }
  }

  /**
   * Set the random seed
   * @param randomSeed the random to be set
   */
  public void setRandomSeed(Random randomSeed) {
    this.randomSeed = randomSeed;
  }
}
