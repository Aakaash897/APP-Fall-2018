package test.col.cs.risk.model;

import static org.junit.Assert.assertFalse;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import col.cs.risk.model.ContinentModel;
import col.cs.risk.model.PlayerModel;
import col.cs.risk.model.TerritoryModel;

/**
 * ContinentModelTest Contains the method for testing the occupacy of the
 * continent.
 * 
 * @author Team25
 * 
 */
public class ContinentModelTest {

	/** continent model object */
	private ContinentModel continentModel;

	/**
	 * Initialization before every test case
	 */
	@Before
	public void before() {
		continentModel = new ContinentModel(1, "cName", 1);
	}

	/**
	 * Test case to check whether the continent is occupied or not
	 */
	@Test
	public void testIsContinentOccupied() {
		TerritoryModel model1 = new TerritoryModel(201, "tname1", 10, 20, new ContinentModel(301, "cname1", 2));
		model1.setArmies(5);
		TerritoryModel model2 = new TerritoryModel(1, "tname2", 30, 40, new ContinentModel(1, "cname2", 3));
		model2.setArmies(5);
		PlayerModel playerModel1 = new PlayerModel(2, "pName");
		model1.setPlayerModel(playerModel1);
		PlayerModel playerModel2 = new PlayerModel(1, "pName");
		model2.setPlayerModel(playerModel2);
		Vector<TerritoryModel> vector = new Vector<>();
		vector.add(model1);
		vector.add(model2);
		continentModel = new ContinentModel(1, "cName", 1, vector);
		assertFalse(continentModel.isContinentOccupiedBy(playerModel1));
		assertFalse(continentModel.isContinentOccupiedBy(playerModel2));
	}
}
