package col.cs.risk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import col.cs.risk.controller.GameController;
import col.cs.risk.helper.Utility;
import col.cs.risk.model.CardExchangeModel;
import col.cs.risk.model.CardModel;
import col.cs.risk.model.Constants;
import col.cs.risk.model.PlayerModel;

/**
 * This class is responsible for the card exchange UI which allows the user to
 * view the cards available and also let's the user choose among the cards to
 * trade in for armies with proper validation.
 * 
 * @author Team25
 *
 */

public class CardTradeView extends JFrame implements Observer {

	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 1L;

	/** Card panel */
	private JPanel cardPanel;

	/** Infantry image label */
	JLabel infantryLabel;

	/** cavalry image label */
	JLabel cavalryLabel;

	/** artillery image label */
	JLabel artilleryLabel;

	/** wild image label */
	JLabel wildLabel;

	/** infantry image card */
	ImageIcon infantry;

	/** cavalry image card */
	ImageIcon cavalry;

	/** artillery image card */
	ImageIcon artillery;

	/** wild image card */
	ImageIcon wild;

	/** infantry cards available with player */
	JComboBox<Integer> infantryCard;

	/** cavalry cards available with player */
	JComboBox<Integer> cavalryCard;

	/** artillery cards available with player */
	JComboBox<Integer> artilleryCard;

	/** wild cards available with player */
	JComboBox<Integer> wildCard;

	/** ok button */
	JButton okButton;

	/** show cards button */
	JButton showCards;

	/** Game model instance */
	GameController gameController;

	/**
	 * Default Constructor
	 */
	public CardTradeView() {
		setTitle("Cards trade screen");
		setVisible(false);
	}

	/**
	 * Constructor
	 * 
	 * @param gameController
	 *            is the object for the GameController class
	 */
	public CardTradeView(GameController gameController) {
		this();
		this.gameController = gameController;
	}

	/**
	 * Initializes the screen components
	 */
	public void initializeComponents() {
		cardPanel = new JPanel();
		infantryLabel = new JLabel();
		cavalryLabel = new JLabel();
		artilleryLabel = new JLabel();
		wildLabel = new JLabel();

		infantry = new ImageIcon(Utility.getImagePath("infantry.png"));
		cavalry = new ImageIcon(Utility.getImagePath("cavalry.png"));
		artillery = new ImageIcon(Utility.getImagePath("artillery.png"));
		wild = new ImageIcon(Utility.getImagePath("wild.png"));

		cardPanel.setName("constructJPanel");
		cardPanel.setBackground(Color.LIGHT_GRAY);

		infantryLabel.setName("infantry");
		infantryLabel.setIcon(infantry);

		cavalryLabel.setName("cavalry");
		cavalryLabel.setIcon(cavalry);

		artilleryLabel.setName("artillery");
		artilleryLabel.setIcon(artillery);

		wildLabel.setName("wild");
		wildLabel.setIcon(wild);

		infantryCard = new JComboBox<>();
		infantryCard.setVisible(true);

		cavalryCard = new JComboBox<>();
		cavalryCard.setVisible(true);

		artilleryCard = new JComboBox<>();
		artilleryCard.setVisible(true);

		wildCard = new JComboBox<>();
		wildCard.setVisible(true);

		setLocation(400, 200);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		cardPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		setBackground(new Color(1, 1, 1));
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				if (!gameController.getGameModel().getCurrentPlayer().isCardTradeMandatory()) {
					exitForm();
				}
			}
		});

		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (gameController.isValidNoOfCardsTraded()) {
					gameController.cardTradeActionPerformed(event);
				}
			}
		});
		cardPanel.add(okButton);
		okButton.setVisible(true);

		showCards = new JButton("Show cards");
		showCards.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				List<String> values = gameController.getGameModel().getCurrentPlayer().getCards().stream()
						.map(x -> x.getType() + "-"
								+ (x.getTerritoryModel() != null ? x.getTerritoryModel().getName() : "no territory"))
						.collect(Collectors.toList());
				Utility.showMessagePopUp(String.join(", ", values), "Card Information");
			}
		});

		cardPanel.add(showCards);
		showCards.setVisible(true);

		GroupLayout groupLayout = new GroupLayout(cardPanel);
		cardPanel.setLayout(groupLayout);

		groupLayout
				.setHorizontalGroup(
						groupLayout
								.createParallelGroup(
										Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(61)
										.addComponent(infantryLabel, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(50)
										.addComponent(cavalryLabel, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(50)
										.addComponent(artilleryLabel, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(50).addComponent(wildLabel, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addContainerGap(50, Short.MAX_VALUE))
								.addGroup(
										groupLayout.createSequentialGroup().addGap(70)
												.addComponent(infantryCard, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(95)
												.addComponent(cavalryCard, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(100)
												.addComponent(artilleryCard, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(100)
												.addComponent(wildCard, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addContainerGap())
								.addGroup(groupLayout.createSequentialGroup().addGap(150).addComponent(showCards)
										.addGap(150).addComponent(okButton).addGap(50)));
		groupLayout
				.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout
								.createSequentialGroup().addContainerGap().addContainerGap().addGap(
										15)
								.addGroup(
										groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(infantryLabel, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(50)
												.addComponent(cavalryLabel, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(50)
												.addComponent(artilleryLabel, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(50)
												.addComponent(wildLabel, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(50))
								.addContainerGap().addGap(
										50)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(infantryCard, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(50)
										.addComponent(cavalryCard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(50)
										.addComponent(artilleryCard, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(50)
										.addComponent(wildCard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(50))
								.addContainerGap().addGap(30)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(showCards)
										.addComponent(okButton))
								.addContainerGap().addGap(50)));

		add(cardPanel, BorderLayout.CENTER);
		pack();
	}

	/**
	 * Calls reset on the current page/options
	 */
	private void reset() {
		if (infantryCard != null) {
			infantryCard.removeAllItems();
		}
		if (cavalryCard != null) {
			cavalryCard.removeAllItems();
		}
		if (artilleryCard != null) {
			artilleryCard.removeAllItems();
		}
		if (wildCard != null) {
			wildCard.removeAllItems();
		}
	}

	/**
	 * Displays card exchange screen
	 * 
	 * @param currentPlayer
	 */
	public void showCardExchangeView(PlayerModel currentPlayer) {
		if (currentPlayer.getCards().size() < 3) {
			Utility.showMessagePopUp(Constants.CARD_INVALID_TRADE_MESSAGE + currentPlayer.getCards().size(),
					"Card Information");
			gameController.handleReinforcement1();
		} else {
			reset();
			setVisible(true);
			int infantryCount = 0;
			int cavalryCount = 0;
			int artilleryCount = 0;
			int wildCount = 0;

			for (CardModel card : currentPlayer.getCards()) {
				switch (card.getType()) {
				case Constants.ARMY_TYPE_INFANTRY:
					infantryCount++;
					break;
				case Constants.ARMY_TYPE_CAVALRY:
					cavalryCount++;
					break;
				case Constants.ARMY_TYPE_ARTILLERY:
					artilleryCount++;
					break;
				case Constants.ARMY_TYPE_WILD:
					wildCount++;
					break;
				}
			}

			if (infantryCount > 0) {
				for (int i = 0; i <= infantryCount; i++) {
					infantryCard.addItem(new Integer(i));
				}
			}
			if (cavalryCount > 0) {
				for (int i = 0; i <= cavalryCount; i++) {
					cavalryCard.addItem(new Integer(i));
				}
			}
			if (artilleryCount > 0) {
				for (int i = 0; i <= artilleryCount; i++) {
					artilleryCard.addItem(new Integer(i));
				}
			}
			if (wildCount > 0) {
				for (int i = 0; i <= wildCount; i++) {
					wildCard.addItem(new Integer(i));
				}
			}

			setVisible(true);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable obj, Object arg) {
		CardExchangeModel model = (CardExchangeModel) obj;
		GameController controller = (GameController) arg;
		controller.handleCardTrade();
		showCardExchangeView(model.getCurrentPlayer());
	}

	/**
	 * Exit from card trade screen.
	 *
	 * @param evt
	 *            the event
	 * 
	 */
	public void exitForm() {
		setVisible(false);
		reset();
		gameController.handleReinforcement1();
	}

	/**
	 * Function to get the Infantry Card Selected
	 * 
	 * @return the infantryCard
	 */
	public int getInfantryCardSelectedItem() {
		int value = 0;
		if (infantryCard.getSelectedItem() != null) {
			value = (Integer) infantryCard.getSelectedItem();
		}
		return value;
	}

	/**
	 * Function to get the Cavalry Card Selected
	 * 
	 * @return the cavalryCard
	 */
	public int getCavalryCardSelectedItem() {
		int value = 0;
		if (cavalryCard.getSelectedItem() != null) {
			value = (Integer) cavalryCard.getSelectedItem();
		}
		return value;
	}

	/**
	 * Function to get the Artillery Card Selected
	 * 
	 * @return the artilleryCard
	 */
	public int getArtilleryCardSelectedItem() {
		int value = 0;
		if (artilleryCard.getSelectedItem() != null) {
			value = (Integer) artilleryCard.getSelectedItem();
		}
		return value;
	}

	/**
	 * Function to get the Wild Card Selected
	 * 
	 * @return the wildCard
	 */
	public int getWildCardSelectedItem() {
		int value = 0;
		if (wildCard.getSelectedItem() != null) {
			value = (Integer) wildCard.getSelectedItem();
		}
		return value;
	}

	/**
	 * Sets selected items for all cards
	 * 
	 * @param infantryNo
	 * @param cavalryNo
	 * @param artilleryNo
	 * @param wildNo
	 */
	public void setSelectedItem(int infantryNo, int cavalryNo, int artilleryNo, int wildNo) {
		for (int i = 0; i <= Constants.FIVE; i++) {
			infantryCard.addItem(new Integer(i));
			cavalryCard.addItem(new Integer(i));
			artilleryCard.addItem(new Integer(i));
			wildCard.addItem(new Integer(i));
		}
		infantryCard.setSelectedItem(new Integer(infantryNo));
		cavalryCard.setSelectedItem(new Integer(cavalryNo));
		artilleryCard.setSelectedItem(new Integer(artilleryNo));
		wildCard.setSelectedItem(new Integer(wildNo));
	}

}
