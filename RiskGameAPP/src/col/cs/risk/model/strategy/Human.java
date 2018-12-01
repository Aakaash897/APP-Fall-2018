package col.cs.risk.model.strategy;

import java.io.Serializable;

import col.cs.risk.helper.Utility;
import col.cs.risk.model.Constants;
import col.cs.risk.model.GameModel;
import col.cs.risk.model.PlayerModel;
import col.cs.risk.model.TerritoryModel;

/**
 * Computer player Human (as strategy) functionalities
 * @author Team25
 *
 */
public class Human implements IStrategy, Serializable {
	
	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 1L;

	/** Player Model */
	PlayerModel playerModel;

	/** Default Constructor */
	public Human() {

	}

	/**
	 * Constructor with player model parameter
	 * @param playerModel
	 */
	public Human(PlayerModel playerModel) {
		this.playerModel = playerModel;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String fortify(GameModel gameModel, TerritoryModel... territoryModels) {
		String str = "";
		if(territoryModels!=null && territoryModels.length>0) {
			TerritoryModel territoryModel = territoryModels[0];
			switch (gameModel.getState()) {
			case Constants.FORTIFICATION_PHASE:
				Utility.writeLog(" ************ "+gameModel.getStateAsStringInDepth()+" : "+playerModel.getName()+" "+getStrategyString()+" ************ ");
				if (territoryModel.getPlayerModel().getId() == gameModel.getCurrentPlayer().getId()) {
					if (territoryModel.getArmies() > 1) {
						Utility.writeLog("Moving armies from territory = "+territoryModel.getName()+", no of armies = "+territoryModel.getArmies());
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
				&& (gameModel.getMoveArmiesFromTerritory().getAdjacentTerritories().contains(territoryModel)
						|| playerModel.isFortificationPossibleByMultipleHop(gameModel, territoryModel))) {
					Utility.writeLog("Moving armies to territory = "+territoryModel.getName()+", no of armies = "+territoryModel.getArmies());
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
		}
		return str;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String attack(GameModel gameModel, TerritoryModel... territoryModels) {
		String str = "";
		if(territoryModels!=null && territoryModels.length>0) {
			TerritoryModel territoryModel = territoryModels[0];
			switch (gameModel.getState()) {
			case Constants.ATTACK_PHASE:
				Utility.writeLog(" ************ "+gameModel.getStateAsStringInDepth()+" : "+playerModel.getName()+" "+getStrategyString()+" ************ ");
				if (territoryModel.getPlayerModel().getId() == gameModel.getCurrentPlayer().getId()) {
					if (territoryModel.getArmies() > 1) {
						Utility.writeLog("Attacking territory = "+territoryModel.getName()+", no of armies = "+territoryModel.getArmies());
						playerModel.setAttackingTerritory(territoryModel);
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
				if (territoryModel.getPlayerModel().getId() != playerModel.getId()
				&& playerModel.getAttackingTerritory().getAdjacentTerritories().contains(territoryModel)) {
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
					str = Constants.DEFEND_COUNTRY_SELECT_MESSAGE;
				}
				break;

			default:
				break;
			}
		}
		return str;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getStrategyString() {
		return Constants.HUMAN;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void initialReInforce(GameModel gameModel) {
		gameModel.gamePhasePlayerTurnSetup1(gameModel.getSelectedTerritory());
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void reInforce(GameModel gameModel) {
		gameModel.gamePhasePlayerTurnSetup1(gameModel.getSelectedTerritory());
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void autoFortifyArmies(GameModel gameModel) {
		//do nothing
	}

}
