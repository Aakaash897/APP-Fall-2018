package col.cs.risk.model;

import java.util.Vector;

/**
 * Player details
 * @author Team
 *
 */
public class PlayerModel {

	/** player number */
	private int id;

	/** player name */
	private String name;

	/** player occupied territories */
	private Vector<TerritoryModel> occupiedTerritories;

	/** no. of armies with player */
	private int armies;

	/** is fortification done */
	private boolean isFortified;

	/**
	 * Constructor with parameters
	 * @param id player id
	 * @param name player name
	 */
	public PlayerModel(int id, String name) {
		this.id = id;
		this.name = name;
		occupiedTerritories = new Vector<>();
	}

	/**
	 * Constructor with parameters
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
	 * @param armies, add passed armies to the existing armies
	 */
	public void addArmies(int armies) {
		this.armies += armies;
	}

	/**
	 * 
	 * @param armies, subtract passed armies from existing armies
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
	 * Add territory to the list of occupied territory
	 * @param territoryModel
	 */
	public void addOccupiedTerritory(TerritoryModel territoryModel) {
		if(occupiedTerritories == null) {
			occupiedTerritories = new Vector<>();
		}
		occupiedTerritories.add(territoryModel);
	}

	/**
	 * @return the isFortified
	 */
	public boolean isFortified() {
		return isFortified;
	}

	/**
	 * @param isFortified the isFortified to set
	 */
	public void setFortified(boolean isFortified) {
		this.isFortified = isFortified;
	}

	/**
	 * Action on fortification phase for the current player
	 * @param gameModel
	 * @param territoryModel
	 * @return status as string to display
	 */
	public String fortify(GameModel gameModel, TerritoryModel territoryModel) {
		String str = "";
		switch (gameModel.getState()) {
		case Constants.FORTIFICATION_PHASE:
			if (territoryModel.getPlayerModel().getId() == gameModel.getCurrentPlayer().getId()) {
				if(territoryModel.getArmies() > 1) {
					gameModel.setState(Constants.FORTIFYING_PHASE);
					gameModel.setMoveArmiesFromTerritory(territoryModel);
					gameModel.notifyPhaseChange();
					str = Constants.MOVE_TO+gameModel.getMoveArmiesFromTerritory().getName();
				} else {
					str = Constants.MIN_TWO_ARMY_MESSAGE;
				}
			} else {
				str = Constants.MOVE_FROM;
			}
			break;
		case Constants.FORTIFYING_PHASE:
			if (territoryModel.getPlayerModel().getId() == gameModel.getCurrentPlayer().getId() 
			&& gameModel.getMoveArmiesFromTerritory().getAdjacentTerritories().contains(territoryModel)) {
				gameModel.setState(Constants.FORTIFY_PHASE);
				gameModel.setMoveArmiesToTerritory(territoryModel);
				gameModel.notifyPhaseChange();
				str = Constants.ARMIES_TO_MOVE;
			} else {
				str = Constants.MOVE_TO+gameModel.getMoveArmiesFromTerritory().getName();
			}
			break;

		default:
			break;
		}
		return str;
	}

}
