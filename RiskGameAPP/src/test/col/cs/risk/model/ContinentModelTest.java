package test.col.cs.risk.model;

import org.junit.Before;

import col.cs.risk.model.ContinentModel;

public class ContinentModelTest {

	private ContinentModel continentModel;

	@Before
	public void before() {
		continentModel = new ContinentModel(1, "cName", 1);
	}
}
