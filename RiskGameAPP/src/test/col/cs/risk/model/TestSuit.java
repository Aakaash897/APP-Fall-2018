package test.col.cs.risk.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.col.cs.risk.controller.StartGameControllerTest;
import test.col.cs.risk.controller.TestSuitRiskController;
import test.col.cs.risk.helper.TestSuitRiskHelper;
import test.col.cs.risk.helper.UtilityTest;

/**
 * Test suit class for executing all test cases together in a row.
 * 
 * @author Team25
 */
@RunWith(Suite.class)
@SuiteClasses({TestSuitRiskController.class,TestSuitRiskHelper.class,TestSuitRisk.class})
public class TestSuit {

}
