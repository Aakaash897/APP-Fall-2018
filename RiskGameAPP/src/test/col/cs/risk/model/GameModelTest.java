package test.col.cs.risk.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import col.cs.risk.model.GameModel;

/**
 * Test cases for Game Model
 * @author Team
 *
 */
public class GameModelTest {
	
	/** Game Model instance */
	GameModel gameModel;
	
	/** Map String to test*/
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
	 * Test case to check that whether all countries belong to the defined set of continents in map file
	 */
	@Test
	public void testcheckContinentsAreValid() {
		String invalidMapString = (mapString.toString()).replaceFirst("South America", "Hi");
		
		assertTrue(gameModel.checkContinentsAreValid(mapString.toString()));
		assertFalse(gameModel.checkContinentsAreValid(invalidMapString));
	}

}
