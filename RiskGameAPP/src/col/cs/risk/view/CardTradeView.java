package col.cs.risk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
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

import col.cs.risk.controller.GameController;
import col.cs.risk.helper.Utility;
import col.cs.risk.model.CardModel;
import col.cs.risk.model.Constants;

public class CardTradeView  extends JFrame {

	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 1L;

	private JPanel cardPanel;
	
	/** Infantry image label */
	JLabel infantryLabel;

	JLabel cavalryLabel;

	JLabel artilleryLabel;

	JLabel wildLabel;
	
	/** infantry image card */
	ImageIcon infantry;
	
	ImageIcon cavalry;
	
	ImageIcon artillery;
	
	ImageIcon wild;

	/** infantry cards available with player */
	JComboBox<Integer> infantryCard;

	JComboBox<Integer> cavalryCard;

	JComboBox<Integer> artilleryCard;

	JComboBox<Integer> wildCard;

	JButton okButton;
	
	JButton showCards;

	/** Game model instance */
	GameController gameController;

	public CardTradeView() {
		setTitle("Cards trade screen");
		setVisible(false);
	}

	public CardTradeView(GameController gameController) {
		this();
		this.gameController = gameController;
		initializeComponents();
	}
	
	public static void main(String []args) {
		CardTradeView view = new CardTradeView();
		view.gameController = new GameController();
		view.initializeComponents();
		view.showCardExchangeView();
		
	}

	private void initializeComponents() {
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

		cardPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		setBackground(new Color(1, 1, 1));
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				exitForm();
			}
		});

		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if(gameController.isValidNoOfCardsTraded()) {
					gameController.cardTradeActionPerformed(event);
				}
			}
		});
		//okButton.setBounds(300, 200, 50, 30);
		cardPanel.add(okButton);
		okButton.setVisible(true);
		
		showCards = new JButton("Show cards");
		showCards.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				
				List<String> values = gameController.getGameModel().getCurrentPlayer().getCards().stream()
						.map(x->x.getType() + "-" + (x.getTerritoryModel()!=null ?
								x.getTerritoryModel().getName() : "no territory")).collect(Collectors.toList());
				Utility.showMessagePopUp(String.join(", ", values), "Card Information");

			}
		});
		//showCards.setBounds(300, 200, 50, 30);
		cardPanel.add(showCards);
		showCards.setVisible(true);

		GroupLayout groupLayout = new GroupLayout(cardPanel);
		cardPanel.setLayout(groupLayout);

		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(61)
						.addComponent(infantryLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 
								GroupLayout.PREFERRED_SIZE).addGap(50)
						.addComponent(cavalryLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addGap(50)
						.addComponent(artilleryLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addGap(50)
						.addComponent(wildLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(50, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup().addGap(70)
						.addComponent(infantryCard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 
								GroupLayout.PREFERRED_SIZE).addGap(95)
						.addComponent(cavalryCard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addGap(100)
						.addComponent(artilleryCard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addGap(100)
						.addComponent(wildCard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup().addGap(150)
						.addComponent(showCards).addGap(150)
						.addComponent(okButton).addGap(50)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addContainerGap()
						.addGap(15)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(infantryLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).addGap(50)
								.addComponent(cavalryLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).addGap(50)
								.addComponent(artilleryLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).addGap(50)
								.addComponent(wildLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).addGap(50))
						.addContainerGap() .addGap(50)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(infantryCard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).addGap(50)
								.addComponent(cavalryCard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).addGap(50)
								.addComponent(artilleryCard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).addGap(50)
								.addComponent(wildCard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).addGap(50))
						.addContainerGap().addGap(30)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(showCards)
								.addComponent(okButton))
						.addContainerGap().addGap(50)));

		add(cardPanel, BorderLayout.CENTER);
		pack();
	}

	private void reset() {
		if(infantryCard!=null) {
			infantryCard.removeAllItems();
		}
		if(cavalryCard!=null) {
			cavalryCard.removeAllItems();
		}
		if(artilleryCard!=null) {
			artilleryCard.removeAllItems();
		}
		if(wildCard!=null) {
			wildCard.removeAllItems();
		}
	}

	public void showCardExchangeView() {
		if(gameController.getGameModel().getCurrentPlayer().getCards().size() < 3) {
			Utility.showMessagePopUp(Constants.CARD_INVALID_TRADE_MESSAGE +
					gameController.getGameModel().getCurrentPlayer().getCards().size(), "Card Information");
		} else {
			reset();
			setVisible(true);
			int infantryCount = 0;
			int cavalryCount = 0;
			int artilleryCount = 0;
			int wildCount = 0;

			for(CardModel card:gameController.getGameModel().getCurrentPlayer().getCards()) {
				switch(card.getType()) {
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

			if(infantryCount > 0) {
				for(int i=0;i<=infantryCount;i++) {
					infantryCard.addItem(new Integer(i));
				}
			}
			if(cavalryCount > 0) {
				for(int i=0;i<=cavalryCount;i++) {
					cavalryCard.addItem(new Integer(i));
				}
			}
			if(artilleryCount > 0) {
				for(int i=0;i<=artilleryCount;i++) {
					artilleryCard.addItem(new Integer(i));
				}
			} 
			if(wildCount > 0) {
				for(int i=0;i<=wildCount;i++) {
					wildCard.addItem(new Integer(i));
				}
			}
			setVisible(true);
		}
	}

	/**
	 * Exit from card trade screen.
	 *
	 * @param evt the event
	 *            
	 */
	public void exitForm() {
		setVisible(false);
	}

	/**
	 * @return the infantryCard
	 */
	public int getInfantryCardSelectedItem() {
		int value = 0;
		if(infantryCard.getSelectedItem() != null) {
			value = (Integer) infantryCard.getSelectedItem();
		}
		return value;
	}

	/**
	 * @return the cavalryCard
	 */
	public int getCavalryCardSelectedItem() {
		int value = 0;
		if(cavalryCard.getSelectedItem() != null) {
			value = (Integer) cavalryCard.getSelectedItem();
		}
		return value;
	}


	/**
	 * @return the artilleryCard
	 */
	public int getArtilleryCardSelectedItem() {
		int value = 0;
		if(artilleryCard.getSelectedItem() != null) {
			value = (Integer) artilleryCard.getSelectedItem();
		}
		return value;
	}


	/**
	 * @return the wildCard
	 */
	public int getWildCardSelectedItem() {
		int value = 0;
		if(wildCard.getSelectedItem() != null) {
			value = (Integer) wildCard.getSelectedItem();
		}
		return value;
	}

	/*
	 * The first set traded in - 4 armies 
	 * The second set traded in - 6 armies
	 *  The third set traded in - 8 armies 
	 *  The fourth set traded in - 10 armies
		The fifth set traded in - 12 armies
		The sixth set traded in - 15 armies
		from next 5 more
		7th - 20
		8th - 25

	 */

}
