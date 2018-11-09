package col.cs.risk.model.phase;

import java.util.Observable;

import col.cs.risk.model.Constants;
import col.cs.risk.model.GameModel;

/**
 * This class handles the entities for the fortification phase such as to show
 * the info according the actions done by the player.
 * 
 * 
 * @author Team25
 *
 */
public class FortificationPhaseModel extends Observable implements GamePhase {

	/** game model */
	private GameModel gameModel;

	/** instance of this class */
	private static FortificationPhaseModel fortificationPhaseModel;

	/** StringBuilder object */
	private StringBuilder stringBuilder;

	/**
	 * 
	 * @return instance of FortificationPhaseModel
	 */
	public static FortificationPhaseModel getInstance() {
		if (fortificationPhaseModel == null) {
			fortificationPhaseModel = new FortificationPhaseModel();
		}
		return fortificationPhaseModel;
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
		return Constants.FORTIFICATION_PHASE_MESSAGE;
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

		switch (gameModel.getState()) {
		case Constants.FORTIFICATION_PHASE:
			if (gameModel.getMoveArmiesFromTerritory() != null) {
				stringBuilder.append("Moving armies from territory: ");
				stringBuilder.append(gameModel.getMoveArmiesFromTerritory().getName());
				stringBuilder.append("\n");
			}
			break;
		case Constants.FORTIFYING_PHASE:
			if (gameModel.getMoveArmiesToTerritory() != null) {
				stringBuilder.append("To territory: ");
				stringBuilder.append(gameModel.getMoveArmiesToTerritory().getName());
				stringBuilder.append("\n");
			}
		case Constants.FORTIFY_PHASE:
			if (gameModel.getNoOfArmiesToMove() != 0) {
				stringBuilder.append("No. of armies moving: ");
				stringBuilder.append(gameModel.getNoOfArmiesToMove());
				stringBuilder.append("\n");
			}
		default:
			break;
		}
		stringBuilder.append("\n");
		return stringBuilder.toString();
	}

	@Override
	public void setMessage(String message) {
		// TODO Auto-generated method stub

	}

}
