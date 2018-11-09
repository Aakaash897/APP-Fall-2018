package col.cs.risk.model.phase;

import java.util.Observable;

import col.cs.risk.model.Constants;
import col.cs.risk.model.GameModel;

/**
 * 
 * @author Team25 Game start phase model
 *
 */
public class StartPhaseModel extends Observable implements GamePhase {

	/** Game model */
	private GameModel gameModel;

	/** StringBuilder object */
	private StringBuilder stringBuilder;

	/** instance of this class */
	private static StartPhaseModel startPhaseModel;

	/**
	 * 
	 * @return instance of StartPhaseModel
	 */
	public static StartPhaseModel getInstance() {
		if (startPhaseModel == null) {
			startPhaseModel = new StartPhaseModel();
		}
		return startPhaseModel;
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
		return Constants.START_PHASE_MESSAGE + " / " + Constants.INITIAL_RE_ENFORCEMENT_PHASE_MESSAGE;
	}

	/**
	 * Returns the info about player
	 * 
	 * @return simple info on start
	 */
	private String basicMessage() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n************* " + getTitle() + " *************\n\n");
		stringBuilder.append("Information:\n\n");
		stringBuilder.append(basicMessage1());
		return stringBuilder.toString();
	}

	/**
	 * Returns the basic player info
	 * 
	 * @return basic player info string according to the phase
	 */
	private String basicMessage1() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Current player: ");
		stringBuilder.append(gameModel.getCurrentPlayer().getName());
		stringBuilder.append("\nNo. of armies available with player: ");
		stringBuilder.append(gameModel.getCurrentPlayer().getArmies());
		stringBuilder.append("\n\n");
		return stringBuilder.toString();
	}

	@Override
	public String getContent() {
		if (stringBuilder == null) {
			stringBuilder = new StringBuilder();
			stringBuilder.append(basicMessage());
		} else {
			if (gameModel.getSelectedTerritory() != null) {
				stringBuilder.append("Placed an army on territory: ");
				stringBuilder.append(gameModel.getSelectedTerritory().getName());
				stringBuilder.append("\n-----------------------\n\n");
			}
			stringBuilder.append(basicMessage1());
		}
		String content = stringBuilder.toString();
		return content;
	}

	@Override
	public String toString() {
		return stringBuilder.toString();
	}

	@Override
	public void setMessage(String message) {
		// TODO Auto-generated method stub

	}

}
