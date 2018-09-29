package col.cs.risk.helper;

import java.util.Random;

/**
 * 
 * @author Team
 * Utility is a list of supporting APIs
 *
 */
public class Utility {
	
	/**
	 * Gets the path of the map file
	 * @param filename
	 * @return path as string
	 */
	public static String getMapPath(String filename) {
		//String path = System.getProperty("user.dir");
		return "resources/risk/map/"+filename;
	}
	
	public static String getImagePath(String filename) {
		return "resources/risk/images/"+filename;
	}

	/**
	 * 
	 * @param num
	 * @return returns the random number between 0 and num parameter
	 */
	public static int getRandomNumber(int num) {
		return new Random().nextInt(num);
	}
}
