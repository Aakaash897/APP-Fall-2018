package col.cs.risk.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import col.cs.risk.controller.PlayerSettingsController;

/**
 * It handles the display of player settings screen such as the number of
 * players required and a button to accept the changes made.
 * 
 * @author Team25
 * 
 *
 */
public class PlayerSettingsView extends JFrame {

	/** Serial version id */
	private static final long serialVersionUID = 1L;

	/** Player settings screen */
	private JPanel playerPanel;

	/** Finish button */
	private JButton finish;

	/** Player Controller */
	private PlayerSettingsController playerSettingsController;

	/**
	 * Default Constructor
	 */
	public PlayerSettingsView() {
		setTitle("Player settings");
	}

	/**
	 * Constructor to initialize components for this class and start the JPanel
	 * 
	 * @param playerController
	 */
	public PlayerSettingsView(PlayerSettingsController playerController) {
		this();
		this.playerSettingsController = playerController;
		initComponents();
		setLocationRelativeTo(null);
		playerController.setPlayerSettingsView(this);
	}

	/**
	 * Initialize components relating to the playerSettingsview which allows the
	 * user to choose from the options on the screen
	 */
	private void initComponents() {
		playerPanel = new JPanel();
		finish = new JButton();

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				exitForm(evt);
			}
		});

		Integer[] playersList = { 2, 3, 4, 5, 6 };
		JComboBox<Integer> players = new JComboBox<>(playersList);
		players.setSelectedItem(3);
		players.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				playerSettingsController.noOfPlayersActionPerformed(event);
			}
		});

		playerPanel.setBackground(new java.awt.Color(1, 1, 1));
		playerPanel.setName("playerPanel");

		finish.setText("Finish");
		finish.setName("Finish");
		finish.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				playerSettingsController.finishActionPerformed(evt);
			}

		});

		JLabel settings = new JLabel("Player settings");
		settings.setName("settings");
		settings.setHorizontalAlignment(SwingConstants.CENTER);
		settings.setVerticalAlignment(SwingConstants.CENTER);
		settings.setForeground(new Color(254, 254, 254));
		settings.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));

		JLabel playerSelection = new JLabel();
		playerSelection.setText("Enter the number of players");
		playerSelection.setName("playerSelection");
		playerSelection.setForeground(new Color(254, 254, 254));

		GroupLayout playerLayout = new javax.swing.GroupLayout(playerPanel);
		playerLayout.setHorizontalGroup(playerLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(playerLayout.createSequentialGroup().addContainerGap().addGap(200)
						.addComponent(settings, 30, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(20)
						.addContainerGap())
				.addGroup(playerLayout.createSequentialGroup().addGap(61)
						.addComponent(playerSelection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(50)
						.addComponent(players, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(243, Short.MAX_VALUE))

				.addGroup(playerLayout.createSequentialGroup().addContainerGap(443, Short.MAX_VALUE)
						.addComponent(finish, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE).addGap(26)
						.addContainerGap()));
		playerLayout.setVerticalGroup(playerLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(playerLayout.createSequentialGroup().addContainerGap().addContainerGap().addGap(15).addGap(37)
						.addComponent(settings).addGap(29).addContainerGap().addGap(25).addContainerGap()
						.addGroup(playerLayout.createParallelGroup(Alignment.BASELINE).addComponent(playerSelection)
								.addGap(20).addComponent(players, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap().addGap(50).addGap(37).addComponent(finish).addGap(29).addContainerGap()));

		playerPanel.setLayout(playerLayout);
		add(playerPanel, java.awt.BorderLayout.CENTER);
		pack();
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
		playerSettingsController.getStartGameController().setHomePageVisiblility(true);
	}

}
