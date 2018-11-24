package col.cs.risk.controller;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import col.cs.risk.helper.MapException;
import col.cs.risk.helper.Utility;
import col.cs.risk.model.Constants;
import col.cs.risk.model.GameModel;
import col.cs.risk.view.ConstructNewMapView;
import col.cs.risk.view.HomePageViewLoader;
import col.cs.risk.view.MapConstructionView;

/**
 * StartGameController It handles the starting of the game Loads the home page
 * view of the game.
 * 
 * @author Team25
 *
 */
public class StartGameController {

	/** Game home Page View */
	private HomePageViewLoader homePageViewLoader;

	/** Load Existing Map View */
	private LoadExistingMapController loadExistingMapController;

	/** Map construction/modification Page View */
	private MapConstructionView mapConstructionView;

	/** Construct New map Page View */
	private ConstructNewMapView constructNewMapView;

	/**
	 * Starts the game page(Loads the home page) with multiple options
	 */
	public StartGameController() {
		Utility.writeLog("*********************** The Risk Game starts **********************", false);
		new HomePageViewLoader(this).setVisible(true);
		new LoadExistingMapController(this);
	}

	/**
	 * Validates empty text
	 * 
	 * @param text
	 * @return true if empty text else false
	 */
	public boolean isEmptyDetails(String text) {
		if (text.isEmpty()) {
			return true;
		} else {
			String str = text.replaceAll("\\s+", "");
			if (str.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Validates Single Line of text
	 * 
	 * @param text
	 * @returns true if single line text else false
	 */
	public boolean isMapContainsSingleCountry(String text) {
		InputStream is = new ByteArrayInputStream(text.getBytes());
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		int lineNo = 0;
		try {
			while (reader.readLine() != null) {
				lineNo++;
				if (lineNo == Constants.TWO) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (lineNo > Constants.ONE) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @return the homePageViewLoader
	 */
	public HomePageViewLoader getHomePageViewLoader() {
		return homePageViewLoader;
	}

	/**
	 * @param homePageViewLoader
	 *            the homePageViewLoader to set
	 */
	public void setHomePageViewLoader(HomePageViewLoader homePageViewLoader) {
		this.homePageViewLoader = homePageViewLoader;
	}

	/**
	 * @return the LoadExistingMapViewContorller
	 */
	public LoadExistingMapController getLoadExistingMapController() {
		return loadExistingMapController;
	}

	/**
	 * Sets LoadExistingMapController instance
	 * 
	 * @param loadingExistingMapController
	 */
	public void setLoadExistingMapController(LoadExistingMapController loadExistingMapController) {
		this.loadExistingMapController = loadExistingMapController;
	}

	/**
	 * Action performed on pressing start game default button on home page
	 * 
	 * @param event
	 */
	public void startGameButtonActionPerformed(ActionEvent event) {
		homePageViewLoader.setVisible(false);
		Utility.writeLog("Start Default Game button pressed");
		setPlayers();
		System.out.println(" Start Default Game button pressed ");
	}

	/**
	 * Initialize the player settings controller
	 */
	public void setPlayers() {
		new PlayerSettingsController(this);
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
		Utility.writeLog("Load Existing Map button pressed");
		loadExistingMapController.openFileChooserFromView();
	}

	/**
	 * Action performed on pressing Exit button on home page
	 * 
	 * @param event
	 */
	public void exitButtonActionPerformed(ActionEvent event) {
		Utility.writeLog(" Exit button pressed ");
		System.exit(0);
	}

	/**
	 * Action performed on pressing New Map construction Button
	 * 
	 * @param event
	 */
	public void newMapButtonActionPerformed(ActionEvent event) {
		Utility.writeLog(" Construct New Map button pressed ");
		mapConstructionView.setVisible(false);
		new ConstructNewMapView(this);
	}

	/**
	 * Action performed on pressing Modify Existing Map Button
	 * 
	 * @param event
	 */
	public void modifyExistingMapButtonActionPerformed(ActionEvent event) {
		Utility.writeLog(" Modify Existing Map button pressed ");
		loadExistingMapController.showModificationView();
	}

	/**
	 * @return the mapConstructionView
	 */
	public MapConstructionView getMapConstructionView() {
		return mapConstructionView;
	}

	/**
	 * @param mapConstructionView
	 *            the mapConstructionView to set
	 */
	public void setMapConstructionView(MapConstructionView mapConstructionView) {
		this.mapConstructionView = mapConstructionView;
	}

	/**
	 * @return the constructNewMapView
	 */
	public ConstructNewMapView getConstructNewMapView() {
		return constructNewMapView;
	}

	/**
	 * @param constructNewMapView
	 *            the constructNewMapView to set
	 */
	public void setConstructNewMapView(ConstructNewMapView constructNewMapView) {
		this.constructNewMapView = constructNewMapView;
	}

	/**
	 * Start/Entry of the game application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new StartGameController();
			}
		});
	}

	/**
	 * Action performed on create button pressed on new map construction page
	 * 
	 * @param line
	 *            Map section
	 * @param line1
	 *            Continent section
	 * @param line2
	 *            Country section
	 */
	public void actionPerformedOnMapCreateButton(String line, String line1, String line2) {
		StringBuilder mapString = new StringBuilder();
		mapString.append("[Map]\n");
		mapString.append(line + "\n\n");
		mapString.append("[Continents]\n");
		mapString.append(line1 + "\n\n");
		mapString.append("[Territories]\n");
		mapString.append(line2 + "\n\n");

		GameModel gameModel = new GameModel();
		if (isEmptyDetails(line1)) {
			constructNewMapView.showErrorPopup("Atleast add one continent ");
		} else if (isEmptyDetails(line2)) {
			constructNewMapView.showErrorPopup("Add countries in Territory section ");
		} else if (!gameModel.isContinentInTerrirotiesValid(mapString.toString())) {
			constructNewMapView.showErrorPopup("Add continents which are present in the continent section ");
		} else if (isEmptyDetails(line1)) {
			constructNewMapView.showErrorPopup("Add country details in country section");
		} else if (isMapContainsSingleCountry(line2)) {
			constructNewMapView.showErrorPopup("Can't create connected map with a single country");
		} else if (!gameModel.isAllTerritoriesHaveAdjacents(mapString.toString())) {
			constructNewMapView.showErrorPopup("Atleast add one adjacent country ");
		} else
			try {
				if (Utility.isConnectedMap(mapString.toString())) {
					try {
						FileWriter fw = new FileWriter(
								new File(Utility.getMapPath(Constants.DEFAULT_MODIFIED_MAP_FILE_NAME)));
						fw.write(mapString.toString());
						fw.close();
						constructNewMapView.setVisible(false);
						GameModel.isBaseMapModified = true;
						GameModel.fileName = Constants.DEFAULT_MODIFIED_MAP_FILE_NAME;
						GameModel.imageSelected = Constants.DEFAULT_MODIFIED_MAP_IMAGE_FILE_NAME;
						setPlayers();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (MapException ex) {
				constructNewMapView.showErrorPopup(ex.getMessage());
			}
	}

	/**
	 * Set home page view visibility
	 * 
	 * @param visible
	 */
	public void setHomePageVisiblility(boolean visible) {
		homePageViewLoader.setVisible(visible);
	}

	/**
	 * Action to taken on map construction view window exit
	 */
	public void exitMapConstructionView() {
		setHomePageVisiblility(true);
	}

	/**
	 * Action to taken on new map construction view window exit
	 */
	public void exitNewMapConstructionView() {
		mapConstructionView.setVisible(true);
	}
}
