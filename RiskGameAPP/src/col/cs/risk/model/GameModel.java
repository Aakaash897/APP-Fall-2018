package col.cs.risk.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import col.cs.risk.helper.Utility;

/**
 * 
 * @author Team
 * 	GameModel class is to maintain game data such as continents, territories, players.
 *
 */
public class GameModel {

	/** Default map string */
	public StringBuilder baseMapString;
	
	/** Is map file modified */
	public static boolean isBaseMapModified;
	
	/** modified game map data */
	public static StringBuilder modifiedMapString;

	/** name of the modified file */
	public static String fileName = "World.map";
	
	public File mapInputFile;

	public Vector<ContinentModel> continents = new Vector<>();
	public Vector<TerritoryModel> territories = new Vector<>();
	public Vector<PlayerModel> players = new Vector<>();

	private boolean isGameMapValid;

	/**
	 * Constructor without parameter for initial verification and setup
	 */
	public GameModel() {
		initializeMapAttributes();
		validateAndLoadMap();

	}


	/**
	 * @return the isValidGameMap
	 */
	public boolean isGameMapValid() {
		return isGameMapValid;
	}


	/**
	 * @param isValidGameMap the isValidGameMap to set
	 */
	public void setGameMapValid(boolean isValidGameMap) {
		this.isGameMapValid = isValidGameMap;
	}



	/**
	 * Initializes the map attributes especially map file as text data
	 * for further processing
	 */
	public void initializeMapAttributes() {
		baseMapString = new StringBuilder();
		File file = new File(Utility.getMapPath("World.map"));
		BufferedReader buffReader;
		try {
			buffReader = new BufferedReader(new FileReader(file));
			String line;
			while((line = buffReader.readLine())!=null) {
				baseMapString.append(line+"\n");
			}
			buffReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Validates and loads the map
	 */
	private void validateAndLoadMap() {
		if(isValidMapFormat()) {
			setGameMapValid(true);
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
	 * 
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
	 * 
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
	 * 
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
				//System.out.println(continentsList.stream().map(x->x.toString()).collect(Collectors.toList()));
				//System.out.println(" line = "+line);
				if (!continentsList.contains(line.split(",")[3]))
					return false;
			}
		}
		return isContinentValid;
	}

	/**
	 * Loads the map to the map model such as continents and territories
	 */
	public void loadGameMap() {
		try {
			mapInputFile = new File(Utility.getMapPath("World.map"));
			Scanner scn = new Scanner(mapInputFile);
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
											continents.elementAt(i).getId(), continents.elementAt(i));
									territories.addElement(territory);
									continents.elementAt(i).addTerritory(territory);
								}
							}
						}
					} while(scn.hasNextLine());
				}
			}

			scn = new Scanner(mapInputFile);
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
	 * Writes data to file
	 */
	public void writeDataToFile() {
		String result = getMapContentToWrite();
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Utility.getMapPath("output.map")));
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
		result.append("image=output.bmp"+"\n");
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

}
