package test.col.cs.risk.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import col.cs.risk.controller.StartGameController;

/**
 * This class Start Game Controller test the basic
 * cases related to map file before testing the game phases.
 * 
 * @author Team25
 *
 */
public class StartGameControllerTest {
	
	/**
	 * Instance for the StarGameController class
	 */
	StartGameController startGameController;
	
	/** 
	 * String for handling empty or null case
	 */
	String empty; 
	
	/** 
	 * String to store the alphabets
	 */
	String alphabets;
	
	/**
	 *  String to store the numeric data
	 */
	String numbers;
	
	/** 
	 * String to store white and newLine characters
	 */
	String space;

	/**
	 * Initialization before every test case
	 */
	@Before
	public void before() {
		startGameController = new StartGameController();
		empty = "";
		alphabets = "abc\ndef";
		numbers = "123\n456";
		space = "   ";
	}
	
	/**
	 * DeInitialization after every test case
	 */
	@After
	public void after() {
		startGameController = null;
		empty = null;
		alphabets = null;
		numbers = null;
		space = null;
	}
	
	/**
	 * Test case to check whether all tags are correct in map file
	 */
	@Test
	public void testIsEmptyDetails() {
		assertTrue(startGameController.isEmptyDetails(empty));
		assertFalse(startGameController.isEmptyDetails(alphabets));
		assertFalse(startGameController.isEmptyDetails(numbers));
		assertTrue(startGameController.isEmptyDetails(space));
	}
	
	/**
	 * Test case to check whether all tags are correct in map file
	 */
	@Test
	public void testIsSingleLineText() {
		assertTrue(startGameController.isMapContainsSingleCountry(empty));
		assertFalse(startGameController.isMapContainsSingleCountry(alphabets));
		assertFalse(startGameController.isMapContainsSingleCountry(numbers));
		assertTrue(startGameController.isMapContainsSingleCountry(space));
	}

}
