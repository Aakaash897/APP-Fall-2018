package col.cs.risk.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * 
 * @author Team Utility is a list of supporting APIs
 *
 */
public class Utility {
	public static String selectedMapFilePath;
	public static StringBuilder baseMapString;

	/**
	 * Gets the path of the map file
	 * 
	 * @param filename
	 * @return path as string
	 */
	public static String getMapPath(String filename) {
		// String path = System.getProperty("user.dir");
		return "resources/risk/map/" + filename;
	}

	public static String getImagePath(String filename) {
		return "resources/risk/images/" + filename;
	}

	public static void saveMapFilePath(String mapFilePath) {
		selectedMapFilePath = mapFilePath;
	}

	public static String getUsedMapFilePath() {
		return selectedMapFilePath;
	}

	/**
	 * 
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
}
