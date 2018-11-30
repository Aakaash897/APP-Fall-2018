package col.cs.risk.model.phase;

import java.io.Serializable;
import java.util.Observable;

import col.cs.risk.model.Constants;
import col.cs.risk.model.GameModel;

/**
 * This class handles the entities for the reinforcement phase such as to show
 * the info according the actions done by the player in the reinforcement phase.
 * 
 * @author Team25
 */
public class ReEnforcementPhaseModel extends Observable implements GamePhase, Serializable {

	/** Serial Version UID */
	private static final long serialVersionUID = -7703987143559944861L;

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
	 * Checks whether the instance variable initialized
	 * @returns true if initialized
	 */
	public static boolean isInitialized() {
		return reEnforcementPhaseModel != null ? true : false;
	}
	
	/**
	 * De-initializes the instance variable
	 */
	public static void clear() {
		reEnforcementPhaseModel = null;
	}

	/**
	 * @return the gameModel
	 */
	public GameModel getGameModel() {
		return gameModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setGameModel(GameModel gameModel) {
		this.gameModel = gameModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void isChanged(boolean isStart) {
		if (isStart) {
			stringBuilder = null;
		}
		setChanged();
		notifyObservers(this);
	}

	/**
	 * {@inheritDoc}
	 */
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
		stringBuilder.append(" - ");
		stringBuilder.append(gameModel.getCurrentPlayer().getStrategy().getStrategyString());
		stringBuilder.append("\nNo. of armies available with player: ");
		stringBuilder.append(gameModel.getCurrentPlayer().getArmies());
		stringBuilder.append("\n\n");
		stringBuilder.append("Information:\n\n");
		return stringBuilder.toString();
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMessage(String message) {
		// TODO Auto-generated method stub
	}

}
