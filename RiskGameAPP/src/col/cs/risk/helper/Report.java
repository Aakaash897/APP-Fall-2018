package col.cs.risk.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Final tournament game report
 * Details of players who won the which game on which map
 * @author Team25
 *
 */
public class Report implements Serializable {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 1L;

	/** map file name */
	private String mapFilename;

	/** no of games on each map */
	private int noOfGames;

	/** winners/draw list */
	private HashMap<Integer, String> gameWinnerMap = new HashMap<>();

	/** current game number */
	private int currentGameNo = 0;

	/** games list */
	private HashMap<Integer, Boolean> gamesList = new HashMap<>();

	/**
	 * Constructor that intializes the main function init
	 * @param filename
	 * @param noOfGames
	 */
	public Report(String filename, int noOfGames) {
		this.mapFilename = filename;
		this.noOfGames = noOfGames;
		init();
	}

	/**
	 * Initialization
	 */
	public void init() {
		for(int i=1;i<=noOfGames;i++) {
			gamesList.put(i, false);
		}
	}

	/**
	 * returns true if game completed on current map
	 * @return isFinished boolean variable 
	 */
	public boolean isThisMapFinished() {
		boolean isFinished = true;
		for(Integer i:gamesList.keySet()) {
			if(gamesList.get(i) == false) {
				isFinished = false;
				break;
			}
		}
		return isFinished;
	}

	/**
	 * returns next game number
	 * @return currentGameNo
	 */
	public int getNextGameNumber() {
		currentGameNo++;
		return currentGameNo;
	}

	/**
	 * Add finished game to the list with winner details
	 * @param gameNo
	 * @param Winner
	 */
	public void addFinishedGame(int gameNo, String Winner) {
		gamesList.put(gameNo, true);
		gameWinnerMap.put(gameNo, Winner);
	}

	/**
	 * this returns the map file name
	 * @return the mapFileName
	 */
	public String getMapFileName() {
		return mapFilename;
	}

	/**
	 * @param mapFileName the mapFileName to set
	 */
	public void setMapFileName(String mapFileName) {
		this.mapFilename = mapFileName;
	}

	/**
	 * this returns the current game no being played
	 * @return the currentGameNo
	 */
	public int getCurrentGameNo() {
		return currentGameNo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(mapFilename);
		strBuilder.append("\n-----------------------\n");
		Set<Integer> games = gameWinnerMap.keySet();
		List<Integer> list = new ArrayList<>(games);
		Collections.sort(list);
		for(Integer game:games) {
			strBuilder.append("Game ");
			strBuilder.append(game);
			strBuilder.append(" : ");
			strBuilder.append(gameWinnerMap.get(game));
			strBuilder.append("\n");
		}
		strBuilder.append("\n-----------------------\n");
		return strBuilder.toString();
	}

	/**
	 * Game result
	 * @returns the game result
	 */
	public String getGamesResult() {
		StringBuilder strBuilder = new StringBuilder();
		Set<Integer> games = gameWinnerMap.keySet();
		List<Integer> list = new ArrayList<>(games);
		Collections.sort(list);
		for(Integer game:games) {
			strBuilder.append("\t | \t");
			strBuilder.append(gameWinnerMap.get(game));
		}
		return strBuilder.toString();
	}

	/**
	 * this returns the game winner map of hash map type
	 * @return the gameWinnerMap
	 */
	public HashMap<Integer, String> getGameWinnerMap() {
		return gameWinnerMap;
	}

}
