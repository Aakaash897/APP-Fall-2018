package test.col.cs.risk.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import col.cs.risk.model.ContinentModel;
import col.cs.risk.model.PlayerModel;
import col.cs.risk.model.TerritoryModel;

public class TerritoryModelTest {
	private TerritoryModel territoryModel;

	@Before
	public void before() {
		territoryModel = new TerritoryModel(201, "tname1", 10, 20, new ContinentModel(301, "cname1", 2));

	}

	@Test
	public void isOccupied() {
		assertFalse(territoryModel.isOccupied());
		PlayerModel playerModel1 = new PlayerModel(2, "pName");
		territoryModel.setPlayerModel(playerModel1);
		assertTrue(territoryModel.isOccupied());
	}
}
