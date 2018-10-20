package col.cs.risk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import col.cs.risk.controller.StartGameController;

/**
 * It handles the view of new map construction or existing map modification
 * screen. It corresponds to the action performed by user on this screen.
 * 
 * @author Team25
 *
 */
public class MapConstructionView extends JFrame {

	/** Serial version id */
	private static final long serialVersionUID = -2566255237255639074L;

	/** Initial game panel */
	private JPanel constructJPanel;

	private JLabel settings;

	/** start game button */
	private JButton newMapButton;

	/** map construct button */
	private JButton modifyExistingMapButton;

	/** Game controller */
	private StartGameController startGameController;

	/**
	 * Default Constructor
	 */
	public MapConstructionView() {
		setTitle("Map contrusction options");
	}

	/**
	 * Constructor which initializes GameController
	 * 
	 * @param gameController
	 */
	public MapConstructionView(StartGameController gameController) {
		this();
		this.startGameController = gameController;
		gameController.setMapConstructionView(this);
		initComponents();
	}

	/**
	 * Initialize the components and also loads the Map construction screen
	 */
	private void initComponents() {
		constructJPanel = new JPanel();
		settings = new JLabel();
		newMapButton = new JButton();
		modifyExistingMapButton = new JButton();

		constructJPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		setBackground(new Color(1, 1, 1));
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				exitForm(event);
			}
		});
		constructJPanel.setName("constructJPanel");
		constructJPanel.setBackground(Color.LIGHT_GRAY);

		settings.setText("Map Construction Screen");
		settings.setName("settings");
		settings.setHorizontalAlignment(SwingConstants.CENTER);
		settings.setVerticalAlignment(SwingConstants.CENTER);
		settings.setForeground(Color.BLACK);
		settings.setFont(new Font("Arial", Font.BOLD, 25));

		newMapButton.setName("newMapButton");
		newMapButton.setText("Consruct New Map");
		newMapButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				startGameController.newMapButtonActionPerformed(event);
			}
		});

		modifyExistingMapButton.setName("modifyExistingMapButton");
		modifyExistingMapButton.setText("Modify Existing Map");
		modifyExistingMapButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				startGameController.modifyExistingMapButtonActionPerformed(event);
			}
		});

		GroupLayout groupLayout = new GroupLayout(constructJPanel);
		constructJPanel.setLayout(groupLayout);

		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap().addGap(200)
						.addGroup(groupLayout.createParallelGroup(Alignment.CENTER)
								.addComponent(settings, GroupLayout.DEFAULT_SIZE, 100, 500).addGap(100)
								.addComponent(newMapButton, GroupLayout.DEFAULT_SIZE, 100, 500).addGap(100)
								.addComponent(modifyExistingMapButton, GroupLayout.DEFAULT_SIZE, 100, 500).addGap(100))
						.addGap(200).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE).addComponent(settings)
						.addGap(40).addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
						.addComponent(newMapButton).addGap(50)
						.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
						.addComponent(modifyExistingMapButton).addGap(100).addContainerGap()));

		add(constructJPanel, BorderLayout.CENTER);
		pack();

	}

	/**
	 * Exit from current screen.
	 * 
	 * @param event
	 *            the event
	 */
	private void exitForm(java.awt.event.WindowEvent event) {
		setVisible(false);
		startGameController.exitMapConstructionView();
	}

}
