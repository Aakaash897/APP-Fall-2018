package col.cs.risk.model.strategy;

import java.io.Serializable;
import java.util.Vector;

import col.cs.risk.helper.Utility;
import col.cs.risk.model.Constants;
import col.cs.risk.model.GameModel;
import col.cs.risk.model.PlayerModel;
import col.cs.risk.model.TerritoryModel;

/**
 * Computer player Random (as strategy) functionalities
 * @author Team 25
 *
 */
public class Random implements IStrategy, Serializable {

	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 1L;
	
	/** Player Model */
	PlayerModel playerModel;

	/** Default Constructor */
	public Random() {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialReInforce(GameModel gameModel) {
		TerritoryModel model = getRandomTerritory();
		Utility.writeLog("Randomly selected territory : "+model.getName());
		gameModel.setSelectedTerritory(model);
		gameModel.gamePhasePlayerTurnSetup1(model);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reInforce(GameModel gameModel) {
		initialReInforce(gameModel);
	}

	/**
	 * Constructor with player model parameter
	 * @param playerModel
	 */
	public Random(PlayerModel playerModel) {
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
			Utility.writeLog(" ************ "+gameModel.getStateAsStringInDepth()+" : "+playerModel.getName()+" "+getStrategyString()+" ************ ");
			territoryModel = getRandomTerritoryToAttackFrom();
			gameModel.setSelectedTerritory(territoryModel);
			if (territoryModel != null && territoryModel.getArmies() > 1) {
				Utility.writeLog("Attacking territory = "+territoryModel.getName()+", no of armies = "+territoryModel.getArmies());
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
				Utility.writeLog("Defending territory = "+territoryModel.getName()+", no of armies = "+territoryModel.getArmies()
						+" belongs to "+territoryModel.getPlayerModel().getName()+" "+
						territoryModel.getPlayerModel().getStrategy().getStrategyString());
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
			Utility.writeLog(" ************ "+gameModel.getStateAsStringInDepth()+" : "+playerModel.getName()+" "+getStrategyString()+" ************ ");
			territoryModel = getRandomTerritoryToFortifyFrom();
			if (territoryModel != null && territoryModel.getArmies() > 1) {
				Utility.writeLog("Moving armies from territory = "+territoryModel.getName()+", no of armies = "+territoryModel.getArmies());
				gameModel.setMoveArmiesFromTerritory(territoryModel);
				gameModel.notifyPhaseChanging();
				gameModel.setState(Constants.FORTIFYING_PHASE);
				gameModel.notifyPhaseChange();
				str = Constants.MOVE_TO + gameModel.getMoveArmiesFromTerritory().getName();
			} else {
				Utility.writeLog(Constants.CANNOT_FORTIFY_MESSAGE);
				gameModel.setState(Constants.CHANGE_TURN);
			}
			break;
		case Constants.FORTIFYING_PHASE:
			territoryModel = getRandomTerritoryToMoveArmiesTo(gameModel);
			if (territoryModel != null) {
				Utility.writeLog("Moving armies to territory = "+territoryModel.getName()+", no of armies = "+territoryModel.getArmies());
				gameModel.setMoveArmiesToTerritory(territoryModel);
				gameModel.notifyPhaseChanging();
				gameModel.setState(Constants.FORTIFY_PHASE);
				gameModel.notifyPhaseChange();
				str = Constants.ARMIES_TO_MOVE;
			} else {
				Utility.writeLog(Constants.CANNOT_FORTIFY_MESSAGE);
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
	 * {@inheritDoc}
	 */
	@Override
	public void autoFortifyArmies(GameModel gameModel) {
		if(gameModel.getMoveArmiesFromTerritory() != null && 
				gameModel.getMoveArmiesFromTerritory().getArmies() > 1 &&
				gameModel.getMoveArmiesToTerritory() != null) {
			if(gameModel.getMoveArmiesFromTerritory().getArmies() == 2) {
				gameModel.getMoveArmiesToTerritory().addArmy();
				gameModel.getMoveArmiesFromTerritory().looseArmy();
				Utility.writeLog("Moving 1 army from "+gameModel.getMoveArmiesToTerritory().getName()+
						" to "+gameModel.getMoveArmiesFromTerritory().getName());
			} else {
				int random = Utility.getRandomNumber(gameModel.getMoveArmiesFromTerritory().getArmies()-2);
				gameModel.getMoveArmiesToTerritory().addArmies(random+1);
				gameModel.getMoveArmiesFromTerritory().looseArmies(random+1);
				Utility.writeLog("Moving "+(random+1)+" armies from "+gameModel.getMoveArmiesToTerritory().getName()+
						" to "+gameModel.getMoveArmiesFromTerritory().getName());
			}
		}
		gameModel.setMoveArmiesFromTerritory(null);
		gameModel.setMoveArmiesToTerritory(null);
		gameModel.setState(Constants.CHANGE_TURN);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getStrategyString() {
		return Constants.RANDOM;
	}

	/**
	 * Gets a territory randomly
	 * @returns territory
	 */
	private TerritoryModel getRandomTerritory() {
		return playerModel.getOccupiedTerritories().get(
				Utility.getRandomNumber(playerModel.getOccupiedTerritories().size()));
	}

	/**
	 * Gets a own territory randomly to attack from
	 * @returns territory
	 */
	private TerritoryModel getRandomTerritoryToAttackFrom() {
		TerritoryModel model = null;
		int randomAttack = Utility.getRandomNumber(Constants.TWO);

		if(randomAttack == Constants.ZERO) {
			Utility.writeLog("Decides to attack");
			Vector<TerritoryModel> territories = new Vector<>();
			for(TerritoryModel territory:playerModel.getOccupiedTerritories()) {
				if(territory.getArmies() > 1) {
					for(TerritoryModel adjacent:territory.getAdjacentTerritories()) {
						if(adjacent.getPlayerModel().getId() != playerModel.getId()) {
							territories.add(territory);
						}
					}
				}
			}

			if(territories.size() > 1) {
				model = territories.get(Utility.getRandomNumber(territories.size()));
			}
		} else {
			Utility.writeLog("Decides not to attack");
		}
		return model;
	}

	/**
	 * Gets others territory randomly to attack
	 * @returns territory
	 */
	private TerritoryModel getNeighbourToAttack() {
		TerritoryModel territoryModel = null;
		Vector<TerritoryModel> territories = new Vector<>();
		for(TerritoryModel territory:playerModel.getAttackingTerritory().getAdjacentTerritories()) {
			if(territory.getPlayerModel().getId() != playerModel.getId()) {
				territories.add(territory);
			}
		}

		if(territories.size() > 1) {
			territoryModel = territories.get(Utility.getRandomNumber(territories.size()));
		}
		return territoryModel;
	}
	
	/**
	 * Gets a territory randomly to fortify(move armies) from
	 * @returns territory
	 */
	private TerritoryModel getRandomTerritoryToFortifyFrom() {
		TerritoryModel model = null;
		Vector<TerritoryModel> territories = new Vector<>();
		for(TerritoryModel territory:playerModel.getOccupiedTerritories()) {
			if(territory.getArmies() > 1) {
				territories.add(territory);
			}
		}

		if(territories.size() > 1) {
			model = territories.get(Utility.getRandomNumber(territories.size()));
		}
		return model;
	}

	/**
	 * Gets a territory randomly to move armies to
	 * @param gameModel
	 * @return
	 */
	private TerritoryModel getRandomTerritoryToMoveArmiesTo(GameModel gameModel) {
		Vector<TerritoryModel> territories = new Vector<>();
		for(TerritoryModel territory:playerModel.getOccupiedTerritories()) {
			if(gameModel.getMoveArmiesFromTerritory().getId() != territory.getId() &&
					(gameModel.getMoveArmiesFromTerritory().getAdjacentTerritories().contains(territory) 
							|| playerModel.isFortificationPossibleByMultipleHop(gameModel, territory))) {
				if(!territories.contains(territory)) {
					territories.add(territory);
				}
			}
		}
		TerritoryModel model = null;
		if(territories.size() > 1) {
			model = territories.get(Utility.getRandomNumber(territories.size()));
		}
		return model;
	}

}
