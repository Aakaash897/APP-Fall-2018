package col.cs.risk.controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JTextField;

import col.cs.risk.helper.MapException;
import col.cs.risk.helper.Utility;
import col.cs.risk.model.CardExchangeModel;
import col.cs.risk.model.CardModel;
import col.cs.risk.model.Constants;
import col.cs.risk.model.GameModel;
import col.cs.risk.model.phase.AttackPhaseModel;
import col.cs.risk.model.phase.EndPhaseModel;
import col.cs.risk.model.phase.FortificationPhaseModel;
import col.cs.risk.model.phase.ReEnforcementPhaseModel;
import col.cs.risk.model.phase.StartPhaseModel;
import col.cs.risk.model.strategy.Human;
import col.cs.risk.view.CardTradeView;
import col.cs.risk.view.MapView;
import col.cs.risk.view.PhaseView;
import col.cs.risk.view.RolledDiceView;

/**
 * Game Controller
 * 
 * This is the Main Driver of the controller. This includes all the phases of
 * game. This class handles the main game functionalities like changePlayerTurn,
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
	public int noOfRoundsCompleted;

	public boolean isMaxNumberOfRoundsSet = true;

	/** Maximum number of rounds allowed per game */
	private static int MAXIMUM_NO_OF_ROUNDS_ALLOWED = Constants.TEN;

	/**
	 * Card exchange view
	 */
	private CardTradeView cardTradeView;

	/** view for displaying rolled dices list */
	private RolledDiceView rolledDiceView;

	private boolean isGameOver = false;

	private boolean isAutoRunning = false;

	/**
	 * Default constructor
	 */
	public GameController() {
		this.gameModel = new GameModel();
	}

	/**
	 * Initialize the Game controller
	 */
	public void initialize() {
		try {
			Utility.writeLog("********************* Game start up phase ***************************");
			gameModel.initialize();
			initComponents();
			new MapView(this).setVisible(true);
			initializePhaseView();
			gameModel.setMainMapPanel(mapMainPanel);
			gameModel.setSubMapPanel(mapSubPanelPlayer);
			mapView.setTitle("Risk Conquest Game");
			mapView.setLocationRelativeTo(null);
			mapView.setResizable(false);
			if (gameModel.getState() == Constants.INITIAL_RE_ENFORCEMENT_PHASE) {
				mapView.getStatusLabel().setText(Constants.RE_ENFORCEMENT_MESSAGE);
			}
			gameModel.notifyPhaseChanging();
			mapMainPanel.repaint();
			initializeCardExchangeView();
			rolledDiceView = new RolledDiceView(this);
			isGameOver = false;
			isAutoRunning = false;
			// isMaxNumberOfRoundsSet = false;
			Utility.writeLog("******************* Initial Reinforcement phase ************************");
			automaticHandleStrategies();
		} catch (MapException ex) {
			System.out.println(ex.getMessage());
			ex.clearHistory();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception: " + ex.getMessage());
		}
	}

	public void automaticHandleStrategies() {
		while (!(gameModel.getCurrentPlayer().getStrategy() instanceof Human) && !isGameOver) {
			gameModel.notifyPhaseChange();
			isAutoRunning = true;
			System.out.println("\n\n--------------------------");
			System.out.println(
					" gameModel.getState() = " + gameModel.getState() + " as string = " + gameModel.getStateAsString());
			System.out.println(" player = " + gameModel.getCurrentPlayer().getName() + " as "
					+ gameModel.getCurrentPlayer().getStrategy().getStrategyString());
			String str = "";
			switch (gameModel.getState()) {
			case Constants.INITIAL_RE_ENFORCEMENT_PHASE:
				if (gameModel.getCurrentPlayer().getArmies() > Constants.ZERO) {
					gameModel.getCurrentPlayer().initialReinforce(gameModel);
				} else {
					gameModel.setState(Constants.ATTACK_PHASE);
				}
				break;
			case Constants.RE_ENFORCEMENT_PHASE:
				if (mapView.getCardButton().isVisible()) {
					mapView.getCardButton().setVisible(false);
				}
				if (gameModel.getCurrentPlayer().getArmies() > Constants.ZERO) {
					gameModel.getCurrentPlayer().reinforce(gameModel);
				} else {
					gameModel.setState(Constants.ATTACK_PHASE);
				}
				break;
			case Constants.CARD_TRADE:
				Utility.writeLog(" ************ " + gameModel.getStateAsStringInDepth() + " of "
						+ gameModel.getCurrentPlayer().getName() + " "
						+ gameModel.getCurrentPlayer().getStrategy().getStrategyString() + " ************ ");
				if (gameModel.getCurrentPlayer().isCardTradeMandatory()) {
					handleAutomaticCardTrade();
				} else {
					handleReinforcement1();
				}
				break;
			case Constants.START_TURN:
				Utility.writeLog("************** Turn start/Reinforcement of " + gameModel.getCurrentPlayer().getName()
						+ " : " + gameModel.getCurrentPlayer().getStrategy().getStrategyString()
						+ " ********************");
				Utility.writeLog(
						"Occupied territories count = " + gameModel.getCurrentPlayer().getOccupiedTerritories().size());
				Utility.writeLog("% of occupied = "
						+ gameModel.getCurrentPlayer().calculatePercentage(gameModel.getCurrentPlayer(), gameModel));
				Utility.writeLog("Round no. of player: " + noOfRoundsCompleted);

				handleStartTurn();
				break;
			case Constants.ACTIVE_TURN:
				gameModel.setState(Constants.ATTACK_PHASE);
				break;
			case Constants.ATTACK_PHASE:
			case Constants.ATTACKING_PHASE:
				str = gameModel.getCurrentPlayer().attack(gameModel);
				break;
			case Constants.ATTACK_FIGHT_PHASE:
				gameModel.setSelectedTerritory(null);
				gameModel.getCurrentPlayer().setAutomatic(true);
				gameModel.getCurrentPlayer().startBattle(gameModel, this);
				break;
			case Constants.FORTIFICATION_PHASE:
			case Constants.FORTIFYING_PHASE:
				str = gameModel.getCurrentPlayer().fortify(gameModel);
				break;
			case Constants.FORTIFY_PHASE:
				gameModel.getCurrentPlayer().autoFortifyArmies(gameModel);
				break;
			case Constants.CHANGE_TURN:
				validatePlayerTurn();
				break;
			case Constants.END_PHASE:
				if (isGameOver) {
					break;
				} else {
					if (gameModel.isWon()) {
						gameOver(Utility.replacePartInMessage(Constants.WINNER, Constants.CHAR_A,
								(gameModel.getCurrentPlayer().getName() + " : "
										+ gameModel.getCurrentPlayer().getStrategy().getStrategyString())));
					} else {
						gameOver(Constants.GAME_OVER_MESSAGE);
					}
				}
				isGameOver = true;
				break;
			default:
				break;
			}
			if (!str.isEmpty()) {
				mapView.getStatusLabel().setText(str);
			}
			if (!isGameOver) {
				gameModel.notifyPhaseChanging();
			}
			gameModel.notifyPhaseChange();
		}
		isAutoRunning = false;

		if ((gameModel.getCurrentPlayer().getStrategy() instanceof Human) && !isGameOver) {
			if (gameModel.getState() == Constants.START_TURN) {
				gameModel.setSelectedTerritory(null);
				handleStartTurn();
				gameModel.notifyPhaseChange();
			}
		}
	}

	public void checkAndRunAuto() {
		System.out.println("GameController.checkAndRunAuto() isAutoRunning = " + isAutoRunning);
		System.out.println("gameModel.getCurrentPlayer().isHuman() = " + gameModel.getCurrentPlayer().isHuman());
		if (!gameModel.getCurrentPlayer().isHuman() && !isAutoRunning) {
			automaticHandleStrategies();
		}
	}

	private void handleAutomaticCardTrade() {
		ArrayList<CardModel> infantry = new ArrayList<>();
		ArrayList<CardModel> cavalry = new ArrayList<>();
		ArrayList<CardModel> artillery = new ArrayList<>();
		ArrayList<CardModel> wild = new ArrayList<>();
		for (CardModel card : gameModel.getCurrentPlayer().getCards()) {
			switch (card.getType()) {
			case Constants.ARMY_TYPE_INFANTRY:
				infantry.add(card);
				break;
			case Constants.ARMY_TYPE_CAVALRY:
				cavalry.add(card);
				break;
			case Constants.ARMY_TYPE_ARTILLERY:
				artillery.add(card);
				break;
			case Constants.ARMY_TYPE_WILD:
				wild.add(card);
				break;
			}
		}

		int infantryCount = 0;
		int cavelryCount = 0;
		int artilleryCount = 0;
		int wildCount = 0;

		if (infantry.size() >= 3) {
			infantryCount = 3;
		} else if (cavalry.size() >= 3) {
			cavelryCount = 3;
		} else if (artillery.size() >= 3) {
			artilleryCount = 3;
		} else if (infantry.size() >= 2 && wild.size() > 0) {
			infantryCount = 2;
			wildCount = 1;
		} else if (cavalry.size() >= 2 && wild.size() > 0) {
			cavelryCount = 2;
			wildCount = 1;
		} else if (artillery.size() >= 2 && wild.size() > 0) {
			artilleryCount = 2;
			wildCount = 1;
		} else if (infantry.size() >= 1 && artillery.size() >= 1 && wild.size() > 0) {
			infantryCount = 1;
			artilleryCount = 1;
			wildCount = 1;
		} else if (infantry.size() >= 1 && cavalry.size() >= 1 && wild.size() > 0) {
			infantryCount = 1;
			cavelryCount = 1;
			wildCount = 1;
		} else if (artillery.size() >= 1 && cavalry.size() >= 1 && wild.size() > 0) {
			cavelryCount = 1;
			artilleryCount = 1;
			wildCount = 1;
		} else if (infantry.size() >= 1 && artillery.size() >= 1 && cavalry.size() >= 1) {
			infantryCount = 1;
			cavelryCount = 1;
			artilleryCount = 1;
		}

		gameModel.getCurrentPlayer().cardTradeActionPerformed(gameModel, infantryCount, cavelryCount, artilleryCount,
				wildCount);
		handleReinforcement1();

	}

	/**
	 * Initialize components
	 */
	private void initComponents() {
		mapMainPanel = new MapPanelController(gameModel);
		mapSubPanelPlayer = new PlayerPanelController(gameModel);
	}

	/**
	 * Initialize phase view, phase view observer pattern
	 */
	private void initializePhaseView() {
		StartPhaseModel startPhaseModel = StartPhaseModel.getInstance();
		ReEnforcementPhaseModel reInforcementPhaseModel = ReEnforcementPhaseModel.getInstance();
		AttackPhaseModel attackPhaseModel = AttackPhaseModel.getInstance();
		FortificationPhaseModel fortificationPhaseModel = FortificationPhaseModel.getInstance();
		EndPhaseModel endPhaseModel = EndPhaseModel.getInstance();

		PhaseView phaseView = PhaseView.getInstance();

		startPhaseModel.addObserver(phaseView);
		reInforcementPhaseModel.addObserver(phaseView);
		attackPhaseModel.addObserver(phaseView);
		fortificationPhaseModel.addObserver(phaseView);
		endPhaseModel.addObserver(phaseView);

		gameModel.setStartPhaseModel(startPhaseModel);
		gameModel.setReInforcementPhaseModel(reInforcementPhaseModel);
		gameModel.setAttackPhaseModel(attackPhaseModel);
		gameModel.setFortificationPhaseModel(fortificationPhaseModel);
		gameModel.setEndPhaseModel(endPhaseModel);

		gameModel.setPhaseView(phaseView);
		phaseView.showMonitor();
	}

	/**
	 * Initialize card exchange view, used as observer pattern
	 */
	private void initializeCardExchangeView() {
		CardExchangeModel cardExchangeModel = CardExchangeModel.getInstance();
		cardTradeView = new CardTradeView(this);
		cardExchangeModel.addObserver(cardTradeView);
		cardTradeView.initializeComponents();
	}

	/**
	 * Action performed on attack button press
	 * 
	 * @param evt
	 */
	public void attackButtonActionPerformed(ActionEvent evt) {
		if (gameModel.getState() == Constants.CARD_TRADE) {
			return;
		}
		gameModel.setState(Constants.ATTACK_PHASE);
		gameModel.notifyPhaseChanging();
		mapView.getCardButton().setVisible(false);
		mapView.getSaveButton().setVisible(false);
		mapView.getAttackButton().setVisible(false);
		if (gameModel.getCurrentPlayer().canAttack()) {
			mapView.getStatusLabel().setText(Constants.ATTACK_COUNTRY_SELECT_MESSAGE);
		} else if (gameModel.getCurrentPlayer().canFortify()) {
			if (gameModel.getCurrentPlayer().isHuman()) {
				Utility.showMessagePopUp(Constants.CANNOT_ATTACK_MESSAGE, Constants.INFORMATION);
			}
			fortifyButtonActionPerformed(null);
		} else {
			if (gameModel.getCurrentPlayer().isHuman()) {
				Utility.showMessagePopUp(Constants.CANNOT_ATTACK_MESSAGE + Constants.FORTIFY_MESSAGE,
						Constants.INFORMATION);
			}
			mapView.getStatusLabel().setText(Constants.CANNOT_ATTACK_MESSAGE + Constants.SELECT_THE_ACTION_MESSAGE);
			validatePlayerTurn();
		}
		gameModel.notifyPhaseChange();
	}

	/**
	 * Action performed on fortify button press
	 * 
	 * @param evt
	 */
	public void fortifyButtonActionPerformed(ActionEvent evt) {
		if (gameModel.getState() == Constants.CARD_TRADE) {
			return;
		}
		gameModel.getCurrentPlayer().setCardAssigned(false);
		System.out.println(" fortify button pressed ");
		gameModel.setState(Constants.FORTIFICATION_PHASE);
		gameModel.notifyPhaseChanging();
		mapView.getStatusLabel().setText(Constants.MOVE_FROM);
		mapView.getCardButton().setVisible(false);
		mapView.getAttackButton().setVisible(false);
		mapView.getFortifyButton().setVisible(false);
		mapView.getSaveButton().setVisible(false);
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
				if (gameModel.moveArmies()) {
					Utility.writeLog(
							"Moving " + armies + " armies from " + gameModel.getMoveArmiesToTerritory().getName()
									+ " to " + gameModel.getMoveArmiesFromTerritory().getName());
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
	 * Action performed on card button pressed
	 * 
	 * @param event
	 */
	public void cardButtonActionPerformed(ActionEvent event) {
		Utility.writeLog("Card button pressed");
		CardExchangeModel.getInstance().checkCardsTradeOption(this, true);
	}

	/**
	 * Action performed on end button press
	 * 
	 * @param evt
	 */
	public void endButtonActionPerformed(ActionEvent evt) {
		Utility.writeLog("End button pressed");
		gameModel.getCurrentPlayer().setCardAssigned(false);
		System.out.println(" end button pressed state = " + gameModel.getState());
		if (gameModel.getPreviousState() == gameModel.getState()) {
			gameModel.setPreviousState(Constants.NEW_GAME);
		}
		switch (gameModel.getState()) {
		case Constants.ATTACK_PHASE:
		case Constants.ATTACKING_PHASE:
			gameModel.getCurrentPlayer().clear();
		case Constants.RE_ENFORCEMENT_PHASE:
			validatePlayerTurn();
			mapView.getSaveButton().setVisible(true);
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
			if (isMaxNumberOfRoundsSet && noOfRoundsCompleted >= MAXIMUM_NO_OF_ROUNDS_ALLOWED) {
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
		Utility.writeLog("================= Changing player(current player) turn =====================");
		gameModel.getCurrentPlayer().setCardAssigned(false);
		gameModel.nextPlayer();
		Utility.writeLog("************** Turn start/Reinforcement of " + gameModel.getCurrentPlayer().getName() + " : "
				+ gameModel.getCurrentPlayer().getStrategy().getStrategyString() + " ********************");
		Utility.writeLog(
				"Occupied territories count = " + gameModel.getCurrentPlayer().getOccupiedTerritories().size());
		Utility.writeLog("% of occupied = "
				+ gameModel.getCurrentPlayer().calculatePercentage(gameModel.getCurrentPlayer(), gameModel));
		Utility.writeLog("Round no. of player: " + (noOfRoundsCompleted + 1));
		gameModel.getCurrentPlayer().setCardAssigned(false);
		System.out.println(" noOfRoundsCompleted = " + noOfRoundsCompleted);
		if (isFirstRound()) {
			handleAttack();
		} else {
			handleReinforcement();
		}
		gameModel.notifyPhaseChange();
		checkAndRunAuto();
	}

	/**
	 * Checks whether its the first round
	 * 
	 * @return true if yes
	 */
	public boolean isFirstRound() {
		return noOfRoundsCompleted == Constants.ZERO ? true : false;
	}

	/**
	 * Handles game over functionality
	 * 
	 * @param message to display
	 */
	public void gameOver(String message) {
		if (!isGameOver) {
			gameModel.setState(Constants.END_PHASE);
			gameModel.notifyPhaseChanging(message);
			mapView.getStatusLabel().setText(message);
			mapView.getAttackButton().setVisible(false);
			mapView.getFortifyButton().setVisible(false);
			mapView.getEndButton().setVisible(false);
			mapView.getSaveButton().setVisible(false);
			mapView.getCardButton().setVisible(false);
			gameModel.notifyPhaseChange();
			isGameOver = true;
			Utility.writeLog(" ************ " + gameModel.getStateAsStringInDepth() + " ************* ");
			Utility.writeLog(message);
			Utility.showMessagePopUp(message, Constants.INFORMATION);
		}
	}

	/**
	 * Launch the game controller class
	 */
	public static void showGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GameController().initialize();
				;
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
		Utility.writeLog(
				"Mouse clicked current status = " + gameModel.getState() + ", " + gameModel.getStateAsStringInDepth());
		int x_coordinate = event.getX();
		int y_coordinate = event.getY();
		switch (gameModel.getState()) {
		case Constants.INITIAL_RE_ENFORCEMENT_PHASE:
			if (gameModel.getCurrentPlayer().getArmies() > Constants.ZERO) {
				gameModel.getTerritoryFromMapLocation(x_coordinate, y_coordinate);
				gameModel.getCurrentPlayer().initialReinforce(gameModel);
			}
			break;
		case Constants.RE_ENFORCEMENT_PHASE:
			if (mapView.getCardButton().isVisible()) {
				mapView.getCardButton().setVisible(false);
			}
			if (mapView.getSaveButton().isVisible()) {
				mapView.getSaveButton().setVisible(false);
			}
			if (gameModel.getCurrentPlayer().getArmies() > Constants.ZERO) {
				gameModel.getTerritoryFromMapLocation(x_coordinate, y_coordinate);
				gameModel.getCurrentPlayer().reinforce(gameModel);
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
				mapView.getSaveButton().setVisible(false);
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
		if (gameModel.getState() == Constants.ATTACK_FIGHT_PHASE) {
			gameModel.setSelectedTerritory(null);
			updateAutomaticMode();
			gameModel.getCurrentPlayer().startBattle(gameModel, this);
		}

		if (gameModel.getState() == Constants.START_TURN) {
			gameModel.setSelectedTerritory(null);
			handleStartTurn();
		}

		if (gameModel.getState() == Constants.ACTIVE_TURN) {
			gameModel.setSelectedTerritory(null);
			handleActiveTurn();
		}
		mapMainPanel.repaint();
		mapSubPanelPlayer.repaint();
		checkAndRunAuto();
	}

	/**
	 * Update automatic roll of dice or all out mode
	 */
	private void updateAutomaticMode() {
		String[] options = { Constants.OK, Constants.CANCEL };
		String option = mapView.showOptionPopup(Constants.AUTOMATIC_OR_ALL_OUT_MODE, options);
		boolean automatic = option.equals(Constants.OK) ? true : false;
		gameModel.getCurrentPlayer().setAutomatic(automatic);
	}

	/**
	 * This function sets the console output upon entering the Attack Phase
	 */
	public void handleAttack() {
		gameModel.setState(Constants.ATTACK_PHASE);
		gameModel.notifyPhaseChanging();
		if (gameModel.getCurrentPlayer().canAttack()) {
			mapView.getStatusLabel().setText(Constants.ATTACK_COUNTRY_SELECT_MESSAGE);
			mapView.getCardButton().setVisible(false);
			mapView.getFortifyButton().setVisible(true);
			mapView.getEndButton().setVisible(true);
			mapView.getAttackButton().setVisible(false);
			mapView.getSaveButton().setVisible(false);
			mapView.getUserEntered().setVisible(false);
		} else if (gameModel.getCurrentPlayer().canFortify()) {
			if (gameModel.getCurrentPlayer().isHuman()) {
				Utility.showMessagePopUp(Constants.CANNOT_ATTACK_MESSAGE, Constants.INFORMATION);
			}
			fortifyButtonActionPerformed(null);
		} else {
			mapView.getStatusLabel().setText(Constants.CANNOT_ATTACK_MESSAGE + Constants.SELECT_THE_ACTION_MESSAGE);
			if (gameModel.getCurrentPlayer().isHuman()) {
				Utility.showMessagePopUp(Constants.CANNOT_ATTACK_MESSAGE + Constants.FORTIFY_MESSAGE,
						Constants.INFORMATION);
			}
			validatePlayerTurn();
		}
	}

	/**
	 * Handle active turn state of the risk game i.e once all the player armies are
	 * placed on the territories but still it will be in reinforcement phase still
	 * next user action
	 */
	private void handleActiveTurn() {
		gameModel.setState(Constants.RE_ENFORCEMENT_PHASE);
		mapView.getStatusLabel().setText(Constants.SELECT_THE_ACTION_MESSAGE);
		mapView.getAttackButton().setVisible(true);
		mapView.getFortifyButton().setVisible(true);
		mapView.getSaveButton().setVisible(true);
		mapView.getEndButton().setVisible(true);
	}

	/**
	 * Handle start turn state of player or before reinforcement i.e after
	 * fortification
	 */
	private void handleStartTurn() {
		gameModel.setSelectedTerritory(null);
		if (isFirstRound()) {
			handleAttack();
		} else {
			handleReinforcement();
			gameModel.notifyPhaseChange();
		}
	}

	/**
	 * Handles the reinforcement state of the game
	 */
	private void handleReinforcement() {
		if (gameModel.getCurrentPlayer().getOccupiedTerritories().isEmpty()) {
			validatePlayerTurn();
		} else {
			gameModel.setState(Constants.RE_ENFORCEMENT_PHASE);
			gameModel.getCurrentPlayer().addTurnBonus(gameModel);
			gameModel.notifyPhaseChange();
			CardExchangeModel.getInstance().checkCardsTradeOption(this, false);
		}
	}

	/**
	 * Options to show when card exchange in progress
	 */
	public void handleCardTrade() {
		mapView.getStatusLabel().setText(Constants.CARD_TRADE_MESSAGE);
		mapView.getCardButton().setVisible(true);
		mapView.getAttackButton().setVisible(false);
		mapView.getSaveButton().setVisible(false);
		mapView.getFortifyButton().setVisible(false);
		mapView.getEndButton().setVisible(false);
		gameModel.notifyPhaseChange();
	}

	/**
	 * Card trade status message
	 */
	public void addCardTradeStatus() {
		mapView.getStatusLabel().setText(Constants.CARD_TRADE_MESSAGE);
		gameModel.notifyPhaseChange();
	}

	/**
	 * Handle reinforcement after card verification
	 */
	public void handleReinforcement1() {
		if (!gameModel.getCurrentPlayer().isCardTradeMandatory()) {
			Utility.writeLog("No of armies with player: " + gameModel.getCurrentPlayer().getArmies());
			gameModel.setState(Constants.RE_ENFORCEMENT_PHASE);
			mapView.getCardButton().setVisible(true);
			if (gameModel.getCurrentPlayer().getArmies() == Constants.ZERO) {
				mapView.getStatusLabel().setText(Constants.SELECT_THE_ACTION_MESSAGE);
				mapView.getAttackButton().setVisible(true);
				mapView.getFortifyButton().setVisible(true);
				mapView.getSaveButton().setVisible(true);
				mapView.getEndButton().setVisible(true);
				mapView.getUserEntered().setVisible(false);
			} else {
				mapView.getStatusLabel().setText(Constants.RE_ENFORCEMENT_MESSAGE);
				mapView.getAttackButton().setVisible(false);
				mapView.getFortifyButton().setVisible(false);
				mapView.getEndButton().setVisible(false);
				mapView.getSaveButton().setVisible(false);
				mapView.getUserEntered().setVisible(false);
			}
			gameModel.notifyPhaseChanging();
			gameModel.notifyPhaseChange();
		}
	}

	/**
	 * Verifies the selected card set is valid for trading
	 * 
	 * @return isValid variable
	 */
	public boolean isValidNoOfCardsTraded() {
		boolean isValid = false;
		int infantryCount = cardTradeView.getInfantryCardSelectedItem();
		int cavarlyCount = cardTradeView.getCavalryCardSelectedItem();
		int artilleryCount = cardTradeView.getArtilleryCardSelectedItem();
		int wildCount = cardTradeView.getWildCardSelectedItem();

		int count = infantryCount + cavarlyCount + artilleryCount + wildCount;

		if (count == Constants.THREE) {
			if (infantryCount == Constants.THREE || cavarlyCount == Constants.THREE
					|| artilleryCount == Constants.THREE) {
				isValid = true;
			} else if (infantryCount == Constants.ONE && cavarlyCount == Constants.ONE
					&& artilleryCount == Constants.ONE) {
				isValid = true;
			} else if (wildCount == Constants.ONE) {
				isValid = true;
			}
		}
		if (!isValid) {
			Utility.showMessagePopUp(Constants.INVALID_NO_OF_CARDS_TRADE_MESSAGE, "Card Information");
		}
		return isValid;
	}

	/**
	 * Action taken on valid card set selected to trade
	 * 
	 * @param evt
	 */
	public void cardTradeActionPerformed(ActionEvent evt) {
		int infantryCard = cardTradeView.getInfantryCardSelectedItem();
		int cavarlyCard = cardTradeView.getCavalryCardSelectedItem();
		int artilleryCard = cardTradeView.getArtilleryCardSelectedItem();
		int wildCard = cardTradeView.getWildCardSelectedItem();

		gameModel.getCurrentPlayer().cardTradeActionPerformed(gameModel, infantryCard, cavarlyCard, artilleryCard,
				wildCard);
		cardTradeView.exitForm();
		handleReinforcement1();
		gameModel.notifyPhaseChange();
	}

	/**
	 * Selection of no. of dice to roll(both attacker and defender)
	 */
	public void setNoOfDiceToRoll() {
		int numberOfDice = gameModel.getCurrentPlayer().getAttackingTerritory().getArmies();
		if (numberOfDice > 1) {
			if (gameModel.getCurrentPlayer().isAutomatic()) {
				if (gameModel.getCurrentPlayer().isHuman()) {
					numberOfDice = numberOfDice < Constants.THREE ? numberOfDice - 1 : Constants.THREE;
				} else {
					numberOfDice = numberOfDice < Constants.THREE ? numberOfDice - 1
							: (Utility.getRandomNumber(Constants.THREE) + 1);
				}
			} else {
				numberOfDice = mapView.showOptionPopup(
						gameModel.getCurrentPlayer().getName() + " : "
								+ gameModel.getCurrentPlayer().getStrategy().getStrategyString(),
						numberOfDice < Constants.THREE ? numberOfDice - 1 : Constants.THREE, Constants.ATTACK_IMAGE,
						gameModel.getCurrentPlayer().getName() + " : "
								+ gameModel.getCurrentPlayer().getStrategy().getStrategyString());
			}
			gameModel.getCurrentPlayer().setAttackingNoOfDice(numberOfDice);
			Utility.writeLog("Attacking no. of dice = " + numberOfDice);

			numberOfDice = gameModel.getCurrentPlayer().getDefendingTerritory().getArmies();
			if (numberOfDice > 0) {
				if (gameModel.getCurrentPlayer().isAutomatic()) {
					if (gameModel.getCurrentPlayer().isHuman()) {
						numberOfDice = numberOfDice < Constants.TWO ? numberOfDice : Constants.TWO;
					} else if (gameModel.getCurrentPlayer().getDefendingTerritory().getPlayerModel().isHuman()) {
						numberOfDice = mapView.showOptionPopup(
								gameModel.getCurrentPlayer().getDefendingTerritory().getPlayerModel().getName() + " : "
										+ gameModel.getCurrentPlayer().getDefendingTerritory().getPlayerModel()
												.getStrategy().getStrategyString(),
								numberOfDice < Constants.TWO ? numberOfDice : Constants.TWO, Constants.DEFEND_IMAGE,
								gameModel.getCurrentPlayer().getDefendingTerritory().getPlayerModel().getName() + " : "
										+ gameModel.getCurrentPlayer().getDefendingTerritory().getPlayerModel()
												.getStrategy().getStrategyString());
					} else {
						numberOfDice = numberOfDice < Constants.TWO ? numberOfDice
								: (Utility.getRandomNumber(Constants.TWO) + 1);
					}
				} else {
					if (!gameModel.getCurrentPlayer().getDefendingTerritory().getPlayerModel().isHuman()) {
						numberOfDice = numberOfDice < Constants.TWO ? numberOfDice
								: (Utility.getRandomNumber(Constants.TWO) + 1);
					} else {
						numberOfDice = mapView.showOptionPopup(
								gameModel.getCurrentPlayer().getDefendingTerritory().getPlayerModel().getName() + " : "
										+ gameModel.getCurrentPlayer().getDefendingTerritory().getPlayerModel()
												.getStrategy().getStrategyString(),
								numberOfDice < Constants.TWO ? numberOfDice : Constants.TWO, Constants.DEFEND_IMAGE,
								gameModel.getCurrentPlayer().getDefendingTerritory().getPlayerModel().getName() + " : "
										+ gameModel.getCurrentPlayer().getDefendingTerritory().getPlayerModel()
												.getStrategy().getStrategyString());
					}
				}
				gameModel.getCurrentPlayer().setDefendingNoOfDice(numberOfDice);
				Utility.writeLog("Defending no. of dice = " + numberOfDice);
			}
		}
	}

	/**
	 * Update rolled dice list
	 */
	public void updateDiceList() {
		if (!gameModel.getCurrentPlayer().isAutomatic()) {
			Utility.showMessagePopUp(Constants.CLICK_OK_TO_ROLL_DICE, "Roll Dice");
		}
		gameModel.getCurrentPlayer().rollAndSetDiceList();
		if (gameModel.getCurrentPlayer().isHuman()) {
			rolledDiceView.showRolledDiceList(gameModel);
		} else {
			updateDiceAction();
		}
	}

	/**
	 * Action performed after rolled dice displayed
	 */
	public void updateDiceAction() {
		gameModel.getCurrentPlayer().updateArmiesOnFightingTerritories(gameModel);
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

	/**
	 * @return the gameModel
	 */
	public GameModel getGameModel() {
		return gameModel;
	}

	/**
	 * @param gameModel the gameModel to set
	 */
	public void setGameModel(GameModel gameModel) {
		this.gameModel = gameModel;
	}

	/**
	 * @return the cardTradeView
	 */
	public CardTradeView getCardTradeView() {
		return cardTradeView;
	}

	/**
	 * @param cardTradeView the cardTradeView to set
	 */
	public void setCardTradeView(CardTradeView cardTradeView) {
		this.cardTradeView = cardTradeView;
	}

	public void saveGame() {
		try {	
			FileOutputStream fileStream = new FileOutputStream(new File(Utility.getSaveGamePath(Constants.DEFAULT_SAVED_GAME_FILE_NAME)));   
	        ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);   

	        objectStream.writeObject(gameModel);
	        
	        objectStream.close();   
	        fileStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
