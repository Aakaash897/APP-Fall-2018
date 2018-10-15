package col.cs.risk.controller;

import col.cs.risk.helper.Utility;
import col.cs.risk.view.LoadExistingMapView;

public class LoadExistingMapController {
	/** Load Existing Map View */
	private LoadExistingMapView loadingExistingMapView;

	/**
	 * Constructor with parameters Intialize Load existing map view
	 * 
	 * @param startGameController
	 */
	public LoadExistingMapController(StartGameController startGameController) {
		// TODO Auto-generated constructor stub
		startGameController.setLoadExistingMapController(this);
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
		// TODO Auto-generated method stub
		loadingExistingMapView.openFileChooser();

	}

	/**
	 * Open File Chooser for loading and modification
	 */
	public void showModificationView() {
		// TODO Auto-generated method stub
		Utility.baseMapString = null;
		loadingExistingMapView.openFileChooser();
		if (Utility.baseMapString != null) {
			loadingExistingMapView.showModificationView(Utility.baseMapString.toString());
		}

	}

}
