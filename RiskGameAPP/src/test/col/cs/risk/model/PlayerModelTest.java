package test.col.cs.risk.model;

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
import col.cs.risk.helper.Utility;
import col.cs.risk.model.CardModel;
import col.cs.risk.model.Constants;
import col.cs.risk.model.ContinentModel;
import col.cs.risk.model.GameModel;
import col.cs.risk.model.PlayerModel;
import col.cs.risk.model.TerritoryModel;
import col.cs.risk.model.strategy.Human;
import col.cs.risk.view.MapView;

/**
 * Test class to check whether the players are being added, armies being
 * assigned, territories being allocated and the attack simulation.
 * 
 * @author Team25
 *
 */

public class PlayerModelTest {

	/**
	 * Player model instance
	 */
	PlayerModel playerModel;

	/**
	 * Game model instance
	 */
	GameModel gameModel;
	
	/**
	 * Card model instance
	 */
	GameModel cardModel;

	/**
	 * Initialization before every test case
	 */
	@Before
	public void before() {
		playerModel = new PlayerModel(101, "player1");
		playerModel.setStrategy(new Human(playerModel));
		gameModel = new GameModel();
		gameModel.setCurrentPlayer(playerModel);
		Utility.canShow = false;
	}

	/**
	 * DeInitialization after every test case
	 */
	@After
	public void after() {
		playerModel = null;
		gameModel = null;
		Utility.canShow = true;
	}

	/**
	 * Test case for calculating territory bonus
	 */
	@Test
	public void testTerritoryBonus() {

		TerritoryModel tmodel1 = new TerritoryModel(201, "tname1", 10, 20, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel2 = new TerritoryModel(202, "tname2", 30, 40, new ContinentModel(302, "cname2", 5));
		TerritoryModel tmodel3 = new TerritoryModel(203, "tname3", 30, 40, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel4 = new TerritoryModel(204, "tname4", 30, 40, new ContinentModel(302, "cname2", 5));
		TerritoryModel tmodel5 = new TerritoryModel(205, "tname5", 30, 40, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel6 = new TerritoryModel(206, "tname6", 30, 40, new ContinentModel(302, "cname2", 5));
		TerritoryModel tmodel7 = new TerritoryModel(201, "tname7", 10, 20, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel8 = new TerritoryModel(202, "tname8", 30, 40, new ContinentModel(302, "cname2", 5));
		TerritoryModel tmodel9 = new TerritoryModel(203, "tname9", 30, 40, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel10 = new TerritoryModel(204, "tname10", 30, 40, new ContinentModel(302, "cname2", 5));
		TerritoryModel tmodel11 = new TerritoryModel(205, "tname11", 30, 40, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel12 = new TerritoryModel(206, "tname12", 30, 40, new ContinentModel(302, "cname2", 5));
		Vector<TerritoryModel> territories = new Vector<>();
		territories.add(tmodel1);
		territories.add(tmodel2);
		territories.add(tmodel3);
		territories.add(tmodel4);
		territories.add(tmodel5);
		territories.add(tmodel6);
		territories.add(tmodel7);
		territories.add(tmodel8);
		territories.add(tmodel9);
		territories.add(tmodel10);
		territories.add(tmodel11);
		territories.add(tmodel12);
		gameModel.setCurrentPlayer(playerModel);
		playerModel.setOccupiedTerritories(territories);

		assertEquals(4, playerModel.territoryBonus());

		territories.remove(0);
		territories.remove(0);
		territories.remove(0);
		assertEquals(3, playerModel.territoryBonus());

		territories.remove(0);
		assertEquals(3, playerModel.territoryBonus());
	}

	/**
	 * Calculating turn bonus for a current player
	 */
	@Test
	public void testTurnBonus() {
		PlayerModel playerModel = new PlayerModel(1, "P1");

		ContinentModel continent = new ContinentModel(1, "C1", 2);
		Vector<ContinentModel> continents = new Vector<>();
		continents.add(continent);

		TerritoryModel model1 = new TerritoryModel(1, "T1", 10, 10, continent);
		TerritoryModel model2 = new TerritoryModel(2, "T2", 10, 20, continent);
		TerritoryModel model3 = new TerritoryModel(3, "T3", 20, 10, continent);
		TerritoryModel model4 = new TerritoryModel(4, "T4", 20, 20, continent);

		Vector<TerritoryModel> territories = new Vector<>();
		territories.add(model1);
		territories.add(model2);
		territories.add(model3);
		territories.add(model4);

		model1.setPlayerModel(playerModel);
		model2.setPlayerModel(playerModel);
		model3.setPlayerModel(playerModel);
		model4.setPlayerModel(playerModel);

		continent.setTerritories(territories);
		playerModel.setOccupiedTerritories(territories);
		gameModel.setTerritories(territories);
		gameModel.setCurrentPlayer(playerModel);
		gameModel.setContinents(continents);

		playerModel.addTurnBonus(gameModel);

		// 2+3 (2- continent bonus and 3 territory bonus)
		assertEquals(5, playerModel.getArmies());

		playerModel.setArmies(0);
		PlayerModel playerModel1 = new PlayerModel(2, "P2");
		model4.setPlayerModel(playerModel1);

		playerModel.addTurnBonus(gameModel);

		// 3 (0- continent bonus and 3 territory bonus)
		assertEquals(3, playerModel.getArmies());

	}

	/**
	 * Test case to verify the canAttack method
	 */
	@Test
	public void testCanAttack() {
		PlayerModel playerModelOther = new PlayerModel(102, "player2");
		TerritoryModel tmodel1 = new TerritoryModel(201, "tname1", 10, 20, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel2 = new TerritoryModel(202, "tname2", 30, 40, new ContinentModel(302, "cname2", 5));
		TerritoryModel tmodel3 = new TerritoryModel(203, "tname3", 30, 40, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel4 = new TerritoryModel(204, "tname4", 30, 40, new ContinentModel(302, "cname2", 5));
		TerritoryModel tmodel5 = new TerritoryModel(205, "tname5", 30, 40, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel6 = new TerritoryModel(206, "tname6", 30, 40, new ContinentModel(301, "cname1", 3));

		tmodel1.addAdjacentTerritory(tmodel2);
		tmodel1.addAdjacentTerritory(tmodel6);

		tmodel2.addAdjacentTerritory(tmodel1);
		tmodel2.addAdjacentTerritory(tmodel3);

		tmodel3.addAdjacentTerritory(tmodel2);
		tmodel3.addAdjacentTerritory(tmodel4);

		tmodel4.addAdjacentTerritory(tmodel3);
		tmodel4.addAdjacentTerritory(tmodel5);

		tmodel5.addAdjacentTerritory(tmodel4);
		tmodel5.addAdjacentTerritory(tmodel6);

		tmodel6.addAdjacentTerritory(tmodel5);
		tmodel6.addAdjacentTerritory(tmodel1);

		tmodel1.setArmies(4);
		tmodel2.setArmies(2);
		tmodel3.setArmies(6);
		tmodel4.setArmies(8);
		tmodel5.setArmies(4);
		tmodel6.setArmies(10);

		Vector<TerritoryModel> territories = new Vector<>();
		territories.add(tmodel1);
		territories.add(tmodel2);
		territories.add(tmodel3);
		tmodel1.setPlayerModel(playerModel);
		tmodel2.setPlayerModel(playerModel);
		tmodel3.setPlayerModel(playerModel);

		Vector<TerritoryModel> territories1 = new Vector<>();
		territories1.add(tmodel4);
		territories1.add(tmodel5);
		territories1.add(tmodel6);
		tmodel4.setPlayerModel(playerModelOther);
		tmodel5.setPlayerModel(playerModelOther);
		tmodel6.setPlayerModel(playerModelOther);

		playerModel.setOccupiedTerritories(territories);
		playerModelOther.setOccupiedTerritories(territories1);
		gameModel.setCurrentPlayer(playerModel);

		assertTrue(playerModel.canAttack());

		tmodel1.setArmies(1);
		tmodel3.setArmies(1);
		assertFalse(playerModel.canAttack());
	}
	
	/**
	 * Test case to verify the canFortify method
	 */
	@Test
	public void testCanFortify() {
		PlayerModel playerModelOther = new PlayerModel(102, "player2");
		TerritoryModel tmodel1 = new TerritoryModel(201, "tname1", 10, 20, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel2 = new TerritoryModel(202, "tname2", 30, 40, new ContinentModel(302, "cname2", 5));
		TerritoryModel tmodel3 = new TerritoryModel(203, "tname3", 30, 40, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel4 = new TerritoryModel(204, "tname4", 30, 40, new ContinentModel(302, "cname2", 5));
		TerritoryModel tmodel5 = new TerritoryModel(205, "tname5", 30, 40, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel6 = new TerritoryModel(206, "tname6", 30, 40, new ContinentModel(301, "cname1", 3));

		tmodel1.addAdjacentTerritory(tmodel2);
		tmodel1.addAdjacentTerritory(tmodel6);

		tmodel2.addAdjacentTerritory(tmodel1);
		tmodel2.addAdjacentTerritory(tmodel3);

		tmodel3.addAdjacentTerritory(tmodel2);
		tmodel3.addAdjacentTerritory(tmodel4);

		tmodel4.addAdjacentTerritory(tmodel3);
		tmodel4.addAdjacentTerritory(tmodel5);

		tmodel5.addAdjacentTerritory(tmodel4);
		tmodel5.addAdjacentTerritory(tmodel6);

		tmodel6.addAdjacentTerritory(tmodel5);
		tmodel6.addAdjacentTerritory(tmodel1);

		tmodel1.setArmies(4);
		tmodel2.setArmies(2);
		tmodel3.setArmies(6);
		tmodel4.setArmies(8);
		tmodel5.setArmies(4);
		tmodel6.setArmies(10);

		Vector<TerritoryModel> territories = new Vector<>();
		territories.add(tmodel1);
		territories.add(tmodel2);
		territories.add(tmodel3);
		tmodel1.setPlayerModel(playerModel);
		tmodel2.setPlayerModel(playerModel);
		tmodel3.setPlayerModel(playerModel);

		Vector<TerritoryModel> territories1 = new Vector<>();
		territories1.add(tmodel4);
		territories1.add(tmodel5);
		territories1.add(tmodel6);
		tmodel4.setPlayerModel(playerModelOther);
		tmodel5.setPlayerModel(playerModelOther);
		tmodel6.setPlayerModel(playerModelOther);

		playerModel.setOccupiedTerritories(territories);
		playerModelOther.setOccupiedTerritories(territories1);
		gameModel.setCurrentPlayer(playerModel);

		assertTrue(playerModel.canFortify());

		tmodel1.setArmies(1);
		tmodel2.setArmies(1);
		tmodel3.setArmies(1);
		assertFalse(playerModel.canFortify());
	}

	/**
	 * This Test case checks Adding Occupied Territories list
	 */
	@Test
	public void testAddOccupiedTerritory() {
		TerritoryModel tmodel1 = new TerritoryModel(201, "tname1", 10, 20, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel2 = new TerritoryModel(202, "tname2", 30, 40, new ContinentModel(302, "cname2", 5));
		TerritoryModel tmodel3 = new TerritoryModel(203, "tname3", 30, 40, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel4 = new TerritoryModel(204, "tname4", 30, 40, new ContinentModel(302, "cname2", 5));
		TerritoryModel tmodel5 = new TerritoryModel(205, "tname5", 30, 40, new ContinentModel(301, "cname1", 3));
		Vector<TerritoryModel> territories = new Vector<>();
		territories.add(tmodel1);
		territories.add(tmodel2);
		territories.add(tmodel3);
		territories.add(tmodel4);
		playerModel.setOccupiedTerritories(territories);

		assertEquals(territories.size(), playerModel.getOccupiedTerritories().size());

		playerModel.addOccupiedTerritory(tmodel5);
		assertEquals(territories, playerModel.getOccupiedTerritories());
		assertEquals(5, playerModel.getOccupiedTerritories().size());
		assertEquals(tmodel3.getName(), playerModel.getOccupiedTerritories().get(2).getName());
	}

	/**
	 * This Test case checks getting Occupied Territories list
	 */
	@Test
	public void testGetOccupiedTerritory() {
		TerritoryModel tmodel1 = new TerritoryModel(201, "tname1", 10, 20, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel2 = new TerritoryModel(202, "tname2", 30, 40, new ContinentModel(302, "cname2", 5));
		TerritoryModel tmodel3 = new TerritoryModel(203, "tname3", 30, 40, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel4 = new TerritoryModel(204, "tname4", 30, 40, new ContinentModel(302, "cname2", 5));
		TerritoryModel tmodel5 = new TerritoryModel(205, "tname5", 30, 40, new ContinentModel(301, "cname1", 3));
		Vector<TerritoryModel> territories = new Vector<>();
		territories.add(tmodel1);
		territories.add(tmodel2);
		territories.add(tmodel3);
		territories.add(tmodel4);
		territories.add(tmodel5);
		playerModel.setOccupiedTerritories(territories);

		assertEquals(territories, playerModel.getOccupiedTerritories());
	}

	/**
	 * This Test case for adding the new cards and removing the cards.
	 */
	@Test
	public void testAddRemoveCards() {
		TerritoryModel tmodel1 = new TerritoryModel(201, "tname1", 10, 20, new ContinentModel(301, "cname1", 3));

		CardModel card1 = new CardModel(1, Constants.ARMY_TYPE_INFANTRY, tmodel1);
		CardModel card2 = new CardModel(2, Constants.ARMY_TYPE_CAVALRY, tmodel1);
		CardModel card3 = new CardModel(3, Constants.ARMY_TYPE_ARTILLERY, tmodel1);

		Vector<CardModel> cards = new Vector<>();
		cards.add(card1);
		cards.add(card2);
		cards.add(card3);

		playerModel.addCards(cards);
		assertEquals(playerModel.getCards().size(), cards.size());

		Vector<CardModel> test = new Vector<>();
		test.add(card2);
		test.add(card3);
		playerModel.removeCards(test);
		assertEquals(playerModel.getCards().size(), cards.size() - 2);
	}

	/**
	 * This Test case checks for the elimination of the player.
	 */
	@Test
	public void testIsPlayerEliminated() {
		TerritoryModel tmodel1 = new TerritoryModel(201, "tname1", 10, 20, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel2 = new TerritoryModel(202, "tname2", 30, 40, new ContinentModel(302, "cname2", 5));
		TerritoryModel tmodel3 = new TerritoryModel(203, "tname3", 30, 40, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel4 = new TerritoryModel(204, "tname4", 30, 40, new ContinentModel(302, "cname2", 5));
		TerritoryModel tmodel5 = new TerritoryModel(205, "tname5", 30, 40, new ContinentModel(301, "cname1", 3));
		Vector<TerritoryModel> territories = new Vector<>();
		territories.add(tmodel1);
		territories.add(tmodel2);
		territories.add(tmodel3);
		territories.add(tmodel4);
		territories.add(tmodel5);
		playerModel.setOccupiedTerritories(territories);
		assertEquals(false, playerModel.isPlayerEliminated(playerModel));

	}

	/**
	 * This Test case checks for setting of attacking territory.
	 */
	@Test
	public void testSettingAttackingTerritory() {
		TerritoryModel tmodel1 = new TerritoryModel(201, "tname1", 10, 20, new ContinentModel(301, "cname1", 3));
		PlayerModel playerModel = new PlayerModel(1, "P1");
		playerModel.setStrategy(new Human(playerModel));
		tmodel1.setPlayerModel(playerModel);
		gameModel.setCurrentPlayer(playerModel);
		gameModel.setState(Constants.ATTACK_PHASE);

		// no of armies greater than 1 on a territory
		tmodel1.setArmies(2);
		assertEquals(Constants.DEFEND_COUNTRY_SELECT_MESSAGE, playerModel.attack(gameModel, tmodel1));
		assertEquals(tmodel1.getId(), playerModel.getAttackingTerritory().getId());

		// no of armies equal to 1 on a territory
		gameModel.setState(Constants.ATTACK_PHASE);
		playerModel.setAttackingTerritory(null);
		tmodel1.setArmies(1);
		assertEquals(Constants.MIN_TWO_ARMY_MESSAGE, playerModel.attack(gameModel, tmodel1));
		assertNull(playerModel.getAttackingTerritory());

		// selected territory doesn't belong to current player
		gameModel.setState(Constants.ATTACK_PHASE);
		tmodel1.setArmies(2);
		tmodel1.setPlayerModel(new PlayerModel(10, "P10"));
		assertEquals(Constants.ATTACK_COUNTRY_SELECT_MESSAGE, playerModel.attack(gameModel, tmodel1));
		assertNull(playerModel.getAttackingTerritory());

	}

	/**
	 * This Test case checks for the setting of defending territory.
	 */
	@Test
	public void testSettingDefendingTerritory() {
		TerritoryModel tmodel1 = new TerritoryModel(201, "tname1", 10, 20, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel2 = new TerritoryModel(202, "tname2", 30, 40, new ContinentModel(301, "cname1", 3));
		PlayerModel playerModel = new PlayerModel(1, "P1");
		playerModel.setStrategy(new Human(playerModel));
		tmodel1.setPlayerModel(playerModel);
		tmodel1.addAdjacentTerritory(tmodel2);
		tmodel2.addAdjacentTerritory(tmodel1);
		PlayerModel playerModelOther = new PlayerModel(10, "P10");
		playerModelOther.setStrategy(new Human(playerModelOther));
		tmodel2.setPlayerModel(playerModelOther);
		gameModel.setCurrentPlayer(playerModel);
		gameModel.setState(Constants.ATTACK_PHASE);
		tmodel1.setArmies(2);
		playerModel.attack(gameModel, tmodel1);

		// selected territory doesn't belong to current player
		String str = "Territory tname1 attacking on territory tname2";
		assertEquals(str, playerModel.attack(gameModel, tmodel2));
		assertEquals(tmodel2.getId(), playerModel.getDefendingTerritory().getId());

		// selected territory belongs to current player or
		// selected territory is not adjacent to attacking territory
		gameModel.setState(Constants.ATTACKING_PHASE);
		playerModel.setDefendingTerritory(null);
		tmodel1.setPlayerModel(playerModel);
		assertEquals(Constants.DEFEND_COUNTRY_SELECT_MESSAGE, playerModel.attack(gameModel, tmodel1));
		assertNull(playerModel.getDefendingTerritory());

	}

	/**
	 * This Test case checks for the capturing of the territory on wining the game.
	 */
	@Test
	public void testCapturingTerritoryOrWinning() {
		TerritoryModel attackingTerritoryModel = new TerritoryModel(201, "tname1", 10, 20, new ContinentModel(301, "cname1", 3));
		TerritoryModel defendingTerritoryModel = new TerritoryModel(202, "tname2", 30, 40, new ContinentModel(301, "cname1", 3));

		TerritoryModel tmodel3 = new TerritoryModel(203, "tname3", 30, 40, new ContinentModel(301, "cname1", 3));
		TerritoryModel tmodel4 = new TerritoryModel(204, "tname4", 30, 40, new ContinentModel(302, "cname2", 5));
		TerritoryModel tmodel5 = new TerritoryModel(205, "tname5", 30, 40, new ContinentModel(301, "cname1", 3));

		tmodel4.addAdjacentTerritory(tmodel3);
		tmodel4.addAdjacentTerritory(tmodel5);
		
		tmodel5.addAdjacentTerritory(tmodel3);
		tmodel5.addAdjacentTerritory(tmodel4);
		
		PlayerModel playerModelOther = new PlayerModel(1, "player2");
		playerModelOther.setStrategy(new Human(playerModelOther));
		attackingTerritoryModel.setPlayerModel(playerModel);
		defendingTerritoryModel.setPlayerModel(playerModelOther);
		
		attackingTerritoryModel.addAdjacentTerritory(tmodel3);
		attackingTerritoryModel.addAdjacentTerritory(tmodel4);
		attackingTerritoryModel.addAdjacentTerritory(tmodel5);
		
		defendingTerritoryModel.addAdjacentTerritory(tmodel3);
		defendingTerritoryModel.addAdjacentTerritory(tmodel4);
		defendingTerritoryModel.addAdjacentTerritory(tmodel5);
		
		tmodel3.setPlayerModel(playerModelOther);
		tmodel4.setPlayerModel(playerModel);
		tmodel5.setPlayerModel(playerModel);

		Vector<TerritoryModel> territories = new Vector<>();
		territories.add(attackingTerritoryModel);
		territories.add(defendingTerritoryModel);
		territories.add(tmodel3);
		territories.add(tmodel4);
		territories.add(tmodel5);
		gameModel.setTerritories(territories);

		playerModel.setAttackingTerritory(attackingTerritoryModel);
		playerModel.setDefendingTerritory(defendingTerritoryModel);
		playerModel.addOccupiedTerritory(tmodel4);
		playerModel.addOccupiedTerritory(tmodel5);
		playerModel.addOccupiedTerritory(attackingTerritoryModel);
		playerModelOther.addOccupiedTerritory(tmodel3);

		defendingTerritoryModel.setArmies(1);
		attackingTerritoryModel.setArmies(1);
		tmodel3.setArmies(10);
		tmodel4.setArmies(10);
		tmodel5.setArmies(10);

		GameController gameController = new GameController();
		gameController.setGameModel(gameModel);
		MapPanelController mapMainPanel = new MapPanelController(gameModel);
		PlayerPanelController mapSubPanelPlayer = new PlayerPanelController(gameModel);
		gameController.setMapMainPanel(mapMainPanel);
		gameController.setMapSubPanelPlayer(mapSubPanelPlayer);
		new MapView(gameController);
		playerModel.setGameController(gameController);
		
		Vector<PlayerModel> players = new Vector<>();
		players.add(playerModel);
		players.add(playerModelOther);
		
		GameModel.setPlayers(players);
		
		// Lost the battle
		playerModel.updateResult(gameModel);
		assertEquals(Constants.ATTACK_PHASE, gameModel.getState());
		assertEquals(playerModelOther.getName(), defendingTerritoryModel.getPlayerModel().getName());

		defendingTerritoryModel.setArmies(0);
		attackingTerritoryModel.setArmies(2);

		playerModel.setAttackingTerritory(attackingTerritoryModel);
		playerModel.setDefendingTerritory(defendingTerritoryModel);

		// Won the battle and captured country
		playerModel.updateResult(gameModel);
		assertEquals(Constants.ATTACK_PHASE, gameModel.getState());
		assertEquals(playerModel.getName(), defendingTerritoryModel.getPlayerModel().getName());

		tmodel3.setPlayerModel(playerModel);
		tmodel4.setPlayerModel(playerModel);
		tmodel5.setPlayerModel(playerModel);
		defendingTerritoryModel.setArmies(0);
		attackingTerritoryModel.setArmies(2);

		playerModel.setAttackingTerritory(attackingTerritoryModel);
		playerModel.setDefendingTerritory(defendingTerritoryModel);

		// Won the battle and game 
		playerModel.updateResult(gameModel);
		assertEquals(Constants.NEW_GAME, gameModel.getState());
	}

	/**
	 * This Test case checks for the action on fortification phase for the current
	 * player.
	 */
	@Test
	public void testFortify() {
		TerritoryModel model1 = new TerritoryModel(201, "tname1", 10, 20, new ContinentModel(301, "cname1", 3));
		TerritoryModel model2 = new TerritoryModel(202, "tname2", 30, 40, new ContinentModel(301, "cname1", 3));

		TerritoryModel tmodel3 = new TerritoryModel(203, "tname3", 30, 40, new ContinentModel(301, "cname1", 3));
		model1.addAdjacentTerritory(tmodel3);

		// Any other phase
		gameModel.setState(Constants.ATTACK_PHASE);
		assertEquals("", playerModel.fortify(gameModel, model1));

		// Fortification phase but selected territory doesn't belong to player
		PlayerModel playerModelOther = new PlayerModel(102, "player2");
		playerModelOther.setStrategy(new Human(playerModelOther));
		model1.setPlayerModel(playerModelOther);
		tmodel3.setPlayerModel(playerModelOther);
		gameModel.setState(Constants.FORTIFICATION_PHASE);
		assertEquals(Constants.MOVE_FROM, playerModel.fortify(gameModel, model1));

		// selected territory doesn't have minimum 2 armies to do fortification move
		model1.setPlayerModel(playerModel);
		model1.setArmies(1);
		assertEquals(Constants.MIN_TWO_ARMY_MESSAGE, playerModel.fortify(gameModel, model1));

		// selected territory is valid to move armies from
		model1.setPlayerModel(playerModel);
		model1.setArmies(3);
		assertEquals(Constants.MOVE_TO + model1.getName(), playerModel.fortify(gameModel, model1));
		assertEquals(Constants.FORTIFYING_PHASE, gameModel.getState());
		assertEquals(model1.getName(), gameModel.getMoveArmiesFromTerritory().getName());

		// selected territory doesn't belongs to player to move armies to
		model2.setPlayerModel(playerModelOther);
		assertEquals(Constants.MOVE_TO + model1.getName(), playerModel.fortify(gameModel, model2));

		// selected territory belongs to player but not adjacent
		model2.setPlayerModel(playerModel);
		assertEquals(Constants.MOVE_TO + model1.getName(), playerModel.fortify(gameModel, model2));

		// valid territory to move to
		model1.addAdjacentTerritory(model2);
		gameModel.setState(Constants.FORTIFYING_PHASE);
		assertEquals(Constants.ARMIES_TO_MOVE, playerModel.fortify(gameModel, model2));
		assertEquals(Constants.FORTIFY_PHASE, gameModel.getState());
		assertEquals(model2.getName(), gameModel.getMoveArmiesToTerritory().getName());
	}

	/**
	 * Test case to test armies assigned in exchange to card trade.
	 */
	@Test
	public void testcardTradeActionPerformed() {
		TerritoryModel tmodel1 = new TerritoryModel(201, "tname1", 10, 20, new ContinentModel(301, "cname1", 3));
		tmodel1.setPlayerModel(playerModel);
		CardModel card1 = new CardModel(1, Constants.ARMY_TYPE_INFANTRY, tmodel1);
		CardModel card2 = new CardModel(1, Constants.ARMY_TYPE_ARTILLERY, tmodel1);
		CardModel card3 = new CardModel(1, Constants.ARMY_TYPE_CAVALRY, tmodel1);
		CardModel card4 = new CardModel(1, Constants.ARMY_TYPE_WILD, tmodel1);

		Vector<CardModel> cards = new Vector<>();
		cards.addElement(card1);
		cards.addElement(card2);
		cards.addElement(card3);
		cards.addElement(card4);
		playerModel.addCards(cards);

		// first set trading
		playerModel.cardTradeActionPerformed(gameModel, 1, 1, 1, 0);
 
		assertEquals(Constants.FOUR, playerModel.getArmies());

		// second set trading (6+4)
		playerModel.cardTradeActionPerformed(gameModel, 2, 0, 0, 1);
		assertEquals(Constants.TEN, playerModel.getArmies());

		// seventh set trading
		gameModel.setCardTradeCount(Constants.SIX);
		playerModel.cardTradeActionPerformed(gameModel, 1, 1, 0, 1);
		assertEquals(Constants.THIRTY, playerModel.getArmies());
	}
	
	/**
	 * Test case to Checks whether card trade is must for the current turn
	 * 
	 * @returns true if trading mandatory
	 */
	@Test
	public void testIsCardTradeRequired() {
		TerritoryModel territory = new TerritoryModel(201, "tname1", 10, 20, new ContinentModel(301, "cname1", 3));
		territory.setPlayerModel(playerModel);
		CardModel card1 = new CardModel(1, Constants.ARMY_TYPE_INFANTRY, territory);
		CardModel card2 = new CardModel(2, Constants.ARMY_TYPE_ARTILLERY, territory);
		CardModel card3 = new CardModel(3, Constants.ARMY_TYPE_CAVALRY, territory);
		CardModel card4 = new CardModel(4, Constants.ARMY_TYPE_WILD, territory);
		CardModel card5 = new CardModel(5, Constants.ARMY_TYPE_WILD, territory);
		Vector<CardModel> cards = new Vector<>();
		cards.addElement(card1);
		cards.addElement(card2);
		cards.addElement(card3);
		cards.addElement(card4);
		cards.addElement(card5);
		playerModel.addCards(cards);
		assertEquals(true,playerModel.isCardTradeRequired());
	}
	
	
	/**
	 * Test case to Check whether player win the game
	 */
	@Test
	public void testWinningStatus() {
		PlayerModel playerModel1 = new PlayerModel(101, "player1");
		PlayerModel playerModel2 = new PlayerModel(102, "player2");

		ContinentModel continent = new ContinentModel(1, "C1", 2);
		TerritoryModel territory1 = new TerritoryModel(1, "T1", 10, 20, continent);
		TerritoryModel territory2 = new TerritoryModel(2, "T2", 50, 70, continent);
		TerritoryModel territory3 = new TerritoryModel(3, "T3", 100, 100, continent);

		territory1.setPlayerModel(playerModel1);
		territory2.setPlayerModel(playerModel1);
		territory3.setPlayerModel(playerModel2);

		Vector<TerritoryModel> territorries = new Vector<>();
		territorries.add(territory1);
		territorries.add(territory2);
		territorries.add(territory3);

		gameModel.setTerritories(territorries);
		gameModel.setCurrentPlayer(playerModel1);

		assertFalse(playerModel.winningStatus(gameModel));

		territory3.setPlayerModel(playerModel1);
		assertTrue(playerModel.winningStatus(gameModel));
	}
}
