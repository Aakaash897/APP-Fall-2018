package col.cs.risk.controller;

import java.awt.Color;
import java.awt.IllegalComponentStateException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import col.cs.risk.view.MapConstructionView;
import col.cs.risk.helper.Utility;
import col.cs.risk.view.HomePageViewLoader;

/**
 * 
 * @author Team
 * Starts the game
 *
 */
public class StartGameController extends java.awt.Frame {

	/** Serial version id */
	private static final long serialVersionUID = 1137729697072494580L;
	
	/** Map construction/modification Page View */
	private MapConstructionView mapConstructionView;

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
	 * Starts the game page(Loads the home page) with multiple options
	 */
	public StartGameController() {
		initComponents();
		HomePageViewLoader.loadHomePage(this);

	}

	private void initComponents() {
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
				startGameButtonActionPerformed(event);				
			}
		});

		constructMapButton.setName("constructMapButton");
		constructMapButton.setText("Construct Map");
		constructMapButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				constructMapButtonActionPerformed(event);				
			}
		});

		loadExistingMapButton.setName("loadExistingMapButton");
		loadExistingMapButton.setText("Load Existing Map");
		loadExistingMapButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				loadExistingMapButtonActionPerformed(event);				
			}
		});

		exitButton.setName("exitButton");
		exitButton.setText("Exit");
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				exitButtonActionPerformed(event);				
			}
		});

		gameImage.setIcon(new ImageIcon(Utility.getImagePath("game_logo.jpg")));
		gameImage.setName("gameImage");

		GroupLayout groupLayout = new GroupLayout(startJPanel);
		startJPanel.setLayout(groupLayout);

	}

	/**
	 * Action performed on pressing start game default button on home page
	 * @param event
	 */
	public void startGameButtonActionPerformed(ActionEvent event) {
		System.out.println(" Start Default Game button pressed ");
	}

	/**
	 * Action performed on pressing construct button on home page
	 * @param event
	 */
	public void constructMapButtonActionPerformed(ActionEvent event) {
		System.out.println(" Construct Map button pressed ");
		setVisible(false);
		new MapConstructionView(this).setVisible(true);
	}

	/**
	 * Action performed on pressing Load existing map button on home page
	 * @param event
	 */
	public void loadExistingMapButtonActionPerformed(ActionEvent event) {
		System.out.println(" Load Existing Map button pressed ");
	}

	/**
	 * Action performed on pressing Exit button on home page
	 * @param event
	 */
	public void exitButtonActionPerformed(ActionEvent event) {
		System.out.println(" Exit button pressed ");
		System.exit(0);
	}

	/**
	 * Start/ entry of the game application
	 * @param args
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new StartGameController().setVisible(true);
			}
		});
	}

	/**
	 * Exit the Application.
	 *
	 * @param event the event
	 */
	private void exitForm(java.awt.event.WindowEvent event) {
		System.exit(0);
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
	 * @return the startJPanel
	 */
	public JPanel getStartJPanel() {
		return startJPanel;
	}

	/**
	 * @param startJPanel the startJPanel to set
	 */
	public void setStartJPanel(JPanel startJPanel) {
		this.startJPanel = startJPanel;
	}

	/**
	 * @return the startGameButton
	 */
	public JButton getStartGameButton() {
		return startGameButton;
	}

	/**
	 * @param startGameButton the startGameButton to set
	 */
	public void setStartGameButton(JButton startGameButton) {
		this.startGameButton = startGameButton;
	}

	/**
	 * @return the constructMapButton
	 */
	public JButton getConstructMapButton() {
		return constructMapButton;
	}

	/**
	 * @param constructMapButton the constructMapButton to set
	 */
	public void setConstructMapButton(JButton constructMapButton) {
		this.constructMapButton = constructMapButton;
	}

	/**
	 * @return the loadExistingMapButton
	 */
	public JButton getLoadExistingMapButton() {
		return loadExistingMapButton;
	}

	/**
	 * @param loadExistingMapButton the loadExistingMapButton to set
	 */
	public void setLoadExistingMapButton(JButton loadExistingMapButton) {
		this.loadExistingMapButton = loadExistingMapButton;
	}

	/**
	 * @return the exitButton
	 */
	public JButton getExitButton() {
		return exitButton;
	}

	/**
	 * @param exitButton the exitButton to set
	 */
	public void setExitButton(JButton exitButton) {
		this.exitButton = exitButton;
	}

	/**
	 * @return the gameImage
	 */
	public JLabel getGameImage() {
		return gameImage;
	}

	/**
	 * @param gameImage the gameImage to set
	 */
	public void setGameImage(JLabel gameImage) {
		this.gameImage = gameImage;
	}

	/**
	 * Action performed on pressing New Map construction Button
	 * @param event
	 */
	public void newMapButtonActionPerformed(ActionEvent event) {
		System.out.println(" Construct New Map button pressed ");
	}
	
	/**
	 * Action performed on pressing Modify Existing Map Button
	 * @param event
	 */
	public void modifyExistingMapButtonActionPerformed(ActionEvent event) {
		System.out.println(" Modify Existing Map button pressed ");
	}
	
	/**
	 * @return the mapConstructionView
	 */
	public MapConstructionView getMapConstructionView() {
		return mapConstructionView;
	}

	/**
	 * @param mapConstructionView the mapConstructionView to set
	 */
	public void setMapConstructionView(MapConstructionView mapConstructionView) {
		this.mapConstructionView = mapConstructionView;
	}
}
