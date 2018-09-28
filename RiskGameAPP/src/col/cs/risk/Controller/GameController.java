package col.cs.risk.Controller;

import java.io.IOException;

import col.cs.risk.model.GameModel;

/**
 * 
 * @author Team
 * Game Model Controller
 *
 */
public class GameController  {
	
	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		GameModel  gameModel = new GameModel();
		gameModel.printTerritories();
		gameModel.printContinets();
		gameModel.writeDataToFile();
		System.out.println(" Is game Map valid = "+gameModel.isGameMapValid());
		
	}
}
