package col.cs.risk.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.JOptionPane;

import col.cs.risk.model.Constants;

/**
 * Utility is a list of supporting APIs.This class contains all the helping
 * variables and methods that are used multiple times These are all static
 * methods
 * 
 * @author Team25
 *
 */
public class Utility {
	/** Selected file path */
	public static String selectedMapFilePath;

	/** Map string */
	public static StringBuilder baseMapString;

	/**
	 * Gets the path of the map file
	 * 
	 * @param filename
	 * @return path as string
	 */
	public static String getMapPath(String filename) {
		return "resources/risk/map/" + filename;
	}

	/**
	 * Gets the path of the Application
	 * 
	 * @return path string
	 */
	public static String getApplicationPath() {
		return System.getProperty("user.dir");
	}

	/**
	 * Gets the path of the map files(text)
	 * 
	 * @return path string
	 */
	public static String getResouceMapPath() {
		StringBuilder path = new StringBuilder();
		path.append(getApplicationPath());
		path.append("/resources/risk/map");
		return path.toString();
	}

	/**
	 * Gets the image path
	 * 
	 * @param filename
	 * @return
	 */
	public static String getImagePath(String filename) {
		return "resources/risk/images/" + filename;
	}

	/**
	 * Save path
	 * 
	 * @param mapFilePath
	 */
	public static void saveMapFilePath(String mapFilePath) {
		selectedMapFilePath = mapFilePath;
	}

	/**
	 * GetSavedPath
	 * 
	 * @return path string
	 */
	public static String getUsedMapFilePath() {
		return selectedMapFilePath;
	}

	/**
	 * Generates the random number
	 * 
	 * @param num
	 * @return returns the random number between 0 and num parameter
	 */
	public static int getRandomNumber(int num) {
		return new Random().nextInt(num);
	}

	/**
	 * Saving the modified map content
	 */
	public static void saveMapString() {
		baseMapString = new StringBuilder();
		File file = new File(selectedMapFilePath);
		BufferedReader buffReader;
		try {
			buffReader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = buffReader.readLine()) != null) {
				baseMapString.append(line + "\n");
			}
			buffReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write content to file
	 * 
	 * @param fileName
	 *            name of the file to write
	 * @param result
	 *            String content to write
	 */
	public static void writeToFile(String fileName, String result) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Utility.getMapPath(fileName)));
			bufferedWriter.write(result);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Message pop up to show error message
	 * 
	 * @param errorMessage
	 */
	public static void showPopUp(String errorMessage) {
		JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Checks is all territories are connected
	 * 
	 * @param result
	 * @returns true if connected
	 * @throws MapException
	 */
	public static boolean isConnectedMap(String result) throws MapException {
		try {
			InputStream is = new ByteArrayInputStream(result.getBytes());
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			HashMap<String, Integer> territoryNames = new HashMap<String, Integer>();

			while ((line = reader.readLine()) != null) {
				if (line.equals("[Territories]")) {
					while ((line = reader.readLine()) != null) {
						if (!line.matches("")) {
							String[] str = line.split(",");
							territoryNames.put(str[0].trim(), 0);
						}
					}
					break;
				}
			}
			is = new ByteArrayInputStream(result.getBytes());
			reader = new BufferedReader(new InputStreamReader(is));
			while ((line = reader.readLine()) != null) {
				if (line.equals("[Territories]")) {
					while ((line = reader.readLine()) != null) {
						if (!line.matches("")) {
							String[] str = line.split(",");
							int adjacents = 0;
							for (int i = 4; i < str.length; i++) {
								boolean isValidTerritory = false;
								for (Entry<String, Integer> territory : territoryNames.entrySet()) {
									if (!str[0].trim().equalsIgnoreCase(str[i].trim())
											&& territory.getKey().equalsIgnoreCase(str[i].trim())) {
										isValidTerritory = true;
										adjacents++;
										break;
									}
								}
								if (!isValidTerritory) {
									throw new MapException(Constants.NOT_A_CONNECTED_MAP_MESSAGE + str[i]);
								}
							}
							territoryNames.put(str[0].trim(), adjacents);
						}
					}
					break;
				}
			}
			for (Entry<String, Integer> territory : territoryNames.entrySet()) {
				if (territory.getValue() == 0) {
					throw new MapException(Constants.NOT_A_CONNECTED_MAP_MESSAGE + territory.getKey());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
