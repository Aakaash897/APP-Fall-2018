package col.cs.risk.model;

import java.util.Vector;
import java.util.stream.Collectors;

/**
 * 
 * @author Team
 * Territory details
 *
 */
public class TerritoryModel {
	private int id;
	private String name;
	private int x_pos;
	private int y_pos;
	private int continentID;
	private ContinentModel continent;
	private Vector<TerritoryModel> adjacentTerritories;

	/**
	 * 
	 * @param id
	 * @param name
	 * @param x_pos
	 * @param y_pos
	 * @param continentID
	 * @param continent
	 */
	public TerritoryModel(int id, String name, int x_pos, int y_pos, int continentID, ContinentModel continent) {
		this.id = id;
		this.name = name;
		this.x_pos = x_pos;
		this.y_pos = y_pos;
		this.continentID = continentID;
		this.continent = continent;
		adjacentTerritories = new Vector<>();
	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param x_pos
	 * @param y_pos
	 * @param continentID
	 * @param continent
	 * @param adjacentTerritoryIDs
	 * @param adjacentTerritories
	 */
	public TerritoryModel(int id, String name, int x_pos, int y_pos, 
			int continentID, ContinentModel continent, 
			Vector<Integer> adjacentTerritoryIDs, Vector<TerritoryModel> adjacentTerritories) {
		this(id, name, x_pos, y_pos, continentID, continent);
		this.adjacentTerritories = adjacentTerritories;
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
	 * @return the x_pos
	 */
	public int getX_pos() {
		return x_pos;
	}

	/**
	 * @param x_pos the x_pos to set
	 */
	public void setX_pos(int x_pos) {
		this.x_pos = x_pos;
	}

	/**
	 * @return the y_pos
	 */
	public int getY_pos() {
		return y_pos;
	}

	/**
	 * @param y_pos the y_pos to set
	 */
	public void setY_pos(int y_pos) {
		this.y_pos = y_pos;
	}

	/**
	 * @return the continentID
	 */
	public int getContinentID() {
		return continentID;
	}

	/**
	 * @param continentID the continentID to set
	 */
	public void setContinentID(int continentID) {
		this.continentID = continentID;
	}

	/**
	 * @return the continent
	 */
	public ContinentModel getContinent() {
		return continent;
	}

	/**
	 * @param continent the continent to set
	 */
	public void setContinent(ContinentModel continent) {
		this.continent = continent;
	}

	/**
	 * @return the adjacentTerritories
	 */
	public Vector<TerritoryModel> getAdjacentTerritories() {
		return adjacentTerritories;
	}

	/**
	 * @param adjacentTerritories the adjacentTerritories to set
	 */
	public void setAdjacentTerritories(Vector<TerritoryModel> adjacentTerritories) {
		this.adjacentTerritories = adjacentTerritories;
	}

	public String printTerritory() {
		return "territory_id = "+this.id+" , t_name = "
				+this.name+", t_continent_id = "+this.continentID + " Adjasent territory id = "
				+adjacentTerritories.stream().map(x->x.toString()).collect(Collectors.toList())
				+" name = "
				+adjacentTerritories.stream().map(x->x.getName()).collect(Collectors.toList());
	}

}
