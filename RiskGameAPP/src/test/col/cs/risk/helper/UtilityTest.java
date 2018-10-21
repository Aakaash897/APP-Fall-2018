package test.col.cs.risk.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import col.cs.risk.helper.MapException;
import col.cs.risk.helper.Utility;
import col.cs.risk.model.Constants;

/**
 * Utility Test class for testing Utility class methods i.e., static methods that are 
 * commonly used across the project.
 * 
 * @author Team25
 *
 */
public class UtilityTest {
	
	/**
	 * Test case for testing whether the adjacent countries are valid countries to connect
	 */
	@Test
	public void testIsConnectedMap() {
		StringBuilder result = new StringBuilder();
		result.append("[Territories]\n");
		result.append("India,20,20,Asia,China\n");
		result.append("China,20,30,Asia,Srilanka\n");
		result.append("Srilanka,50,60,Asia,Africa\n");
		result.append("Africa,70,80,Asia,Srilanka\n");
		
		try {
			assertTrue(Utility.isConnectedMap(result.toString()));
		} catch (MapException e) {
			e.printStackTrace();
		}
		
		result.append("America,70,80,Asia,London\n");
		result.append("London,70,80,Asia,Australia\n");
		try {
			Utility.isConnectedMap(result.toString());
		} catch (MapException ex) {
			assertEquals(Constants.NOT_A_CONNECTED_MAP_MESSAGE+"Australia", ex.getMessage());
		}
	}
	
	/**
	 * Test case to check Writing of data to a file 
	 * @throws IOException 
	 */
	@Test
	public void testWriteDataToFile() throws IOException {
		StringBuilder mapString = new StringBuilder();
		String fileName = "currMap.map";
		StringBuilder result = new StringBuilder();
		result.append("hi how are you\n");
		result.append("I am goog\n");
		
		Utility.writeToFile(fileName,result.toString());
		
		BufferedReader buffReader = new BufferedReader(new FileReader(Utility.getMapPath(fileName)));
		String line;
		while((line = buffReader.readLine())!=null) {
			mapString.append(line);
			mapString.append("\n");
		}
		buffReader.close();
		assertEquals(result.toString(),mapString.toString());
	}
}
