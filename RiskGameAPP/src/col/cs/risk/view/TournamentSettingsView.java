package col.cs.risk.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
		Integer[] numberOfStrategies = { 2, 3, 4 };
		JComboBox<Integer> playerNumCombo = new JComboBox<Integer>(numberOfStrategies);
		Integer[] numberOfGames = { 1, 2, 3, 4, 5 };
		JComboBox<Integer> gameNumCombo = new JComboBox<Integer>(numberOfGames);
		JComboBox turnNumCombobox = new JComboBox();
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
				System.out.println("OK Pressed");
				setVisible(false);
				//needs to set map list
				//GameModel.tournamentMapList
				System.out.println(" playersStrategiesMap key = "+playersStrategiesMap.keySet().stream().map(x->x.toString()).collect(Collectors.toList()));
				System.out.println(" playersStrategiesMap val = "+playersStrategiesMap.values().stream().map(x->x.toString()).collect(Collectors.toList()));

				GameModel.isTournamentMode = true;
				GameModel.tournamentNoOfMaps = numOfMaps;
				GameModel.tournamentNoOfGame = noOfGames;
				GameModel.tournamentNoOfTurns = noOfTurns;
				startGameController.tournamentModeOKButtonActionPerformed(numOfPlayers, playersStrategiesMap);
				dispose();
			}

		});

		numberOfMapsCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				numOfMaps = (int) numberOfMapsCombo.getSelectedItem();
				System.out.println("numOfMaps = "+numOfMaps);
			}
		});

		turnNumCombobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noOfTurns = (int) turnNumCombobox.getSelectedIndex();
				System.out.println("noOfTurns = "+noOfTurns);
			}
		});

		gameNumCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noOfGames = (int) gameNumCombo.getSelectedIndex();
				System.out.println("noOfGames = "+noOfGames);
			}
		});
		
		playersStrategiesMap.put(1, Constants.AGGRESSIVE);
		playersStrategiesMap.put(2, Constants.AGGRESSIVE);

		playerNumCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				numOfPlayers = (int) playerNumCombo.getSelectedItem();
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
																								.addComponent(playerOneBehavior, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																								.addComponent(playerTwoBehavior, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																								.addComponent(playerThreeBehavior, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																								.addComponent(playerFourBehavior, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																								.addGroup(gl_modePanel.createSequentialGroup()
																										.addComponent(playerNumCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																										.addGap(45)))
																						.addGap(372))))))))))
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

