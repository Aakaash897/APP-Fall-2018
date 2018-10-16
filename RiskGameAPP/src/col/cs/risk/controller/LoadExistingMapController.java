package col.cs.risk.controller;

import col.cs.risk.helper.Utility;
import col.cs.risk.model.GameModel;
import col.cs.risk.view.LoadExistingMapView;

public class LoadExistingMapController {
	/** Load Existing Map View */
	private LoadExistingMapView loadingExistingMapView;
	
	private StartGameController startGameController;

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
		loadingExistingMapView.openFileChooser();

	}
	
	public void setModelDetails(boolean isModified, String fileName) {
		GameModel.isBaseMapModified = isModified;
		GameModel.fileName = fileName;
		if(fileName.equals("currMap.map")) {
			GameModel.imageSelected = "currMap.png";
		} else {
			GameModel.imageSelected = fileName.replace(".map", ".bmp");
		}
	}
	
	public void actionPerformedOnSave(String result) {
		String fileName = "currMap.map";
		GameModel.fileName = fileName;
		Utility.writeToFile(fileName, result);
		startGameController.setPlayers();
	}
	
	public void actionPerformedOnCancel(String result) {
		String fileName = "currMap.map";
		GameModel.fileName = fileName;
		Utility.writeToFile(fileName, result);
		startGameController.setPlayers();
	}
	
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
			loadingExistingMapView.showModificationView(Utility.baseMapString.toString());
		}

	}

}
