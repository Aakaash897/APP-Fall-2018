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

import col.cs.risk.model.GameModel;
import col.cs.risk.model.PlayerModel;

/**
 * This is an observer class that provides the player domination details
 * such as the percentage controlled, total number of armies and also the
 * territories controller by each after every event.
 * 
 * @author Team25
 *
 */
public class PlayerDominationView implements Observer {

	private JPanel playerDominationViewPanel;

	private JTextArea playerViewTextArea;

	private JFrame playerFrame;
	
	private GameModel gameModel;
	
	private StringBuilder stringBuilder = null;

	private static PlayerDominationView playerDominationView;

	public static PlayerDominationView getInstance() {
		if(playerDominationView == null) {
			playerDominationView = new PlayerDominationView();
		}
		return playerDominationView;
	}

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
		playerFrame.setVisible(true);
	}

	@Override
	public void update(Observable object, Object arg) {
		if(object != null) {
			this.stringBuilder = ((PlayerModel)object).getDominationViewContent(this.stringBuilder, gameModel);
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
	 * @param stringBuilder the stringBuilder to set
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
	 * @param gameModel the gameModel to set
	 */
	public void setGameModel(GameModel gameModel) {
		this.gameModel = gameModel;
	}
	
}
