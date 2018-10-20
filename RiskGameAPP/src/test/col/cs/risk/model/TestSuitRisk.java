package test.col.cs.risk.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.col.cs.risk.controller.StartGameControllerTest;
import test.col.cs.risk.helper.UtilityTest;

/**
 * Test suit class for executing all test cases together.
 * @author Team25
 *
 */
@RunWith(Suite.class)
@SuiteClasses({StartGameControllerTest.class,GameModelTest.class, UtilityTest.class})
public class TestSuitRisk {

}
