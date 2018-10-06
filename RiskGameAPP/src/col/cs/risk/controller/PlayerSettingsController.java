package col.cs.risk.controller;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JComboBox;

import col.cs.risk.model.GameModel;
import col.cs.risk.model.PlayerModel;
import col.cs.risk.view.PlayerSettingsView;

/**
 * Player settings Controller
 * @author Team
 *
 */
public class PlayerSettingsController {

	/** no of players */
	public static int noOfPlayers = 3;

	/** selected map either World, India, Europe etc */
	public static String selectedMap; 
	
	/** player settings view instance */
	public PlayerSettingsView playerSettingsView;

	/**
	 * Constructor
	 */
	public PlayerSettingsController() {
		new PlayerSettingsView(this).setVisible(true);
	}

	/**
	 * Action performed on pressing finish button on settings screen
	 * @param evt
	 */
	public void finishActionPerformed(ActionEvent evt) {
		setPlayers();
		playerSettingsView.setVisible(false);
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
	private void setPlayers() {
		Vector<PlayerModel> players = new Vector<>();
		for(int i=0;i<noOfPlayers;i++) {
			players.add(new PlayerModel(i, "Player_"+(i+1)));
		}
		GameModel.players.addAll(players);
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
		case "World": 
			GameModel.imageSelected="World.bmp";
			break;
		case "3D Cliff": 
			GameModel.imageSelected="3D Cliff.bmp";
			break;
		case "India": 
			GameModel.imageSelected="India.bmp";
			break;
		case "British Columbia": 
			GameModel.imageSelected="British Columbia.bmp";
			break;
		case "Europe": 
			GameModel.imageSelected="Europe.bmp";
			break;
		case "UserDefined":
			GameModel.imageSelected="ABC_Map.bmp";
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
	public static int getNoOfPlayers() {
		return noOfPlayers;
	}

	/**
	 * @param noOfPlayers the noOfPlayers to set
	 */
	public static void setNoOfPlayers(int noOfPlayers) {
		PlayerSettingsController.noOfPlayers = noOfPlayers;
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

}
