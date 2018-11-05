package col.cs.risk.model.phase;

import col.cs.risk.model.GameModel;

/**
 * 
 * @author Team25
 * Game phase Maintenance
 *
 */
public interface GamePhase {
	
	/**
	 * To check the whether game phase changed
	 * @param isStart
	 */
	public void isChanged(boolean isStart);
	
	/**
	 * 
	 * @return s the title of the phase
	 */
	public String getTitle();
	
	/**
	 * Sets the game model object
	 * @param gameModel
	 */
	public void setGameModel(GameModel gameModel);
	
	/**
	 * Set Message of a model
	 * @param message
	 */
	public void setMessage(String message);
	
	/** Get content to display on phase view*/
	public String getContent();

}
