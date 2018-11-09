package col.cs.risk.controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

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

	/** Maximum number of rounds allowed per game */
	private static int MAXIMUM_NO_OF_ROUNDS_ALLOWED = Constants.TEN;

	/**
	 * Card exchange view
	 */
	private CardTradeView cardTradeView;

	/** view for displaying rolled dices list */
	private RolledDiceView rolledDiceView;

	/**
	 * Constructor to initialize the Jpanel
	 * 
	 * @param booleans
	 */
	public GameController(Boolean... booleans) {
		try {
			gameModel = new GameModel(false);
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
		} catch (MapException ex) {
			System.out.println(ex.getMessage());
			ex.clearHistory();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception: " + ex.getMessage());
		}
	}

	/**
	 * Default constructor
	 */
	public GameController() {

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
		mapView.getAttackButton().setVisible(false);
		if (gameModel.getCurrentPlayer().canAttack()) {
			mapView.getStatusLabel().setText(Constants.ATTACK_COUNTRY_SELECT_MESSAGE);
		} else if(gameModel.getCurrentPlayer().canFortify()){
			Utility.showMessagePopUp(Constants.CANNOT_ATTACK_MESSAGE, "Information");
			fortifyButtonActionPerformed(null);
		} else {
			Utility.showMessagePopUp(Constants.CANNOT_ATTACK_MESSAGE+Constants.FORTIFY_MESSAGE, "Information");
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
		CardExchangeModel.getInstance().checkCardsTradeOption(this, true);
	}

	/**
	 * Action performed on end button press
	 * 
	 * @param evt
	 */
	public void endButtonActionPerformed(ActionEvent evt) {
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
		gameModel.getCurrentPlayer().setCardAssigned(false);
		gameModel.nextPlayer();
		gameModel.getCurrentPlayer().setCardAssigned(false);
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
	public boolean isFirstRound() {
		return noOfRoundsCompleted == Constants.ZERO ? true : false;
	}

	/**
	 * Handles game over functionality
	 * 
	 * @param message
	 *            to display
	 */
	public void gameOver(String message) {
		gameModel.setState(Constants.END_PHASE);
		gameModel.notifyPhaseChanging(message);
		mapView.getStatusLabel().setText(message);
		mapView.getAttackButton().setVisible(false);
		mapView.getFortifyButton().setVisible(false);
		mapView.getEndButton().setVisible(false);
		mapView.getCardButton().setVisible(false);
		gameModel.notifyPhaseChange();
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
			if (mapView.getCardButton().isVisible()) {
				mapView.getCardButton().setVisible(false);
			}
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
			mapView.getUserEntered().setVisible(false);
		} else if(gameModel.getCurrentPlayer().canFortify()){
			Utility.showMessagePopUp(Constants.CANNOT_ATTACK_MESSAGE, "Information");
			fortifyButtonActionPerformed(null);
		} else {
			mapView.getStatusLabel().setText(Constants.CANNOT_ATTACK_MESSAGE + Constants.SELECT_THE_ACTION_MESSAGE);
			Utility.showMessagePopUp(Constants.CANNOT_ATTACK_MESSAGE+Constants.FORTIFY_MESSAGE, "Information");
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
			gameModel.setState(Constants.RE_ENFORCEMENT_PHASE);
			mapView.getCardButton().setVisible(true);
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

		Vector<CardModel> cards = gameModel.getCurrentPlayer().getCards();
		Vector<CardModel> cardsToBeRemoved = new Vector<>();

		for (CardModel card : cards) {
			if (infantryCard <= 0 && cavarlyCard <= 0 && artilleryCard <= 0 && wildCard <= 0) {
				break;
			}
			switch (card.getType()) {
			case Constants.ARMY_TYPE_INFANTRY:
				if (infantryCard > 0) {
					cardsToBeRemoved.add(card);
					infantryCard--;
				}
				break;
			case Constants.ARMY_TYPE_CAVALRY:
				if (cavarlyCard > 0) {
					cardsToBeRemoved.add(card);
					cavarlyCard--;
				}
				break;
			case Constants.ARMY_TYPE_ARTILLERY:
				if (artilleryCard > 0) {
					cardsToBeRemoved.add(card);
					artilleryCard--;
				}
				break;
			case Constants.ARMY_TYPE_WILD:
				if (wildCard > 0) {
					cardsToBeRemoved.add(card);
					wildCard--;
				}
				break;
			}
		}

		if (cardsToBeRemoved.size() == Constants.THREE) {
			gameModel.getCurrentPlayer().addAdditionalBounusForTradeCardMatch(cardsToBeRemoved);
			gameModel.getCurrentPlayer().removeCards(cardsToBeRemoved);
		}

		int tradeCount = gameModel.getCardTradeCount();
		tradeCount++;
		int armies;
		if (tradeCount <= Constants.SIX) {
			armies = gameModel.getCardArmyMap().get(tradeCount);
		} else {
			armies = gameModel.getCardArmyMap().get(Constants.SIX) + (tradeCount - Constants.SIX) * Constants.FIVE;
		}
		gameModel.setCardTradeCount(tradeCount);
		cardTradeView.exitForm();
		gameModel.getCurrentPlayer().addArmies(armies);
		gameModel.getCurrentPlayer().setCardTradeMandatory(false);
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
				numberOfDice = numberOfDice < Constants.THREE ? numberOfDice - 1 : Constants.THREE;
			} else {
				numberOfDice = mapView.showOptionPopup(gameModel.getCurrentPlayer().getName(),
						numberOfDice < Constants.THREE ? numberOfDice - 1 : Constants.THREE, Constants.ATTACK_IMAGE,
						gameModel.getCurrentPlayer().getName());
			}
			gameModel.getCurrentPlayer().setAttackingNoOfDice(numberOfDice);

			numberOfDice = gameModel.getCurrentPlayer().getDefendingTerritory().getArmies();
			if (numberOfDice > 0) {
				if (gameModel.getCurrentPlayer().isAutomatic()) {
					numberOfDice = numberOfDice < Constants.TWO ? numberOfDice : Constants.TWO;
				} else {
					numberOfDice = mapView.showOptionPopup(
							gameModel.getCurrentPlayer().getDefendingTerritory().getPlayerModel().getName(),
							numberOfDice < Constants.TWO ? numberOfDice : Constants.TWO, Constants.DEFEND_IMAGE,
							gameModel.getCurrentPlayer().getDefendingTerritory().getPlayerModel().getName());
				}
				gameModel.getCurrentPlayer().setDefendingNoOfDice(numberOfDice);
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
		rolledDiceView.showRolledDiceList(gameModel);
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

	/**
	 * @return the gameModel
	 */
	public GameModel getGameModel() {
		return gameModel;
	}

	/**
	 * @param gameModel
	 *            the gameModel to set
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
	 * @param cardTradeView
	 *            the cardTradeView to set
	 */
	public void setCardTradeView(CardTradeView cardTradeView) {
		this.cardTradeView = cardTradeView;
	}

}
