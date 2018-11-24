package col.cs.risk.model.strategy;

import java.util.Vector;

import col.cs.risk.helper.Utility;
import col.cs.risk.model.Constants;
import col.cs.risk.model.GameModel;
import col.cs.risk.model.PlayerModel;
import col.cs.risk.model.TerritoryModel;

public class Cheater implements IStrategy {

	/** Player Model */
	PlayerModel playerModel;

	/** Default Constructor */
	public Cheater() {

	}

	/**
	 * Constructor with player model parameter
	 * @param playerModel
	 */
	public Cheater(PlayerModel playerModel) {
		this.playerModel = playerModel;
	}

	@Override
	public void initialReInforce(GameModel gameModel) {
		TerritoryModel model = getRandomTerritory();
		Utility.writeLog("Selected territory : "+model.getName());
		gameModel.setSelectedTerritory(model);
		gameModel.gamePhasePlayerTurnSetup1(model);
	}

	@Override
	public void reInforce(GameModel gameModel) {
		for(TerritoryModel territoryModel:playerModel.getOccupiedTerritories()) {
			Utility.writeLog("Doubling armies on territory = "+territoryModel.getName()+
					" from "+territoryModel.getArmies()+" to "+(territoryModel.getArmies()*2));
			territoryModel.addArmies(territoryModel.getArmies());
		}
		playerModel.setArmies(Constants.ZERO);
		gameModel.setState(Constants.ATTACK_PHASE);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String attack(GameModel gameModel, TerritoryModel... territoryModels) {
		String str = "";
		if(gameModel.getState() == Constants.ATTACK_PHASE) {
			Utility.writeLog(" ************ "+gameModel.getStateAsStringInDepth()+" : "+playerModel.getName()+" "+getStrategyString()+" ************ ");
			Vector<TerritoryModel> newTerritories = new Vector<>();
			for(TerritoryModel territoryModel:playerModel.getOccupiedTerritories()) {
				for(TerritoryModel adjacent:territoryModel.getAdjacentTerritories()) {
					if(adjacent.getPlayerModel().getId() != playerModel.getId()) {
						if(!newTerritories.contains(adjacent)) {
							newTerritories.add(adjacent);
						}
					}
				}
			}
			for(TerritoryModel adjacent: newTerritories) {
				adjacent.getPlayerModel().removeOccupiedTerritory(adjacent);
				adjacent.setPlayerModel(playerModel);
				adjacent.setArmies(1);
				playerModel.addOccupiedTerritory(adjacent);
				Utility.writeLog("Conquered territory = "+adjacent.getName());
			}
			
			gameModel.notifyPhaseChanging();
			gameModel.notifyPhaseChange();

			//if(playerModel.winningStatus(gameModel)) {
			if(gameModel.isWon()) {
				gameModel.setState(Constants.END_PHASE);
				//str = Utility.replacePartInMessage(Constants.WINNER, Constants.CHAR_A, playerModel.getName());
			} else {
				if(newTerritories.size() > 0) {
					playerModel.assignCard(gameModel);
				} else {
					Utility.writeLog(Constants.CANNOT_ATTACK_MESSAGE);
				}
				
				gameModel.setState(Constants.FORTIFICATION_PHASE);
				gameModel.notifyPhaseChanging();
				gameModel.notifyPhaseChange();
				str = Constants.MOVE_FROM;
			}
		}
		return str;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String fortify(GameModel gameModel, TerritoryModel... territoryModels) {
		if(gameModel.getState() == Constants.FORTIFICATION_PHASE) {
			Utility.writeLog(" ************ "+gameModel.getStateAsStringInDepth()+" : "+playerModel.getName()+" "+getStrategyString()+" ************ ");
			Vector<TerritoryModel> territories = playerModel.getOccupiedTerritories();
			for(TerritoryModel territoryModel:territories) {
				boolean hasNeighbor = false;
				for(TerritoryModel adjacent:territoryModel.getAdjacentTerritories()) {
					if(adjacent.getPlayerModel().getId() != playerModel.getId()) {
						hasNeighbor = true;
						break;
					}
				}
				if(hasNeighbor) {
					territoryModel.addArmies(territoryModel.getArmies());
					Utility.writeLog("Doubling armies on territory = "+territoryModel.getName()+
							" from "+territoryModel.getArmies()+" to "+(territoryModel.getArmies()*2));
				}
			}
		}
		gameModel.setState(Constants.CHANGE_TURN);
		return "";
	}

	@Override
	public String getStrategyString() {
		return Constants.CHEATER;
	}

	private TerritoryModel getRandomTerritory() {
		return playerModel.getOccupiedTerritories().get(
				Utility.getRandomNumber(playerModel.getOccupiedTerritories().size()));
	}

	@Override
	public void autoFortifyArmies(GameModel gameModel) {
		//do nothing
	}

}
