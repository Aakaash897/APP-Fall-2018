package col.cs.risk.model;

/**
 * This class contains the list of Strings with declared values
 * that are used throughout the system.
 * 
 * @author Team25
 *
 */
public class Constants {
	// All the Constants are declared and initialized here
	public static final int NEW_GAME = 1;
	public static final int INITIAL_RE_ENFORCEMENT_PHASE = 2;
	public static final int RE_ENFORCEMENT_PHASE = 3;
	public static final int FORTIFICATION_PHASE = 4;
	public static final int END_PHASE = 5;
	public static final int START_TURN = 6;
	public static final int ACTIVE_TURN = 7;
	public static final int FORTIFYING_PHASE = 8;
	public static final int FORTIFY_PHASE = 9;
	public static final int ATTACK_PHASE = 10;
	public static final int ATTACKING_PHASE = 11;
	public static final int ATTACK_FIGHT_PHASE = 12;
	public static final String RE_ENFORCEMENT_MESSAGE = "Place an army on a territory you occupy";
	public static final String MOVE_FROM = "Select a country of yours to move armies from";
	public static final String MIN_TWO_ARMY_MESSAGE = "Your occupied country should have minimum 2 army";
	public static final String MOVE_TO = "Select a country of yours to move armies to, which should be an adjusant to ";
	public static final String ARMIES_TO_MOVE = "Enter the no. of armies to move, in text box & press enter";
	public static final String INITIAL_RE_ENFORCEMENT_PHASE_MESSAGE = "Initial Reinforcement";
	public static final String ATTACK_PHASE_MESSAGE = "Attack";
	public static final String REINFORCEMENT_PHASE_MESSAGE = "Reinforcement";
	public static final String FORTIFICATION_PHASE_MESSAGE = "Fortification";
	public static final String NEW_GAME_MESSAGE = "New Game";
	public static final String MIN_ONE_ARMY_MESSAGE = "Minimum one army should be left on a owned territory";
	public static final String VALID_DIGIT_MESSAGE = "Enter a valid digit";
	public static final String GAME_OVER_MESSAGE = "Game over";
	public static final String REINFORCE_MESSAGE = "Reinforce";
	public static final String FORTIFY_MESSAGE = "Fortify";
	public static final String SELECT_THE_ACTION_MESSAGE = "Click on the action you want to do";
	public static final String NOT_A_CONNECTED_MAP_MESSAGE = "Not a connected map invalid country exists: ";
	public static final String NOT_COMPLETE_CONNECTED_MAP_MESSAGE = "Not a completely connected map";
	public static final String INVALID_MAP_MESSAGE = "Invalid map, check the format";
	public static final String INVALID_PLAYER_NO_SELECTION_MESSAGE = "Invalid number of players selected";
	public static final String ATTACK_COUNTRY_SELECT_MESSAGE = "Select a territory of yours to attack from";
	public static final int ZERO = 0;
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int NINE = 9;
	public static final int TWENTY = 20;
	public static final String DEFAULT_MAP_FILE_NAME = "World.map";
	public static final String DEFAULT_MODIFIED_MAP_FILE_NAME = "currMap.map";
	public static final String DEFAULT_MODIFIED_MAP_IMAGE_FILE_NAME = "currMap.png";

}
