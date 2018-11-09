package test.col.cs.risk.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suit class for executing all model test cases together in a row.
 * 
 * @author Team25
 */
@RunWith(Suite.class)
@SuiteClasses({GameModelTest.class, PlayerModelTest.class, 
	ContinentModelTest.class, TerritoryModelTest.class})
public class ModelTestSuit {

}
