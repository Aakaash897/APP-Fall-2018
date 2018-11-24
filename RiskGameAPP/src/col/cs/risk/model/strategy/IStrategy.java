package col.cs.risk.model.strategy;

import col.cs.risk.model.GameModel;
import col.cs.risk.model.TerritoryModel;

public interface IStrategy {
	
	public void initialReInforce(GameModel gameModel);
	
	public void reInforce(GameModel gameModel);
	
	/**
	 * User actions on attack phase actions, selection of attacking and defending
	 * territory
	 * 
	 * @param gameModel
	 * @param territoryModel
	 * @return status string
	 */
	public String attack(GameModel gameModel, TerritoryModel... territoryModels);
	
	/**
	 * Action on fortification phase for the current player
	 * 
	 * @param gameModel
	 * @param territoryModel
	 *            It is an instance of TerritoryModel that holds territories details
	 * @return status as string to display
	 */
	public String fortify(GameModel gameModel, TerritoryModel... territoryModels);
	
	public void autoFortifyArmies(GameModel gameModel);
	
	public String getStrategyString();
	
}
