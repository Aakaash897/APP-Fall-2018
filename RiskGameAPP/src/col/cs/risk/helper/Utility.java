package col.cs.risk.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Utility is a list of supporting APIs
 * @author Team 
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
	 * @return path string
	 */
	public static String getApplicationPath() {
		return System.getProperty("user.dir");
	}

	/**
	 * Gets the path of the map files(text)
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
	 * @param filename
	 * @return
	 */
	public static String getImagePath(String filename) {
		return "resources/risk/images/" + filename;
	}

	/**
	 * Save path
	 * @param mapFilePath
	 */
	public static void saveMapFilePath(String mapFilePath) {
		selectedMapFilePath = mapFilePath;
	}

	/**
	 * GetSavedPath
	 * @return path string
	 */
	public static String getUsedMapFilePath() {
		return selectedMapFilePath;
	}

	/**
	 * Generates the random number
	 * @param num
	 * @return returns the random number between 0 and num parameter
	 */
	public static int getRandomNumber(int num) {
		return new Random().nextInt(num);
	}

	public static void saveMapString() {
		baseMapString = new StringBuilder();
		File file = new File(selectedMapFilePath);
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
	 * Write content to file
	 * @param fileName name of the file to write
	 * @param result String content to write
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
}
