package col.cs.risk.model;

import java.util.Vector;
import java.util.stream.Collectors;

/**
 * This class keeps record of the territory details as well as getter and setter
 * methods for the territory and as well as the methods for printing the
 * territory list and to check if the territoty isoccupied or not.
 * 
 * @author Team25
 *
 */
public class TerritoryModel {

	/** territory id */
	private int id;
	
	/** territory name */
	private String name;
	
	/** territory x position */
	private int x_pos;
	
	/** territory y position */
	private int y_pos;
	
	/** continent it belongs to */
	private ContinentModel continent;
	
	/** adjacent territories it connected to */
	private Vector<TerritoryModel> adjacentTerritories;
	
	/** Player occupied this territory */
	private PlayerModel playerModel;
	
	/** no of armies on this territory */
	private int armies;

	/**
	 * Constructor with five parameters
	 * @param id territory id
	 * @param name territory name
	 * @param x_pos territory x position
	 * @param y_pos territory y position
	 * @param continent continent it belongs to
	 */
	public TerritoryModel(int id, String name, int x_pos, int y_pos, ContinentModel continent) {
		this.id = id;
		this.name = name;
		this.x_pos = x_pos;
		this.y_pos = y_pos;
		this.continent = continent;
		adjacentTerritories = new Vector<>();
	}

	/**
	 * Constructor with six parameters
	 * @param id territory id
	 * @param name territory name
	 * @param x_pos territory x position
	 * @param y_pos territory y position
	 * @param continent continent it belongs to
	 * @param adjacentTerritories list of adjacent territories
	 */
	public TerritoryModel(int id, String name, int x_pos, int y_pos, 
			ContinentModel continent, 
			Vector<TerritoryModel> adjacentTerritories) {
		this(id, name, x_pos, y_pos, continent);
		this.adjacentTerritories = adjacentTerritories;
	}

	/**
	 * Checks whether is territory occupied
	 * @return boolean
	 */
	public boolean isOccupied() {
		boolean isOccupied = true;
		if(playerModel == null) {
			isOccupied = false;
		}
		return isOccupied;
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

	/**
	 * @return the playerModel
	 */
	public PlayerModel getPlayerModel() {
		return playerModel;
	}

	/**
	 * @param playerModel the playerModel to set
	 */
	public void setPlayerModel(PlayerModel playerModel) {
		this.playerModel = playerModel;
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
	 * @param armies
	 */
	public void addArmies(int armies) {
		this.armies += armies;
	}
	
	/**
	 * 
	 * @param armies
	 */
	public void looseArmies(int armies) {
		this.armies -= armies;
	}
	
	/**
	 * add an army
	 */
	public void addArmy() {
		armies++;
	}
	
	/**
	 * loose an army
	 */
	public void looseArmy() {
		armies--;
	}
	
	/**
	 * Add a territory to the list of existing adjacent territories
	 * @param territoryModel
	 */
	public void addAdjacentTerritory(TerritoryModel territoryModel) {
		if(adjacentTerritories == null) {
			adjacentTerritories = new Vector<>();
		}
		adjacentTerritories.add(territoryModel);
	}

	/**
	 * Print all territory details
	 * @return territory details as string
	 */
	public String printTerritory() {
		return "territory_id = "+this.id+" , t_name = "
				+this.name+", t_continent_id = "+continent.getId() + " Adjasent territory id = "
				+adjacentTerritories.stream().map(x->x.toString()).collect(Collectors.toList())
				+" name = "
				+adjacentTerritories.stream().map(x->x.getName()).collect(Collectors.toList());
	}

}
