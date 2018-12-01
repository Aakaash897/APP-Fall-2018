package col.cs.risk.model.strategy;

import java.io.Serializable;
import java.util.Vector;

import col.cs.risk.helper.Utility;
import col.cs.risk.model.Constants;
import col.cs.risk.model.GameModel;
import col.cs.risk.model.PlayerModel;
import col.cs.risk.model.TerritoryModel;

/**
 * Computer player Benevolent (as strategy) functionalities
 * @author Team 25
 *
 */
public class Benevolent implements IStrategy, Serializable {

	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 1L;
	
	/** Player Model */
	PlayerModel playerModel;

	/** Default Constructor */
	public Benevolent() {

	}
	/** Weakest territory */
	private TerritoryModel weakestTerritory;
	
	/**
	 * Constructor with player model parameter
	 * @param playerModel
	 */
	public Benevolent(PlayerModel playerModel) {
		this.playerModel = playerModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialReInforce(GameModel gameModel) {
		TerritoryModel model = getWeakestTerritory();
		Utility.writeLog("Weakest territory : "+model.getName());
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
	 * {@inheritDoc}
	 */
	@Override
	public String attack(GameModel gameModel, TerritoryModel... territoryModels) {
		if(gameModel.getState() == Constants.ATTACK_PHASE) {
			Utility.writeLog(" ************ "+gameModel.getStateAsStringInDepth()+" : "+playerModel.getName()+" "+getStrategyString()+" ************ ");
			Utility.writeLog("Benevolevnt will not attack");
			gameModel.setState(Constants.FORTIFICATION_PHASE);
			gameModel.notifyPhaseChanging();
			gameModel.notifyPhaseChange();
			return Constants.MOVE_FROM;
		}
		return "";
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
			territoryModel = getWeakestTerritoryStrongestAdjacent(gameModel);
			if (territoryModel != null && territoryModel.getArmies() > 1) {
				Utility.writeLog("Moving armies from territory = "+territoryModel.getName()+", no of armies = "+territoryModel.getArmies());
				gameModel.setMoveArmiesFromTerritory(territoryModel);
				gameModel.notifyPhaseChanging();
				gameModel.setState(Constants.FORTIFYING_PHASE);
				gameModel.notifyPhaseChange();
				str = Constants.MOVE_TO + gameModel.getMoveArmiesFromTerritory().getName();
			} else {
				Utility.writeLog(Constants.CANNOT_FORTIFY_MESSAGE);
				weakestTerritory = null;
				gameModel.setMoveArmiesFromTerritory(null);
				gameModel.setState(Constants.CHANGE_TURN);
			}
			break;
		case Constants.FORTIFYING_PHASE:
			territoryModel = weakestTerritory; 
			if (territoryModel != null) {
				Utility.writeLog("Moving armies to territory = "+territoryModel.getName()+", no of armies = "+territoryModel.getArmies());
				gameModel.setMoveArmiesToTerritory(territoryModel);
				gameModel.notifyPhaseChanging();
				gameModel.setState(Constants.FORTIFY_PHASE);
				gameModel.notifyPhaseChange();
				str = Constants.ARMIES_TO_MOVE;
			} else {
				Utility.writeLog(Constants.CANNOT_FORTIFY_MESSAGE);
				weakestTerritory = null;
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
			int armiesToMove = gameModel.getMoveArmiesFromTerritory().getArmies()-1;
			gameModel.getMoveArmiesToTerritory().addArmies(armiesToMove);
			gameModel.getMoveArmiesFromTerritory().looseArmies(armiesToMove);
			Utility.writeLog("Moving "+armiesToMove+" armies from "+gameModel.getMoveArmiesToTerritory().getName()+
					" to "+gameModel.getMoveArmiesFromTerritory().getName());
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
		return Constants.BENEVOLENT;
	}

	/**
	 * Returns the weakest territory among all occupied territory
	 * @returns weakest territory
	 */
	private TerritoryModel getWeakestTerritory() {
		TerritoryModel weakest = playerModel.getOccupiedTerritories().get(0);
		Vector<TerritoryModel> territories = new Vector<>();
		for(TerritoryModel territoryModel:playerModel.getOccupiedTerritories()) {
			if(territoryModel.getArmies() < weakest.getArmies()) {
				weakest = territoryModel;
			}
			if(territoryModel.getArmies() == 1) {
				territories.add(territoryModel);
			}
		}
		if(territories.size() > 1) {
			weakest = territories.get(
					Utility.getRandomNumber(territories.size()));
		}
		return weakest;
	}

	/**
	 * Returns strongest adjacent to weakest territory
	 * @param gameModel
	 * @return Strongest adjacent
	 */
	private TerritoryModel getWeakestTerritoryStrongestAdjacent(GameModel gameModel) {
		weakestTerritory = getWeakestTerritory();
		TerritoryModel strongestTerritory = null;
		for(TerritoryModel territory:playerModel.getOccupiedTerritories()) {
			if(weakestTerritory.getId() != territory.getId() && territory.getArmies() > 1 &&
					weakestTerritory.getAdjacentTerritories().contains(territory)) {
				if(strongestTerritory == null) {
					strongestTerritory = territory;
				} else if(territory.getArmies() > strongestTerritory.getArmies()) {
					strongestTerritory = territory;
				}
			}
		}
		if(strongestTerritory == null) {
			gameModel.setMoveArmiesFromTerritory(weakestTerritory);
			for(TerritoryModel territory:playerModel.getOccupiedTerritories()) {
				if(gameModel.getMoveArmiesFromTerritory().getId() != territory.getId() &&
						territory.getArmies() > 1 &&
						(gameModel.getMoveArmiesFromTerritory().getAdjacentTerritories().contains(territory) 
								|| playerModel.isFortificationPossibleByMultipleHop(gameModel, territory))) {
					if(strongestTerritory == null) {
						strongestTerritory = territory;
						break;
					} 
				}
			}
		}
		gameModel.setMoveArmiesFromTerritory(null);
		return strongestTerritory;
	}
}
