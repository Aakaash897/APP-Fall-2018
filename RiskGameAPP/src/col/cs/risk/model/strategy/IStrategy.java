package col.cs.risk.model.strategy;

import col.cs.risk.model.GameModel;
import col.cs.risk.model.TerritoryModel;

/**
 * this interface handles all the phases of the game
 * 
 * @author Team 25
 *
 */
public interface IStrategy {

	/**
	 * this method initialize the initial reinforcement i.e. assigning armies to the
	 * territories
	 * 
	 * @param gameModel
	 */
	public void initialReInforce(GameModel gameModel);

	/**
	 * this method does the movement of the armies as well as assigning new armies
	 * 
	 * @param gameModel
	 */
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
	 * @param territoryModel It is an instance of TerritoryModel that holds
	 *                       territories details
	 * @return status as string to display
	 */
	public String fortify(GameModel gameModel, TerritoryModel... territoryModels);

	/**
	 * this method automatically fortify armies
	 * 
	 * @param gameModel
	 */
	public void autoFortifyArmies(GameModel gameModel);

	/**
	 * this method returns the which strategy the player is using
	 * 
	 * @return strategy name
	 */
	public String getStrategyString();

}
