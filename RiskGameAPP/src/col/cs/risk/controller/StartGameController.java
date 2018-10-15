package col.cs.risk.controller;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import col.cs.risk.helper.Utility;
import col.cs.risk.model.GameModel;
import col.cs.risk.view.ConstructNewMapView;
import col.cs.risk.view.HomePageViewLoader;
import col.cs.risk.view.LoadExistingMapView;
import col.cs.risk.view.MapConstructionView;

/**
 * Starts the game
 * @author Team
 *
 */
public class StartGameController{
	
	/** Game home Page View */
	private HomePageViewLoader homePageViewLoader;
	
	/** Load Existing Map View */
	private LoadExistingMapView loadingExistingMapView;
	
	/** Map construction/modification Page View */
	private MapConstructionView mapConstructionView;
	
	/** Construct New map Page View */
	private ConstructNewMapView constructNewMapView;
	
	/**
	 * Starts the game page(Loads the home page) with multiple options
	 */
	public StartGameController() {
		new HomePageViewLoader(this).setVisible(true);
		new LoadExistingMapView(this);
	}
	
	/**
	 * Validates empty text
	 * @param text
	 * @return
	 */
	public boolean isEmptyDetails(String text) {
		if(text.isEmpty() ) {
			return true;
		}
		else {
			String str = text.replaceAll("\\s+","");
			if(str.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Validates Single Line of text
	 * @param text
	 * @returns true if single line text else false
	 */
	public boolean isSingleLineText(String text) {
		InputStream is = new ByteArrayInputStream(text.getBytes());
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		int lineNo = 0;
		try {
			while(reader.readLine()!=null) {
				lineNo++;
				if(lineNo == 2) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(lineNo > 1) {
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
	 * Gets LoadExistingMapView instance
	 * @return LoadExistingMapView instance
	 */
	public LoadExistingMapView getLoadingExistingMapView() {
		return loadingExistingMapView;
	}

	/**
	 * Sets LoadExistingMapView instance
	 * @param loadingExistingMapView
	 */
	public void setLoadingExistingMapView(LoadExistingMapView loadingExistingMapView) {
		this.loadingExistingMapView = loadingExistingMapView;
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
		homePageViewLoader.setVisible(false);
		setPlayers();
		System.out.println(" Start Default Game button pressed ");
	}
	
	/**
	 * Player settings
	 */
	private void setPlayers() {
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
		loadingExistingMapView.openFileChooser();
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
		mapConstructionView.setVisible(false);
		new ConstructNewMapView(this);
	}

	/**
	 * Action performed on pressing Modify Existing Map Button
	 * @param event
	 */
	public void modifyExistingMapButtonActionPerformed(ActionEvent event) {
		System.out.println(" Modify Existing Map button pressed ");
		loadingExistingMapView.showModificationView();
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
	 * @return the constructNewMapView
	 */
	public ConstructNewMapView getConstructNewMapView() {
		return constructNewMapView;
	}

	/**
	 * @param constructNewMapView the constructNewMapView to set
	 */
	public void setConstructNewMapView(ConstructNewMapView constructNewMapView) {
		this.constructNewMapView = constructNewMapView;
	}

	/**
	 * Start/Entry of the game application
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
	 * @param line Map section
	 * @param line1 Continent section
	 * @param line2 Country section
	 */
	public void actionPerformedOnMapCreateButton(String line, String line1, String line2) {
		StringBuilder mapString = new StringBuilder();
		mapString.append("[Map]\n");
		mapString.append(line+"\n\n");
		mapString.append("[Continents]\n");
		mapString.append(line1+"\n\n");
		mapString.append("[Territories]\n");
		mapString.append(line2+"\n\n");
		
		GameModel gameModel = new GameModel();
		if(isEmptyDetails(line1)) {
			constructNewMapView.setErrorStatus("Atleast add one continent ");
		} else if(isEmptyDetails(line2)) {
			constructNewMapView.setErrorStatus("Add countries in Territory section ");
		} else if(!gameModel.checkContinentsAreValid(mapString.toString())) {
			constructNewMapView.setErrorStatus("Add continents which are present in the continent section ");
		} else if(isEmptyDetails(line1)) {
			constructNewMapView.setErrorStatus("Add country details in country section");
		} else if(isSingleLineText(line2)) {
			constructNewMapView.setErrorStatus("Can't create connected map with a single country");
		} else if(!gameModel.isAllTerritoriesConnected(mapString.toString())) {
			constructNewMapView.setErrorStatus("Atleast add one adjacent country ");
		} else {
			try
			{
				FileWriter fw = new FileWriter(new File(Utility.getMapPath("currMap.map")));
				fw.write(mapString.toString());
				fw.close();
				constructNewMapView.setVisible(false);
				GameModel.isBaseMapModified = true;
				GameModel.modifiedMapString = mapString;
				GameModel.fileName = "currMap.map";
				setPlayers();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
