package col.cs.risk.model;

import java.util.Vector;
import java.util.stream.Collectors;

/**
 * 
 * @author Team
 * Continent details
 * 
 */
public class ContinentModel {
	private int id;
	private String name;
	private int score;
	private Vector<TerritoryModel> territories;
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param score
	 */
	public ContinentModel(int id, String name, int score) {
		this.id = id;
		this.name = name;
		this.score = score;
	}
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param score
	 * @param territoryIDs
	 * @param territories
	 */
	public ContinentModel(int id, String name, int score, Vector<Integer> territoryIDs,
			Vector<TerritoryModel> territories) {
		this(id, name, score);
		this.territories = territories;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the territories
	 */
	public Vector<TerritoryModel> getTerritories() {
		return territories;
	}

	/**
	 * @param territorries the territories to set
	 */
	public void setTerritories(Vector<TerritoryModel> territorries) {
		this.territories = territorries;
	}

	/**
	 * 
	 * @param territory add territory to the list
	 */
	public void addTerritory(TerritoryModel territory) {
		if(territories == null) {
			territories = new Vector<>();
		}
		territories.add(territory);
	}
	
	/**
	 * 
	 * @return Continent in string format
	 */
	public String printContinent() {
		return "id = "+this.id+", name = "+this.name+", score = "+this.score+", territories = "
				+territories.stream().map(x->x.getId()).collect(Collectors.toList());
	}

}
