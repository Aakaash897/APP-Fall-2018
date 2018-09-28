package col.cs.risk.model;

import java.util.Vector;

/**
 * 
 * @author Team
 * Player details
 *
 */
public class PlayerModel {

	private int id;
	private String name;
	private Vector<TerritoryModel> occupiedTerritories;
	private int armies;
	
	/**
	 * 
	 * @param id
	 * @param name
	 */
	public PlayerModel(int id, String name) {
		this.id = id;
		this.name = name;
		occupiedTerritories = new Vector<>();
	}
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param occupiedTerritories
	 */
	public PlayerModel(int id, String name, Vector<TerritoryModel> occupiedTerritories) {
		this(id,name);
		this.occupiedTerritories = occupiedTerritories;
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
	 * @return the occupiedTerritories
	 */
	public Vector<TerritoryModel> getOccupiedTerritories() {
		return occupiedTerritories;
	}
	
	/**
	 * @param occupiedTerritories the occupiedTerritories to set
	 */
	public void setOccupiedTerritories(Vector<TerritoryModel> occupiedTerritories) {
		this.occupiedTerritories = occupiedTerritories;
	}
	
	/**
	 * @return the armies
	 */
	public int getArmies() {
		return armies;
	}
	
	/**
	 * @param armies the armies to set
	 */
	public void setArmies(int armies) {
		this.armies = armies;
	}
	
	/**
	 * 
	 * @param territoryModel
	 */
	public void addOccupiedTerritory(TerritoryModel territoryModel) {
		if(occupiedTerritories == null) {
			occupiedTerritories = new Vector<>();
		}
		occupiedTerritories.add(territoryModel);
	}
	
}
