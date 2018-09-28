package col.cs.risk.helper;

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

}
