/**
 * 
 */
package col.cs.risk.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import col.cs.risk.helper.Utility;
import col.cs.risk.model.GameModel;
import col.cs.risk.model.PlayerModel;

/**
 * This is an observer class that provides the player domination details such as
 * the percentage controlled, total number of armies and also the territories
 * controller by each after every event.
 * 
 * @author Team25
 *
 */
public class PlayerDominationView implements Observer {

	/** player domination View panel */
	private JPanel playerDominationViewPanel;

	/** player Text area */
	private JTextArea playerViewTextArea;

	/** player Frame */
	private JFrame playerFrame;

	/** GameModel Instance */
	private GameModel gameModel;

	/** StringBuilder object */
	private StringBuilder stringBuilder = null;

	/** PlayerDominationView Instance */
	private static PlayerDominationView playerDominationView;

	/**
	 * 
	 * @return instance of PlayerDominationView
	 */
	public static PlayerDominationView getInstance() {
		if (playerDominationView == null) {
			playerDominationView = new PlayerDominationView();
		}
		return playerDominationView;
	}

	/**
	 * Checks whether object initialized
	 * @return
	 */
	public static boolean isInitialized() {
		return playerDominationView != null ? true : false;
	}

	/**
	 * Clears all objects
	 */
	public static void clear() {
		if(playerDominationView != null) {
			playerDominationView.playerFrame.setVisible(false);
			playerDominationView.playerFrame.dispose();
		}
		playerDominationView = null;
	}

	/**
	 * function that helps to maintain the frame size of the player domination view
	 * 
	 */
	public void showMonitor() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) (screenSize.height * 2 / 2.6);
		int width = (int) (screenSize.width * 2 / 4.9);
		playerFrame = new JFrame();
		JScrollPane scrollPanel;

		playerViewTextArea = new JTextArea("", 40, 40);
		playerViewTextArea.setEditable(false);
		scrollPanel = new JScrollPane(playerViewTextArea);
		scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		playerDominationViewPanel = new JPanel();
		playerDominationViewPanel.add(scrollPanel);

		playerFrame.setTitle("Player Domination View");
		playerFrame.setLocation(300, 10);
		playerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		playerFrame.setPreferredSize(new Dimension(width, height));
		playerFrame.add(playerDominationViewPanel);
		playerFrame.setResizable(true);
		playerFrame.pack();
		if(Utility.canShow) {
			playerFrame.setVisible(true);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable object, Object arg) {
		if (object != null) {
			this.stringBuilder = ((PlayerModel) object).getDominationViewContent(this.stringBuilder, gameModel);
			playerViewTextArea.setText(stringBuilder.toString());
		}
		playerFrame.repaint();
	}

	/**
	 * @return the stringBuilder
	 */
	public StringBuilder getStringBuilder() {
		return stringBuilder;
	}

	/**
	 * @param stringBuilder
	 *            the stringBuilder to set
	 */
	public void setStringBuilder(StringBuilder stringBuilder) {
		this.stringBuilder = stringBuilder;
	}

	/**
	 * @return the gameModel
	 */
	public GameModel getGameModel() {
		return gameModel;
	}

	/**
	 * @param gameModel
	 *            the gameModel to set
	 */
	public void setGameModel(GameModel gameModel) {
		this.gameModel = gameModel;
	}

}
