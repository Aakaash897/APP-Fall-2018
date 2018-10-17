package col.cs.risk.helper;

import col.cs.risk.controller.StartGameController;
import col.cs.risk.model.GameModel;

/**
 * Used to throw invalid map exception i.e map is not connected
 * @author Team
 *
 */
public class MapException extends Exception{

	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param errorMessage
	 */
	public MapException(String errorMessage) {
		super(errorMessage);
	}
	
	/**
	 * Clears the history such as players and map details
	 */
	public void clearHistory() {
		GameModel.players.removeAllElements();
		GameModel.players.removeAllElements();
		GameModel.isBaseMapModified = false;
		GameModel.fileName = "World.map";
		new StartGameController();
		Utility.showPopUp(getMessage());
	}
}
