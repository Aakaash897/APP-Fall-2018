package col.cs.risk.controller;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JComboBox;

import col.cs.risk.model.GameModel;
import col.cs.risk.model.PlayerModel;
import col.cs.risk.view.PlayerSettingsView;

/**
 * PlayerSettingsController It controls the no of players playing the game. It
 * performs actions corresponding to the no of players selected.
 * 
 * @author Team25
 *
 */
public class PlayerSettingsController {

	/** no of players */
	public int noOfPlayers = 3;

	/** selected map either World, India, Europe etc */
	public static String selectedMap; 
	
	/** player settings view instance */
	public PlayerSettingsView playerSettingsView;
	
	/** Start game Controller instance */
	private StartGameController startGameController;

	/**
	 * Default Constructor which also initializes player settings view
	 */
	public PlayerSettingsController(StartGameController startGameController) {
		this.startGameController = startGameController;
		new PlayerSettingsView(this).setVisible(true);
	}

	/**
	 * Action performed on pressing finish button on settings screen
	 * @param evt
	 */
	public void finishActionPerformed(ActionEvent evt) {
		setPlayers();
		playerSettingsView.setVisible(false);
		GameController.showGUI();
	}
	
	/**
	 * No of players selection component
	 * @param evt
	 */
	public void noOfPlayersActionPerformed(ActionEvent evt) {
		JComboBox<?> comboBox = (JComboBox<?>) evt.getSource();
		noOfPlayers = (Integer) comboBox.getSelectedItem();
		System.out.println(" Selected number = "+noOfPlayers);
	}

	/**
	 * Function sets the players for the game
	 */
	public void setPlayers() {
		Vector<PlayerModel> players = new Vector<>();
		for(int i=0;i<noOfPlayers;i++) {
			players.add(new PlayerModel(i, "Player_"+(i+1)));
		}
		GameModel.players = players;
	}
	
	/**
	 * User selected map representation such as India, 3D Cliff, Europe etc.
	 * @param evt
	 */
	public void mapSelectionActionPerformed(ActionEvent evt) {
		JComboBox<?> comboBox = (JComboBox<?>) evt.getSource();
		selectedMap = (String) comboBox.getSelectedItem();
		GameModel.isBaseMapModified = true;
		System.out.println(" Selected map = "+selectedMap);

		switch(selectedMap) {
		case "UserDefined":
			GameModel.imageSelected = "currMap.png";
			break;
		default:
			GameModel.imageSelected = selectedMap+".bmp";
			break;
		}
	}
	
	/**
	 * @return the playerSettingsView
	 */
	public PlayerSettingsView getPlayerSettingsView() {
		return playerSettingsView;
	}

	/**
	 * @param playerSettingsView the playerSettingsView to set
	 */
	public void setPlayerSettingsView(PlayerSettingsView playerSettingsView) {
		this.playerSettingsView = playerSettingsView;
	}

	/**
	 * @return the noOfPlayers
	 */
	public int getNoOfPlayers() {
		return noOfPlayers;
	}

	/**
	 * @param noOfPlayers the noOfPlayers to set
	 */
	public void setNoOfPlayers(int noOfPlayers) {
		this.noOfPlayers = noOfPlayers;
	}

	/**
	 * @return the selectedMap
	 */
	public static String getSelectedMap() {
		return selectedMap;
	}

	/**
	 * @param selectedMap the selectedMap to set
	 */
	public static void setSelectedMap(String selectedMap) {
		PlayerSettingsController.selectedMap = selectedMap;
	}

	/**
	 * @return the startGameController
	 */
	public StartGameController getStartGameController() {
		return startGameController;
	}

	/**
	 * @param startGameController the startGameController to set
	 */
	public void setStartGameController(StartGameController startGameController) {
		this.startGameController = startGameController;
	}

}
