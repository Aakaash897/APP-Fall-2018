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
	 * @param territoryNumber indicates the territory number
	 * @param cardType for the card type
	 */
	public CardModel(String cardType, int territory) {
		super();
		this.cardType = cardType;
		this.territory = territory;
	}

}
