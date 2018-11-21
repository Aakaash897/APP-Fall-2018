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
 * Test cases for Game Model methods regarding the map attributes such as the
 * connectivity, validation and player assignment.
 * 
 * @author Team25
 *
 */
public class GameModelTest {

	/**
	 * Game model instance
	 */
	GameModel gameModel;

	/**
	 * Continent model instance
	 */
	ContinentModel continentModel;

	/**
	 * Map file contents as String
	 */
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
	public void testIsTagsCorrect() {
		String invalidMapString = (mapString.toString()).replace("Territories", "Hi");

		assertTrue(gameModel.isTagsCorrect(mapString.toString()));
		assertFalse(gameModel.isTagsCorrect(invalidMapString));
	}

	/**
	 * Test case to check whether all territories are connected in a map file
	 */
	@Test
	public void testIsAllTerritoriesConnected() {
		String invalidMapString = (mapString.toString()).substring(0, mapString.length() - 7);

		assertTrue(gameModel.isAllTerritoriesHaveAdjacents(mapString.toString()));
		assertFalse(gameModel.isAllTerritoriesHaveAdjacents(invalidMapString));
	}

	/**
	 * Test case to check that whether all countries belong to the defined set of
	 * continents in map file
	 */
	@Test
	public void testCheckContinentsAreValid() {
		String invalidMapString = (mapString.toString()).replaceFirst("South America", "Hi");

		assertTrue(gameModel.isContinentInTerrirotiesValid(mapString.toString()));
		assertFalse(gameModel.isContinentInTerrirotiesValid(invalidMapString));
	}

	/**
	 * Test case to check that whether all countries belong to the defined set of
	 * continents in map file
	 */
	@Test
	public void testReadFile() {
		String fileName = Constants.DEFAULT_MAP_FILE_NAME;
		mapString = gameModel.readFile(mapString, fileName);

		assertTrue(mapString.length() > 0 ? true : false);
	}

	/**
	 * Test case to check that whether player is added
	 */
	@Test
	public void testAddPlayer() {
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
	public void testMoveArmies() {
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
		Vector<PlayerModel> playerVector = new Vector<>();
		for (int i = 0; i < 5; i++) {
			PlayerModel tempPlayerModel = new PlayerModel(i, "name" + i);
			playerVector.add(tempPlayerModel);
		}
		GameModel.players = playerVector;
		// Setting first player as current player
		gameModel.setCurrentPlayer(playerVector.elementAt(0));

		// changing the player
		gameModel.nextPlayer();
		assertTrue(gameModel.getCurrentPlayer() == playerVector.get(1) ? true : false);

		// changing the player
		gameModel.nextPlayer();
		assertTrue(gameModel.getCurrentPlayer() == playerVector.get(2) ? true : false);

		// changing the player
		gameModel.nextPlayer();
		assertTrue(gameModel.getCurrentPlayer() == playerVector.get(3) ? true : false);

		// changing the player
		gameModel.nextPlayer();
		assertTrue(gameModel.getCurrentPlayer() == playerVector.get(4) ? true : false);

		// changing the player
		gameModel.nextPlayer();
		assertTrue(gameModel.getCurrentPlayer() == playerVector.get(0) ? true : false);
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
	 * Test case for getting territory from selected coordinates
	 */
	@Test
	public void testGetTerritoryFromMapLocation() {
		ContinentModel continent = new ContinentModel(1, "C1", 2);
		TerritoryModel territory1 = new TerritoryModel(1, "T1", 10, 20, continent);
		TerritoryModel territory2 = new TerritoryModel(2, "T2", 50, 70, continent);
		TerritoryModel territory3 = new TerritoryModel(3, "T3", 100, 100, continent);

		Vector<TerritoryModel> territorries = new Vector<>();
		territorries.add(territory1);
		territorries.add(territory2);
		territorries.add(territory3);

		gameModel.setTerritories(territorries);

		assertEquals(territory2, gameModel.getTerritoryFromMapLocation(50, 70));
		assertEquals(territory2.getName(), gameModel.getTerritoryFromMapLocation(50, 70).getName());
	}

	/**
	 * Test case for Converting data in models(continent and territory) to string
	 * 
	 * @return String of map data
	 * 
	 */
	@Test
	public void testGetMapContentToWrite() {

		ContinentModel continent = new ContinentModel(1, "C1", 2);
		TerritoryModel territory1 = new TerritoryModel(1, "T1", 10, 20, continent);
		TerritoryModel territory2 = new TerritoryModel(2, "T2", 50, 70, continent);
		TerritoryModel territory3 = new TerritoryModel(3, "T3", 100, 100, continent);
		Vector<TerritoryModel> territorries = new Vector<>();
		territorries.add(territory1);
		territorries.add(territory2);
		territorries.add(territory3);
		continent.setTerritories(territorries);
		Vector<ContinentModel> continents = new Vector<>();
		continents.add(continent);
		gameModel.setContinents(continents);

		String str = "[Map]" + "\n" + "author=Shwetha" + "\n" + "image=currMap.jpg" + "\n" + "wrap=no" + "\n"
				+ "scroll=horizontal" + "\n" + "warn=yes" + "\n\n" + "[Continents]" + "\n" + "C1" + "=" + "2" + "\n"
				+ "\n" + "[Territories]" + "\n" + "T1," + "10," + "20," + "C1" + "\n" + "T2," + "50," + "70," + "C1"
				+ "\n" + "T3," + "100," + "100," + "C1" + "\n" + "\n" + "\n\n\n";

		assertEquals(str, gameModel.getMapContentToWrite());
	}

	/**
	 * Test Case Validates the whether territories belongs to the defined continents
	 * 
	 * @param mapText Map file as string
	 * @return true if all territories belongs to the predefined continents.
	 */
	@Test
	public void isContinentInTerrirotiesValid() {
		assertEquals(true, gameModel.isContinentInTerrirotiesValid(mapString.toString()));

	}

	/**
	 * Test case to test whether the player won if all other players are eliminated
	 */
	@Test
	public void testIsOwn() {
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

		assertFalse(gameModel.isWon());

		territory3.setPlayerModel(playerModel1);
		assertTrue(gameModel.isWon());
	}

	/**
	 * test case to test/validate correct startup phase of game
	 */
	@Test
	public void testGameStartPhase() {
		Vector<PlayerModel> players = new Vector<>();
		for (int i = 0; i < 3; i++) {
			players.add(new PlayerModel(i, "Player_" + (i + 1)));
		}
		GameModel.players = players;

		try {
			GameModel newGameModel = new GameModel();
			newGameModel.initialize();
			
			// game start phase
			assertEquals(Constants.INITIAL_RE_ENFORCEMENT_PHASE, newGameModel.getState());
			// world map countries size
			assertEquals(42, newGameModel.getTerritories().size());
			// 42 + 2 wild card
			assertEquals(44, newGameModel.getCardsDeck().size());
			assertEquals(players.get(0).getName(), newGameModel.getCurrentPlayer().getName());
			// list of countries occupied by each player
			assertEquals(14, newGameModel.getCurrentPlayer().getOccupiedTerritories().size());
			// 35 - 14(used to occupy counties)
			assertEquals(21, newGameModel.getCurrentPlayer().getArmies());

		} catch (MapException e) {
			e.printStackTrace();
		}

	}
}
