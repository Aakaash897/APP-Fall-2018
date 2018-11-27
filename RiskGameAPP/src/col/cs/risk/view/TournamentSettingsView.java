package col.cs.risk.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import col.cs.risk.controller.StartGameController;
import col.cs.risk.model.Constants;
import col.cs.risk.model.GameModel;



public class TournamentSettingsView extends JFrame {

	private JPanel Panel;

	int numOfMaps=1; 

	int numOfPlayers=2;

	int noOfGames = 1;

	int noOfTurns=10;

	HashMap<Integer, String> playersStrategiesMap = new HashMap<>();
	
	Vector<String> mapChoiceForTournament = new Vector<>();

	/** Game controller */
	private StartGameController startGameController;

	public TournamentSettingsView(StartGameController gameController) {
		this();
		this.startGameController = gameController;
		initComponents();
		setLocationRelativeTo(null);
	}

	public TournamentSettingsView() {
		setTitle("Tounament Mode Choice");
	}

	private void initComponents() {

		Panel = new javax.swing.JPanel();
		Panel.setForeground(Color.BLACK);
		String[] behaviour = {Constants.AGGRESSIVE, Constants.BENEVOLENT, Constants.RANDOM, Constants.CHEATER};
		JComboBox<String> playerOneBehavior = new JComboBox<String>(behaviour);
		JComboBox<String> playerTwoBehavior = new JComboBox<String>(behaviour);
		JComboBox<String> playerThreeBehavior = new JComboBox<String>(behaviour);
		JComboBox<String> playerFourBehavior = new JComboBox<String>(behaviour);
		
		Integer[] numberOfMaps = { 1, 2, 3, 4, 5 };
		JComboBox<Integer> numberOfMapsCombo = new JComboBox<Integer>(numberOfMaps);
		String[] mapSelection = {Constants.WORLD,Constants.CLIFF,Constants.ICELAND,Constants.TEXAS,Constants.MEXICO};
		JComboBox<String> mapOne = new JComboBox<String>(mapSelection);
		JComboBox<String> mapTwo = new JComboBox<String>(mapSelection);
		JComboBox<String> mapThree = new JComboBox<String>(mapSelection);
		JComboBox<String> mapFour = new JComboBox<String>(mapSelection);
		JComboBox<String> mapFive = new JComboBox<String>(mapSelection);
		mapTwo.setSelectedItem(Constants.CLIFF);
		mapThree.setSelectedItem(Constants.ICELAND);
		mapFour.setSelectedItem(Constants.TEXAS);
		mapFive.setSelectedItem(Constants.MEXICO);
		
		Integer[] numberOfStrategies = { 2, 3, 4 };
		JComboBox<Integer> playerNumCombo = new JComboBox<Integer>(numberOfStrategies);
		Integer[] numberOfGames = { 1, 2, 3, 4, 5 };
		JComboBox<Integer> gameNumCombo = new JComboBox<Integer>(numberOfGames);
		JComboBox<Integer> turnNumCombobox = new JComboBox<Integer>();
		for (int i = 10; i <= 50; i++) {
			turnNumCombobox.addItem(new Integer(i));
		}

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				exitForm(event);
			}
		});

		Panel.setBackground(new java.awt.Color(1, 1, 1));
		Panel.setName("jPanel1");

		JLabel descriptionLabel = new JLabel("Choose tournament option values to proceed ");
		descriptionLabel.setForeground(Color.WHITE);

		JLabel mapLabel = new JLabel("Number of maps");
		mapLabel.setForeground(Color.WHITE);
		
		JLabel mapOneLabel = new JLabel("Map 1");
		mapOneLabel.setForeground(Color.WHITE);

		JLabel mapTwoLabel = new JLabel("Map 2");
		mapTwoLabel.setForeground(Color.WHITE);

		JLabel mapThreeLabel = new JLabel("Map 3");
		mapThreeLabel.setForeground(Color.WHITE);

		JLabel mapFourLabel = new JLabel("Map 4");
		mapFourLabel.setForeground(Color.WHITE);
		
		JLabel mapFiveLabel = new JLabel("Map 5");
		mapFiveLabel.setForeground(Color.WHITE);

		JLabel strategiesLabel = new JLabel("Number of player strategies");
		strategiesLabel.setForeground(Color.WHITE);

		JLabel gameLabel = new JLabel("Number of games");
		gameLabel.setForeground(Color.WHITE);

		JLabel numberOfTurnsLabel = new JLabel("Number of turns allowed");
		numberOfTurnsLabel.setForeground(Color.WHITE);

		JLabel playerOne = new JLabel("Player 1");
		playerOne.setForeground(Color.WHITE);

		JLabel playerTwo = new JLabel("Player 2");
		playerTwo.setForeground(Color.WHITE);

		JLabel playerThree = new JLabel("Player 3");
		playerThree.setForeground(Color.WHITE);

		JLabel playerFour = new JLabel("Player 4");
		playerFour.setForeground(Color.WHITE);
		
		mapOne.setVisible(false);
		mapOneLabel.setVisible(false);
		mapTwo.setVisible(false);
		mapTwoLabel.setVisible(false);
		mapThree.setVisible(false);
		mapThreeLabel.setVisible(false);
		mapFour.setVisible(false);
		mapFourLabel.setVisible(false);
		mapFive.setVisible(false);
		mapFiveLabel.setVisible(false);
		

		playerOneBehavior.setVisible(false);
		playerOne.setVisible(false);
		playerTwoBehavior.setVisible(false);
		playerTwo.setVisible(false);
		playerThreeBehavior.setVisible(false);
		playerThree.setVisible(false);
		playerFour.setVisible(false);
		playerFourBehavior.setVisible(false);

		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(numOfMaps>noOfGames)
				{
					JOptionPane.showMessageDialog(Panel, "Number of Maps CANNOT be higer than Number of Games Being Played");
					System.out.println("Number of Maps CANNOT be higer than Number of Games Being Played");
				}
				else {
				System.out.println("OK Pressed");
				setVisible(false);
				
				GameModel.tournamentMapList = mapChoiceForTournament;
				System.out.println(" playersStrategiesMap key = "+playersStrategiesMap.keySet().stream().map(x->x.toString()).collect(Collectors.toList()));
				System.out.println(" playersStrategiesMap val = "+playersStrategiesMap.values().stream().map(x->x.toString()).collect(Collectors.toList()));

				GameModel.isTournamentMode = true;
				GameModel.tournamentNoOfMaps = numOfMaps;
				GameModel.tournamentNoOfGame = noOfGames;
				GameModel.tournamentNoOfTurns = noOfTurns;
				startGameController.tournamentModeOKButtonActionPerformed(numOfPlayers, playersStrategiesMap);
				dispose();
				}
			}

		});
		
		mapChoiceForTournament.add(Constants.WORLD);

		numberOfMapsCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				numOfMaps = (Integer) numberOfMapsCombo.getSelectedItem();
				System.out.println("numOfMaps = "+numOfMaps);
				
				if (numOfMaps == 1 )
				{
					mapChoiceForTournament.clear();
					mapChoiceForTournament.add(Constants.WORLD);
					mapOne.setVisible(true);
					mapOneLabel.setVisible(true);
					mapTwo.setVisible(false);
					mapTwoLabel.setVisible(false);
					mapThree.setVisible(false);
					mapThreeLabel.setVisible(false);
					mapFour.setVisible(false);
					mapFourLabel.setVisible(false);
					mapFive.setVisible(false);
					mapFiveLabel.setVisible(false);
					
				}
				
				if (numOfMaps == 2 )
				{
					mapChoiceForTournament.clear();
					mapChoiceForTournament.add(Constants.WORLD);
					mapChoiceForTournament.add(Constants.CLIFF);
					mapOne.setVisible(true);
					mapOneLabel.setVisible(true);
					mapTwo.setVisible(true);
					mapTwoLabel.setVisible(true);
					mapThree.setVisible(false);
					mapThreeLabel.setVisible(false);
					mapFour.setVisible(false);
					mapFourLabel.setVisible(false);
					mapFive.setVisible(false);
					mapFiveLabel.setVisible(false);
					
				}
				
				if (numOfMaps == 3 )
				{
					mapChoiceForTournament.clear();
					mapChoiceForTournament.add(Constants.WORLD);
					mapChoiceForTournament.add(Constants.CLIFF);
					mapChoiceForTournament.add(Constants.ICELAND);
					mapOne.setVisible(true);
					mapOneLabel.setVisible(true);
					mapTwo.setVisible(true);
					mapTwoLabel.setVisible(true);
					mapThree.setVisible(true);
					mapThreeLabel.setVisible(true);
					mapFour.setVisible(false);
					mapFourLabel.setVisible(false);
					mapFive.setVisible(false);
					mapFiveLabel.setVisible(false);
					
				}
				
				if (numOfMaps == 4 )
				{
					mapChoiceForTournament.clear();
					mapChoiceForTournament.add(Constants.WORLD);
					mapChoiceForTournament.add(Constants.CLIFF);
					mapChoiceForTournament.add(Constants.ICELAND);
					mapChoiceForTournament.add(Constants.MEXICO);
					mapOne.setVisible(true);
					mapOneLabel.setVisible(true);
					mapTwo.setVisible(true);
					mapTwoLabel.setVisible(true);
					mapThree.setVisible(true);
					mapThreeLabel.setVisible(true);
					mapFour.setVisible(true);
					mapFourLabel.setVisible(true);
					mapFive.setVisible(false);
					mapFiveLabel.setVisible(false);
					
				}
				
				if (numOfMaps == 5 )
				{
					mapChoiceForTournament.clear();
					mapChoiceForTournament.add(Constants.WORLD);
					mapChoiceForTournament.add(Constants.CLIFF);
					mapChoiceForTournament.add(Constants.ICELAND);
					mapChoiceForTournament.add(Constants.MEXICO);
					mapChoiceForTournament.add(Constants.TEXAS);
					mapOne.setVisible(true);
					mapOneLabel.setVisible(true);
					mapTwo.setVisible(true);
					mapTwoLabel.setVisible(true);
					mapThree.setVisible(true);
					mapThreeLabel.setVisible(true);
					mapFour.setVisible(true);
					mapFourLabel.setVisible(true);
					mapFive.setVisible(true);
					mapFiveLabel.setVisible(true);
					
				}
				
			}
		});
		mapOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println(mapOne.getSelectedItem());
				mapChoiceForTournament.add(0, (String) mapOne.getSelectedItem());
			}
		});
		mapTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				System.out.println( mapTwo.getSelectedItem());
				mapChoiceForTournament.add(1, (String) mapTwo.getSelectedItem());
			}
		});
		mapThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				System.out.println( mapThree.getSelectedItem());
				mapChoiceForTournament.add(2, (String) mapThree.getSelectedItem());
			}
		});
		mapFour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				System.out.println( mapFour.getSelectedItem());
				mapChoiceForTournament.add(3, (String) mapFour.getSelectedItem());
			}
		});
		mapFive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				System.out.println( mapFive.getSelectedItem());
				mapChoiceForTournament.add(4, (String) mapFive.getSelectedItem());
			}
		});

		turnNumCombobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noOfTurns = (Integer) turnNumCombobox.getSelectedItem();
				System.out.println("noOfTurns = "+noOfTurns);
			}
		});

		gameNumCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noOfGames = (Integer) gameNumCombo.getSelectedItem();
				System.out.println("noOfGames = "+noOfGames);
			}
		});
		
		playersStrategiesMap.put(1, Constants.AGGRESSIVE);
		playersStrategiesMap.put(2, Constants.AGGRESSIVE);
		
		playerNumCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				numOfPlayers = (Integer) playerNumCombo.getSelectedItem();
				System.out.println("numOfPlayers = "+numOfPlayers);

				if (numOfPlayers == 2) {
					playersStrategiesMap.clear();
					playersStrategiesMap.put(1, Constants.AGGRESSIVE);
					playersStrategiesMap.put(2, Constants.AGGRESSIVE);
					playerOneBehavior.setVisible(true);
					playerOne.setVisible(true);
					playerTwoBehavior.setVisible(true);
					playerTwo.setVisible(true);
					playerThreeBehavior.setVisible(false);
					playerThree.setVisible(false);
					playerFour.setVisible(false);
					playerFourBehavior.setVisible(false);

				}
				if (numOfPlayers == 3) {
					playersStrategiesMap.clear();
					playersStrategiesMap.put(1, Constants.AGGRESSIVE);
					playersStrategiesMap.put(2, Constants.AGGRESSIVE);
					playersStrategiesMap.put(3, Constants.AGGRESSIVE);
					playerOneBehavior.setVisible(true);
					playerOne.setVisible(true);
					playerTwoBehavior.setVisible(true);
					playerTwo.setVisible(true);
					playerThreeBehavior.setVisible(true);
					playerThree.setVisible(true);
					playerFour.setVisible(false);
					playerFourBehavior.setVisible(false);
				}
				if (numOfPlayers == 4) {
					playersStrategiesMap.clear();
					playersStrategiesMap.put(1, Constants.AGGRESSIVE);
					playersStrategiesMap.put(2, Constants.AGGRESSIVE);
					playersStrategiesMap.put(3, Constants.AGGRESSIVE);
					playersStrategiesMap.put(4, Constants.AGGRESSIVE);
					playerOneBehavior.setVisible(true);
					playerOne.setVisible(true);
					playerTwoBehavior.setVisible(true);
					playerTwo.setVisible(true);
					playerThreeBehavior.setVisible(true);
					playerThree.setVisible(true);
					playerFour.setVisible(true);
					playerFourBehavior.setVisible(true);
				}
			}
		});
		playerOneBehavior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println(playerOneBehavior.getSelectedItem());
				playersStrategiesMap.put(1, (String) playerOneBehavior.getSelectedItem());
			}
		});
		playerTwoBehavior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				System.out.println( playerTwoBehavior.getSelectedItem());
				playersStrategiesMap.put(2, (String) playerTwoBehavior.getSelectedItem());
			}
		});
		playerThreeBehavior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				System.out.println( playerThreeBehavior.getSelectedItem());
				playersStrategiesMap.put(3, (String) playerThreeBehavior.getSelectedItem());
			}
		});
		playerFourBehavior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				System.out.println( playerFourBehavior.getSelectedItem());
				playersStrategiesMap.put(4, (String) playerFourBehavior.getSelectedItem());
			}
		});



		javax.swing.GroupLayout gl_modePanel = new javax.swing.GroupLayout(Panel);
		gl_modePanel.setHorizontalGroup(
				gl_modePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_modePanel.createSequentialGroup()
						.addGap(156)
						.addComponent(descriptionLabel)
						.addContainerGap(218, Short.MAX_VALUE))
				.addGroup(gl_modePanel.createSequentialGroup()
						.addGap(88)
						.addGroup(gl_modePanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_modePanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_modePanel.createSequentialGroup()
												.addComponent(mapFiveLabel, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
												.addContainerGap())
										.addGroup(gl_modePanel.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_modePanel.createSequentialGroup()
														.addComponent(mapFourLabel, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
														.addContainerGap())
												.addGroup(gl_modePanel.createParallelGroup(Alignment.LEADING)
														.addGroup(gl_modePanel.createSequentialGroup()
																.addComponent(mapThreeLabel, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
																.addContainerGap())
														.addGroup(gl_modePanel.createParallelGroup(Alignment.LEADING)
																.addGroup(gl_modePanel.createSequentialGroup()
																		.addComponent(mapTwoLabel, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
																		.addContainerGap())
																.addGroup(gl_modePanel.createParallelGroup(Alignment.LEADING)
																		.addGroup(gl_modePanel.createSequentialGroup()
																				.addComponent(mapOneLabel, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
																				.addContainerGap())
						.addGroup(gl_modePanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_modePanel.createSequentialGroup()
										.addComponent(playerFour, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
										.addContainerGap())
								.addGroup(gl_modePanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_modePanel.createSequentialGroup()
												.addComponent(playerThree, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
												.addContainerGap())
										.addGroup(gl_modePanel.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_modePanel.createSequentialGroup()
														.addComponent(playerTwo, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
														.addContainerGap())
												.addGroup(gl_modePanel.createParallelGroup(Alignment.LEADING)
														.addGroup(gl_modePanel.createSequentialGroup()
																.addComponent(playerOne, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
																.addContainerGap())
														.addGroup(gl_modePanel.createSequentialGroup()
																.addGroup(gl_modePanel.createParallelGroup(Alignment.LEADING)
																		.addComponent(mapLabel)
																		.addComponent(strategiesLabel, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
																		.addComponent(gameLabel, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
																		.addComponent(numberOfTurnsLabel, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
																.addGap(69)
																.addGroup(gl_modePanel.createParallelGroup(Alignment.LEADING)
																		.addGroup(gl_modePanel.createSequentialGroup()
																				.addComponent(gameNumCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																				.addContainerGap())
																		.addGroup(gl_modePanel.createParallelGroup(Alignment.LEADING)
																				.addGroup(gl_modePanel.createSequentialGroup()
																						.addComponent(turnNumCombobox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																						.addContainerGap())
																				.addGroup(gl_modePanel.createParallelGroup(Alignment.LEADING)
																						.addGroup(gl_modePanel.createSequentialGroup()
																								.addComponent(playerNumCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																								.addContainerGap())
																						.addGroup(gl_modePanel.createParallelGroup(Alignment.TRAILING)
																								.addGroup(gl_modePanel.createSequentialGroup()
																										.addComponent(numberOfMapsCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																										.addContainerGap(305, Short.MAX_VALUE))
																								.addGroup(gl_modePanel.createSequentialGroup()
																										.addComponent(okButton)
																										.addGap(72))))
																				.addGroup(gl_modePanel.createSequentialGroup()
																						.addGroup(gl_modePanel.createParallelGroup(Alignment.TRAILING)
																								.addComponent(mapOne, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																								.addComponent(mapTwo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																								.addComponent(mapThree, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																								.addComponent(mapFour, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																								.addComponent(mapFive, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																								.addGroup(gl_modePanel.createSequentialGroup()
																										.addComponent(numberOfMapsCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																										.addGap(45)))
																				.addGroup(gl_modePanel.createSequentialGroup()
																						.addGroup(gl_modePanel.createParallelGroup(Alignment.TRAILING)
																								.addComponent(playerOneBehavior, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																								.addComponent(playerTwoBehavior, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																								.addComponent(playerThreeBehavior, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																								.addComponent(playerFourBehavior, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																								.addGroup(gl_modePanel.createSequentialGroup()
																										.addComponent(playerNumCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																										.addGap(45)))
																						.addGap(372)))))))))))))))))
				);
		gl_modePanel.setVerticalGroup(
				gl_modePanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_modePanel.createSequentialGroup()
						.addGap(28)
						.addComponent(descriptionLabel)
						.addGap(38)
						.addGroup(gl_modePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(mapLabel)
								.addComponent(numberOfMapsCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_modePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(strategiesLabel)
								.addComponent(playerNumCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGap(18)
						.addGroup(gl_modePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(gameLabel)
								.addComponent(gameNumCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(24)
						.addGroup(gl_modePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(numberOfTurnsLabel)
								.addComponent(turnNumCombobox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						
						.addGap(18)
						.addGroup(gl_modePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(mapOneLabel)
								.addComponent(mapOne, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_modePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(mapTwoLabel)
								.addComponent(mapTwo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_modePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(mapThreeLabel)
								.addComponent(mapThree, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_modePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(mapFourLabel)
								.addComponent(mapFour, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_modePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(mapFiveLabel)
								.addComponent(mapFive, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
																
						.addGap(18)
						.addGroup(gl_modePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(playerOne)
								.addComponent(playerOneBehavior, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_modePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(playerTwo)
								.addComponent(playerTwoBehavior, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_modePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(playerThree)
								.addComponent(playerThreeBehavior, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_modePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(playerFour)
								.addComponent(playerFourBehavior, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(okButton)
						.addGap(200).addContainerGap())
				);
		Panel.setLayout(gl_modePanel);

		add(Panel, java.awt.BorderLayout.CENTER);

		pack();
	}

	private void exitForm(java.awt.event.WindowEvent event) {
		setVisible(false);
		startGameController.chooseMode();
		dispose();
	}

}

