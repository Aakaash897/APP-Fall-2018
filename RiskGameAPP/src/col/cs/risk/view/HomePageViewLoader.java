package col.cs.risk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.IllegalComponentStateException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;

import col.cs.risk.controller.StartGameController;
import col.cs.risk.helper.Utility;

/**
 * 
 * @author Team Loads the home page
 *
 */
public class HomePageViewLoader extends Frame {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 5937936098745280534L;

	/** Initial game panel */
	private JPanel startJPanel;

	/** start game button */
	private JButton startGameButton;

	/** map construct button */
	private JButton constructMapButton;

	/** load existing map button */
	private JButton loadExistingMapButton;

	/** exit button */
	private JButton exitButton;

	/** game image */
	private JLabel gameImage;

	/**
	 * Game controller
	 */
	private StartGameController startController;

	/**
	 * Utility Object
	 */

	public HomePageViewLoader(StartGameController gameController) {
		this.startController = gameController;
		gameController.setHomePageViewLoader(this);
		initComponents();

	}

	/**
	 * Initialize the components
	 */
	public void initComponents() {
		startJPanel = new JPanel();
		startGameButton = new JButton();
		constructMapButton = new JButton();
		loadExistingMapButton = new JButton();
		exitButton = new JButton();
		gameImage = new JLabel();

		startJPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		setBackground(new Color(1, 1, 1));
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				exitForm(event);
			}
		});
		startJPanel.setName("startJPanel");

		startGameButton.setName("startGameButton");
		startGameButton.setText("Start Default Game");
		startGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				startController.startGameButtonActionPerformed(event);
			}
		});

		constructMapButton.setName("constructMapButton");
		constructMapButton.setText("Construct Map");
		constructMapButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				startController.constructMapButtonActionPerformed(event);
			}
		});

		loadExistingMapButton.setName("loadExistingMapButton");
		loadExistingMapButton.setText("Load Existing Map");
		loadExistingMapButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				startController.loadExistingMapButtonActionPerformed(event);
			}
		});

		exitButton.setName("exitButton");
		exitButton.setText("Exit");
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				startController.exitButtonActionPerformed(event);
			}
		});

		gameImage.setIcon(new ImageIcon(Utility.getImagePath("game_logo.jpg")));
		gameImage.setName("gameImage");

		loadHomePage();

	}

	/**
	 * 
	 * @param startController
	 *            Loads the Game home page
	 */
	private void loadHomePage() {

		GroupLayout groupLayout = new GroupLayout(startJPanel);
		startJPanel.setLayout(groupLayout);

		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addComponent(gameImage).addContainerGap(10,
								Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup().addGap(100)
								.addGroup(groupLayout.createParallelGroup(Alignment.CENTER)
										.addComponent(startGameButton, GroupLayout.DEFAULT_SIZE, 100, 500).addGap(50)
										.addComponent(constructMapButton, GroupLayout.DEFAULT_SIZE, 100, 500).addGap(50)
										.addComponent(loadExistingMapButton, GroupLayout.DEFAULT_SIZE, 100, 500)
										.addGap(50).addComponent(exitButton, GroupLayout.DEFAULT_SIZE, 100, 500)
										.addGap(50))
								.addContainerGap()))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap().addComponent(gameImage)
				.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE).addComponent(startGameButton)
				.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE).addComponent(constructMapButton)
				.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE).addComponent(loadExistingMapButton)
				.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE).addComponent(exitButton)
				.addContainerGap()));

		add(startJPanel, BorderLayout.CENTER);
		pack();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setBackground(Color bgColor) {
		synchronized (getTreeLock()) {
			if ((bgColor != null) && (bgColor.getAlpha() < 255) && !isUndecorated()) {
				throw new IllegalComponentStateException("The frame is decorated");
			}
			super.setBackground(bgColor);
		}
	}

	/**
	 * Exit the Application.
	 *
	 * @param event
	 *            the event
	 */
	private void exitForm(java.awt.event.WindowEvent event) {
		System.exit(0);
	}
}