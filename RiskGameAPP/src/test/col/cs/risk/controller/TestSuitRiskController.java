package test.col.cs.risk.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.col.cs.risk.controller.StartGameControllerTest;
import test.col.cs.risk.helper.UtilityTest;

/**
 * Test suit class for executing all test cases together in a row.
 * 
 * @author Team25
 */
@RunWith(Suite.class)
@SuiteClasses({StartGameControllerTest.class, GameControllerTest.class, PlayerSettingsControllerTest.class,
	StartGameControllerTest.class})
public class TestSuitRiskController {

}