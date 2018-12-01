package col.cs.risk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.IllegalComponentStateException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.Transient;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import col.cs.risk.controller.StartGameController;

public class GameModeSettingsView extends JFrame {

	/** SingleMode JButton */
	private JButton singleMode;
	
	/** Tournament Mode JButton */
	private JButton tournamentMode;
	
	/** Panel Instance */
	private JPanel startJPanel;

	/** Game controller */
	private StartGameController startGameController;

	public GameModeSettingsView() {
		setTitle("Mode Choice");
	}

	/**
	 * Assigns the StartGameController object and Calls the initComponents 
	 * function to initialize all the components
	 * 
	 * @param gameController instance of the StartGameCOntroler Class
	 */
	public GameModeSettingsView(StartGameController gameController) {
		this();
		this.startGameController = gameController;

		initComponents();
	}

	/**
	 * Function to initialize components required for the view/UI. This includes
	 * the Background color and other components such as the buttons and Panel
	 * 
	 */
	public void initComponents() {

		singleMode = new JButton();
		tournamentMode = new JButton();
		startJPanel = new JPanel();
		startJPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		setBackground(new Color(1, 1, 1));
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				exitForm(event);
			}
		});
		startJPanel.setName("startJPanel");

		singleMode.setName("singlemode");
		singleMode.setText("Single Mode");
		
		/* Action Performed for Button Selection */
		singleMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				setVisible(false);
				startGameController.singleModePlayerSettings();
			}
		});

		tournamentMode.setName("tournamentmode");
		tournamentMode.setText("Tournament Mode");
		
		/* Action Performed for Button Selection */
		tournamentMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {

				System.out.println("Tournament Mode Selected");
				setVisible(false);
				startGameController.tournamentMode();
			}
		});

		loadModePage();

	}

	/**
	 * Combines all the components in the JFrames/JPanel and adds it
	 * to the group layout along with alignment and color attributes.
	 * 
	 */
	private void loadModePage() {

	 GroupLayout groupLayout = new GroupLayout(startJPanel);
		startJPanel.setLayout(groupLayout);

		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(100)
										.addGroup(groupLayout.createParallelGroup(Alignment.CENTER).addGap(100)
												.addComponent(singleMode, GroupLayout.DEFAULT_SIZE, 100, 500).addGap(75)
												.addComponent(tournamentMode, GroupLayout.DEFAULT_SIZE, 100, 500)
												.addGap(100).addGap(50))
										.addContainerGap().addGap(100)))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap().addGap(50)
						.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE).addComponent(singleMode)
						.addGap(75).addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
						.addComponent(tournamentMode).addGap(100).addContainerGap()));

		add(startJPanel, BorderLayout.CENTER);
		pack();
	}

	
	/**
	 * Sets the Background color of the calling Panel
	 * 
	 * @param bgColor is the object for the Color Class
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
	 * Function transfers the control back to the Controller Class
	 * 
	 * @param event Event Instance
	 */
	private void exitForm(java.awt.event.WindowEvent event) {
		setVisible(false);
		startGameController.setHomePageVisiblility(true);
	}
}
