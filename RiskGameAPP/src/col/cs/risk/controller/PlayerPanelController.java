package col.cs.risk.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.GroupLayout;
import javax.swing.JPanel;

import col.cs.risk.model.GameModel;
import col.cs.risk.model.PlayerModel;

/**
 * Control display of player panel while playing
 * 
 * @author Team
 *
 */
public class PlayerPanelController extends JPanel {

	/** Serial version id */
	private static final long serialVersionUID = 7867550198028350235L;
	
	/** Game model instance */
	private GameModel gameModel;

	/** 
	 * Constructor with parameters
	 * Initialize the components
	 * @param gameModel
	 */
	public PlayerPanelController(GameModel gameModel) {
		this.gameModel = gameModel;
		initComponents();
		repaint();
	}

	/**
	 * Initialize components
	 */
	private void initComponents() {
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 700, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 100, Short.MAX_VALUE));		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Font font = new Font("Arial Black", Font.ITALIC, 26);
		int width, position;
		String name = gameModel.getCurrentPlayer().getName();
		int currPlayerId = gameModel.getCurrentPlayer().getId();

		for(PlayerModel playerModel:GameModel.players) {
			int location = 0;
			if(playerModel.getArmies() > 9) {
				location = -15;
			}

			if(currPlayerId == gameModel.getCurrentPlayer().getId()) {
				width = 915 - (GameModel.players.size() * 75);
				position = 115;
				graphics.setFont(font);
				graphics.setColor(Color.DARK_GRAY);
				graphics.drawString(name, position, 40);
				graphics.setFont(new Font("Courier", Font.TRUETYPE_FONT, 50));
				graphics.drawString(Integer.toString(gameModel.getCurrentPlayer().getArmies()), 50 + location, 70);
				graphics.setFont(new Font("Verdana", Font.BOLD, 15));
				graphics.drawString("Armies", 35, 90);
			}  else { 
				position = 915 - (GameModel.players.size() * 75);
				width= 75; 
			}

			switch(gameModel.getCurrentPlayer().getId()) {
			case 0: graphics.setColor(Color.red); break;
			case 1: graphics.setColor(Color.blue); break;
			case 2: graphics.setColor(Color.green); break;
			case 3: graphics.setColor(Color.orange); break;
			case 4: graphics.setColor(Color.pink); break;
			case 5: graphics.setColor(Color.darkGray); break;
			}
			
			graphics.fillRect(position + (playerModel.getId() * 75), 63, width, 35);
			gameModel.nextPlayer();
		}
	}

}
