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
	public String cardType;
	public int territory;

	/**
	 * Instantiates a new card model.
	 *
	 * @param territoryNumber
	 *            indicates the territory number
	 * @param cardType
	 *            for the card type
	 */
	public CardModel(String cardType, int territory) {
		super();
		this.cardType = cardType;
		this.territory = territory;
	}

	/**
	 * Instantiates a new risk card model.
	 *
	 * @param territoryNumber Denotes the ID of the Territory
	 *            
	 * @param cardType Indicates the Type of Card
	 *           
	 */
	public CardModel(int territoryNumber, int cardType) {
		this.territory = territoryNumber;
		switch (cardType) {
		case 0:
			this.cardType = Constants.ARMY_TYPE_INFANTRY;
			break;
		case 1:
			this.cardType = Constants.ARMY_TYPE_CAVALRY;
			break;
		case 2:
			this.cardType = Constants.ARMY_TYPE_ARTILLERY;
			break;
		default:
			this.cardType = Constants.ARMY_TYPE_WILD;
			break;
		}

	}
}
