package test.col.cs.risk.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.col.cs.risk.controller.ControllerTestSuit;
import test.col.cs.risk.helper.HelperTestSuitRisk;

/**
 * Test suit class for executing all test cases together.
 * 
 * @author Team25
 */
@RunWith(Suite.class)
@SuiteClasses({ControllerTestSuit.class, ModelTestSuit.class, HelperTestSuitRisk.class})
public class TestSuitRisk {

}
