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

	/** Serial Version UID */
	private static final long serialVersionUID = 8936770435291963614L;

	/** game model */
	private GameModel gameModel;

	/** instance of this class */
	private static AttackPhaseModel attackPhaseModel;

	/** String to update on view */
	private StringBuilder stringBuilder;

	/** for the player info */
	private String message;

	/**
	 * this method returns the instance of this class. if the instance of the class
	 * is not created it creates and returns it otherwise returns it.
	 * 
	 * @returns instance of AttackPhaseModel
	 */
	public static AttackPhaseModel getInstance() {
		if (attackPhaseModel == null) {
			attackPhaseModel = new AttackPhaseModel();
		}
		return attackPhaseModel;
	}

	/**
	 * Checks whether the instance variable initialized
	 * 
	 * @returns true if initialized
	 */
	public static boolean isInitialized() {
		return attackPhaseModel != null ? true : false;
	}

	/**
	 * De initializes the instance variable
	 */
	public static void clear() {
		attackPhaseModel = null;
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
		return Constants.ATTACK_PHASE_MESSAGE;
	}

	/**
	 * {@inheritDoc}
	 */
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
		stringBuilder.append(" - ");
		stringBuilder.append(gameModel.getCurrentPlayer().getStrategy().getStrategyString());
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
				stringBuilder.append(" ( ");
				stringBuilder.append(gameModel.getCurrentPlayer().getDefendingTerritory().getPlayerModel().getName());
				stringBuilder.append(" - ");
				stringBuilder.append(gameModel.getCurrentPlayer().getDefendingTerritory().getPlayerModel().getStrategy()
						.getStrategyString());
				stringBuilder.append(" ) \n");
			}
			break;
		case Constants.ATTACK_FIGHT_PHASE:
			if (Constants.ATTACK_DICE_SELECTION.equals(message)) {
				message = "";
				stringBuilder.append("Attacking no of dice: " + gameModel.getCurrentPlayer().getAttackingNoOfDice());
				stringBuilder.append("\n");
			} else if (Constants.DEFEND_DICE_SELECTION.equals(message)) {
				message = "";
				stringBuilder.append("Defending no of dice: " + gameModel.getCurrentPlayer().getDefendingNoOfDice());
				stringBuilder.append("\n");
			} else if (Constants.SHOW_DICE_SELECTION.equals(message)) {
				message = "";
				for (Integer i : gameModel.getCurrentPlayer().getAttackingDiceList().keySet()) {
					stringBuilder.append("Attacking dice no: " + i + ",  & rolled phase is: "
							+ gameModel.getCurrentPlayer().getAttackingDiceList().get(i));
					stringBuilder.append("\n");
				}
				stringBuilder.append("\n");
				for (Integer i : gameModel.getCurrentPlayer().getDefendingDiceList().keySet()) {
					stringBuilder.append("Defending dice no: " + i + ",  & rolled phase is: "
							+ gameModel.getCurrentPlayer().getDefendingDiceList().get(i));
					stringBuilder.append("\n");
				}
			} else if (gameModel.getCurrentPlayer().getGameController() != null) {
				stringBuilder.append(
						gameModel.getCurrentPlayer().getGameController().getMapView().getStatusLabel().getText());
				stringBuilder.append("\n");
			}
			break;
		case Constants.CAPTURE:
			if (Constants.MOVING_ARMIES.equals(message)) {
				message = "";
				stringBuilder.append("Moving " + gameModel.getNoOfArmiesToMove());
				stringBuilder.append(" armies to newly captured territory\n");
				stringBuilder.append("----------------------------------\n");
			} else if (gameModel.getCurrentPlayer().getGameController() != null) {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMessage(String message) {
		this.message = message;
	}
}
