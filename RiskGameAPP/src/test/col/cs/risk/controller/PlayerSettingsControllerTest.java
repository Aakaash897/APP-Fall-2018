package test.col.cs.risk.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import col.cs.risk.controller.PlayerSettingsController;
import col.cs.risk.helper.Utility;
import col.cs.risk.model.Constants;
import col.cs.risk.model.GameModel;

/**
 * PlayerSettingsControllerTest It tests various functions like no of players
 * playing the game.
 * 
 * @author Team25
 *
 */
public class PlayerSettingsControllerTest {
	
	/** PlayerSettingsController object */
	PlayerSettingsController playerSettingsController;
	
	/**
	 * Initialization before each test case
	 */
	@Before
	public void set() {
		playerSettingsController = new PlayerSettingsController();
		playerSettingsController = new PlayerSettingsController();
		playerSettingsController.noOfPlayers = 3;
		playerSettingsController.setPlayers();
		Utility.canShow = false;
	}
	
	/**
	 * DeInitialization after each test case
	 */
	@After
	public void remove() {
		playerSettingsController = null;
		Utility.canShow = true;
	}

	/**
	 * Test case for testing after setting the players.
	 */
	@Test
	public void testSetPlayers() {
		assertTrue(GameModel.players.size() == playerSettingsController.noOfPlayers);
	}
	
	/**
	 * Test case for testing settings of strategies
	 */
	@Test
	public void testPlayerStrategy() {
		HashMap<String, String> playersStrategies = new HashMap<>();
		playersStrategies.put(GameModel.getPlayers().get(0).getName(), Constants.HUMAN);
		playersStrategies.put(GameModel.getPlayers().get(1).getName(), Constants.AGGRESSIVE);
		playersStrategies.put(GameModel.getPlayers().get(2).getName(), Constants.BENEVOLENT);
		playerSettingsController.setPlayerStrategyMap(playersStrategies);
		
		playerSettingsController.setPlayersStrategy();
		
		assertEquals(Constants.HUMAN, GameModel.getPlayers().get(0).getStrategy().getStrategyString());
		assertEquals(Constants.BENEVOLENT, GameModel.getPlayers().get(2).getStrategy().getStrategyString());
		
	}
}
