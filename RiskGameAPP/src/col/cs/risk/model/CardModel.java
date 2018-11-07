package col.cs.risk.model;

/**
 * This class keeps record of the Card Details as well as maintains getter and
 * setter methods for selecting the card type and the methods for printing the
 * territory and the card associated with the each territory.
 * 
 * @author Team25
 *
 */
public class CardModel {
	
	private int id;
	
	private String type;
	
	public TerritoryModel territoryModel;

	/**
	 * Instantiates a new card model.
	 * @param type cardType
	 * @param territoryModel indicates the territory
	 */
	public CardModel(int id, String type, TerritoryModel territoryModel) {
		this.id = id;
		this.type = type;
		this.territoryModel = territoryModel;
	}

	/**
	 * Instantiates a new card model.
	 * @param type
	 * @param territoryModel 
	 */
	public CardModel(int id, int type, TerritoryModel territoryModel) {
		this.id = id;
		this.territoryModel = territoryModel;
		this.type = getCardTypeAsString(type);
	}

	public String getCardTypeAsString(int type) {
		String cardType = "";
		switch (type) {
		case 0:
			cardType = Constants.ARMY_TYPE_INFANTRY;
			break;
		case 1:
			cardType = Constants.ARMY_TYPE_CAVALRY;
			break;
		case 2:
			cardType = Constants.ARMY_TYPE_ARTILLERY;
			break;
		default:
			cardType = Constants.ARMY_TYPE_WILD;
			break;
		}
		return cardType;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the territoryModel
	 */
	public TerritoryModel getTerritoryModel() {
		return territoryModel;
	}

	/**
	 * @param territoryModel the territoryModel to set
	 */
	public void setTerritoryModel(TerritoryModel territoryModel) {
		this.territoryModel = territoryModel;
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
}
