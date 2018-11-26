package col.cs.risk.view;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import col.cs.risk.controller.PlayerSettingsController;
import col.cs.risk.model.Constants;
import col.cs.risk.model.GameModel;
import col.cs.risk.model.PlayerModel; 
/**
 * It handles the display of player Strategies settings screen such as each
 * player Strategy and a button to save the changes made.
 * 
 * @author Team25
 * 
 *
 */
public class PlayerStrategySettingsView extends JFrame implements ActionListener {

	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 1L;

	/** Label to display Players title */
	JLabel playerTitle,currentplayerLabel; 

	/** Button to Save Strategies for Corresponding Player */
	JButton SaveButton; 

	/** Title Player */
	String titlePlayer;

	/**
	 * Combo box for Players Strategies
	 */
	JComboBox<String> StrategyComboBox1,StrategyComboBox2,StrategyComboBox3,StrategyComboBox4,StrategyComboBox5,StrategyComboBox6;

	/** No of Players Playing Game */
	int noOfPlayers;

	/** HashKey and HashValue to Store details in the map */
	String playerType;

	/** Map to store the Players and Corresponding Strategies */
	HashMap<String,String> playersStrategiesMap = new HashMap<>();

	/** Instance for PlayerSettingsController Class */
	private PlayerSettingsController playerSettingsController;

	/**
	 * Constructor to initialize and start the Frame
	 * 
	 * @param noOfPlayers No of Players Playing Game 
	 * @param TitlePlayer Title set as Players
	 */
	public PlayerStrategySettingsView(int noOfPlayers, String TitlePlayer, PlayerSettingsController playerSettingsController){ 
		super();
		this.titlePlayer = TitlePlayer;
		this.noOfPlayers = noOfPlayers;
		this.playerSettingsController = playerSettingsController;

		playerTitle = new JLabel(titlePlayer); 
		setTitle("PlayerStartegySettingsPanel");
		playerTitle.setBounds(150,50,300,20); 
		playerTitle.setForeground(new Color(254, 254, 254));
		playerTitle.setHorizontalAlignment(SwingConstants.CENTER);
		playerTitle.setVerticalAlignment(SwingConstants.CENTER);
		playerTitle.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		add(playerTitle);

		int y = 0;
		for(int i = 1;i<= noOfPlayers;i++)
		{

			currentplayerLabel =new JLabel("player"+i); 
			setTitle("PlayerStartegySettingsPanel");
			currentplayerLabel.setBounds(50,100+y,300,20); 
			currentplayerLabel.setForeground(new Color(254, 254, 254));
			currentplayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
			currentplayerLabel.setVerticalAlignment(SwingConstants.CENTER);
			currentplayerLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
			add(currentplayerLabel);
			y = y+100;
		}

		for(PlayerModel playerModel:GameModel.players) {
			playersStrategiesMap.put(playerModel.getName(), Constants.HUMAN);
		}

		int y1 = 0;
		if(noOfPlayers >= 2)
		{
			StrategyComboBox1 = new JComboBox<String>();
			StrategyComboBox1.addItem(Constants.HUMAN);
			StrategyComboBox1.addItem(Constants.AGGRESSIVE);
			StrategyComboBox1.addItem(Constants.BENEVOLENT);
			StrategyComboBox1.addItem(Constants.RANDOM);
			StrategyComboBox1.addItem(Constants.CHEATER);
			StrategyComboBox1.setVisible(true);
			StrategyComboBox1.setBounds(300,100+y1,200,20);
			y1 = y1+100;
			getContentPane().add(StrategyComboBox1);
			StrategyComboBox1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					playerType = (String) StrategyComboBox1.getSelectedItem();
					playersStrategiesMap.put(GameModel.players.get(0).getName(), playerType);
					System.out.println(playersStrategiesMap.toString());
				}

			});
			StrategyComboBox2 = new JComboBox<String>();
			StrategyComboBox2.addItem(Constants.HUMAN);
			StrategyComboBox2.addItem(Constants.AGGRESSIVE);
			StrategyComboBox2.addItem(Constants.BENEVOLENT);
			StrategyComboBox2.addItem(Constants.RANDOM);
			StrategyComboBox2.addItem(Constants.CHEATER);
			StrategyComboBox2.setVisible(true);
			StrategyComboBox2.setBounds(300,100+y1,200,20);
			getContentPane().add(StrategyComboBox2);
			StrategyComboBox2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					playerType = (String) StrategyComboBox2.getSelectedItem();
					playersStrategiesMap.put(GameModel.players.get(1).getName(), playerType);
					System.out.println(playersStrategiesMap.toString());

				}

			});

		}
		if(noOfPlayers >= 3)
		{
			y1 = y1+100;
			StrategyComboBox3 = new JComboBox<String>();
			StrategyComboBox3.addItem(Constants.HUMAN);
			StrategyComboBox3.addItem(Constants.AGGRESSIVE);
			StrategyComboBox3.addItem(Constants.BENEVOLENT);
			StrategyComboBox3.addItem(Constants.RANDOM);
			StrategyComboBox3.addItem(Constants.CHEATER);
			StrategyComboBox3.setVisible(true);
			StrategyComboBox3.setBounds(300,100+y1,200,20);
			getContentPane().add(StrategyComboBox3);
			StrategyComboBox3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					playerType = (String) StrategyComboBox3.getSelectedItem();
					playersStrategiesMap.put(GameModel.players.get(2).getName(), playerType);
					System.out.println(playersStrategiesMap.toString());

				}

			});

		}

		if(noOfPlayers >= 4)
		{
			y1 = y1+100;
			StrategyComboBox4 = new JComboBox<String>();
			StrategyComboBox4.addItem(Constants.HUMAN);
			StrategyComboBox4.addItem(Constants.AGGRESSIVE);
			StrategyComboBox4.addItem(Constants.BENEVOLENT);
			StrategyComboBox4.addItem(Constants.RANDOM);
			StrategyComboBox4.addItem(Constants.CHEATER);
			StrategyComboBox4.setVisible(true);
			StrategyComboBox4.setBounds(300,100+y1,200,20);
			getContentPane().add(StrategyComboBox4);
			StrategyComboBox4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					playerType = (String) StrategyComboBox4.getSelectedItem();
					playersStrategiesMap.put(GameModel.players.get(3).getName(), playerType);
					System.out.println(playersStrategiesMap.toString());

				}

			});

		}

		if(noOfPlayers >= 5)
		{
			y1 = y1+100;
			StrategyComboBox5 = new JComboBox<String>();
			StrategyComboBox5.addItem(Constants.HUMAN);
			StrategyComboBox5.addItem(Constants.AGGRESSIVE);
			StrategyComboBox5.addItem(Constants.BENEVOLENT);
			StrategyComboBox5.addItem(Constants.RANDOM);
			StrategyComboBox5.addItem(Constants.CHEATER);
			StrategyComboBox5.setVisible(true);
			StrategyComboBox5.setBounds(300,100+y1,200,20);
			getContentPane().add(StrategyComboBox5);
			StrategyComboBox5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					playerType = (String) StrategyComboBox5.getSelectedItem();
					playersStrategiesMap.put(GameModel.players.get(4).getName(), playerType);
					System.out.println(playersStrategiesMap.toString());

				}

			});

		}
		if(noOfPlayers >= 6)
		{
			y1 = y1+100;
			StrategyComboBox6 = new JComboBox<String>();
			StrategyComboBox6.addItem(Constants.HUMAN);
			StrategyComboBox6.addItem(Constants.AGGRESSIVE);
			StrategyComboBox6.addItem(Constants.BENEVOLENT);
			StrategyComboBox6.addItem(Constants.RANDOM);
			StrategyComboBox6.addItem(Constants.CHEATER);
			StrategyComboBox6.setVisible(true);
			StrategyComboBox6.setBounds(300,100+y1,200,20);
			getContentPane().add(StrategyComboBox6);
			StrategyComboBox6.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					playerType = (String) StrategyComboBox6.getSelectedItem();
					playersStrategiesMap.put(GameModel.players.get(5).getName(), playerType);
					System.out.println(playersStrategiesMap.toString());

				}

			});

		}

		SaveButton=new JButton("Save");  
		SaveButton.setBounds(280,100+y,80,30);  
		SaveButton.addActionListener(this); 
		getContentPane().setBackground(new java.awt.Color(1, 1, 1));
		add(SaveButton); 
		setSize(700,1000);  
		setLayout(null);  
		setVisible(true); 

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				exitForm(event);
			}
		});

	}   

	/**
	 * Exit from Player setting screen.
	 *
	 * @param evt
	 *            the event
	 * 
	 */
	private void exitForm(WindowEvent evt) {
		setVisible(false);
		playerSettingsController.setPlayerSettingsVisible(true);
		dispose();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("selected = "+playersStrategiesMap.toString());
		setVisible(false);
		playerSettingsController.playerStrategyTypeSaveActionPerformed(playersStrategiesMap);
		dispose();
	}


}
