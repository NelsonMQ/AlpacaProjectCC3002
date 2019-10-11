package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

import controller.handlers.*;
import model.Tactician;
import model.factory.GameMapFactory.FieldFactory;
import model.factory.IEquipableItemFactory.*;
import model.factory.IUnitFactory.*;
import model.items.IEquipableItem;
import model.map.Field;
import model.map.Location;
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
  private IUnitFactory unitFactory;
  private List<Tactician> roundOrder;
  private List<Tactician> nextRoundOrder;
  private Random randomSeed;
  private List<String> winners;
  private int tacticiansNum;
  private List<IUnit> unitsToMove = new ArrayList<>();

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
    fieldFactory.setRandomSeed(new Random(10));
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
        unitsToMove.addAll(actualPlayer.getUnits());
      }
    }
    else {
      roundOrder.remove(0);
      actualPlayer = roundOrder.get(0);
      unitsToMove.addAll(actualPlayer.getUnits());
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
    setTacticiansHandler();
    this.winners = null;
    this.maxRounds = maxTurns;
    this.roundNumber = 1;
    this.unitsToMove.addAll(actualPlayer.getUnits());
  }

  /**
   * Set the tactician handlers
   */
  private void setTacticiansHandler() {
    GiveItemHandler giveItemHandler = new GiveItemHandler(this);
    MoveUnitHandler moveUnitHandler = new MoveUnitHandler(this);
    SelectUnitHandler selectUnitHandler = new SelectUnitHandler(this);
    SelectItemHandler selectItemHandler = new SelectItemHandler(this);
    UseItemHandler useItemHandler = new UseItemHandler(this);
    EquipItemHandler equipItemHandler = new EquipItemHandler(this);
    EndTurnHandler endTurnHandler = new EndTurnHandler(this);
    for (Tactician tactician: tacticians) {
      tactician.addEquipItemObserver(equipItemHandler);
      tactician.addGiveItemObserver(giveItemHandler);
      tactician.addMoveUnitObserver(moveUnitHandler);
      tactician.addSelectItemObserver(selectItemHandler);
      tactician.addUseItemObserver(useItemHandler);
      tactician.addSelectUnitObserver(selectUnitHandler);
      tactician.addEndTurnObserver(endTurnHandler);
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
    if (actualPlayer.getUnits().contains(map.getCell(x, y).getUnit())){
      actualPlayer.setSelectedUnit(map.getCell(x, y).getUnit());
    }
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
    if(unit.getCurrentHitPoints()<=0){
      removeUnit(unit);
    }
    if(getSelectedUnit().getCurrentHitPoints()<=0){
      removeUnit(getSelectedUnit());
    }
  }

  /**
   * Selects an item from the selected unit's inventory.
   *
   * @param index
   *     the location of the item in the inventory.
   */
  public void selectItem(int index) {
    IEquipableItem item = getSelectedUnit().getItems().get(index);
    actualPlayer.setSelectedItem(item);
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
    IUnit unit = map.getCell(x,y).getUnit();
    IEquipableItem item = getSelectedItem();
    getSelectedUnit().giveItemTo(unit,item);
  }

  /**
   * Assign an Alpaca to the actual player
   * @param x
   *      horizontal position where the unit will be set
   * @param y
   *      vertical position where the unit will be set
   */
  public void assignAlpacaToActualPlayer(int x,int y) {
    if(map.getCell(x,y).getUnit()==null) {
      unitFactory = new AlpacaFactory();
      IUnit unit = unitFactory.create();
      actualPlayer.getUnits().add(unit);
      putUnitOn(x, y, unit);
      unitsToMove.add(unit);
    }
  }

  /**
   * Assign an Archer to the actual player
   * @param x
   *      horizontal position where the unit will be set
   * @param y
   *      vertical position where the unit will be set
   */
  public void assignArcherToActualPlayer(int x, int y) {
    if(map.getCell(x,y).getUnit()==null) {
      unitFactory = new ArcherFactory();
      IUnit unit = unitFactory.create();
      actualPlayer.getUnits().add(unit);
      putUnitOn(x, y, unit);
      unitsToMove.add(unit);
    }
  }

  /**
   * Assign a Cleric to the actual player
   * @param x
   *      horizontal position where the unit will be set
   * @param y
   *      vertical position where the unit will be set
   */
  public void assignClericToActualPlayer(int x, int y) {
    if(map.getCell(x,y).getUnit()==null) {
      unitFactory = new ClericFactory();
      IUnit unit = unitFactory.create();
      actualPlayer.getUnits().add(unit);
      putUnitOn(x, y, unit);
      unitsToMove.add(unit);
    }
  }

  /**
   * Assign a Fighter to the actual player
   * @param x
   *      horizontal position where the unit will be set
   * @param y
   *      vertical position where the unit will be set
   */
  public void assignFighterToActualPlayer(int x, int y) {
    if(map.getCell(x,y).getUnit()==null) {
      unitFactory = new FighterFactory();
      IUnit unit = unitFactory.create();
      actualPlayer.getUnits().add(unit);
      putUnitOn(x, y, unit);
      unitsToMove.add(unit);
    }
  }

  /**
   * Assign a Hero to the actual player
   * @param x
   *      horizontal position where the unit will be set
   * @param y
   *      vertical position where the unit will be set
   */
  public void assignHeroToActualPlayer(int x, int y) {
    if(map.getCell(x,y).getUnit()==null) {
      unitFactory = new HeroFactory();
      IUnit unit = unitFactory.create();
      actualPlayer.getUnits().add(unit);
      putUnitOn(x, y, unit);
      unitsToMove.add(unit);
    }
  }

  /**
   * Assign a Sorcerer to the actual player
   * @param x
   *      horizontal position where the unit will be set
   * @param y
   *      vertical position where the unit will be set
   */
  public void assignSorcererToActualPlayer(int x, int y) {
    if(map.getCell(x,y).getUnit()==null) {
      unitFactory = new SorcererFactory();
      IUnit unit = unitFactory.create();
      actualPlayer.getUnits().add(unit);
      putUnitOn(x, y, unit);
      unitsToMove.add(unit);
    }
  }

  /**
   * Assign a SwordMaster to the actual player
   * @param x
   *      horizontal position where the unit will be set
   * @param y
   *      vertical position where the unit will be set
   */
  public void assignSwordMasterToActualPlayer(int x, int y) {
    if(map.getCell(x,y).getUnit()==null) {
      unitFactory = new SwordMasterFactory();
      IUnit unit = unitFactory.create();
      actualPlayer.getUnits().add(unit);
      putUnitOn(x, y, unit);
      unitsToMove.add(unit);
    }
  }

  /**
   * Adds an Axe to the selected unit
   */
  public void addAxeToSelectedUnit() {
    getSelectedUnit().addItem(new AxeFactory().create());
  }

  /**
   * Adds a Bow to the selected unit
   */
  public void addBowToSelectedUnit() {
    getSelectedUnit().addItem(new BowFactory().create());
  }

  /**
   * Adds a Darkness to the selected unit
   */
  public void addDarknessToSelectedUnit() {
    getSelectedUnit().addItem(new DarknessFactory().create());
  }

  /**
   * Adds a Light to the selected unit
   */
  public void addLightToSelectedUnit() {
    getSelectedUnit().addItem(new LightFactory().create());
  }

  /**
   * Adds a Spear to the selected unit
   */
  public void addSpearToSelectedUnit() {
    getSelectedUnit().addItem(new SpearFactory().create());
  }

  /**
   * Adds a Spirit to the selected unit
   */
  public void addSpiritToSelectedUnit() {
    getSelectedUnit().addItem(new SpiritFactory().create());
  }

  /**
   * Adds a Staff to the selected unit
   */
  public void addStaffToSelectedUnit() {
    getSelectedUnit().addItem(new StaffFactory().create());
  }

  /**
   * Adds a Sword to the selected unit
   */
  public void addSwordToSelectedUnit() {
    getSelectedUnit().addItem(new SwordFactory().create());
  }

  /**
   * Prepares the order of the round
   */
  public void prepareRoundOrder() {
    roundOrder.addAll(nextRoundOrder);
    Collections.shuffle(nextRoundOrder,randomSeed);
    while(nextRoundOrder.get(0)==roundOrder.get(tacticians.size()-1))
      Collections.shuffle(nextRoundOrder, randomSeed);
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
          winners.add(tacticians.get(0).getName());
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
    Location targetLocation = map.getCell(x, y);
    if(targetLocation.getUnit()==null && unitsToMove.contains(getSelectedUnit())) {
      getSelectedUnit().moveTo(targetLocation);
      unitsToMove.remove(getSelectedUnit());
    }
  }

  /**
   * Puts a unit on the (x,y) cell
   * @param x
   *      horizontal position where the unit will be set
   * @param y
   *      vertical position where the unit will be set
   * @param unit
   *      the unit
   */
  public void putUnitOn(int x, int y,IUnit unit) {
    Location cell = map.getCell(x,y);
    if(cell.getUnit()==null) {
      unit.setLocation(cell);
      cell.setUnit(unit);
    }
  }

  /**
   * Puts the selected unit on the (x,y) cell
   * @param x
   *      horizontal position where the unit will be set
   * @param y
   *      vertical position where the unit will be set
   */
  public void putSelectedUnitOn(int x, int y) {
    putUnitOn(x,y,getSelectedUnit());
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
      }
    }
  }
}
