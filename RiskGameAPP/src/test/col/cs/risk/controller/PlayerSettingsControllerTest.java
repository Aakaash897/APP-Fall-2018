package test.col.cs.risk.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import col.cs.risk.controller.PlayerSettingsController;
import col.cs.risk.controller.StartGameController;
import col.cs.risk.model.GameModel;

/**
 * PlayerSettingsControllerTest It tests various functions like no of players playing the game.
 * 
 * @author Team25
 *
 */
public class PlayerSettingsControllerTest {

	/**
	 * Test case for testing after setting the players.
	 */
	@Test
	public void testSetPlayers() {
		StartGameController startGameController = new StartGameController();
		PlayerSettingsController playerSettingsController = new PlayerSettingsController(startGameController);
		playerSettingsController.noOfPlayers = 10;
		playerSettingsController.setPlayers();
		assertTrue(GameModel.players.size() == playerSettingsController.noOfPlayers);
	}
}
