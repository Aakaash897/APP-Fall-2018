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
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JPanel;

import col.cs.risk.helper.MapException;
import col.cs.risk.helper.Utility;
import col.cs.risk.model.CardModel;

/**
 * GameModel class is to maintain game data such as continents, territories and
 * players. It also handles the important map validations such as valid continents,
 * adjacent territories etc.
 * 
 * @author Team25
 *
 */
public class GameModel {

	/** Default map string */
	public StringBuilder baseMapString;

	/** modified game map data */
	public StringBuilder modifiedMapString;

	/** game map data */
	public File mapFileStream;

	/** Boolean Object for validatingMap */
	private boolean isGameMapValid;

	/** stores if map file modified */
	public static boolean isBaseMapModified;

	/** name of the modified file */
	public static String fileName = Constants.DEFAULT_MAP_FILE_NAME;

	/** current state of the game */
	private int state;

	/** list of continents */
	public Vector<ContinentModel> continents = new Vector<>();

	/** list of countries */
	public Vector<TerritoryModel> territories = new Vector<>();
	
	public Vector<CardModel> totCards = new Vector<>();

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
	 * @throws MapException
	 */
	public GameModel(Boolean ...booleans) throws MapException {
		state = Constants.INITIAL_RE_ENFORCEMENT_PHASE;
		initCurrentPlayer();
		initializeMapAttributes();
		validateAndLoadMap();
		assignCardToEachTerritory();
		distributeArmies();
		assignTerritories();
	}

	
	
	/**
	 * Function to Initialize the number of cards and add them
	 * to the CardModel Vector. Includes the WildCard also.
	 * 
	 */
	private void assignCardToEachTerritory() {
		
		for (int position = 0; position < territories.size(); position++)
			totCards.add(new CardModel(position, position % 3));
		
		Random wildCard = new Random();
		
		if (totCards.size() > 0)
			for (int wildCardCount = 1; wildCardCount <= 2; wildCardCount++)
				totCards.add(new CardModel(wildCard.nextInt(totCards.size()), -1));
		
	}

	/**
	 * Initializes the map attributes especially map file as text data
	 * for further processing
	 */
	public void initializeMapAttributes() {
		if(isBaseMapModified) {
			modifiedMapString = readFile(modifiedMapString, fileName);
		} else {
			baseMapString = readFile(baseMapString, Constants.DEFAULT_MAP_FILE_NAME);
		}
	}

	/**
	 * Reads the File
	 * @param mapString It is a StringBuilder reference that holds the data of file 
	 * @param fileName It is a String that holds the name of file
	 */
	public StringBuilder readFile(StringBuilder mapString, String fileName) {
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
	 * @throws MapException 
	 */
	private void validateAndLoadMap() throws MapException {
		if(isValidMapFormat()) {
			setMapValid(true);
			loadGameMap();
		} else {
			throw new MapException(Constants.INVALID_MAP_MESSAGE);
		}
		validatePlayerTerritoriesSize();
	}

	/**
	 * Validating number of players should be less than number of territories 
	 * @throws MapException
	 */
	private void validatePlayerTerritoriesSize() throws MapException {
		if(territories.size() < players.size()) {
			throw new MapException(Constants.INVALID_PLAYER_NO_SELECTION_MESSAGE);
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
					&& isAllTerritoriesHaveAdjacents(modifiedMapString.toString())
					&& isContinentInTerrirotiesValid(modifiedMapString.toString());
		} else {
			return isTagsCorrect(baseMapString.toString())
					&& isAllTerritoriesHaveAdjacents(baseMapString.toString())
					&& isContinentInTerrirotiesValid(baseMapString.toString());
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
	public Boolean isAllTerritoriesHaveAdjacents(String mapText) {
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
	public Boolean isContinentInTerrirotiesValid(String mapText) {
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
				continentsList.add(line.split("=")[0].trim());
			}
			else if (!line.equals("") && !line.trim().equals("[Territories]")
					&& isTerritory) {
				if (line.split(",").length < 4) {
					isContinentValid = false;
				} else if(!continentsList.contains(line.split(",")[3].trim())) {
					boolean isFound = false;
					for(String continent:continentsList) {
						if(continent.equalsIgnoreCase(line.split(",")[3].trim())) {
							isFound = true;
							break;
						}
					}
					if(!isFound) {
						isContinentValid = false;
					}
				}
			}
		}
		return isContinentValid;
	}

	/**
	 * Loads the map to the map model such as continents and territories
	 * @throws MapException 
	 */
	public void loadGameMap() throws MapException {
		try {
			if(isBaseMapModified) {
				mapFileStream = new File(Utility.getMapPath(fileName));
			} else {
				mapFileStream = new File(Utility.getMapPath(Constants.DEFAULT_MAP_FILE_NAME));
			}
			Scanner scn = new Scanner(mapFileStream);
			String name;
			int id, score, x_pos, y_pos;
			boolean isContinentsDone = false;
			String line;
			while (scn.hasNextLine()) {
				line = scn.nextLine();
				//Continents section
				if (line.equals("[Continents]")) {
					line = scn.nextLine();
					id = 0;
					do {
						if(line.split("=").length > 1) {
							name = line.split("=")[0].trim();
							score = Integer.parseInt(line.split("=")[1].trim());
							//Adding new continent to the list of all continents 
							continents.add(new ContinentModel(id++, name, score));
						}
						line = scn.nextLine();
						if(line.matches("")) {
							isContinentsDone = true;
						}
					} while(!isContinentsDone);
				}

				//Territories section
				if (line.equals("[Territories]")) {
					int index = 0;
					do {
						line = scn.nextLine();
						if(!line.matches("")) {
							String[] str = line.split(",");
							id = index++;
							name = str[0].trim();
							x_pos = Integer.parseInt(str[1].trim());
							y_pos = Integer.parseInt(str[2].trim());

							for(int i=0;i<continents.size();i++) {
								//Adding new territory to the list of all territories
								if(continents.elementAt(i).getName().equalsIgnoreCase(str[3].trim())) {
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
			//Adds the adjacent territories to each territory
			while(scn.hasNextLine()) {
				line = scn.nextLine();
				if (line.equals("[Territories]")) {
					do {
						line = scn.nextLine();
						if(!line.matches("")) {
							String[] str = line.split(",");
							Vector<TerritoryModel> adjacentTerritories = new Vector<>();
							for(int i=4;i<str.length;i++) {
								boolean isValidTerritory = false;
								for(int j=0;j<territories.size();j++) {
									if(territories.elementAt(j).getName().equalsIgnoreCase(str[i].trim())
											&& !str[0].trim().equalsIgnoreCase(str[i].trim())) {
										adjacentTerritories.add(territories.elementAt(j));
										isValidTerritory = true;
										break;
									}
								}
								if(!isValidTerritory) {
									throw new MapException(Constants.NOT_A_CONNECTED_MAP_MESSAGE + str[i]);
								}
							}
							for(int i=0;i<territories.size();i++) {
								if(territories.elementAt(i).getName().equalsIgnoreCase(str[0].trim())) {
									territories.elementAt(i).setAdjacentTerritories(adjacentTerritories);
								}
							}
						}
					} while(scn.hasNextLine());
				}
			}

			for(TerritoryModel territoryModel:territories) {
				if(territoryModel.getAdjacentTerritories().size() == 0) {
					throw new MapException(Constants.NOT_A_CONNECTED_MAP_MESSAGE + territoryModel.getName());
				}
			}
			scn.close();
			isCompleteConnectionExist();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * API to check for complete connection i.e completely connected map
	 * @return true if connected
	 * @throws MapException
	 */
	public boolean isCompleteConnectionExist() throws MapException {
		TerritoryModel territoryModel = territories.get(0);
		HashSet<Integer> territoryIds = new HashSet<>();
		territoryIds.add(territoryModel.getId());
		territoryIds = isCompleteConnectedMap(territoryModel,territoryIds);
		if(territoryIds.size() < territories.size()) {
			throw new MapException(Constants.NOT_COMPLETE_CONNECTED_MAP_MESSAGE);
		}
		return true;
	}

	/**
	 * Recursively traversing through the territories to reach all possible territories
	 * @param territoryModel It is an instance of TerritoryModel that holds territories details
	 * @param territoryIds It is an set that stores the territories id's
	 * @return set of traversed territory id's
	 */
	public HashSet<Integer> isCompleteConnectedMap(TerritoryModel territoryModel, HashSet<Integer> territoryIds){
		if(!isFinished(territoryIds)) {
			if(!territoryIds.contains(new Integer(territoryModel.getId()))) {
				territoryIds.add(territoryModel.getId());
			}
			for(TerritoryModel territory:territoryModel.getAdjacentTerritories()) {
				if(isFinished(territoryIds)) {
					break;
				}
				territoryIds = processTerritory(territoryIds, territory);
			}
		}
		return territoryIds;
	}
	
	/**
	 * Is traversal trough all territories done
	 * @param territoryIds It is an set that stores the territories id's
	 * @return true if all countries are traversed
	 */
	private boolean isFinished(HashSet<Integer> territoryIds) {
		if(territories.size() == territoryIds.size() || territoryIds.size() > territories.size()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * If the territory is not processed it will process and to list of traversed territories
	 * @param territoryIds It is an set that stores the territories id's
	 * @param territory It is an instance of TerritoryModel that holds territories details
	 * @return set of processed territories
	 */
	private HashSet<Integer> processTerritory(HashSet<Integer> territoryIds, TerritoryModel territory) {
		if(!territoryIds.contains(territory.getId())) {
			territoryIds = isCompleteConnectedMap(territory,territoryIds);
		}
		return territoryIds;
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
		if(unOccupiedTerritories == null || unOccupiedTerritories.size() == Constants.ZERO) {
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
		while(unOccupiedTerritories.size() > Constants.ZERO) {
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
	 * @param territoryModel It is an instance of TerritoryModel that holds territories details
	 * @param playerModel It is an instance of PlayerModel that holds players details
	 */
	private void occupyTerritory(TerritoryModel territoryModel, PlayerModel playerModel) {
		territoryModel.setPlayerModel(playerModel);
		playerModel.addOccupiedTerritory(territoryModel);
		territoryModel.addArmy();
		playerModel.looseArmy();
	}

	/**
	 * Add an army on a territory from an occupied player
	 * @param territoryModel It is an instance of TerritoryModel that holds territories details
	 * @param playerModel It is an instance of PlayerModel that holds players details
	 */
	private void addArmyOnOccupiedTerritory(TerritoryModel territoryModel, PlayerModel playerModel) {
		territoryModel.addArmy();
		playerModel.looseArmy();
	}

	/**
	 * Changes the current player to next player
	 */
	public void nextPlayer() {
		if(currentPlayer.getId() == players.lastElement().getId()) {
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
	 * @param x_coordinate It stores X_coordinate of map
	 * @param y_coordinate It stores y_coordinate of map
	 */
	public void gamePhasePlayerTurnSetup(int x_coordinate, int y_coordinate) {
		TerritoryModel territoryModel = getTerritoryFromMapLocation(x_coordinate, y_coordinate);
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
					if(playerModel.getArmies() == Constants.ZERO) {
						index++;
					}
				}

				if (index == players.size()) {
					setState(Constants.START_TURN);
				}
				break;
			case Constants.RE_ENFORCEMENT_PHASE:
				if(territoryModel.getPlayerModel().getId() == currentPlayer.getId()) {
					addArmyOnOccupiedTerritory(territoryModel, currentPlayer);
					if(currentPlayer.getArmies() == Constants.ZERO) {
						setState(Constants.ACTIVE_TURN);
					}
				}  
				break;
			}
		}
	}

	/**
	 * Final modifications done by the current player
	 * @param x_coordinate It stores X_coordinate of map
	 * @param y_coordinate It stores Y_coordinate of map
	 * @return status to display on game screen
	 */
	public String gamePhaseActivePlayerActions(int x_coordinate, int y_coordinate) {
		TerritoryModel territoryModel = getTerritoryFromMapLocation(x_coordinate, y_coordinate);
		if(territoryModel != null) {
			System.out.println(" territory model name = "+territoryModel.getName());
			if (getState() == Constants.FORTIFICATION_PHASE || getState() == Constants.FORTIFYING_PHASE) {
				return currentPlayer.fortify(this, territoryModel);
			} else if(getState() == Constants.ATTACK_PHASE || getState() == Constants.ATTACKING_PHASE) {
				return currentPlayer.attack(this, territoryModel);
			}
		}
		return "";
	}

	/**
	 * Move armies from one territory to other
	 * @return true if moving successful
	 */
	public Boolean moveArmies() {
		boolean isMoved = false;
		if(moveArmiesFromTerritory.getArmies() > noOfArmiesToMove) {
			moveArmiesToTerritory.addArmies(noOfArmiesToMove);
			moveArmiesFromTerritory.looseArmies(noOfArmiesToMove);
			isMoved = true;
		} 
		return isMoved;
	}

	/**
	 * API to calculate continent bonus for current player
	 * @return no of armies as bonus
	 */
	public int continentBonus() {
		int bonus = Constants.ZERO;
		for(ContinentModel continentModel:continents) {
			if(continentModel.isContinentOccupiedBy(currentPlayer)) {
				bonus += continentModel.getScore();
			}
		}
		return bonus;
	}

	/**
	 * To find out the territory location by coordinates (+/- 20)
	 * @param x_coordinate It stores X_coordinate of map
	 * @param y_coordinate It stores Y_coordinate of map
	 * @return TerritoryModel corresponding to the co_odinates
	 */
	public TerritoryModel getTerritoryFromMapLocation(int x_coordinate, int y_coordinate) {
		TerritoryModel territoryModel = null;
		int size = Constants.TWENTY;
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
		case Constants.ATTACKING_PHASE:
		case Constants.ATTACK_FIGHT_PHASE:
		case Constants.CAPTURE:
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
	
	public boolean isWon() {
		boolean isWon = true;
		for(TerritoryModel territory:territories) {
			if(territory.getPlayerModel().getId() != currentPlayer.getId()) {
				isWon = false;
				break;
			}
		}
		return isWon;
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
