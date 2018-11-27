package col.cs.risk.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import col.cs.risk.model.Constants;

public class Report {

	private String mapFilename;

	private int noOfGames;

	//private HashMap<String, String> gameWinnerMap = new HashMap<>();
	private HashMap<Integer, String> gameWinnerMap = new HashMap<>();

	private int currentGameNo = 0;

	private HashMap<Integer, Boolean> gamesList = new HashMap<>();

	public Report(String filename, int noOfGames) {
		this.mapFilename = filename;
		this.noOfGames = noOfGames;
		init();
	}

	public void init() {
		for(int i=1;i<=noOfGames;i++) {
			gamesList.put(i, false);
		}
	}

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

	public int getNextGameNumber() {
		currentGameNo++;
		return currentGameNo;
	}

	public void addFinishedGame(int gameNo, String Winner) {
		gamesList.put(gameNo, true);
		//gameWinnerMap.put(getGameNoToString(gameNo), Winner);
		gameWinnerMap.put(gameNo, Winner);
	}

	/**
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
	 * @return the currentGameNo
	 */
	public int getCurrentGameNo() {
		return currentGameNo;
	}

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

		/*strBuilder.append(gameWinnerMap.keySet().stream().map(x->x.toString()).collect(Collectors.toList()));
		strBuilder.append("\n");
		strBuilder.append(gameWinnerMap.values().stream().map(x->x.toString()).collect(Collectors.toList()));*/
		strBuilder.append("\n-----------------------\n");
		return strBuilder.toString();
	}
}
