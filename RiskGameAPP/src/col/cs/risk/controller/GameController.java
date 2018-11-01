package col.cs.risk.controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

import col.cs.risk.helper.MapException;
import col.cs.risk.helper.Utility;
import col.cs.risk.model.Constants;
import col.cs.risk.model.GameModel;
import col.cs.risk.view.MapView;

/**
 * Game Controller
 * 
 * This is the Main Driver of the controller. This includes all the phases of game. This
 * class handles the main game functionalities like changePlayerTurn,
 * handleAttack, endGame etc.
 * 
 * @author Team25
 *
 */
public class GameController {

	/** Game model instance */
	private GameModel gameModel;

	/** Game map panel */
	private MapPanelController mapMainPanel;

	/** Game player panel */
	private PlayerPanelController mapSubPanelPlayer;

	/** Game panel */
	private MapView mapView;

	/** No of players completed the current round */
	private int currentRoundCompletedPlayersCount;

	/** No of rounds/turns completed */
	private int noOfRoundsCompleted;

	/** Maximum number of rounds allowed per game */
	private static int MAXIMUM_NO_OF_ROUNDS_ALLOWED = Constants.SIX;

	/**
	 * Constructor to initialize the 
	 * Jpanel
	 * 
	 * @param booleans
	 */
	public GameController(Boolean... booleans) {
		try {
			gameModel = new GameModel(false);
			initComponents();
			new MapView(this).setVisible(true);
			gameModel.setMainMapPanel(mapMainPanel);
			gameModel.setSubMapPanel(mapSubPanelPlayer);
			mapView.setTitle("Risk Conquest Game");
			mapView.setLocationRelativeTo(null);
			mapView.setResizable(false);
			if (gameModel.getState() == Constants.INITIAL_RE_ENFORCEMENT_PHASE) {
				mapView.getStatusLabel().setText(Constants.RE_ENFORCEMENT_MESSAGE);
			}
			mapMainPanel.repaint();
		} catch (MapException ex) {
			System.out.println(ex.getMessage());
			ex.clearHistory();
		} catch (Exception ex) {
			System.out.println("Exception: "+ex.getMessage());
		}
	}

	/**
	 * Initialize components
	 */
	private void initComponents() {
		mapMainPanel = new MapPanelController(gameModel);
		mapSubPanelPlayer = new PlayerPanelController(gameModel);
	}

	/**
	 * Action performed on attack button press
	 * 
	 * @param evt
	 */
	public void attackButtonActionPerformed(ActionEvent evt) {
		gameModel.setState(Constants.ATTACK_PHASE);
		mapView.getAttackButton().setVisible(false);
		if(gameModel.getCurrentPlayer().canAttack()) {
			mapView.getStatusLabel().setText(Constants.ATTACK_COUNTRY_SELECT_MESSAGE);
		} else {
			mapView.getStatusLabel().setText(Constants.CANNOT_ATTACK_MESSAGE+Constants.SELECT_THE_ACTION_MESSAGE);
		}
		gameModel.notifyPhaseChange();
	}

	/**
	 * Action performed on fortify button press
	 * 
	 * @param evt
	 */
	public void fortifyButtonActionPerformed(ActionEvent evt) {
		System.out.println(" fortify button pressed ");
		gameModel.setState(Constants.FORTIFICATION_PHASE);
		mapView.getStatusLabel().setText(Constants.MOVE_FROM);
		mapView.getAttackButton().setVisible(false);
		mapView.getFortifyButton().setVisible(false);
		mapView.getEndButton().setVisible(true);
		gameModel.notifyPhaseChange();
	}

	/**
	 * Action performed on user entered value to move armies on fortification phase
	 * 
	 * @param event
	 */
	public void userEnteredDataActionPerformed(ActionEvent event) {
		JTextField txtField = (JTextField) event.getSource();
		try {
			int armies = Integer.parseInt(txtField.getText());
			if (gameModel.getMoveArmiesFromTerritory().getArmies() <= armies) {
				mapView.getStatusLabel().setText(Constants.MIN_ONE_ARMY_MESSAGE);
			} else {
				gameModel.setNoOfArmiesToMove(armies);
				if(gameModel.moveArmies()) {
					gameModel.setNoOfArmiesToMove(Constants.ZERO);
					gameModel.setMoveArmiesFromTerritory(null);
					gameModel.setMoveArmiesToTerritory(null);
				}
				validatePlayerTurn();
			}
		} catch (NumberFormatException exception) {
			mapView.getStatusLabel().setText(Constants.VALID_DIGIT_MESSAGE);
		}
		mapSubPanelPlayer.repaint();
		mapMainPanel.repaint();
	}

	/**
	 * Action performed on end button press
	 * 
	 * @param evt
	 */
	public void endButtonActionPerformed(ActionEvent evt) {
		System.out.println(" end button pressed state = " + gameModel.getState());
		switch (gameModel.getState()) {
		case Constants.ATTACK_PHASE:
		case Constants.RE_ENFORCEMENT_PHASE:
			validatePlayerTurn();
			break;
		case Constants.FORTIFICATION_PHASE:
		case Constants.FORTIFYING_PHASE:
		case Constants.FORTIFY_PHASE:
			gameModel.setNoOfArmiesToMove(Constants.ZERO);
			gameModel.setMoveArmiesFromTerritory(null);
			gameModel.setMoveArmiesToTerritory(null);
			validatePlayerTurn();
			break;
		}
	}

	/**
	 * Validating the player turn
	 */
	public void validatePlayerTurn() {
		currentRoundCompletedPlayersCount++;
		if (currentRoundCompletedPlayersCount == GameModel.getPlayers().size()) {
			noOfRoundsCompleted++;
			currentRoundCompletedPlayersCount = Constants.ZERO;
			if (noOfRoundsCompleted >= MAXIMUM_NO_OF_ROUNDS_ALLOWED) {
				gameOver(Constants.GAME_OVER_MESSAGE);
			} else {
				changeTurn();
			}
		} else {
			changeTurn();
		}
	}

	/**
	 * Changing the current player
	 */
	private void changeTurn() {
		gameModel.nextPlayer();
		System.out.println(" noOfRoundsCompleted = " + noOfRoundsCompleted);
		if (isFirstRound()) {
			handleAttack();
		} else {
			handleReinforcement();
		}
		gameModel.notifyPhaseChange();
	}

	/**
	 * Checks whether its the first round
	 * 
	 * @return true if yes
	 */
	private boolean isFirstRound() {
		return noOfRoundsCompleted == Constants.ZERO ? true : false;
	}

	public void gameOver(String message) {
		mapView.getStatusLabel().setText(message);
		mapView.getAttackButton().setVisible(false);
		mapView.getFortifyButton().setVisible(false);
		mapView.getEndButton().setVisible(false);
		mapSubPanelPlayer.repaint();
		Utility.showMessagePopUp(message, "Information");
	}

	/**
	 * Launch the game controller class
	 */
	public static void showGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GameController(false);
			}
		});
	}

	/**
	 * Action performed on mouse click on the map
	 * 
	 * @param event
	 *
	 */
	public void mouseClicked(MouseEvent event) {
		System.out.println("\n\n\n------------------");
		System.out.println("Mouse clicked status = " + gameModel.getState() + ", " + gameModel.getStateAsString());
		int x_coordinate = event.getX();
		int y_coordinate = event.getY();
		switch (gameModel.getState()) {
		case Constants.INITIAL_RE_ENFORCEMENT_PHASE:
		case Constants.RE_ENFORCEMENT_PHASE:
			if (gameModel.getCurrentPlayer().getArmies() > Constants.ZERO) {
				gameModel.gamePhasePlayerTurnSetup(x_coordinate, y_coordinate);
			}
			break;
		case Constants.FORTIFICATION_PHASE:
		case Constants.FORTIFYING_PHASE:
			String str = gameModel.gamePhaseActivePlayerActions(x_coordinate, y_coordinate);
			if (!str.isEmpty()) {
				mapView.getStatusLabel().setText(str);
			}
			if (gameModel.getState() == Constants.FORTIFY_PHASE) {
				mapView.getUserEntered().setText("");
				mapView.getUserEntered().setVisible(true);
				mapView.getUserEntered().setEditable(true);
				mapView.getEndButton().setVisible(true);
			}
			break;
		case Constants.ATTACK_PHASE:
		case Constants.ATTACKING_PHASE:
			str = gameModel.gamePhaseActivePlayerActions(x_coordinate, y_coordinate);
			if (!str.isEmpty()) {
				mapView.getStatusLabel().setText(str);
			}
			break;
		}

		if(gameModel.getState() == Constants.ATTACK_FIGHT_PHASE) {
			gameModel.getCurrentPlayer().startBattle(gameModel, mapView, this);
		}

		if (gameModel.getState() == Constants.START_TURN) {
			handleStartTurn();
		}

		if (gameModel.getState() == Constants.ACTIVE_TURN) {
			handleActiveTurn();
		}
		mapMainPanel.repaint();
		mapSubPanelPlayer.repaint();
	}

	/**
	 * This function sets the console output upon
	 * entering the Attack Phase
	 */
	public void handleAttack() {
		gameModel.setState(Constants.ATTACK_PHASE);
		if(gameModel.getCurrentPlayer().canAttack()) {
			mapView.getStatusLabel().setText(Constants.ATTACK_COUNTRY_SELECT_MESSAGE);
		} else {
			mapView.getStatusLabel().setText(Constants.CANNOT_ATTACK_MESSAGE+Constants.SELECT_THE_ACTION_MESSAGE);
		}
		mapView.getFortifyButton().setVisible(true);
		mapView.getEndButton().setVisible(true);
		mapView.getAttackButton().setVisible(false);
		mapView.getUserEntered().setVisible(false);
	}

	/**
	 * Handle active turn state of
	 * the risk game i.e once all the player armies are placed on the territories
	 * but still it will be in reinforcement phase still next user action
	 */
	private void handleActiveTurn() {
		gameModel.setState(Constants.RE_ENFORCEMENT_PHASE);
		mapView.getStatusLabel().setText(Constants.SELECT_THE_ACTION_MESSAGE);
		mapView.getAttackButton().setVisible(true);
		mapView.getFortifyButton().setVisible(true);
		mapView.getEndButton().setVisible(true);
	}

	/**
	 * Handle start turn state of player or before reinforcement i.e after fortification
	 */
	private void handleStartTurn() {
		if (isFirstRound()) {
			handleAttack();
		} else {
			handleReinforcement();
			gameModel.notifyPhaseChange();
		}
	}

	/**
	 * Handles the reinforcement state
	 * of the game
	 */
	private void handleReinforcement() {
		if(gameModel.getCurrentPlayer().getOccupiedTerritories().isEmpty()) {
			validatePlayerTurn();
		} else {
			gameModel.setState(Constants.RE_ENFORCEMENT_PHASE);
			gameModel.getCurrentPlayer().addTurnBonus(gameModel);
			if (gameModel.getCurrentPlayer().getArmies() == Constants.ZERO) {
				mapView.getStatusLabel().setText(Constants.SELECT_THE_ACTION_MESSAGE);
				mapView.getAttackButton().setVisible(true);
				mapView.getFortifyButton().setVisible(true);
				mapView.getEndButton().setVisible(true);
				mapView.getUserEntered().setVisible(false);
			} else {
				mapView.getStatusLabel().setText(Constants.RE_ENFORCEMENT_MESSAGE);
				mapView.getAttackButton().setVisible(false);
				mapView.getFortifyButton().setVisible(false);
				mapView.getEndButton().setVisible(false);
				mapView.getUserEntered().setVisible(false);
			}
		}
	}

	/**
	 * @return the mapMainPanel
	 */
	public MapPanelController getMapMainPanel() {
		return mapMainPanel;
	}

	/**
	 * @param mapMainPanel
	 *            the mapMainPanel to set
	 */
	public void setMapMainPanel(MapPanelController mapMainPanel) {
		this.mapMainPanel = mapMainPanel;
	}

	/**
	 * @return the mapSubPanelPlayer
	 */
	public PlayerPanelController getMapSubPanelPlayer() {
		return mapSubPanelPlayer;
	}

	/**
	 * @param mapSubPanelPlayer
	 *            the mapSubPanelPlayer to set
	 */
	public void setMapSubPanelPlayer(PlayerPanelController mapSubPanelPlayer) {
		this.mapSubPanelPlayer = mapSubPanelPlayer;
	}

	/**
	 * @return the mapView
	 */
	public MapView getMapView() {
		return mapView;
	}

	/**
	 * @param mapView
	 *            the mapView to set
	 */
	public void setMapView(MapView mapView) {
		this.mapView = mapView;
	}
}
