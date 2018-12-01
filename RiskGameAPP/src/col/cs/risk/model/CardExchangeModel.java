package col.cs.risk.model;

import java.util.Observable;

import col.cs.risk.controller.GameController;
import col.cs.risk.model.strategy.Human;

/**
 * This model class handles the card exchange entities like current players,
 * which cards players will exchnage.it keeps the record for that things.
 * 
 * @author Team25
 *
 */
public class CardExchangeModel extends Observable {

	/** game model */
	private PlayerModel currentPlayer;

	/** instance of this class */
	private static CardExchangeModel cardExchangeModel;

	/**
	 * 
	 * @returns instance of CardExchangeModel
	 */
	public static CardExchangeModel getInstance() {
		if (cardExchangeModel == null) {
			cardExchangeModel = new CardExchangeModel();
		}
		return cardExchangeModel;
	}
	
	/**
	 * Checks whether the instance variable initialized
	 * @returns true if initialized
	 */
	public static boolean isInitialized() {
		return cardExchangeModel != null ? true : false;
	}

	/**
	 * Notifies observers waiting for state change
	 * 
	 * @param gameController
	 */
	public void isChanged(GameController gameController) {
		setChanged();
		notifyObservers(gameController);
	}

	/**
	 * Notifies the observers if card trade action needs to be taken
	 * 
	 * @param gameController
	 * @param isOpted
	 */
	public void checkCardsTradeOption(GameController gameController, boolean isOpted) {
		this.currentPlayer = gameController.getGameModel().getCurrentPlayer();
		if (isOpted || gameController.getGameModel().getCurrentPlayer().isCardTradeRequired()) {
			gameController.getGameModel().setState(Constants.CARD_TRADE);
			gameController.getGameModel().notifyPhaseChanging();
			if (isOpted && !currentPlayer.isCardTradeMandatory()) {
				currentPlayer.setCardTradeMandatory(false);
			} else {
				currentPlayer.setCardTradeMandatory(true);
			}

			if(this.currentPlayer.getStrategy() instanceof Human) {
				isChanged(gameController);
			}
		} else {
			currentPlayer.setCardTradeMandatory(false);
			gameController.handleReinforcement1();
		}
	}

	/**
	 * @return the currentPlayer
	 */
	public PlayerModel getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * @param currentPlayer
	 *            the currentPlayer to set
	 */
	public void setCurrentPlayer(PlayerModel currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	/**
	 * De-initialize the object
	 */
	public static void clear() {
		if(cardExchangeModel != null) {
			cardExchangeModel = null;
		}
	}
}
