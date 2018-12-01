package col.cs.risk.model.strategy;

import java.io.Serializable;
import java.util.Vector;

import col.cs.risk.helper.Utility;
import col.cs.risk.model.Constants;
import col.cs.risk.model.GameModel;
import col.cs.risk.model.PlayerModel;
import col.cs.risk.model.TerritoryModel;

public class Aggressive implements IStrategy, Serializable {

	/** Player Model */
	PlayerModel playerModel;

	/** Default Constructor */
	public Aggressive() {

	}

	/**
	 * TerritoryModel instance 0
	 */
	private TerritoryModel strongest;

	/**
	 * Constructor with player model parameter
	 * 
	 * @param playerModel
	 */
	public Aggressive(PlayerModel playerModel) {
		this.playerModel = playerModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String attack(GameModel gameModel, TerritoryModel... territoryModels) {
		String str = "";
		TerritoryModel territoryModel;
		switch (gameModel.getState()) {
		case Constants.ATTACK_PHASE:
			Utility.writeLog(" ************ " + gameModel.getStateAsStringInDepth() + " : " + playerModel.getName()
					+ " " + getStrategyString() + " ************ ");
			territoryModel = getStrongestTerritory();
			gameModel.setSelectedTerritory(territoryModel);
			if (territoryModel != null && territoryModel.getArmies() > 1) {
				Utility.writeLog("Attacking territory = " + territoryModel.getName() + " no. of armies = "
						+ territoryModel.getArmies());
				playerModel.setAttackingTerritory(territoryModel);
				gameModel.notifyPhaseChanging();
				gameModel.setState(Constants.ATTACKING_PHASE);
				gameModel.notifyPhaseChange();
				str = Constants.DEFEND_COUNTRY_SELECT_MESSAGE;
			} else {
				Utility.writeLog(Constants.CANNOT_ATTACK_MESSAGE);
				gameModel.setState(Constants.FORTIFICATION_PHASE);
				gameModel.notifyPhaseChanging();
				gameModel.notifyPhaseChange();
				str = Constants.MOVE_FROM;
			}
			break;
		case Constants.ATTACKING_PHASE:
			territoryModel = getNeighbourToAttack();
			gameModel.setSelectedTerritory(territoryModel);
			if (territoryModel != null) {
				Utility.writeLog("Defending territory = " + territoryModel.getName() + ", no of armies = "
						+ territoryModel.getArmies() + " belongs to " + territoryModel.getPlayerModel().getName() + " "
						+ territoryModel.getPlayerModel().getStrategy().getStrategyString());
				playerModel.setDefendingTerritory(territoryModel);
				gameModel.notifyPhaseChanging();
				gameModel.setState(Constants.ATTACK_FIGHT_PHASE);
				gameModel.notifyPhaseChange();
				str = Constants.ATTACK_BW_TERRITORIES_MESSAGE;
				str = str.replace("A", playerModel.getAttackingTerritory().getName());
				str = str.replace("B", playerModel.getDefendingTerritory().getName());
			} else {
				Utility.writeLog(Constants.CANNOT_ATTACK_MESSAGE);
				playerModel.setAttackingTerritory(null);
				gameModel.setState(Constants.FORTIFICATION_PHASE);
				gameModel.notifyPhaseChanging();
				gameModel.notifyPhaseChange();
				str = Constants.MOVE_FROM;
			}
			break;
		}
		return str;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String fortify(GameModel gameModel, TerritoryModel... territoryModels) {
		TerritoryModel territoryModel;
		String str = "";
		switch (gameModel.getState()) {
		case Constants.FORTIFICATION_PHASE:
			Utility.writeLog(" ************ " + gameModel.getStateAsStringInDepth() + " : " + playerModel.getName()
					+ " " + getStrategyString() + " ************ ");
			territoryModel = getStrongestTerritoryStrongestAdjacent(gameModel);
			if (territoryModel != null && territoryModel.getArmies() > 1) {
				Utility.writeLog("Moving armies from territory = " + territoryModel.getName() + ", no of armies = "
						+ territoryModel.getArmies());
				gameModel.setMoveArmiesFromTerritory(territoryModel);
				gameModel.notifyPhaseChanging();
				gameModel.setState(Constants.FORTIFYING_PHASE);
				gameModel.notifyPhaseChange();
				str = Constants.MOVE_TO + gameModel.getMoveArmiesFromTerritory().getName();
			} else {
				Utility.writeLog(Constants.CANNOT_FORTIFY_MESSAGE);
				strongest = null;
				gameModel.setMoveArmiesFromTerritory(null);
				gameModel.setState(Constants.CHANGE_TURN);
			}
			break;
		case Constants.FORTIFYING_PHASE:
			territoryModel = strongest; // getStrongestTerritory();
			if (territoryModel != null) {
				Utility.writeLog("Moving armies to territory = " + territoryModel.getName() + ", no of armies = "
						+ territoryModel.getArmies());
				gameModel.setMoveArmiesToTerritory(territoryModel);
				gameModel.notifyPhaseChanging();
				gameModel.setState(Constants.FORTIFY_PHASE);
				gameModel.notifyPhaseChange();
				str = Constants.ARMIES_TO_MOVE;
			} else {
				Utility.writeLog(Constants.CANNOT_FORTIFY_MESSAGE);
				strongest = null;
				gameModel.setMoveArmiesFromTerritory(null);
				gameModel.setState(Constants.CHANGE_TURN);
			}
			break;
		default:
			break;
		}
		return str;
	}

	/**
	 * this method automatically move the armies from one territory to another
	 * territory
	 * 
	 * @param gameModel
	 */
	public void autoFortifyArmies(GameModel gameModel) {
		if (gameModel.getMoveArmiesFromTerritory() != null && gameModel.getMoveArmiesFromTerritory().getArmies() > 1
				&& gameModel.getMoveArmiesToTerritory() != null) {
			int armiesToMove = gameModel.getMoveArmiesFromTerritory().getArmies() - 1;
			Utility.writeLog("Moving " + armiesToMove + " armies from " + gameModel.getMoveArmiesToTerritory().getName()
					+ " to " + gameModel.getMoveArmiesFromTerritory().getName());
			gameModel.getMoveArmiesToTerritory().addArmies(armiesToMove);
			gameModel.getMoveArmiesFromTerritory().looseArmies(armiesToMove);
		}
		gameModel.setMoveArmiesFromTerritory(null);
		gameModel.setMoveArmiesToTerritory(null);
		gameModel.setState(Constants.CHANGE_TURN);
	}

	@Override
	public void initialReInforce(GameModel gameModel) {
		TerritoryModel model = getStrongestTerritory();
		Utility.writeLog("Strongest territory : " + model.getName());
		gameModel.setSelectedTerritory(model);
		gameModel.gamePhasePlayerTurnSetup1(model);
	}

	@Override
	public void reInforce(GameModel gameModel) {
		initialReInforce(gameModel);
	}

	/**
	 * this method returns the strongest territory which has largest no of armies
	 * 
	 * @return strongestTerritory territoryModel instance
	 */
	private TerritoryModel getStrongestTerritory() {
		TerritoryModel strongestTerritory = playerModel.getOccupiedTerritories().get(0);
		Vector<TerritoryModel> territories = playerModel.getOccupiedTerritories();
		for (TerritoryModel territoryModel : territories) {
			if (territoryModel.getArmies() > strongestTerritory.getArmies()) {
				strongestTerritory = territoryModel;
			}
		}
		if (strongestTerritory.getArmies() == 1) {
			strongestTerritory = playerModel.getOccupiedTerritories()
					.get(Utility.getRandomNumber(playerModel.getOccupiedTerritories().size()));
		}
		return strongestTerritory;
	}

	/**
	 * this method returns the neighbor territory to attack
	 * 
	 * @return territoryModel neighbor territory model instance
	 */
	private TerritoryModel getNeighbourToAttack() {
		TerritoryModel territoryModel = null;
		for (TerritoryModel territory : playerModel.getAttackingTerritory().getAdjacentTerritories()) {
			if (territory.getPlayerModel().getId() != playerModel.getId()) {
				territoryModel = territory;
				break;
			}
		}
		return territoryModel;
	}

	/**
	 * this method returns the second strongest territory which has second largest
	 * no of armies
	 * 
	 * @return strongestTerritory territoryModel instance
	 */
	private TerritoryModel getStrongestTerritoryStrongestAdjacent1(TerritoryModel territoryModel) {
		TerritoryModel strongestTerritory = null;
		for (TerritoryModel territory : territoryModel.getAdjacentTerritories()) {
			if (territory.getPlayerModel().getId() == playerModel.getId()) {
				if (strongestTerritory == null) {
					strongestTerritory = territory;
				} else if (strongestTerritory.getArmies() < territory.getArmies()) {
					strongestTerritory = territory;
				}
			}
		}
		return strongestTerritory;
	}

	/**
	 * this method returns adjacent territory to the strongest territory which has
	 * largest no of armies
	 * 
	 * @return strongestTerritory territoryModel instance
	 */
	private TerritoryModel getStrongestTerritoryStrongestAdjacent(GameModel gameModel) {
		strongest = getStrongestTerritory();
		gameModel.setMoveArmiesFromTerritory(strongest);
		TerritoryModel strongestTerritory = null;
		Vector<TerritoryModel> territories = playerModel.getOccupiedTerritories();
		for (TerritoryModel territory : territories) {
			if (gameModel.getMoveArmiesFromTerritory().getId() != territory.getId() && territory.getArmies() > 1
					&& (gameModel.getMoveArmiesFromTerritory().getAdjacentTerritories().contains(territory)
							|| playerModel.isFortificationPossibleByMultipleHop(gameModel, territory))) {
				if (strongestTerritory == null) {
					strongestTerritory = territory;
				} else if (territory.getArmies() > strongestTerritory.getArmies()) {
					strongestTerritory = territory;
				}
			}
		}
		gameModel.setMoveArmiesFromTerritory(null);
		return strongestTerritory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getStrategyString() {
		return Constants.AGGRESSIVE;
	}

}
