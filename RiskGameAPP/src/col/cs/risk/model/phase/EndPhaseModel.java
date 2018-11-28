package col.cs.risk.model.phase;

import java.io.Serializable;
import java.util.Observable;

import col.cs.risk.model.Constants;
import col.cs.risk.model.GameModel;

/**
 * EndPhaseModel class handles the scenario whenever the phase ends.
 * 
 * @author Team25
 */
public class EndPhaseModel extends Observable implements GamePhase, Serializable {
	
	/** Serial Version UID */
	private static final long serialVersionUID = 2502595744982746284L;

	/** Object of game model */
	private GameModel gameModel;

	/** StringBuilder object */
	private StringBuilder stringBuilder;

	/** instance of this class */
	private static EndPhaseModel endPhaseModel;

	/** for the player info */
	private String message;

	/**
	 * 
	 * @return instance of FortificationPhaseModel
	 */
	public static EndPhaseModel getInstance() {
		if (endPhaseModel == null) {
			endPhaseModel = new EndPhaseModel();
		}
		return endPhaseModel;
	}
	
	public static boolean isInitialized() {
		return endPhaseModel != null ? true : false;
	}
	
	public static void clear() {
		endPhaseModel = null;
	}

	/**
	 * @return the gameModel
	 */
	public GameModel getGameModel() {
		return gameModel;
	}

	@Override
	public void setGameModel(GameModel gameModel) {
		this.gameModel = gameModel;
	}

	@Override
	public void isChanged(boolean isStart) {
		if (isStart) {
			stringBuilder = null;
		}
		setChanged();
		notifyObservers(this);
	}

	@Override
	public String getTitle() {
		return Constants.END_PHASE_MESSAGE;
	}

	/**
	 * Returns the basic player info
	 * 
	 * @return basic player info string
	 */
	private String basicMessage() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n************* " + getTitle() + " *************\n\n");
		stringBuilder.append("Current player: ");
		stringBuilder.append(gameModel.getCurrentPlayer().getName());
		stringBuilder.append("\n\n");
		stringBuilder.append("Information:\n\n");
		return stringBuilder.toString();
	}

	@Override
	public String getContent() {
		if (stringBuilder == null) {
			stringBuilder = new StringBuilder();
			stringBuilder.append(basicMessage());
		}
		if(message != null) {
			stringBuilder.append(message);
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}
}
