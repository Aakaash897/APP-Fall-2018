package test.col.cs.risk.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import col.cs.risk.controller.GameController;

/**
 * This class Game Controller test the main driver functions
 * that handles the game phases.
 * 
 * @author Team25
 *
 */
public class GameControllerTest {

	/**
	 * Test case to check whether the round is first or not.
	 */
	@Test
	public void testIsFirstRound() {
		GameController gameController = new GameController(false);
		
		gameController.noOfRoundsCompleted = 0;
		assertTrue("Failed test", gameController.isFirstRound());

		gameController.noOfRoundsCompleted = 1;
		assertFalse("Failed test", gameController.isFirstRound());
	}
}