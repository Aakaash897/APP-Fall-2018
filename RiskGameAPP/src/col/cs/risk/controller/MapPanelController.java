package col.cs.risk.controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import col.cs.risk.helper.Utility;
import col.cs.risk.model.Constants;
import col.cs.risk.model.GameModel;
import col.cs.risk.model.PlayerModel;
import col.cs.risk.model.TerritoryModel;

/**
 * MapPanelcontroller This class handles the graphical representation of the
 * map. It draws the connection b/w the territories.
 * 
 * @author Team25
 *
 */
public class MapPanelController extends JPanel {

	/** Serial id */
	private static final long serialVersionUID = -8886545109650518679L;

	/** Map Image */
	private Image mapImage;

	/** Default map image filename */
	private String mapImageName = "World.bmp";

	/** Game Model instance */
	private GameModel gameModel;

	/**
	 * Controller without parameters
	 */
	public MapPanelController() {
		if (!GameModel.imageSelected.equals("")) {
			mapImageName = GameModel.imageSelected;
		}
		try {
			mapImage = ImageIO.read(new File(Utility.getImagePath(mapImageName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Controller with GameModel Parameter
	 * 
	 * @param gameModel
	 */
	public MapPanelController(GameModel gameModel) {
		this();
		this.gameModel = gameModel;
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		graphics.drawImage(mapImage, 10, 7, null);
		graphics.setColor(Color.white);

		for (TerritoryModel territoryModel : gameModel.getTerritories()) {
			graphics.drawRect(territoryModel.getX_pos(), territoryModel.getY_pos(), 25, 25);
		}

		if (gameModel.getTerritories().size() > 0) {
			connectAjdacentCountries(graphics);
		}

		putPlayersAndArmies(graphics);

		graphics.setFont(new Font("Verdana", Font.BOLD, 15));
		graphics.setColor(Color.black);
		int height = getImageHeight()-Constants.FIVE;
		if (height < 621) {
			height += 30;
		} else {
			height = 650;
		}
		graphics.drawString("Current state: " + gameModel.getStateAsString(), 10, height);
	}

	/**
	 * Adding armies on territories
	 * 
	 * @param graphics
	 */
	private void putPlayersAndArmies(Graphics graphics) {
		int alignment;
		for (TerritoryModel territoryModel : gameModel.getTerritories()) {
			PlayerModel playerModel = territoryModel.getPlayerModel();
			if (territoryModel.getArmies() > 9) {
				alignment = -3;
			} else {
				alignment = 0;
			}

			if (playerModel == null) {
				graphics.setColor(Color.white);
			} else {
				graphics.setColor(Utility.getColor(playerModel.getId()));
			}

			graphics.fillRect(territoryModel.getX_pos(), territoryModel.getY_pos(), 25, 25);
			graphics.setColor(Color.white);
			graphics.drawString(territoryModel.getArmies() == 0 ? "" : Integer.toString(territoryModel.getArmies()),
					territoryModel.getX_pos() + 8 + alignment, territoryModel.getY_pos() + 18);
		}
	}

	/**
	 * Connecting adjacent countries
	 * 
	 * @param graphics
	 */
	private void connectAjdacentCountries(Graphics graphics) {
		for (TerritoryModel territoryModel : gameModel.getTerritories()) {
			if (territoryModel.getAdjacentTerritories().size() > 0) {
				for (TerritoryModel adjacentModel : territoryModel.getAdjacentTerritories()) {
					connectCounties(territoryModel.getX_pos(), territoryModel.getY_pos(), adjacentModel.getX_pos(),
							adjacentModel.getY_pos(), graphics);
				}
			}
		}
	}

	/**
	 * Connecting counties by drawing a line
	 * 
	 * @param srcX_pos
	 * @param srcY_pos
	 * @param destX_pos
	 * @param destY_pos
	 * @param graphics
	 */
	private void connectCounties(int srcX_pos, int srcY_pos, int destX_pos, int destY_pos, Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setColor(Color.black);
		graphics2D.setStroke(new BasicStroke(2));
		graphics2D.draw(new Line2D.Float(srcX_pos, srcY_pos, destX_pos, destY_pos));
	}

	/**
	 * Refresh and repaint the display
	 */
	public void refresh() {
		repaint();
	}

	/**
	 * @return the mapImage
	 */
	public Image getMapImage() {
		return mapImage;
	}

	/**
	 * @param mapImage
	 *            the mapImage to set
	 */
	public void setMapImage(Image mapImage) {
		this.mapImage = mapImage;
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

	/**
	 * @return the mapImageName
	 */
	public String getMapImageName() {
		return mapImageName;
	}

	/**
	 * @param mapImageName
	 *            the mapImageName to set
	 */
	public void setMapImageName(String mapImageName) {
		this.mapImageName = mapImageName;
	}

	/**
	 * 
	 * @return Height of MapImage
	 */
	public int getImageHeight() {
		return mapImage.getHeight(null);
	}

}
