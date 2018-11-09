package col.cs.risk.model.phase;

import java.util.Observable;

import col.cs.risk.model.Constants;
import col.cs.risk.model.GameModel;

/**
 * This class handles the entities for the reinforcement phase such as to show
 * the info according the actions done by the player in the reinforcement phase.
 * 
 * @author Team25
 */
public class ReEnforcementPhaseModel extends Observable implements GamePhase {

	/** game model */
	private GameModel gameModel;

	/** instance of this class */
	private static ReEnforcementPhaseModel reEnforcementPhaseModel;

	/** StringBuilder object */
	private StringBuilder stringBuilder;

	/**
	 * 
	 * @return s instance of ReEnforcementPhaseModel
	 */
	public static ReEnforcementPhaseModel getInstance() {
		if (reEnforcementPhaseModel == null) {
			reEnforcementPhaseModel = new ReEnforcementPhaseModel();
		}
		return reEnforcementPhaseModel;
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
		return Constants.REINFORCEMENT_PHASE_MESSAGE;
	}

	/**
	 * Returns the basic player info
	 * 
	 * @return basic player info string according to the phase
	 */
	private String basicMessage() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n************* " + getTitle() + " *************\n\n");
		stringBuilder.append("Current player: ");
		stringBuilder.append(gameModel.getCurrentPlayer().getName());
		stringBuilder.append("\nNo. of armies available with player: ");
		stringBuilder.append(gameModel.getCurrentPlayer().getArmies());
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
		if (gameModel.getState() == Constants.CARD_TRADE) {
			stringBuilder.append("Card trading \n\n");
		} else {
			if (gameModel.getSelectedTerritory() != null) {
				stringBuilder.append("Placed an army on territory: ");
				stringBuilder.append(gameModel.getSelectedTerritory().getName());
				stringBuilder.append("\n\n");
			}
		}
		return stringBuilder.toString();
	}

	@Override
	public void setMessage(String message) {
		// TODO Auto-generated method stub
	}

}
