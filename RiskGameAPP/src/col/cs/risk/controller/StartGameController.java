package col.cs.risk.controller;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import col.cs.risk.view.HomePageViewLoader;
import col.cs.risk.view.MapConstructionView;

/**
 * 
 * @author Team
 * Starts the game
 *
 */
public class StartGameController{
	
	/** Game home Page View */
	private HomePageViewLoader homePageViewLoader;
	
	/** Map construction/modification Page View */
	private MapConstructionView mapConstructionView;
	
	/**
	 * Starts the game page(Loads the home page) with multiple options
	 */
	public StartGameController() {
		new HomePageViewLoader(this).setVisible(true);
	}

	/**
	 * @return the homePageViewLoader
	 */
	public HomePageViewLoader getHomePageViewLoader() {
		return homePageViewLoader;
	}

	/**
	 * @param homePageViewLoader the homePageViewLoader to set
	 */
	public void setHomePageViewLoader(HomePageViewLoader homePageViewLoader) {
		this.homePageViewLoader = homePageViewLoader;
	}

	/**
	 * Action performed on pressing start game default button on home page
	 * 
	 * @param event
	 */
	public void startGameButtonActionPerformed(ActionEvent event) {
		setPlayers();
		System.out.println(" Start Default Game button pressed ");
	}
	
	/**
	 * Player settings
	 */
	private void setPlayers() {
		homePageViewLoader.setVisible(false);
		new PlayerSettingsController();
	}

	/**
	 * Action performed on pressing construct button on home page
	 * 
	 * @param event
	 */
	public void constructMapButtonActionPerformed(ActionEvent event) {
		homePageViewLoader.setVisible(false);
		new MapConstructionView(this).setVisible(true);
	}

	/**
	 * Action performed on pressing Load existing map button on home page
	 * 
	 * @param event
	 */
	public void loadExistingMapButtonActionPerformed(ActionEvent event) {
		System.out.println(" Load Existing Map button pressed ");
		homePageViewLoader.openFileChooser();
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

	/**
	 * Start/ entry of the game application
	 * @param args
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new StartGameController();
			}
		});
	}
}
