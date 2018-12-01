package test.col.cs.risk.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import col.cs.risk.controller.GameController;
import col.cs.risk.controller.MapPanelController;
import col.cs.risk.controller.PlayerPanelController;
import col.cs.risk.helper.Report;
import col.cs.risk.helper.Utility;
import col.cs.risk.model.CardModel;
import col.cs.risk.model.Constants;
import col.cs.risk.model.ContinentModel;
import col.cs.risk.model.GameModel;
import col.cs.risk.model.PlayerModel;
import col.cs.risk.model.TerritoryModel;
import col.cs.risk.model.strategy.Benevolent;
import col.cs.risk.model.strategy.Cheater;
import col.cs.risk.view.CardTradeView;
import col.cs.risk.view.MapView;

/**
 * This class Game Controller test the main driver functions that handles the
 * game phases.
 * 
 * @author Team25
 *
 */
public class GameControllerTest {

	/**
	 * Game controller object
	 */
	GameController gameController;

	/**
	 * gameModel object
	 */
	GameModel gameModel;

	/**
	 * card trade view object
	 */
	CardTradeView cardTradeView;

	/**
	 * player model object
	 */
	PlayerModel playerModel;

	/**
	 * Initialization before every test case
	 */
	@Before
	public void before() {
		Utility.canShow = false;
		gameController = new GameController();
		gameModel = new GameModel();
		gameController.setGameModel(gameModel);
		playerModel = new PlayerModel(101, "player1");
		gameModel.setCurrentPlayer(playerModel);
		cardTradeView = new CardTradeView(gameController);
		cardTradeView.initializeComponents();
		gameController.setCardTradeView(cardTradeView);

		MapPanelController mapMainPanel = new MapPanelController(gameModel);
		PlayerPanelController mapSubPanelPlayer = new PlayerPanelController(gameModel);
		gameController.setMapMainPanel(mapMainPanel);
		gameController.setMapSubPanelPlayer(mapSubPanelPlayer);
		new MapView(gameController);
	}

	/**
	 * DeInitialization after every test case
	 */
	@After
	public void after() {
		Utility.canShow = true;
		gameController = null;
		gameModel = null;
		cardTradeView = null;
		playerModel = null;
	}

	/**
	 * Test case to check whether the round is first or not.
	 */
	@Test
	public void testIsFirstRound() {
		GameController gameController = new GameController();

		gameController.noOfRoundsCompleted = 0;
		assertTrue("Failed test", gameController.isFirstRound());

		gameController.noOfRoundsCompleted = 1;
		assertFalse("Failed test", gameController.isFirstRound());
	}

	/**
	 * Test case to check whether the trading card set is valid or not according
	 * risk game rule
	 */
	@Test
	public void testSelectedCardSetValidation() {
		TerritoryModel tmodel1 = new TerritoryModel(201, "tname1", 10, 20, new ContinentModel(301, "cname1", 3));
		tmodel1.setPlayerModel(playerModel);
		CardModel card1 = new CardModel(1, Constants.ARMY_TYPE_INFANTRY, tmodel1);
		CardModel card2 = new CardModel(2, Constants.ARMY_TYPE_ARTILLERY, tmodel1);
		CardModel card3 = new CardModel(3, Constants.ARMY_TYPE_CAVALRY, tmodel1);
		CardModel card4 = new CardModel(4, Constants.ARMY_TYPE_WILD, tmodel1);
		CardModel card5 = new CardModel(5, Constants.ARMY_TYPE_WILD, tmodel1);
		CardModel card6 = new CardModel(6, Constants.ARMY_TYPE_WILD, tmodel1);
		CardModel card7 = new CardModel(7, Constants.ARMY_TYPE_INFANTRY, tmodel1);
		CardModel card8 = new CardModel(8, Constants.ARMY_TYPE_INFANTRY, tmodel1);

		Vector<CardModel> cards = new Vector<>();
		cards.addElement(card1);
		cards.addElement(card2);
		cards.addElement(card3);
		cards.addElement(card4);
		cards.addElement(card5);
		cards.addElement(card6);
		cards.addElement(card7);
		cards.addElement(card8);
		playerModel.addCards(cards);

		// invalid set of cards
		// 3 wild cards
		cardTradeView.setSelectedItem(0, 0, 0, 3);
		assertFalse(gameController.isValidNoOfCardsTraded());

		// 2 wild cards + 1 any
		cardTradeView.setSelectedItem(0, 0, 1, 2);
		assertFalse(gameController.isValidNoOfCardsTraded());

		// any 2 + 1 other(not wild) card
		cardTradeView.setSelectedItem(0, 2, 1, 0);
		assertFalse(gameController.isValidNoOfCardsTraded());

		// more than 3 cards(any)
		cardTradeView.setSelectedItem(2, 2, 2, 0);
		assertFalse(gameController.isValidNoOfCardsTraded());
		cardTradeView.setSelectedItem(1, 1, 1, 1);
		assertFalse(gameController.isValidNoOfCardsTraded());

		// valid set of cards
		// 1 each and only 3 cards
		cardTradeView.setSelectedItem(1, 1, 1, 0);
		assertTrue(gameController.isValidNoOfCardsTraded());
		cardTradeView.setSelectedItem(0, 1, 1, 1);
		assertTrue(gameController.isValidNoOfCardsTraded());

		// any 2 + 1 wild card
		cardTradeView.setSelectedItem(0, 0, 2, 1);
		assertTrue(gameController.isValidNoOfCardsTraded());

		// any 3 (not wild)
		cardTradeView.setSelectedItem(3, 0, 0, 0);
		assertTrue(gameController.isValidNoOfCardsTraded());
	}
	
	/**
	 * Test case for game saving and loading
	 */
	@Test
	public void testGameSaveAndLoad() {
		PlayerModel playerModel2 = new PlayerModel(102, "player2");
		PlayerModel playerModel3 = new PlayerModel(103, "player3");
		
		Vector<PlayerModel> players = new Vector<>();
		players.add(playerModel);
		players.add(playerModel2);
		players.add(playerModel3);
		GameModel.players = players;
		gameModel.setState(Constants.RE_ENFORCEMENT_PHASE);
		
		gameController.saveGame();
		GameModel savedResult = gameController.loadSavedGame();
		
		assertEquals(Constants.RE_ENFORCEMENT_PHASE, savedResult.getState());
		assertEquals(playerModel.getName(), savedResult.getCurrentPlayer().getName());
		assertEquals(players.size(), savedResult.players.size());
		
		Constants.DEFAULT_SAVED_GAME_FILE_NAME = "nullFile";
		assertNull(gameController.loadSavedGame());
	}
	
	/**
	 * Test case to test tournament mode functionalities
	 */
	@Test
	public void testTournamentMode() {
		
		PlayerModel playerModel1 = new PlayerModel(0, "player2");
		PlayerModel playerModel2 = new PlayerModel(1, "player3");
		
		playerModel1.setStrategy(new Cheater(playerModel1));
		playerModel2.setStrategy(new Benevolent(playerModel2));
		
		Vector<PlayerModel> players = new Vector<>();
		players.add(playerModel1);
		players.add(playerModel2);
		GameModel.players = players;
		GameModel.isTournamentMode = true;
		GameModel.tournamentMapList.addElement(Constants.DEFAULT_MAP_FILE_NAME);
		GameModel.reports.addElement(new Report(Constants.DEFAULT_MAP_FILE_NAME, GameModel.tournamentNoOfGame));
		
		gameController.startNewGame();
		
		String winner = playerModel1.getName()+" - "+playerModel1.getStrategy().getStrategyString();
		assertEquals(winner, gameController.testWinner);
	}
}