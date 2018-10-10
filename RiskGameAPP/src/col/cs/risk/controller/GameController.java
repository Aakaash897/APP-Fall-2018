package col.cs.risk.controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

import col.cs.risk.model.Constants;
import col.cs.risk.model.GameModel;
import col.cs.risk.view.MapView;

/**
 * Game Model Controller
 * @author Team
 *
 */
public class GameController implements MouseListener {

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

	/**
	 * Constructor with parameters
	 * @param booleans
	 */
	public GameController(Boolean ...booleans) {
		gameModel = new GameModel(false);
		initComponents();
		new MapView(this).setVisible(true);
		gameModel.setMainMapPanel(mapMainPanel);
		gameModel.setSubMapPanel(mapSubPanelPlayer);
		mapView.setTitle("Risk Conquest Game");
		mapView.setLocationRelativeTo(null);
		mapView.setResizable(false);
		if(gameModel.getState() == Constants.INITIAL_RE_ENFORCEMENT_PHASE) {
			mapView.getStatusLabel().setText(Constants.RE_ENFORCEMENT_MESSAGE);
		}
		mapMainPanel.repaint();
		mapMainPanel.addMouseListener(this);
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
	 * @param evt
	 */
	public void attackButtonActionPerformed(ActionEvent evt) {
		gameModel.setState(Constants.ATTACK_PHASE);
		mapView.getAttackButton().setVisible(false);
		mapView.getStatusLabel().setText("Attack Phase is not yet implemented, do fortification or end your turn");
		gameModel.notifyPhaseChange();
	}

	/**
	 * Action performed on fortify button press
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
	 * @param event
	 */
	public void userEnteredDataActionPerformed(ActionEvent event) {
		JTextField txtField = (JTextField) event.getSource();
		try {
			int armies = Integer.parseInt(txtField.getText());
			if(gameModel.getMoveArmiesFromTerritory().getArmies() <= armies) {
				mapView.getStatusLabel().setText(Constants.MIN_ONE_ARMY_MESSAGE);
			} else {
				gameModel.setNoOfArmiesToMove(armies);
				gameModel.moveArmies();
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
	 * @param evt
	 */
	public void endButtonActionPerformed(ActionEvent evt) {
		System.out.println(" end button pressed state = "+gameModel.getState());
		switch(gameModel.getState()) {
		case Constants.ATTACK_PHASE:
		case Constants.RE_ENFORCEMENT_PHASE:
			validatePlayerTurn();
			break;
		case Constants.FORTIFICATION_PHASE:
		case Constants.FORTIFYING_PHASE:
		case Constants.FORTIFY_PHASE:
			gameModel.setNoOfArmiesToMove(0);
			gameModel.setMoveArmiesFromTerritory(null);
			gameModel.setMoveArmiesToTerritory(null);
			validatePlayerTurn();
			break;
		}
	}

	/**
	 * Validating the player turn
	 */
	private void validatePlayerTurn() {
		currentRoundCompletedPlayersCount++;
		if(currentRoundCompletedPlayersCount == GameModel.getPlayers().size()) {
			noOfRoundsCompleted++;
			currentRoundCompletedPlayersCount = 0;
			if(noOfRoundsCompleted > 2) {
				mapView.getStatusLabel().setText(Constants.GAME_OVER_MESSAGE);
				mapView.getAttackButton().setVisible(false);
				mapView.getFortifyButton().setVisible(false);
				mapView.getEndButton().setVisible(false);
				mapSubPanelPlayer.repaint();
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
		System.out.println(" noOfRoundsCompleted = "+noOfRoundsCompleted);
		if(isFirstRound()) {
			gameModel.setState(Constants.ATTACK_PHASE);
			mapView.getAttackButton().setVisible(false);
			mapView.getFortifyButton().setVisible(true);
			mapView.getEndButton().setVisible(true);
			mapView.getUserEntered().setVisible(false);
			mapView.getStatusLabel().setText("Attack Phase is not yet implemented, do fortification or end your turn");
		} else {
			handleReinforcement();
		}
		gameModel.notifyPhaseChange();
	}
	
	/**
	 * Checks whether its the first round
	 * @return true if yes
	 */
	private boolean isFirstRound() {
		return noOfRoundsCompleted == 0 ? true : false;
	}

	/**
	 * Launch the game controller
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
	 * {@inheritDoc}
	 *
	 */
	@Override
	public void mouseClicked(MouseEvent event) {
		System.out.println("\n\n\n------------------");
		System.out.println("Mouse clicked status = "+gameModel.getState()+", "+gameModel.getStateAsString());
		int x_coordinate = event.getX();
		int y_coordinate = event.getY();
		switch(gameModel.getState()) {
		case Constants.INITIAL_RE_ENFORCEMENT_PHASE:
		case Constants.RE_ENFORCEMENT_PHASE:
			if(gameModel.getCurrentPlayer().getArmies() > 0) {
				gameModel.gamePhasePlayerTurnSetup(x_coordinate, y_coordinate);
			} 
			break;
		case Constants.FORTIFICATION_PHASE:	
			String str = gameModel.gamePhaseActivePlayerFinalModification(x_coordinate, y_coordinate);
			if(!str.isEmpty()) {
				mapView.getStatusLabel().setText(str);
			}
			break;
		case Constants.FORTIFYING_PHASE:
			str = gameModel.gamePhaseActivePlayerFinalModification(x_coordinate, y_coordinate);
			if(!str.isEmpty()) {
				mapView.getStatusLabel().setText(str);
			}
			if(gameModel.getState() == Constants.FORTIFY_PHASE) {
				mapView.getUserEntered().setText("");
				mapView.getUserEntered().setVisible(true);
				mapView.getUserEntered().setEditable(true);
				mapView.getEndButton().setVisible(true);
			}
			break;
		}

		if(gameModel.getState() == Constants.START_TURN) {
			handleStartTurn();
		}
		
		if(gameModel.getState() == Constants.ACTIVE_TURN) {
			handleActiveTurn();
		}
		
		mapMainPanel.repaint();
		mapSubPanelPlayer.repaint();
	}

	/**
	 * Handle attack state
	 */
	private void handleAttack() {
		mapView.getStatusLabel().setText("Attack Phase is not yet implemented, do fortification or end your turn");
		mapView.getFortifyButton().setVisible(true);
		mapView.getEndButton().setVisible(true);
		mapView.getAttackButton().setVisible(false);
	}
	
	/**
	 * Handle active turn state
	 */
	private void handleActiveTurn() {
		mapView.getStatusLabel().setText(Constants.SELECT_THE_ACTION_MESSAGE);
		mapView.getAttackButton().setVisible(true);
		mapView.getFortifyButton().setVisible(true);
		mapView.getEndButton().setVisible(true);
		gameModel.setState(Constants.RE_ENFORCEMENT_PHASE);
	}
	
	/**
	 * Handle start turn state of player or before reinforcement
	 */
	private void handleStartTurn() {
		if(isFirstRound()) {
			gameModel.setState(Constants.ATTACK_PHASE);
			handleAttack();
		} else {
			handleReinforcement();
			gameModel.notifyPhaseChange();
		}
	}
	
	/**
	 * Handle reinforcement state
	 */
	private void handleReinforcement() {
		gameModel.setState(Constants.RE_ENFORCEMENT_PHASE);
		gameModel.addTurnBonusToCurrentPlayer();
		if(gameModel.getCurrentPlayer().getArmies() == 0) {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * @return the mapMainPanel
	 */
	public MapPanelController getMapMainPanel() {
		return mapMainPanel;
	}

	/**
	 * @param mapMainPanel the mapMainPanel to set
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
	 * @param mapSubPanelPlayer the mapSubPanelPlayer to set
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
	 * @param mapView the mapView to set
	 */
	public void setMapView(MapView mapView) {
		this.mapView = mapView;
	}
}
