package col.cs.risk.helper;

import col.cs.risk.controller.StartGameController;
import col.cs.risk.model.GameModel;

/**
 * The class Map exception is used to throw invalid map exception
 * i.e., if any errors in map like it is not connected.
 * 
 * @author Team25
 *
 */
public class MapException extends Exception {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the class
	 * @param errorMessage : Message passed using this
	 */
	public MapException(String errorMessage) {
		super(errorMessage);
	}
	
	/**
	 * Clears the history such as players and all map details
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
