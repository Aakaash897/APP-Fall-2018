package col.cs.risk.model.phase;

import java.io.Serializable;
import java.util.Observable;

import col.cs.risk.model.Constants;
import col.cs.risk.model.GameModel;

/**
 * AttackPhaseModel class is observable class observe the changes in the attack
 * phases and notifies according to that change. In this all the attack phases
 * are handled.
 * 
 * @author Team25
 *
 */
public class AttackPhaseModel extends Observable implements GamePhase, Serializable {

	/** game model */
	private GameModel gameModel;

	/** instance of this class */
	private static AttackPhaseModel attackPhaseModel;

	private StringBuilder stringBuilder;

	/**
	 * 
	 * @returns instance of AttackPhaseModel
	 */
	public static AttackPhaseModel getInstance() {
		if (attackPhaseModel == null) {
			attackPhaseModel = new AttackPhaseModel();
		}
		return attackPhaseModel;
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
		return Constants.ATTACK_PHASE_MESSAGE;
	}

	@Override
	public void setGameModel(GameModel gameModel) {
		this.gameModel = gameModel;
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
		case Constants.ATTACK_PHASE:
			if (gameModel.getCurrentPlayer().getAttackingTerritory() != null) {
				stringBuilder.append("Attacking territory: ");
				stringBuilder.append(gameModel.getCurrentPlayer().getAttackingTerritory().getName());
				stringBuilder.append(" \n");
			}
			break;
		case Constants.ATTACKING_PHASE:
			if (gameModel.getCurrentPlayer().getDefendingTerritory() != null) {
				stringBuilder.append("Defending territory: ");
				stringBuilder.append(gameModel.getCurrentPlayer().getDefendingTerritory().getName());
				stringBuilder.append(" \n");
			}
			break;
		case Constants.ATTACK_FIGHT_PHASE:
			if (gameModel.getCurrentPlayer().getGameController() != null) {
				stringBuilder.append(
						gameModel.getCurrentPlayer().getGameController().getMapView().getStatusLabel().getText());
				stringBuilder.append("\n");
			}
			break;
		case Constants.CAPTURE:
			if (gameModel.getCurrentPlayer().getGameController() != null) {
				stringBuilder.append("Capturing defending territory\n");
			}
			break;
		case Constants.LOST_BATTLE:
			if (gameModel.getCurrentPlayer().getGameController() != null) {
				stringBuilder.append("Lost the battle\n");
			}
			break;
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
