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
import col.cs.risk.view.MapView;
import col.cs.risk.view.RolledDiceView;

/**
 * This class maintains the player details as well as implements the methods
 * related to player like occupyTerritory and do fortification etc.
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
	
	public boolean isAssignedAlready=false;

	/** Card Model Object */
	private CardModel cardModelObj;

	/** CardModel vector */
	private Vector<CardModel> cardVector = new Vector<>();

	/**
	 * Constructor with parameters
	 * 
	 * @param id
	 *            player id
	 * @param name
	 *            player name
	 */
	public PlayerModel(int id, String name) {
		this.id = id;
		this.name = name;
		occupiedTerritories = new Vector<>();
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param id
	 *            It holds the player id
	 * @param name
	 *            It hold the player name
	 * @param occupiedTerritories
	 *            It is a vector that holds the list of occupied territories
	 */
	public PlayerModel(int id, String name, Vector<TerritoryModel> occupiedTerritories) {
		this(id, name);
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
	 * Add turn bonus to current player
	 */
	public void addTurnBonus(GameModel gameModel) {
		int bonus = territoryBonus() + gameModel.continentBonus();
		addArmies(bonus);
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
	public String fortify(GameModel gameModel, TerritoryModel territoryModel) {
		String str = "";
		switch (gameModel.getState()) {
		case Constants.FORTIFICATION_PHASE:
			if (territoryModel.getPlayerModel().getId() == gameModel.getCurrentPlayer().getId()) {
				if (territoryModel.getArmies() > 1) {
					gameModel.setMoveArmiesFromTerritory(territoryModel);
					gameModel.notifyPhaseChanging();
					gameModel.setState(Constants.FORTIFYING_PHASE);
					gameModel.notifyPhaseChange();
					str = Constants.MOVE_TO + gameModel.getMoveArmiesFromTerritory().getName();
				} else {
					str = Constants.MIN_TWO_ARMY_MESSAGE;
				}
			} else {
				str = Constants.MOVE_FROM;
			}
			break;
		case Constants.FORTIFYING_PHASE:
			if (territoryModel.getPlayerModel().getId() == gameModel.getCurrentPlayer().getId()
					&& gameModel.getMoveArmiesFromTerritory().getAdjacentTerritories().contains(territoryModel)) {
				gameModel.setMoveArmiesToTerritory(territoryModel);
				gameModel.notifyPhaseChanging();
				gameModel.setState(Constants.FORTIFY_PHASE);
				gameModel.notifyPhaseChange();
				str = Constants.ARMIES_TO_MOVE;
			} else {
				str = Constants.MOVE_TO + gameModel.getMoveArmiesFromTerritory().getName();
			}
			break;

		default:
			break;
		}
		return str;
	}

	/**
	 * User actions on attack phase actions, selection of attacking and defending
	 * territory
	 * 
	 * @param gameModel
	 * @param territoryModel
	 * @return
	 */
	public String attack(GameModel gameModel, TerritoryModel territoryModel) {
		String str = "";
		switch (gameModel.getState()) {
		case Constants.ATTACK_PHASE:
			if (territoryModel.getPlayerModel().getId() == gameModel.getCurrentPlayer().getId()) {
				if (territoryModel.getArmies() > 1) {
					attackingTerritory = territoryModel;
					gameModel.notifyPhaseChanging();
					gameModel.setState(Constants.ATTACKING_PHASE);
					gameModel.notifyPhaseChange();
					str = Constants.DEFEND_COUNTRY_SELECT_MESSAGE;
				} else {
					str = Constants.MIN_TWO_ARMY_MESSAGE;
				}
			} else {
				str = Constants.ATTACK_COUNTRY_SELECT_MESSAGE;
			}
			break;
		case Constants.ATTACKING_PHASE:
			System.out.println(" attackingTerritory " + attackingTerritory.printTerritory());
			System.out.println(" defending territory = " + territoryModel.printTerritory());
			if (territoryModel.getPlayerModel().getId() != getId()
					&& attackingTerritory.getAdjacentTerritories().contains(territoryModel)) {
				defendingTerritory = territoryModel;
				gameModel.notifyPhaseChanging();
				gameModel.setState(Constants.ATTACK_FIGHT_PHASE);
				gameModel.notifyPhaseChange();
				str = Constants.ATTACK_BW_TERRITORIES_MESSAGE;
				str = str.replace("A", attackingTerritory.getName());
				str = str.replace("B", defendingTerritory.getName());
			} else {
				str = Constants.DEFEND_COUNTRY_SELECT_MESSAGE;
			}
			break;

		default:
			break;
		}
		return str;
	}

	/**
	 * Beginning of the battle b/w attacker and defender
	 * 
	 * @param gameModel
	 * @param mapView
	 * @param gameController
	 */
	public void startBattle(GameModel gameModel, MapView mapView, GameController gameController) {
		rolledDiceView = new RolledDiceView(this);
		this.gameController = gameController;
		String[] options = { Constants.OK, Constants.CANCEL };
		String option = gameController.getMapView().showOptionPopup(Constants.AUTOMATIC_OR_ALL_OUT_MODE, options);
		isAutomatic = option.equals(Constants.OK) ? true : false;
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
		setNoOfDiceToRoll();
		if (attackingNoOfDice != 0 && defendingNoOfDice != 0) {
			System.out.println(" isAutomatic = " + isAutomatic);
			if (!isAutomatic) {
				Utility.showMessagePopUp(Constants.CLICK_OK_TO_ROLL_DICE, "Roll Dice");
			}
			rollAndSetDiceList();
			rolledDiceView.showRolledDiceList(gameModel);
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
	 * Selection of no. of dice to roll(both attacker and defender)
	 */
	private void setNoOfDiceToRoll() {
		int numberOfDice = attackingTerritory.getArmies();
		if (numberOfDice > 1) {
			if (isAutomatic) {
				numberOfDice = numberOfDice < Constants.THREE ? numberOfDice - 1 : Constants.THREE;
			} else {
				numberOfDice = gameController.getMapView().showOptionPopup(getName(),
						numberOfDice < Constants.THREE ? numberOfDice - 1 : Constants.THREE, Constants.ATTACK_IMAGE,
						getName());
			}
			attackingNoOfDice = numberOfDice;

			numberOfDice = defendingTerritory.getArmies();
			if (numberOfDice > 0) {
				if (isAutomatic) {
					numberOfDice = numberOfDice < Constants.TWO ? numberOfDice : Constants.TWO;
				} else {
					numberOfDice = gameController.getMapView().showOptionPopup(
							defendingTerritory.getPlayerModel().getName(),
							numberOfDice < Constants.TWO ? numberOfDice : Constants.TWO, Constants.DEFEND_IMAGE,
							defendingTerritory.getPlayerModel().getName());
				}
				defendingNoOfDice = numberOfDice;
			}
		}
	}

	/**
	 * Rolling the dice and listing of selected faces
	 */
	private void rollAndSetDiceList() {
		attackingDiceList.clear();
		defendingDiceList.clear();
		for (int i = 1; i <= attackingNoOfDice; i++) {
			attackingDiceList.put(i, Utility.getRandomNumber(Constants.SIX) + Constants.ONE);
		}
		for (int i = 1; i <= defendingNoOfDice; i++) {
			defendingDiceList.put(i, Utility.getRandomNumber(Constants.SIX) + Constants.ONE);
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
		if (defendingTerritory.getArmies() == 0 || attackingTerritory.getArmies() == 1) {
			updateResult(gameModel);
		} else {
			engageBattle(gameModel);
		}
	}

	/**
	 * Updating the battle status
	 * 
	 */
	private void updateFightStatusDisplayMessage(boolean isboth, String player, int noOfArmies) {
		String message;
		if (isboth) {
			message = Constants.BOTH_LOST_ARMIES_MESSAGE;
		} else {
			message = Utility.replacePartInMessage(Constants.LOST_ARMIES_MESSAGE, "A", player);
			message = Utility.replacePartInMessage(message, "B", Integer.toString(noOfArmies));
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
	private void updateResult(GameModel gameModel) {
		if (defendingTerritory.getArmies() == 0) {
			gameModel.setState(Constants.CAPTURE);
			updateStatus(Constants.CAPTURING_MESSAGE);

			int noOfArmiesToMove = gameController.getMapView().showInputDialogPopup(attackingTerritory.getArmies() - 1);
			PlayerModel lostPlayer = defendingTerritory.getPlayerModel();
			lostPlayer.removeOccupiedTerritory(defendingTerritory);
			defendingTerritory.setPlayerModel(this);
			addOccupiedTerritory(defendingTerritory);
			defendingTerritory.setArmies(noOfArmiesToMove);
			attackingTerritory.looseArmies(noOfArmiesToMove);
			int randomNo = Utility.getRandomNumber(gameModel.totCards.size());
			cardVector.add(gameModel.totCards.get(randomNo));
			gameModel.totCards.remove(randomNo);
			System.out.println("**************************************************");
			for(int i=0;i<cardVector.size();i++)
			{
				System.out.println(cardVector.get(0).cardType);				
			}
			gameModel.notifyPhaseChange();
		} else if (attackingTerritory.getArmies() == 1) {
			gameModel.setState(Constants.CAPTURE);
			gameController.getGameModel().notifyPhaseChanging();
		}

		clear();

		if (gameModel.isWon()) {
			gameController.gameOver(Utility.replacePartInMessage(Constants.WINNER, Constants.CHAR_A, getName()));
		} else {
			gameController.handleAttack();
		}
		gameModel.notifyPhaseChange();
	}

	/**
	 * Function to validate whether the player is able to attack on any territory
	 * 
	 * @return
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
		System.out.println(" canAttack = " + canAttack);
		return canAttack;
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
	 * Checks if is changed.
	 */
	public void isChanged() {
		try {
			int i[] = { 0 };
			System.out.println(i[3]);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("------------------\n");

		}
		setChanged();
		notifyObservers(this);
	}

	private StringBuilder basicContent(StringBuilder stringBuilder) {
		if (stringBuilder == null || stringBuilder.length() == 0) {
			stringBuilder = new StringBuilder();
			stringBuilder.append("\n************* Player Domination View *************\n\n");
			stringBuilder.append("Information:\n\n");
		}
		return stringBuilder;
	}

	public StringBuilder getDominationViewContent(StringBuilder stringBuilder, GameModel gameModel) {
		stringBuilder = basicContent(stringBuilder);
		for (PlayerModel player : GameModel.players) {
			stringBuilder.append("Player: ");
			stringBuilder.append(player.getName());
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

	public double calculatePercentage(PlayerModel player, GameModel gameModel) {
		int totalNoTerritories = gameModel.getTerritories().size();
		double percentage = 0.0;
		if (totalNoTerritories != 0) {
			percentage = (player.getOccupiedTerritories().size() * 100) / totalNoTerritories;
		}
		return percentage;
	}

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

}
