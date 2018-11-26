package col.cs.risk.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import col.cs.risk.controller.GameController;
import col.cs.risk.helper.Utility;
import col.cs.risk.model.strategy.IStrategy;
import col.cs.risk.view.RolledDiceView;

/**
 * This class maintains the player details as well as implements the methods
 * related to player like occupyTerritory and do fortification etc. It also simulates the
 * attack phase of the game and handles the update needed along with it.
 * 
 * @author Team25
 *
 */
public class PlayerModel extends Observable {

	/** player number */
	private int id;

	/** player name */
	private String name;

	/** player occupied territories */
	private Vector<TerritoryModel> occupiedTerritories;

	/** no. of armies with player */
	private int armies;

	/** is fortification done */
	private boolean isFortified;

	/** No of dice selected to attack */
	private int attackingNoOfDice;

	/** No of dice selected to defend */
	private int defendingNoOfDice;

	/** List of attack dice to roll */
	private HashMap<Integer, Integer> attackingDiceList = new HashMap<>();

	/** List of defend dice to roll */
	private HashMap<Integer, Integer> defendingDiceList = new HashMap<>();

	/** view for displaying rolled dices list */
	private RolledDiceView rolledDiceView;

	/** Attacking Territory */
	private TerritoryModel attackingTerritory;

	/** Defending Territory */
	private TerritoryModel defendingTerritory;

	/** Game controller instance */
	private GameController gameController;

	/** is auto out mode on */
	private boolean isAutomatic;

	/** Is card assigned to player */
	private boolean isCardAssigned = false;

	/** Is card trade mandatory for the current turn */
	private boolean isCardTradeMandatory = false;

	/** CardModel vector */
	private Vector<CardModel> cards = new Vector<>();
	
	/**
	 * Strategy model
	 */
	private IStrategy strategy;

	/**
	 * Constructor with parameters
	 * @param id player id
	 * @param name player name
	 */
	public PlayerModel(int id, String name) {
		this.id = id;
		this.name = name;
		occupiedTerritories = new Vector<>();
	}
	
	/**
	 * Constructor with parameters
	 * @param id player id
	 * @param name player name
	 * @param strategy player strategy
	 */
	public PlayerModel(int id, String name, IStrategy strategy) {
		this(id, name);
		this.strategy = strategy;
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param id
	 *            It holds the player id
	 * @param name
	 *            It hold the player name
	 * @param strategy
	 * 			 It contains strategy of the player
	 * @param occupiedTerritories
	 *            It is a vector that holds the list of occupied territories
	 */
	public PlayerModel(int id, String name, IStrategy strategy, Vector<TerritoryModel> occupiedTerritories) {
		this(id, name, strategy);
		this.occupiedTerritories = occupiedTerritories;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the occupiedTerritories
	 */
	public Vector<TerritoryModel> getOccupiedTerritories() {
		return occupiedTerritories;
	}

	/**
	 * @param occupiedTerritories
	 *            the occupiedTerritories to set
	 */
	public void setOccupiedTerritories(Vector<TerritoryModel> occupiedTerritories) {
		this.occupiedTerritories = occupiedTerritories;
		isChanged();
	}

	/**
	 * @return the armies
	 */
	public int getArmies() {
		return armies;
	}

	/**
	 * @param armies
	 *            the armies to set
	 */
	public void setArmies(int armies) {
		this.armies = armies;
		isChanged();
	}

	/**
	 * 
	 * @param armies,
	 *            add passed armies to the existing armies
	 */
	public void addArmies(int armies) {
		this.armies += armies;
		isChanged();
	}

	/**
	 * 
	 * @param armies,
	 *            subtract passed armies from existing armies
	 */
	public void looseArmies(int armies) {
		this.armies -= armies;
		isChanged();
	}

	/**
	 * add an army
	 */
	public void addArmy() {
		armies++;
		isChanged();
	}

	/**
	 * loose an army
	 */
	public void looseArmy() {
		armies--;
		isChanged();
	}

	/**
	 * Add territory to the list of occupied territory
	 * 
	 * @param territoryModel
	 */
	public void addOccupiedTerritory(TerritoryModel territoryModel) {
		if (occupiedTerritories == null) {
			occupiedTerritories = new Vector<>();
		}
		occupiedTerritories.add(territoryModel);
		isChanged();
	}

	/**
	 * Remove territory from the list of occupied territory
	 * 
	 * @param territoryModel
	 */
	public void removeOccupiedTerritory(TerritoryModel territoryModel) {
		if (occupiedTerritories != null) {
			occupiedTerritories.remove(territoryModel);
			isChanged();
		}
	}

	/**
	 * @return the isFortified
	 */
	public boolean isFortified() {
		return isFortified;
	}

	/**
	 * @param isFortified
	 *            the isFortified to set
	 */
	public void setFortified(boolean isFortified) {
		this.isFortified = isFortified;
	}

	/**
	 * @return the attackingNoOfDice
	 */
	public int getAttackingNoOfDice() {
		return attackingNoOfDice;
	}

	/**
	 * @param attackingNoOfDice
	 *            the attackingNoOfDice to set
	 */
	public void setAttackingNoOfDice(int attackingNoOfDice) {
		this.attackingNoOfDice = attackingNoOfDice;
	}

	/**
	 * @return the defendingNoOfDice
	 */
	public int getDefendingNoOfDice() {
		return defendingNoOfDice;
	}

	/**
	 * @param defendingNoOfDice
	 *            the defendingNoOfDice to set
	 */
	public void setDefendingNoOfDice(int defendingNoOfDice) {
		this.defendingNoOfDice = defendingNoOfDice;
	}

	/**
	 * @return the attackingDiceList
	 */
	public HashMap<Integer, Integer> getAttackingDiceList() {
		return attackingDiceList;
	}

	/**
	 * @param attackingDiceList
	 *            the attackingDiceList to set
	 */
	public void setAttackingDiceList(HashMap<Integer, Integer> attackingDiceList) {
		this.attackingDiceList = attackingDiceList;
	}

	/**
	 * @return the defendingDiceList
	 */
	public HashMap<Integer, Integer> getDefendingDiceList() {
		return defendingDiceList;
	}

	/**
	 * @param defendingDiceList
	 *            the defendingDiceList to set
	 */
	public void setDefendingDiceList(HashMap<Integer, Integer> defendingDiceList) {
		this.defendingDiceList = defendingDiceList;
	}

	/**
	 * Add turn bonus to current player
	 */
	public void addTurnBonus(GameModel gameModel) {
		int bonus = territoryBonus() + gameModel.continentBonus();
		addArmies(bonus);
	}

	/**
	 * Checks whether card trade is must for the current turn
	 * 
	 * @returns true if trading mandatory
	 */
	public boolean isCardTradeRequired() {
		Utility.writeLog("No. of cards available = "+cards.size());
		boolean isRequired = false;
		if (cards.size() >= Constants.FIVE) {
			isRequired = true;
		} 
		Utility.writeLog("Is Card Trade Mandatory = "+isRequired);
		return isRequired;
	}

	/**
	 * API to calculate territory bonus for current player
	 * 
	 * @return no of armies as bonus
	 */
	public int territoryBonus() {
		double bonus = Constants.ZERO;
		if (getOccupiedTerritories().size() < Constants.NINE) {
			bonus = Constants.THREE;
		} else {
			bonus = Math.floor(getOccupiedTerritories().size() / Constants.THREE);
		}
		Utility.writeLog("territory bonus = "+bonus);
		return (int) bonus;
	}

	/**
	 * Action on fortification phase for the current player
	 * 
	 * @param gameModel
	 * @param territoryModel
	 *            It is an instance of TerritoryModel that holds territories details
	 * @return status as string to display
	 */
	public boolean isFortificationPossibleByMultipleHop(GameModel gameModel, TerritoryModel territoryModel) {
		boolean isMultihop = false;
		HashSet<Integer> territoryIds = new HashSet<>();
		for(TerritoryModel territory: gameModel.getMoveArmiesFromTerritory().getAdjacentTerritories()) {
			isMultihop = isMultipleHop(gameModel, territoryModel, territory, 
					territoryIds, isMultihop);
			if(isMultihop) {
				break;
			}
		}
		System.out.println(" isMultihop possible = "+isMultihop);
		return isMultihop;
	}

	public boolean isMultipleHop(GameModel gameModel, TerritoryModel selectedTerritoryModel, 
			TerritoryModel adjacentTerritoryModel, HashSet<Integer> territoryIds, boolean isMultihop) {
		if(!territoryIds.contains(adjacentTerritoryModel.getId()) && 
				adjacentTerritoryModel.getPlayerModel().getId() == this.id) {
			territoryIds.add(adjacentTerritoryModel.getId());
			if(adjacentTerritoryModel.getId() == selectedTerritoryModel.getId()) {
				isMultihop = true;
			} else {
				for(TerritoryModel territory:adjacentTerritoryModel.getAdjacentTerritories()) {
					isMultihop = isMultipleHop(gameModel, selectedTerritoryModel, territory, territoryIds, isMultihop);
					if(isMultihop) {
						break;
					}
				}
			}
		} else if(adjacentTerritoryModel.getId() == selectedTerritoryModel.getId()) {
			isMultihop = false;
		}
		return isMultihop;
	}

	/**
	 * Beginning of the battle b/w attacker and defender
	 * 
	 * @param gameModel
	 * @param gameController
	 */
	public void startBattle(GameModel gameModel, GameController gameController) {
		Utility.writeLog("---------- Battle started --------");
		this.gameController = gameController;
		engageBattle(gameModel);
	}

	/**
	 * Continuing the battle until attacker lose all but one or defender lose all
	 * armies.
	 * 
	 * @param gameModel
	 */
	private void engageBattle(GameModel gameModel) {
		gameModel.notifyPhaseChange();
		attackingNoOfDice = 0;
		defendingNoOfDice = 0;
		gameController.setNoOfDiceToRoll();
		if (attackingNoOfDice != 0 && defendingNoOfDice != 0) {
			gameController.updateDiceList();
		} else {
			updateResult(gameModel);
		}
	}

	/**
	 * @return the isAutomatic
	 */
	public boolean isAutomatic() {
		return isAutomatic;
	}

	/**
	 * @param isAutomatic
	 *            the isAutomatic to set
	 */
	public void setAutomatic(boolean isAutomatic) {
		this.isAutomatic = isAutomatic;
	}

	/**
	 * Rolling the dice and listing of selected faces
	 */
	public void rollAndSetDiceList() {
		//Utility.writeLog("Rolling and setting selected dices:");
		attackingDiceList.clear();
		defendingDiceList.clear();
		for (int i = 1; i <= attackingNoOfDice; i++) {
			attackingDiceList.put(i, Utility.getRandomNumber(Constants.SIX) + Constants.ONE);
			//Utility.writeLog("attackingDiceList dice no:   "+i+" rolled phase: "+attackingDiceList.get(i));
		}
		for (int i = 1; i <= defendingNoOfDice; i++) {
			defendingDiceList.put(i, Utility.getRandomNumber(Constants.SIX) + Constants.ONE);
			//Utility.writeLog("defendingDiceList dice no: "+i+" rolled phase: "+defendingDiceList.get(i));
		}
	}

	/**
	 * Update the no of armies on attacking/defending territory after each fight
	 * 
	 * @param gameModel
	 */
	public void updateArmiesOnFightingTerritories(GameModel gameModel) {
		Collection<Integer> attackDiceValues = attackingDiceList.values();
		Collection<Integer> defendDiceValues = defendingDiceList.values();
		List<Integer> attackList = attackDiceValues.stream().sorted().map(x -> x).collect(Collectors.toList());
		List<Integer> defendList = defendDiceValues.stream().sorted().map(x -> x).collect(Collectors.toList());

		if (attackList.size() == Constants.THREE && defendList.size() == Constants.TWO) {
			if (attackList.get(Constants.TWO) > defendList.get(Constants.ONE)) {
				defendingTerritory.looseArmy();
				if (attackList.get(Constants.ONE) > defendList.get(Constants.ZERO)) {
					defendingTerritory.looseArmy();
					updateFightStatusDisplayMessage(false, defendingTerritory.getPlayerModel().getName(),
							Constants.TWO);
				} else {
					attackingTerritory.looseArmy();
					updateFightStatusDisplayMessage(true, "", Constants.ZERO);
				}
			} else {
				attackingTerritory.looseArmy();
				if (attackList.get(Constants.ONE) > defendList.get(Constants.ZERO)) {
					defendingTerritory.looseArmy();
					updateFightStatusDisplayMessage(true, "", Constants.ZERO);
				} else {
					attackingTerritory.looseArmy();
					updateFightStatusDisplayMessage(false, getName(), Constants.TWO);
				}
			}
		} else if (attackList.size() == Constants.THREE && defendList.size() == Constants.ONE) {
			if (attackList.get(Constants.TWO) > defendList.get(Constants.ZERO)) {
				defendingTerritory.looseArmy();
				updateFightStatusDisplayMessage(false, defendingTerritory.getPlayerModel().getName(), Constants.ONE);
			} else {
				attackingTerritory.looseArmy();
				updateFightStatusDisplayMessage(false, getName(), Constants.ONE);
			}

		} else if (attackList.size() == Constants.TWO && defendList.size() == Constants.TWO) {
			if (attackList.get(Constants.ONE) > defendList.get(Constants.ONE)) {
				defendingTerritory.looseArmy();
				if (attackList.get(Constants.ZERO) > defendList.get(Constants.ZERO)) {
					defendingTerritory.looseArmy();
					updateFightStatusDisplayMessage(false, defendingTerritory.getPlayerModel().getName(),
							Constants.TWO);
				} else {
					attackingTerritory.looseArmy();
					updateFightStatusDisplayMessage(true, "", Constants.ZERO);
				}
			} else {
				attackingTerritory.looseArmy();
				if (attackList.get(Constants.ZERO) > defendList.get(Constants.ZERO)) {
					defendingTerritory.looseArmy();
					updateFightStatusDisplayMessage(true, "", Constants.ZERO);
				} else {
					attackingTerritory.looseArmy();
					updateFightStatusDisplayMessage(false, getName(), Constants.TWO);
				}
			}
		} else if (attackList.size() == Constants.TWO && defendList.size() == Constants.ONE) {
			if (attackList.get(Constants.ONE) > defendList.get(Constants.ZERO)) {
				defendingTerritory.looseArmy();
				updateFightStatusDisplayMessage(false, defendingTerritory.getPlayerModel().getName(), Constants.ONE);
			} else {
				attackingTerritory.looseArmy();
				updateFightStatusDisplayMessage(false, getName(), Constants.ONE);
			}

		} else if (attackList.size() == Constants.ONE && defendList.size() == Constants.TWO) {
			if (attackList.get(Constants.ZERO) > defendList.get(Constants.ONE)) {
				defendingTerritory.looseArmy();
				updateFightStatusDisplayMessage(false, defendingTerritory.getPlayerModel().getName(), Constants.ONE);
			} else {
				attackingTerritory.looseArmy();
				updateFightStatusDisplayMessage(false, getName(), Constants.ONE);
			}
		} else if (attackList.size() == Constants.ONE && defendList.size() == Constants.ONE) {
			if (attackList.get(Constants.ZERO) > defendList.get(Constants.ZERO)) {
				defendingTerritory.looseArmy();
				updateFightStatusDisplayMessage(false, defendingTerritory.getPlayerModel().getName(), Constants.ONE);
			} else {
				attackingTerritory.looseArmy();
				updateFightStatusDisplayMessage(false, getName(), Constants.ONE);
			}
		}
		if (defendingTerritory.getArmies() <= 0 || attackingTerritory.getArmies() <= 1) {
			updateResult(gameModel);
		} else {
			engageBattle(gameModel);
		}
	}

	/**
	 * Updating the battle status
	 * 
	 * @param isboth
	 * @param player
	 * @param noOfArmies
	 */
	private void updateFightStatusDisplayMessage(boolean isboth, String player, int noOfArmies) {
		String message;
		if (isboth) {
			message = Constants.BOTH_LOST_ARMIES_MESSAGE;
			Utility.writeLog("Both attacker and defender lost an army");
		} else {
			message = Utility.replacePartInMessage(Constants.LOST_ARMIES_MESSAGE, "A", player);
			message = Utility.replacePartInMessage(message, "B", Integer.toString(noOfArmies));
			if(getName().equals(player)) {
				Utility.writeLog("Attacker lost "+noOfArmies);
			} else {
				Utility.writeLog("Defender lost "+noOfArmies);
			}
		}
		updateStatus(message);
	}

	/**
	 * Request for updating the view status
	 * 
	 * @param message
	 */
	private void updateStatus(String message) {
		gameController.getMapView().getStatusLabel().setText(message);
		gameController.getMapView().updatePlayerPanel();
		gameController.getGameModel().notifyPhaseChanging();
	}

	/**
	 * Action taken once the battle is over
	 * 
	 * @param gameModel
	 */
	public void updateResult(GameModel gameModel) {
		System.out.println("End of battle");
		if (defendingTerritory.getArmies() == 0) {
			gameModel.setState(Constants.CAPTURE);
			updateStatus(Constants.CAPTURING_MESSAGE);
			Utility.writeLog(Constants.CAPTURING_MESSAGE +defendingTerritory.getName());
			
			int noOfArmiesToMove;
			if(isHuman()) {
				noOfArmiesToMove = gameController.getMapView().showInputDialogPopup(attackingTerritory.getArmies() - 1);
			} else {
				noOfArmiesToMove = attackingTerritory.getArmies() - 1;
			}
			Utility.writeLog("Moving "+noOfArmiesToMove+" armies to territory "+defendingTerritory.getName());
			PlayerModel lostPlayer = defendingTerritory.getPlayerModel();
			lostPlayer.removeOccupiedTerritory(defendingTerritory);
			defendingTerritory.setPlayerModel(this);
			addOccupiedTerritory(defendingTerritory);
			defendingTerritory.setArmies(noOfArmiesToMove);
			attackingTerritory.looseArmies(noOfArmiesToMove);
			gameModel.notifyPhaseChange();
			if (!isCardAssigned) {
				assignCard(gameModel);
			}
			if (isPlayerEliminated(lostPlayer)) {
				Utility.writeLog(lostPlayer.getName()+" eliminated from the game ");
				Utility.writeLog(lostPlayer.getCards().size()+" cards added to current player "+getName());
				addCards(lostPlayer.getCards());
				lostPlayer.removeCards(lostPlayer.getCards());
			}
		} else if (attackingTerritory.getArmies() == 1) {
			Utility.writeLog("Attacker lost the battle ");
			gameModel.setState(Constants.LOST_BATTLE);
			gameController.getGameModel().notifyPhaseChanging();
		}

		clear();

		if (gameModel.isWon()) {
			gameController.gameOver(Utility.replacePartInMessage(Constants.WINNER, Constants.CHAR_A, 
					(getName()+getStrategy().getStrategyString())));
		} else {
			gameController.handleAttack();
		}
		gameModel.notifyPhaseChange();
	}
	
	public boolean winningStatus(GameModel gameModel) {
		if (gameModel.isWon()) {
			gameModel.setState(Constants.END_PHASE);
			return true;
		}
		return false;
	}

	/**
	 * Checks whether any player eliminated from the game
	 * 
	 * @param player
	 * @return true if eliminated
	 */
	public boolean isPlayerEliminated(PlayerModel player) {
		boolean eliminated = false;
		if (player.getOccupiedTerritories().size() == 0) {
			eliminated = true;
		}
		return eliminated;
	}

	/**
	 * Assigns a card to the player
	 * 
	 * @param gameModel
	 */
	public void assignCard(GameModel gameModel) {
		isCardAssigned = true;
		CardModel card = gameModel.drawCard();
		Utility.writeLog("Drawn a card: "+card.getType()+", country on it is: "+
		(card.getTerritoryModel()==null?"":card.getTerritoryModel().getName()));
		if (card != null)
			cards.add(card);
		gameModel.getCardsDeck().remove(card);
	}

	/**
	 * Removes the list of cards from player
	 * 
	 * @param cardsToBeRemoved
	 */
	public void removeCards(Vector<CardModel> cardsToBeRemoved) {
		if (cardsToBeRemoved != null && cardsToBeRemoved.size() > 0) {
			cards.removeAll(cardsToBeRemoved);
		}
	}

	/**
	 * Adds list of cards to the player
	 * 
	 * @param cardsToBeAdded
	 */
	public void addCards(Vector<CardModel> cardsToBeAdded) {
		if (cardsToBeAdded != null && cardsToBeAdded.size() > 0) {
			cards.addAll(cardsToBeAdded);
		}
	}

	/**
	 * Checks whether the traded card has the territory which belongs to the player,
	 * if yes it adds two additional armies to the territory
	 * 
	 * @param cardsToBeRemoved
	 */
	public void addAdditionalBounusForTradeCardMatch(Vector<CardModel> cardsToBeRemoved) {
		boolean isBonusToBeAdded = false;
		TerritoryModel matchedTerritoryModel = null;
		CardModel matchedCard = null;
		for (CardModel cardModel : cardsToBeRemoved) {
			if (isTradedCardMatchAnyTerritoryOfPlayer(cardModel.getTerritoryModel())) {
				isBonusToBeAdded = true;
				matchedTerritoryModel = cardModel.getTerritoryModel();
				matchedCard = cardModel;
				break;
			}
		}
		if (isBonusToBeAdded && matchedTerritoryModel != null) {
			matchedTerritoryModel.addArmies(Constants.TWO);
			Utility.writeLog("Added bonus 2 armies for trading card: "+matchedCard.getType()+
					", which has a territory name: "+matchedTerritoryModel.getName());
		}
	}

	/**
	 * Function to validate whether the player is able to attack on any territory
	 * 
	 * @returns true if player can attack(has more than 1 army)
	 */
	public boolean canAttack() {
		boolean canAttack = false;
		for (TerritoryModel territoryModel : occupiedTerritories) {
			if (territoryModel.getArmies() > 1) {
				for (TerritoryModel adjacent : territoryModel.getAdjacentTerritories()) {
					if (adjacent.getPlayerModel().getId() != getId()) {
						canAttack = true;
						break;
					}
				}
			}
		}
		Utility.writeLog(getName()+" can attack = "+canAttack);
		return canAttack;
	}

	/**
	 * Function to validate whether the player is able to fortify
	 * @return
	 */
	public boolean canFortify() {
		boolean canFortify = false;
		for (TerritoryModel territoryModel : occupiedTerritories) {
			if (territoryModel.getArmies() > 1) {
				for (TerritoryModel adjacent : territoryModel.getAdjacentTerritories()) {
					if (adjacent.getPlayerModel().getId() == getId()) {
						canFortify = true;
						break;
					}
				}
			}
		}
		Utility.writeLog(getName()+" can fortify = "+canFortify);
		return canFortify;
	}


	/**
	 * Clear battle history
	 */
	public void clear() {
		isAutomatic = false;
		attackingTerritory = null;
		defendingTerritory = null;
		attackingDiceList.clear();
		attackingDiceList.clear();
		attackingNoOfDice = Constants.ZERO;
		defendingNoOfDice = Constants.ZERO;
		if (rolledDiceView != null) {
			rolledDiceView.dispose();
		}
		rolledDiceView = null;
	}

	/**
	 * Function to notify observers when player state change.
	 */
	public void isChanged() {
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Checks whether a particular traded card has territory which belongs to
	 * current player
	 * 
	 * @param territoryModel
	 * @return true if traded card has territory which belongs to current player
	 */
	public boolean isTradedCardMatchAnyTerritoryOfPlayer(TerritoryModel territoryModel) {
		boolean isMatch = false;
		if (territoryModel != null && territoryModel.getPlayerModel().getId() == this.id) {
			isMatch = true;
		}
		return isMatch;
	}
	

	/**
	 * Action taken on valid card set selected to trade
	 * @param gameModel
	 * @param infantryCard
	 * @param cavarlyCard
	 * @param artilleryCard
	 * @param wildCard
	 */
	public void cardTradeActionPerformed(GameModel gameModel, int infantryCard, int cavarlyCard, int artilleryCard, int wildCard) {
		Utility.writeLog(" ------------ Trading cards -------------- ");
		Vector<CardModel> cards = getCards();
		Vector<CardModel> cardsToBeRemoved = new Vector<>();

		Utility.writeLog("Available cards : "+cards.stream().map(x -> x.getType()).collect(Collectors.toList()));
		
		for (CardModel card : cards) {
			if (infantryCard <= 0 && cavarlyCard <= 0 && artilleryCard <= 0 && wildCard <= 0) {
				break;
			}
			switch (card.getType()) {
			case Constants.ARMY_TYPE_INFANTRY:
				if (infantryCard > 0) {
					cardsToBeRemoved.add(card);
					infantryCard--;
				}
				break;
			case Constants.ARMY_TYPE_CAVALRY:
				if (cavarlyCard > 0) {
					cardsToBeRemoved.add(card);
					cavarlyCard--;
				}
				break;
			case Constants.ARMY_TYPE_ARTILLERY:
				if (artilleryCard > 0) {
					cardsToBeRemoved.add(card);
					artilleryCard--;
				}
				break;
			case Constants.ARMY_TYPE_WILD:
				if (wildCard > 0) {
					cardsToBeRemoved.add(card);
					wildCard--;
				}
				break;
			}
		}

		if (cardsToBeRemoved.size() == Constants.THREE) {
			Utility.writeLog("Traded cards : "+cardsToBeRemoved.stream().map(x -> x.getType()).collect(Collectors.toList()));
			addAdditionalBounusForTradeCardMatch(cardsToBeRemoved);
			removeCards(cardsToBeRemoved);
		}

		int tradeCount = gameModel.getCardTradeCount();
		tradeCount++;
		int armies;
		if (tradeCount <= Constants.SIX) {
			armies = gameModel.getCardArmyMap().get(tradeCount);
		} else {
			armies = gameModel.getCardArmyMap().get(Constants.SIX) + (tradeCount - Constants.SIX) * Constants.FIVE;
		}
		gameModel.setCardTradeCount(tradeCount);
		addArmies(armies);
		setCardTradeMandatory(false);
		Utility.writeLog("Armies added to player due to card trade = "+armies);
	}
	
	public boolean isHuman() {
		boolean isHuman = false;
		if(getStrategy().getStrategyString() == Constants.HUMAN) {
			isHuman = true;
		}
		return isHuman;
	}

	/**
	 * Basic information to display on player domination view
	 * 
	 * @param stringBuilder
	 * @returns StringBuilder of basic string
	 */
	private StringBuilder basicContent(StringBuilder stringBuilder) {
		if (stringBuilder == null || stringBuilder.length() == 0) {
			stringBuilder = new StringBuilder();
			stringBuilder.append("\n************* Player Domination View *************\n\n");
			stringBuilder.append("Information:\n\n");
		}
		return stringBuilder;
	}

	/**
	 * The details to display on domination view
	 * 
	 * @param stringBuilder
	 * @param gameModel
	 * @return StringBuilder of information
	 */
	public StringBuilder getDominationViewContent(StringBuilder stringBuilder, GameModel gameModel) {
		stringBuilder = basicContent(stringBuilder);
		for (PlayerModel player : GameModel.players) {
			stringBuilder.append("Player: ");
			stringBuilder.append(player.getName());
			stringBuilder.append(" as ");
			stringBuilder.append(player.getStrategy().getStrategyString());
			stringBuilder.append("\n");
			stringBuilder.append("Percentage of map controlled by this player: ");
			stringBuilder.append(calculatePercentage(player, gameModel));
			stringBuilder.append("%\n");
			stringBuilder.append("Continents controlled by this player: ");
			stringBuilder.append(getcontrolledContinents(player));
			stringBuilder.append("\n");
			stringBuilder.append("Total number of armies owned by this player: ");
			stringBuilder.append(player.getArmies());
			stringBuilder.append("\n");
			stringBuilder.append("Territories controlled by this player: ");
			stringBuilder.append(
					player.getOccupiedTerritories().stream().map(x -> x.getName()).collect(Collectors.toList()));
			stringBuilder.append("\n");
			stringBuilder.append("---------\n\n");
		}
		stringBuilder.append("==========================\n\n");
		return stringBuilder;
	}

	/**
	 * Calculate percentage of map occupied by player
	 * 
	 * @param player
	 * @param gameModel
	 * @returns percentage
	 */
	public double calculatePercentage(PlayerModel player, GameModel gameModel) {
		int totalNoTerritories = gameModel.getTerritories().size();
		double percentage = 0.0;
		if (totalNoTerritories != 0) {
			percentage = (player.getOccupiedTerritories().size() * 100) / totalNoTerritories;
		}
		return percentage;
	}

	/**
	 * Gets the list of continents controlled by a player
	 * 
	 * @param player
	 * @return String of controlled continents
	 */
	public String getcontrolledContinents(PlayerModel player) {
		Set<String> continents = new HashSet<>();
		for (TerritoryModel territoryModel : player.getOccupiedTerritories()) {
			if (isPlayerOwnContinent(player, territoryModel.getContinent())) {
				if (!continents.contains(territoryModel.getContinent().getName())) {
					continents.add(territoryModel.getContinent().getName());
				}
			}
		}
		String continentsStr = "";
		if (continents.size() > 0) {
			continentsStr = String.join(", ", continents);
		}
		return continentsStr;
	}

	/**
	 * Checks whether a player owns a given continent
	 * 
	 * @param player
	 * @param continentModel
	 * @returns true if owns
	 */
	public boolean isPlayerOwnContinent(PlayerModel player, ContinentModel continentModel) {
		boolean isOwned = true;
		for (TerritoryModel territory : continentModel.getTerritories()) {
			if (territory.getPlayerModel() != null && territory.getPlayerModel().getId() != player.getId()) {
				isOwned = false;
				break;
			}
		}
		return isOwned;
	}
	
	public void reinforce(GameModel gameModel) {
		Utility.writeLog("----------- ReInforce of player : "+getName()+" "+getStrategy().getStrategyString()+" armies : "+getArmies()+" -------------");
		strategy.reInforce(gameModel);
	}
	
	public void initialReinforce(GameModel gameModel) {
		Utility.writeLog("----------- InitialReInforce of player : "+getName()+" "+getStrategy().getStrategyString()+" armies : "+getArmies()+" -------------");
		strategy.initialReInforce(gameModel);
	}
	
	public String attack(GameModel gameModel, TerritoryModel... territoryModel) {
		return strategy.attack(gameModel, territoryModel);
	}
	
	public String fortify(GameModel gameModel, TerritoryModel... territoryModel) {
		return strategy.fortify(gameModel, territoryModel);
	}
	
	public void autoFortifyArmies(GameModel gameModel) {
		strategy.autoFortifyArmies(gameModel);
	}

	/**
	 * @return the attackingTerritory
	 */
	public TerritoryModel getAttackingTerritory() {
		return attackingTerritory;
	}

	/**
	 * @param attackingTerritory
	 *            the attackingTerritory to set
	 */
	public void setAttackingTerritory(TerritoryModel attackingTerritory) {
		this.attackingTerritory = attackingTerritory;
	}

	/**
	 * @return the defendingTerritory
	 */
	public TerritoryModel getDefendingTerritory() {
		return defendingTerritory;
	}

	/**
	 * @param defendingTerritory
	 *            the defendingTerritory to set
	 */
	public void setDefendingTerritory(TerritoryModel defendingTerritory) {
		this.defendingTerritory = defendingTerritory;
	}

	/**
	 * @return the gameController
	 */
	public GameController getGameController() {
		return gameController;
	}

	/**
	 * @param gameController
	 *            the gameController to set
	 */
	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	/**
	 * @return the cards
	 */
	public Vector<CardModel> getCards() {
		return cards;
	}

	/**
	 * @param cards
	 *            the cards to set
	 */
	public void setCards(Vector<CardModel> cards) {
		this.cards = cards;
	}

	/**
	 * @return the isCardAssigned
	 */
	public boolean isCardAssigned() {
		return isCardAssigned;
	}

	/**
	 * @param isCardAssigned
	 *            the isCardAssigned to set
	 */
	public void setCardAssigned(boolean isCardAssigned) {
		this.isCardAssigned = isCardAssigned;
	}

	/**
	 * @return the isCardTradeMandatory
	 */
	public boolean isCardTradeMandatory() {
		return isCardTradeMandatory;
	}

	/**
	 * @param isCardTradeMandatory
	 *            the isCardTradeMandatory to set
	 */
	public void setCardTradeMandatory(boolean isCardTradeMandatory) {
		this.isCardTradeMandatory = isCardTradeMandatory;
	}

	/**
	 * @return the strategy
	 */
	public IStrategy getStrategy() {
		return strategy;
	}

	/**
	 * @param strategy the strategy to set
	 */
	public void setStrategy(IStrategy strategy) {
		this.strategy = strategy;
	}
	
}
