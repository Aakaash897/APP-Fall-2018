package col.cs.risk.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import col.cs.risk.controller.StartGameController;



public class tournamentView extends JFrame {
	
	private JPanel Panel;
	
	int numMap,numPlayer;
	
	/** Game controller */
	private StartGameController startGameController;
	
	
	public tournamentView(StartGameController gameController) {
		
		this();
		this.startGameController = gameController;
		initComponents();
		setLocationRelativeTo(null);
	}
	
	public tournamentView() {
		setTitle("Tounament Mode Choice");
	}

	private void initComponents() {
		
		Panel = new javax.swing.JPanel();
		Panel.setForeground(Color.BLACK);
		String[] behaviour = { "Cheater", "Random", "Benevolent", "Aggressive" };
		JComboBox playerOneBehavior = new JComboBox(behaviour);
		JComboBox playerTwoBehavior = new JComboBox(behaviour);
		JComboBox playerThreeBehavior = new JComboBox(behaviour);
		JComboBox playerFourBehavior = new JComboBox(behaviour);
		Integer[] numberOfMaps = { 1, 2, 3, 4, 5 };
		JComboBox numberOfMapsCombo = new JComboBox(numberOfMaps);
		Integer[] numberOfStrategies = { 2, 3, 4 };
		JComboBox playerNumCombo = new JComboBox(numberOfStrategies);
		Integer[] numberOfGames = { 1, 2, 3, 4, 5 };
		JComboBox gameNumCombo = new JComboBox(numberOfGames);
		JComboBox turnNumCombobox = new JComboBox();
		for (int i = 10; i <= 40; i++) {
			turnNumCombobox.addItem(new Integer(i));
		}
		
		Panel.setBackground(new java.awt.Color(1, 1, 1));
		Panel.setName("jPanel1");
		
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("OK Pressed");
				setVisible(false);
				
			}

		});
		
		JLabel descriptionLabel = new JLabel("Choose Proper Values To Proceed ");
		descriptionLabel.setForeground(Color.WHITE);

		JLabel mapLabel = new JLabel("Map");
		mapLabel.setForeground(Color.WHITE);

		JLabel strategiesLabel = new JLabel("Player Strategies");
		strategiesLabel.setForeground(Color.WHITE);

		JLabel gameLabel = new JLabel("Game");
		gameLabel.setForeground(Color.WHITE);

		JLabel numberOfTurnsLabel = new JLabel("Number of Allowed turns");
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
		
		numberOfMapsCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				numMap = (int) numberOfMapsCombo.getSelectedItem();
			}
		});
		
		playerNumCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				numPlayer = (int) playerNumCombo.getSelectedItem();

				if (numPlayer == 2) {

					playerOneBehavior.setVisible(true);
					playerOne.setVisible(true);
					playerTwoBehavior.setVisible(true);
					playerTwo.setVisible(true);
					playerThreeBehavior.setVisible(false);
					playerThree.setVisible(false);
					playerFour.setVisible(false);
					playerFourBehavior.setVisible(false);

				}
				if (numPlayer == 3) {

					playerOneBehavior.setVisible(true);
					playerOne.setVisible(true);
					playerTwoBehavior.setVisible(true);
					playerTwo.setVisible(true);
					playerThreeBehavior.setVisible(true);
					playerThree.setVisible(true);
					playerFour.setVisible(false);
					playerFourBehavior.setVisible(false);
				}
				if (numPlayer == 4) {
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
			}
		});
		playerTwoBehavior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
			
					System.out.println( playerTwoBehavior.getSelectedItem());
			}
		});
		playerThreeBehavior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
					System.out.println( playerThreeBehavior.getSelectedItem());
			}
		});
		playerFourBehavior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
					System.out.println( playerFourBehavior.getSelectedItem());
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
																		.addComponent(strategiesLabel, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
																		.addComponent(gameLabel, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
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
						.addGroup(gl_modePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(gameLabel)
								.addComponent(playerNumCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
						.addGap(21))
				.addGroup(gl_modePanel.createSequentialGroup()
						.addContainerGap(155, Short.MAX_VALUE)
						.addComponent(gameNumCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(226))
				);
		Panel.setLayout(gl_modePanel);

		add(Panel, java.awt.BorderLayout.CENTER);

		pack();
	}
	}

