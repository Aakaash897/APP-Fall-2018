package col.cs.risk.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JPanel;

import col.cs.risk.helper.Utility;

/**
 * GameModel class is to maintain game data such as continents, territories, players.
 * @author Team
 *
 */
public class GameModel {

	/** Default map string */
	public StringBuilder baseMapString;
	
	/** modified game map data */
	public static StringBuilder modifiedMapString;

	/** game map data */
	public File mapFileStream;

	/** Boolean Object for validatingMap */
	private boolean isGameMapValid;

	/** stores if map file modified */
	public static boolean isBaseMapModified;

	/** name of the modified file */
	public static String fileName = "World.map";

	/** current state of the game */
	private int state;

	/** list of continents */
	public Vector<ContinentModel> continents = new Vector<>();

	/** list of countries */
	public Vector<TerritoryModel> territories = new Vector<>();

	/** list of players */
	public static Vector<PlayerModel> players = new Vector<>();

	/** current player */
	public PlayerModel currentPlayer;
	
	/** map of no. of player to no. of army */
	public HashMap<Integer, Integer> playerArmyMap;

	/** String for Map Selected */
	public static String imageSelected = "World.bmp";

	/** Map panel */
	private JPanel mainMapPanel;

	/** Player panel */
	private JPanel subMapPanel;

	/** Territory from which armies to move */
	private TerritoryModel moveArmiesFromTerritory;

	/** Territory to which armies to move */
	private TerritoryModel moveArmiesToTerritory;

	/** No of armies to move from one territory to another */
	private int noOfArmiesToMove;
	
	/** list of unOccupied territories */
	public Vector<TerritoryModel> unOccupiedTerritories;

	/**
	 * Instance block to fill player and army details
	 */
	{
		playerArmyMap = new HashMap<>();
		playerArmyMap.put(2, 40);
		playerArmyMap.put(3, 35);
		playerArmyMap.put(4, 30);
		playerArmyMap.put(5, 25);
		playerArmyMap.put(6, 20);
	}

	/**
	 * Default Constructor
	 */
	public GameModel() {
		
	}
	
	/**
	 * Constructor with parameters for initial verification and setup
	 * @param booleans
	 */
	public GameModel(Boolean ...booleans) {
		state = Constants.INITIAL_RE_ENFORCEMENT_PHASE;
		initCurrentPlayer();
		initializeMapAttributes();
		validateAndLoadMap();
		distributeArmies();
		assignTerritories();
	}
	
	/**
	 * Initializes the map attributes especially map file as text data
	 * for further processing
	 */
	public void initializeMapAttributes() {
		if(isBaseMapModified) {
			modifiedMapString = readFile(modifiedMapString, fileName);
		} else {
			baseMapString = readFile(baseMapString, "World.map");
		}
	}

	/**
	 * Reads the File
	 * @param mapString
	 * @param fileName
	 */
	private StringBuilder readFile(StringBuilder mapString, String fileName) {
		mapString = new StringBuilder();
		File file = new File(Utility.getMapPath(fileName));
		BufferedReader buffReader;
		try {
			buffReader = new BufferedReader(new FileReader(file));
			String line;
			while((line = buffReader.readLine())!=null) {
				mapString.append(line+"\n");
			}
			buffReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapString;
	}

	/**
	 * Validates and loads the map
	 */
	private void validateAndLoadMap() {
		if(isValidMapFormat()) {
			setMapValid(true);
			loadGameMap();
		}
	}

	/**
	 * It does 3 map validations, 
	 * 1) checks whether the tags are correct
	 * 2) checks all territories are connected
	 * 3) checks whether all territories are belongs to the predefined continents.
	 * @return true if map file is valid else false.
	 * 
	 */
	public boolean isValidMapFormat() {
		if(isBaseMapModified) {
			return isTagsCorrect(modifiedMapString.toString())
					&& isAllTerritoriesConnected(modifiedMapString.toString())
					&& checkContinentsAreValid(modifiedMapString.toString());
		} else {
			return isTagsCorrect(baseMapString.toString())
					&& isAllTerritoriesConnected(baseMapString.toString())
					&& checkContinentsAreValid(baseMapString.toString());
		}
	}

	/**
	 * Checks if tags are valid
	 * @param mapText Map file as string
	 * @return true if all tags are correct
	 * 
	 */
	public Boolean isTagsCorrect(String mapText) {
		Boolean flag = ((mapText.indexOf("[Map]") != -1) && (mapText.indexOf("[Territories]") != -1)
				&& (mapText.indexOf("[Continents]") != -1));
		return flag;
	}

	/**
	 * Validates if territories are connected
	 * @param mapText Map file as string
	 * @return true if all territories are connected
	 */
	public Boolean isAllTerritoriesConnected(String mapText) {
		Boolean isTerritory = false;
		Boolean isConnected = true;
		for (String line : mapText.split("\n")) {
			if (isTerritory == true) {
				if (!line.equals("") && line.split(",").length <= 4) 
					return isConnected = false;
			}
			if (line.equals("[Territories]"))
				isTerritory = true;
		}
		return isConnected;
	}

	/**
	 * Validates the whether territories belongs to the defined continents
	 * @param mapText Map file as string
	 * @return true if all territories belongs to the predefined continents.
	 */
	public Boolean checkContinentsAreValid(String mapText) {
		Boolean isContinent = false;
		Boolean isTerritory = false;
		Boolean isContinentValid = true;
		List<String> continentsList = new ArrayList<String>();
		for (String line : mapText.split("\n")) {
			if (line.equals("[Continents]"))
				isContinent = true;

			else if (line.equals("[Territories]"))
				isTerritory = true;

			else if (isContinent && !isTerritory) {
				continentsList.add(line.split("=")[0]);
			}
			else if (!line.equals("") && !line.trim().equals("[Territories]")
					&& isTerritory) {
				if (line.split(",").length < 4 || !continentsList.contains(line.split(",")[3])) {
					isContinentValid = false;
					break;
				}
			}
		}
		return isContinentValid;
	}

	/**
	 * Loads the map to the map model such as continents and territories
	 */
	public void loadGameMap() {
		try {
			if(isBaseMapModified) {
				mapFileStream = new File(Utility.getMapPath(fileName));
			} else {
				mapFileStream = new File(Utility.getMapPath("World.map"));
			}
			Scanner scn = new Scanner(mapFileStream);
			String name;
			int id, score, x_pos, y_pos;
			boolean isContinentsDone = false;
			String line;
			while (scn.hasNextLine()) {
				line = scn.nextLine();
				if (line.equals("[Continents]")) {
					line = scn.nextLine();
					id = 0;
					do {
						if(line.split("=").length > 1) {
							name = line.split("=")[0];
							score = Integer.parseInt(line.split("=")[1].trim());

							continents.add(new ContinentModel(id++, name, score));
						}
						line = scn.nextLine();
						if(line.matches("")) {
							isContinentsDone = true;
						}
					} while(!isContinentsDone);
				}

				if (line.equals("[Territories]")) {
					int index = 0;
					do {
						line = scn.nextLine();
						if(!line.matches("")) {
							String[] str = line.split(",");
							id = index++;
							name = str[0];
							x_pos = Integer.parseInt(str[1].trim());
							y_pos = Integer.parseInt(str[2].trim());

							for(int i=0;i<continents.size();i++) {
								if(continents.elementAt(i).getName().matches(str[3])) {
									TerritoryModel territory = new TerritoryModel(id, name, x_pos, y_pos, 
											continents.elementAt(i));
									territories.addElement(territory);
									continents.elementAt(i).addTerritory(territory);
								}
							}
						}
					} while(scn.hasNextLine());
				}
			}

			scn = new Scanner(mapFileStream);
			while(scn.hasNextLine()) {
				line = scn.nextLine();
				if (line.equals("[Territories]")) {
					do {
						line = scn.nextLine();
						if(!line.matches("")) {
							String[] str = line.split(",");
							Vector<Integer> adjacentTerritoryIDs = new Vector<>();
							Vector<TerritoryModel> adjacentTerritories = new Vector<>();
							for(int i=4;i<str.length;i++) {
								for(int j=0;j<territories.size();j++) {
									if(territories.elementAt(j).getName().matches(str[i])) {
										adjacentTerritoryIDs.add(territories.elementAt(j).getId());
										adjacentTerritories.add(territories.elementAt(j));
									}
								}
							}
							for(int i=0;i<territories.size();i++) {
								if(territories.elementAt(i).getName().matches(str[0])) {
									territories.elementAt(i).setAdjacentTerritories(adjacentTerritories);
								}
							}
						}
					} while(scn.hasNextLine());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Distribute armies between players
	 */
	private void distributeArmies() {
		int armies = playerArmyMap.get(players.size());
		for(PlayerModel playerModel:players) {
			playerModel.addArmies(armies);
		}
	}

	/**
	 * Assign territories to players
	 */
	private void assignTerritories() {
		TerritoryModel territoryModel;
		if(unOccupiedTerritories == null || unOccupiedTerritories.size() == 0) {
			unOccupiedTerritories = new Vector<>(territories);
		}
		while((territoryModel = getRandomUnoccupiedTerritory()) != null) {
			occupyTerritory(territoryModel, currentPlayer);
			nextPlayer();
		}
	}

	/**
	 * Gets the unoccupied territory in serial order
	 * @return unoccupied territory
	 */
	private TerritoryModel getUnoccupiedTerritory() {
		for(TerritoryModel model:territories) {
			if(!model.isOccupied()) {
				return model;
			}
		}
		return null;
	}
	
	/**
	 * Gets the unOccupied territory randomly
	 * @return TerritoryModel which is not occupied by any player else null
	 */
	private TerritoryModel getRandomUnoccupiedTerritory() {
		TerritoryModel model = null;
		while(unOccupiedTerritories.size()>0) {
			int randomIndex = Utility.getRandomNumber(unOccupiedTerritories.size());
			model = unOccupiedTerritories.get(randomIndex);
			if(!model.isOccupied()) {
				unOccupiedTerritories.remove(randomIndex);
				break;
			}
		}
		return model;
	}
	
	/**
	 * Player occupies a territory
	 * @param territoryModel
	 * @param playerModel
	 */
	private void occupyTerritory(TerritoryModel territoryModel, PlayerModel playerModel) {
		territoryModel.setPlayerModel(playerModel);
		playerModel.addOccupiedTerritory(territoryModel);
		territoryModel.addArmy();
		playerModel.looseArmy();
	}

	/**
	 * Add an army on a territory from an occupied player
	 * @param territoryModel
	 * @param playerModel
	 */
	private void addArmyOnOccupiedTerritory(TerritoryModel territoryModel, PlayerModel playerModel) {
		territoryModel.addArmy();
		playerModel.looseArmy();
	}

	/**
	 * Changes the current player to next player
	 */
	public void nextPlayer() {
		if(currentPlayer == players.lastElement()) {
			currentPlayer = players.firstElement();
		} else {
			int playerNo = currentPlayer.getId();
			currentPlayer = players.get(++playerNo);
		}
	}

	/**
	 * Add player to the existing player list
	 * @param id player id
	 * @param name player name
	 */
	public void addPlayer(int id, String name) {
		players.add(new PlayerModel(id, name));
	}

	/**
	 * Phase change notification
	 */
	public void notifyPhaseChange() {
		if (this.mainMapPanel != null && this.subMapPanel != null) {
			this.mainMapPanel.repaint();
			this.subMapPanel.repaint();
		}
	}

	/**
	 * Game phase setup done for each player at the start of the turn
	 * @param x_coordinate
	 * @param y_coordinate
	 */
	public void gamePhasePlayerTurnSetup(int x_coordinate, int y_coordinate) {
		TerritoryModel territoryModel = getMapLocation(x_coordinate, y_coordinate);
		if(territoryModel!=null) {
			System.out.println("selected territory name = "+territoryModel.getName()+" occupied by = "+territoryModel.getPlayerModel().getName());
			switch(getState()) {
			case Constants.INITIAL_RE_ENFORCEMENT_PHASE:
				if(territoryModel.getPlayerModel().getId() == currentPlayer.getId()) {
					addArmyOnOccupiedTerritory(territoryModel, currentPlayer);
					nextPlayer();
				}
				int index = 0;
				for(PlayerModel playerModel:players) {
					if(playerModel.getArmies() == 0) {
						index++;
					}
				}

				if (index == players.size()) {
					setState(Constants.START_TURN);
					System.out.println("status " + getState());
				}
				break;
			case Constants.RE_ENFORCEMENT_PHASE:
				if(territoryModel.getPlayerModel().getId() == currentPlayer.getId()) {
					addArmyOnOccupiedTerritory(territoryModel, currentPlayer);
					if(currentPlayer.getArmies() == 0) {
						setState(Constants.ACTIVE_TURN);
					}
				}  
				break;
			}
		}
	}

	/**
	 * Final modifications done by the current player
	 * @param x_coordinate
	 * @param y_coordinate
	 * @return status to display on game screen
	 */
	public String gamePhaseActivePlayerFinalModification(int x_coordinate, int y_coordinate) {
		TerritoryModel territoryModel = getMapLocation(x_coordinate, y_coordinate);
		if(territoryModel != null) {
			System.out.println(" territory model name = "+territoryModel.getName());
			if (getState() == Constants.FORTIFICATION_PHASE || getState() == Constants.FORTIFYING_PHASE) {
				return currentPlayer.fortify(this, territoryModel);
			} 
		}
		return "";
	}

	/**
	 * Move armies from one territory to other
	 * @return true if moving successful
	 */
	public boolean moveArmies() {
		if(moveArmiesFromTerritory.getArmies() > noOfArmiesToMove) {
			moveArmiesToTerritory.addArmies(noOfArmiesToMove);
			moveArmiesFromTerritory.looseArmies(noOfArmiesToMove);
			noOfArmiesToMove = 0;
			return true;
		} 
		return false;
	}

	/**
	 * Add turn bonus to current player
	 */
	public void addTurnBonusToCurrentPlayer() {
		int bonus = territoryBonus()+continentBonus();
		currentPlayer.addArmies(bonus);
	}

	/**
	 * API to calculate territory bonus for current player
	 * @return no of armies as bonus
	 */
	public int territoryBonus() {
		double bonus = 0;
		if(currentPlayer.getOccupiedTerritories().size() < 9) {
			bonus = 3;
		} else {
			bonus = Math.floor(currentPlayer.getOccupiedTerritories().size() / 3);
		}
		return (int) bonus;
	}

	/**
	 * API to calculate continent bonus for current player
	 * @return no of armies as bonus
	 */
	public int continentBonus() {
		int bonus = 0;
		for(ContinentModel continentModel:continents) {
			if(continentModel.isContinentOccupiedBy(currentPlayer)) {
				bonus += continentModel.getScore();
			}
		}
		return bonus;
	}

	/**
	 * To find out the territory location by coordinates
	 * @param x_coordinate
	 * @param y_coordinate
	 * @return TerritoryModel corresponding to the co_odinates
	 */
	public TerritoryModel getMapLocation(int x_coordinate, int y_coordinate) {
		TerritoryModel territoryModel = null;
		int size = 30;
		for(TerritoryModel territory:territories) {
			if(Math.abs(territory.getX_pos()-x_coordinate) <= size || Math.abs(x_coordinate-territory.getX_pos()) <= size) {
				if(Math.abs(territory.getY_pos()-y_coordinate) <= size || Math.abs(y_coordinate-territory.getY_pos()) <= size) {
					territoryModel = territory;
				}
			}
		}
		return territoryModel;
	}

	/**
	 * To get current state in a string format
	 * @return current state as string
	 */
	public String getStateAsString() {
		String stateString;
		switch(getState()) {
		case Constants.NEW_GAME :
			stateString = Constants.NEW_GAME_MESSAGE;
			break;
		case Constants.INITIAL_RE_ENFORCEMENT_PHASE :
			stateString = Constants.INITIAL_RE_ENFORCEMENT_PHASE_MESSAGE;
			break;
		case Constants.RE_ENFORCEMENT_PHASE :
			stateString = Constants.REINFORCEMENT_PHASE_MESSAGE;
			break;
		case Constants.ATTACK_PHASE :
			stateString = Constants.ATTACK_PHASE_MESSAGE;
			break;
		case Constants.FORTIFICATION_PHASE :
		case Constants.FORTIFYING_PHASE:
		case Constants.FORTIFY_PHASE:
			stateString = Constants.FORTIFICATION_PHASE_MESSAGE;
			break;
		default :
			stateString = "";
			break;
		}
		return stateString;
	}

	/**
	 * Writes data to file
	 */
	public void writeDataToFile(String fileName) {
		String result = getMapContentToWrite();
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Utility.getMapPath(fileName)));
			bufferedWriter.write(result);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Converts data in models(continent and territory) to string
	 * @return String of map data
	 * 
	 */
	private String getMapContentToWrite() {
		StringBuilder result = new StringBuilder();
		result.append("[Map]"+"\n");
		result.append("author=Shwetha"+"\n");
		result.append("image=currMap.jpg"+"\n");
		result.append("wrap=no"+"\n");
		result.append("scroll=horizontal"+"\n");
		result.append("warn=yes"+"\n\n");

		result.append("[Continents]"+"\n");
		for(ContinentModel con:continents) {
			result.append(con.getName());
			result.append("=");
			result.append(con.getScore()+"\n");
		}
		result.append("\n");

		result.append("[Territories]"+"\n");
		for(ContinentModel con:continents) {
			for(TerritoryModel model:con.getTerritories()) {
				result.append(model.getName()+",");
				result.append(model.getX_pos()+",");
				result.append(model.getY_pos()+",");
				result.append(con.getName());
				for(TerritoryModel ter:model.getAdjacentTerritories()) {
					result.append(","+ter.getName());
				}
				result.append("\n");
			}
			result.append("\n");
		}
		result.append("\n\n\n");
		return result.toString();
	}

	/**
	 * Prints all territories with its details
	 */
	public void printTerritories() {
		for(int i=0;i<territories.size();i++) {
			System.out.println(territories.get(i).printTerritory());
		}
		System.out.println();
	}

	/**
	 * Prints all continents with its details
	 */
	public void printContinets() {
		for(int i=0;i<continents.size();i++) {
			System.out.println(continents.get(i).printContinent());
		}
		System.out.println();
	}

	/**
	 * Set current player
	 */
	private void initCurrentPlayer() {
		currentPlayer = players.firstElement();
	}

	/**
	 * @return the isMapFileModified
	 */
	public boolean isMapFileModified() {
		return isBaseMapModified;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return the gameState
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param gameState the gameState to set
	 */
	public void setState(int gameState) {
		this.state = gameState;
	}

	/**
	 * @return the isValidGameMap
	 */
	public boolean isMapValid() {
		return isGameMapValid;
	}

	/**
	 * @param isValidGameMap the isValidGameMap to set
	 */
	public void setMapValid(boolean isValidGameMap) {
		this.isGameMapValid = isValidGameMap;
	}

	/**
	 * @return the baseMapString
	 */
	public StringBuilder getBaseMapString() {
		return baseMapString;
	}

	/**
	 * @param baseMapString the baseMapString to set
	 */
	public void setBaseMapString(StringBuilder baseMapString) {
		this.baseMapString = baseMapString;
	}

	/**
	 * @return the modifiedMapString
	 */
	public StringBuilder getModifiedMapString() {
		return modifiedMapString;
	}

	/**
	 * @return the mapFileStream
	 */
	public File getMapFileStream() {
		return mapFileStream;
	}

	/**
	 * @param mapFileStream the mapFileStream to set
	 */
	public void setMapFileStream(File mapFileStream) {
		this.mapFileStream = mapFileStream;
	}

	/**
	 * @return the continents
	 */
	public Vector<ContinentModel> getContinents() {
		return continents;
	}

	/**
	 * @param continents the continents to set
	 */
	public void setContinents(Vector<ContinentModel> continents) {
		this.continents = continents;
	}

	/**
	 * @return the territories
	 */
	public Vector<TerritoryModel> getTerritories() {
		return territories;
	}

	/**
	 * @param territories the territories to set
	 */
	public void setTerritories(Vector<TerritoryModel> territories) {
		this.territories = territories;
	}

	/**
	 * @return the players
	 */
	public static Vector<PlayerModel> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public static void setPlayers(Vector<PlayerModel> players) {
		GameModel.players = players;
	}

	/**
	 * @return the currentPlayer
	 */
	public PlayerModel getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * @param currentPlayer the currentPlayer to set
	 */
	public void setCurrentPlayer(PlayerModel currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * @return the imageSelected
	 */
	public static String getImageSelected() {
		return imageSelected;
	}

	/**
	 * @param imageSelected the imageSelected to set
	 */
	public static void setImageSelected(String imageSelected) {
		GameModel.imageSelected = imageSelected;
	}

	/**
	 * @return the mainMapPanel
	 */
	public JPanel getMainMapPanel() {
		return mainMapPanel;
	}

	/**
	 * @param mainMapPanel the mainMapPanel to set
	 */
	public void setMainMapPanel(JPanel mainMapPanel) {
		this.mainMapPanel = mainMapPanel;
	}

	/**
	 * @return the subMapPanel
	 */
	public JPanel getSubMapPanel() {
		return subMapPanel;
	}

	/**
	 * @param subMapPanel the subMapPanel to set
	 */
	public void setSubMapPanel(JPanel subMapPanel) {
		this.subMapPanel = subMapPanel;
	}

	/**
	 * @return the moveFromTerritory
	 */
	public TerritoryModel getMoveArmiesFromTerritory() {
		return moveArmiesFromTerritory;
	}

	/**
	 * @param moveFromTerritory the moveFromTerritory to set
	 */
	public void setMoveArmiesFromTerritory(TerritoryModel moveFromTerritory) {
		this.moveArmiesFromTerritory = moveFromTerritory;
	}

	/**
	 * @return the moveToTerritory
	 */
	public TerritoryModel getMoveArmiesToTerritory() {
		return moveArmiesToTerritory;
	}

	/**
	 * @param moveToTerritory the moveToTerritory to set
	 */
	public void setMoveArmiesToTerritory(TerritoryModel moveToTerritory) {
		this.moveArmiesToTerritory = moveToTerritory;
	}

	/**
	 * @return the noOfArmiesToMove
	 */
	public int getNoOfArmiesToMove() {
		return noOfArmiesToMove;
	}

	/**
	 * @param noOfArmiesToMove the noOfArmiesToMove to set
	 */
	public void setNoOfArmiesToMove(int noOfArmiesToMove) {
		this.noOfArmiesToMove = noOfArmiesToMove;
	}
}
