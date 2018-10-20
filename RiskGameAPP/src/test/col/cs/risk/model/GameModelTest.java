package test.col.cs.risk.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import col.cs.risk.helper.MapException;
import col.cs.risk.model.Constants;
import col.cs.risk.model.ContinentModel;
import col.cs.risk.model.GameModel;
import col.cs.risk.model.PlayerModel;
import col.cs.risk.model.TerritoryModel;

/**
 * Test cases for Game Model
 * 
 * @author Team25
 *
 */
public class GameModelTest {

	/** Game model instance */
	GameModel gameModel;

	/** Map file contents as String */
	StringBuilder mapString;

	/**
	 * Initialization before every test case
	 */
	@Before
	public void before() {
		gameModel = new GameModel();

		mapString = new StringBuilder();
		mapString.append("[Map]\nauthor=Sean O'Connor\n");
		mapString.append("[Continents]\nNorth America=5\n");
		mapString.append("South America=2\n");
		mapString.append("[Territories]\nAlaska,70,126,North America,Northwest Territory\n");
		mapString.append("Northwest Territory,148,127,North America,Alaska\n");
		mapString.append("Venezuala,259,303,South America,Alaska\n");
	}

	/**
	 * DeInitialization after every test case
	 */
	@After
	public void after() {
		gameModel = null;
		mapString = null;
	}

	/**
	 * Test case to check whether all tags are correct in map file
	 */
	@Test
	public void testisTagsCorrect() {
		String invalidMapString = (mapString.toString()).replace("Territories", "Hi");

		assertTrue(gameModel.isTagsCorrect(mapString.toString()));
		assertFalse(gameModel.isTagsCorrect(invalidMapString));
	}

	/**
	 * Test case to check whether all territories are connected in a map file
	 */
	@Test
	public void testisAllTerritoriesConnected() {
		String invalidMapString = (mapString.toString()).substring(0, mapString.length() - 7);

		assertTrue(gameModel.isAllTerritoriesConnected(mapString.toString()));
		assertFalse(gameModel.isAllTerritoriesConnected(invalidMapString));
	}

	/**
	 * Test case to check that whether all countries belong to the defined set of
	 * continents in map file
	 */
	@Test
	public void testcheckContinentsAreValid() {
		String invalidMapString = (mapString.toString()).replaceFirst("South America", "Hi");

		assertTrue(gameModel.checkContinentsAreValid(mapString.toString()));
		assertFalse(gameModel.checkContinentsAreValid(invalidMapString));
	}

	/**
	 * Test case to check that whether all countries belong to the defined set of
	 * continents in map file
	 */
	@Test
	public void testreadFile() {
		String fileName = "currMap.map";
		mapString = gameModel.readFile(mapString, fileName);

		assertTrue(mapString.length() > 0 ? true : false);
	}

	/**
	 * Test case to check that whether player is added
	 */
	@Test
	public void testaddPlayer() {
		int size = GameModel.getPlayers().size();
		gameModel.addPlayer(101, "playerName");
		// size increase by one
		assertTrue(GameModel.getPlayers().size() == (size + 1) ? true : false);
		assertFalse(GameModel.getPlayers().size() <= size ? true : false);
	}

	/**
	 * Test case to test if the armies have been moved
	 */
	@Test
	public void testmoveArmies() {
		TerritoryModel model1 = new TerritoryModel(201, "tname1", 10, 20, new ContinentModel(301, "cname1", 2));
		model1.setArmies(5);
		TerritoryModel model2 = new TerritoryModel(202, "tname2", 30, 40, new ContinentModel(302, "cname2", 3));
		model2.setArmies(5);
		Vector<TerritoryModel> vector = new Vector<>();
		vector.add(model1);
		vector.add(model2);
		gameModel.setTerritories(vector);
		gameModel.setMoveArmiesFromTerritory(model1);
		gameModel.setMoveArmiesToTerritory(model2);
		gameModel.setNoOfArmiesToMove(1);

		// moved armies successfully
		assertTrue(gameModel.moveArmies());
		assertEquals(4, model1.getArmies());
		assertEquals(6, model2.getArmies());

		// armies not moved
		gameModel.setNoOfArmiesToMove(8);
		assertFalse(gameModel.moveArmies());
		assertEquals(4, model1.getArmies());
		assertEquals(6, model2.getArmies());
	}

	/**
	 * Test case to test if the player changes turn wise
	 */
	@Test
	public void testNextPlayer() {
		PlayerModel playerModel = new PlayerModel(1, "name");
		gameModel.setCurrentPlayer(playerModel);
		Vector<PlayerModel> playerVector = new Vector<>();
		for (int i = 1; i < 5; i++) {
			PlayerModel tempPlayerModel = new PlayerModel(1, "name");
			playerVector.add(tempPlayerModel);

		}
		gameModel.setPlayers(playerVector);
		assertTrue(gameModel.getCurrentPlayer() == playerModel ? true : false);
	}

	/**
	 * Test case for calculating territory bonus
	 */
	@Test
	public void testterritoryBonus() {

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
		PlayerModel playerModel = new PlayerModel(101, "player1");
		gameModel.setCurrentPlayer(playerModel);
		playerModel.setOccupiedTerritories(territories);

		assertEquals(4, gameModel.territoryBonus());

		territories.remove(0);
		territories.remove(0);
		territories.remove(0);
		assertEquals(3, gameModel.territoryBonus());

		territories.remove(0);
		assertEquals(3, gameModel.territoryBonus());
	}

	/**
	 * Test case for complete connection of game map
	 */
	@Test
	public void testIsCompleteConnectionExist() {
		ContinentModel continent = new ContinentModel(1, "C1", 2);
		TerritoryModel model1 = new TerritoryModel(1, "T1", 10, 10, continent);
		TerritoryModel model2 = new TerritoryModel(2, "T2", 10, 20, continent);
		TerritoryModel model3 = new TerritoryModel(3, "T3", 20, 10, continent);
		TerritoryModel model4 = new TerritoryModel(4, "T4", 20, 20, continent);

		model1.addAdjacentTerritory(model2);
		model2.addAdjacentTerritory(model3);
		model3.addAdjacentTerritory(model4);
		model4.addAdjacentTerritory(model1);
		
		Vector<TerritoryModel> territories = new Vector<>();
		territories.add(model1);
		territories.add(model2);
		territories.add(model3);
		territories.add(model4);
		gameModel.setTerritories(territories);

		try {
			boolean isValid = gameModel.isCompleteConnectionExist();
			assertTrue(isValid);
		} catch (MapException e) {
			e.printStackTrace();
		}

		model1.addAdjacentTerritory(model2);
		model2.addAdjacentTerritory(model1);
		model3.addAdjacentTerritory(model4);
		model4.addAdjacentTerritory(model3);

		territories.removeAllElements();
		territories.add(model1);
		territories.add(model2);
		territories.add(model3);
		territories.add(model4);
		gameModel.setTerritories(territories);

		try {
			gameModel.isCompleteConnectionExist();
		} catch (MapException ex) {
			assertEquals(Constants.NOT_COMPLETE_CONNECTED_MAP_MESSAGE, ex.getMessage());
		}
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

		gameModel.addTurnBonusToCurrentPlayer();

		// 2+3 (2- continent bonus and 3 territory bonus)
		assertEquals(5, playerModel.getArmies());

		playerModel.setArmies(0);
		PlayerModel playerModel1 = new PlayerModel(2, "P2");
		model4.setPlayerModel(playerModel1);

		gameModel.addTurnBonusToCurrentPlayer();

		// 3 (0- continent bonus and 3 territory bonus)
		assertEquals(3, playerModel.getArmies());

	}
}
