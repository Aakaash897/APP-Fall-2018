package col.cs.risk.controller;

import col.cs.risk.helper.Utility;
import col.cs.risk.model.GameModel;
import col.cs.risk.view.LoadExistingMapView;

/**
 * LoadExistingMapController This class handles the loading of the existing map.
 * This class handles all the actions
 * 
 * @author Team25
 *
 */
public class LoadExistingMapController {

	/** Load Existing Map View */
	private LoadExistingMapView loadingExistingMapView;

	/** StartGameController instance */
	public StartGameController startGameController;

	/**
	 * Constructor with parameters Intialize Load existing map view
	 * 
	 * @param startGameController
	 */
	public LoadExistingMapController(StartGameController startGameController) {
		startGameController.setLoadExistingMapController(this);
		this.startGameController = startGameController;
		new LoadExistingMapView(this);
	}

	/**
	 * Gets LoadExistingMapView instance
	 * 
	 * @return LoadExistingMapView instance
	 */
	public LoadExistingMapView getLoadingExistingMapView() {
		return loadingExistingMapView;
	}

	/**
	 * Sets LoadExistingMapView instance
	 * 
	 * @param loadingExistingMapView
	 */
	public void setLoadingExistingMapView(LoadExistingMapView loadingExistingMapView) {
		this.loadingExistingMapView = loadingExistingMapView;
	}

	/**
	 * Open File Chooser for loading the map
	 */
	public void openFileChooserFromView() {
		System.out.println("LoadExistingMapController.openFileChooserFromView()");
		Utility.baseMapString = null;
		loadingExistingMapView.openFileChooser();
		if (Utility.baseMapString != null) {
			startGameController.setHomePageVisiblility(false);
			startGameController.setPlayers();
		}
	}

	/**
	 * Set modified map details in game model
	 * 
	 * @param isModified
	 * @param fileName
	 */
	public void setModelDetails(boolean isModified, String fileName) {
		GameModel.isBaseMapModified = isModified;
		GameModel.fileName = fileName;
		if (fileName.equals("currMap.map")) {
			GameModel.imageSelected = "currMap.png";
		} else {
			GameModel.imageSelected = fileName.replace(".map", ".bmp");
		}
	}

	/**
	 * Action performed on save
	 * 
	 * @param result
	 */
	public void actionPerformedOnSave(String result) {
		String fileName = "currMap.map";
		GameModel.fileName = fileName;
		Utility.writeToFile(fileName, result);
		startGameController.setPlayers();
	}

	/**
	 * Action performed on cancel
	 * 
	 * @param result
	 */
	public void actionPerformedOnCancel(String result) {
		String fileName = "currMap.map";
		GameModel.fileName = fileName;
		Utility.writeToFile(fileName, result);
		exitModificaitonView();
	}

	/**
	 * Loading map construction view page
	 */
	public void loadMapConstructionView() {
		startGameController.getMapConstructionView().setVisible(true);
	}

	/**
	 * Open File Chooser for loading and modification
	 */
	public void showModificationView() {
		Utility.baseMapString = null;
		loadingExistingMapView.openFileChooser();
		if (Utility.baseMapString != null) {
			startGameController.getMapConstructionView().setVisible(false);
			loadingExistingMapView.showModificationView(Utility.baseMapString.toString());
		}
	}

	/**
	 * Exiting map modification view
	 */
	public void exitModificaitonView() {
		startGameController.getMapConstructionView().setVisible(true);
	}

}
