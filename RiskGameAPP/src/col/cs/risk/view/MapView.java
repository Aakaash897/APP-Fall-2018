package col.cs.risk.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import col.cs.risk.controller.GameController;
import col.cs.risk.helper.Utility;
import col.cs.risk.model.Constants;

/**
 * This class handles the display of map and also shows the player details this
 * shows the main game play screen.
 * 
 * @author Team25
 *
 */
public class MapView extends JFrame implements MouseListener {

	/** serial version id */
	private static final long serialVersionUID = 3468501561814767862L;

	/** status label */
	private JLabel statusLabel;

	/** attack button */
	private JButton attackButton;

	/** fortify button */
	private JButton fortifyButton;
	
	/** card exchange Button	 */
	private JButton cardButton;

	/** Field to take user input for moving armies during fortification */
	private JTextField userEntered;

	/** end button */
	private JButton endButton;

	/** game controller instance */
	GameController gameController;

	/**
	 * Default Constructor
	 */
	public MapView() {
		setTitle("Risk Conquest Game");
	}

	/**
	 * Constructor with GameController parameter This initialize the main mapview of
	 * the game
	 * 
	 * @param gameController
	 */
	public MapView(GameController gameController) {
		this();
		this.gameController = gameController;
		gameController.setMapView(this);
		showMap();
		gameController.getMapMainPanel().addMouseListener(this);
	}

	/**
	 * Load Map and player details
	 */
	private void showMap() {
		statusLabel = new JLabel();
		attackButton = new JButton();
		fortifyButton = new JButton();
		cardButton = new JButton();
		endButton = new JButton();
		userEntered = new JTextField();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(GameController.class);

		setTitle(resourceMap.getString("Form.title"));
		setBackground(resourceMap.getColor("Form.background"));
		setForeground(resourceMap.getColor("Form.foreground"));
		setName("Form");

		gameController.getMapMainPanel().setBackground(resourceMap.getColor("mapMainPanel.background"));
		gameController.getMapMainPanel().setBorder(null);
		gameController.getMapMainPanel().setName("mapMainPanel");
		gameController.getMapMainPanel().setLayout(null);

		gameController.getMapSubPanelPlayer().setBackground(resourceMap.getColor("mapSubPanelPlayer.background"));
		gameController.getMapSubPanelPlayer().setBorder(null);
		gameController.getMapSubPanelPlayer().setForeground(resourceMap.getColor("mapSubPanelPlayer.foreground"));
		gameController.getMapSubPanelPlayer().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		gameController.getMapSubPanelPlayer().setName("mapSubPanelPlayer");
		gameController.getMapSubPanelPlayer().setLayout(new AbsoluteLayout());

		statusLabel.setFont(resourceMap.getFont("statusLabel.font"));
		statusLabel.setForeground(resourceMap.getColor("statusLabel.foreground"));
		statusLabel.setText(Constants.REINFORCE_MESSAGE);
		statusLabel.setForeground(Color.WHITE);
		statusLabel.setFont(new Font("Airal", Font.PLAIN, 14));
		statusLabel.setName("statusLabel");
		gameController.getMapSubPanelPlayer().add(statusLabel, new AbsoluteConstraints(122, 70, 610, -1));

		attackButton.setVisible(false);
		attackButton.setFont(resourceMap.getFont("attackButton.font"));
		attackButton.setText(Constants.ATTACK_MESSAGE);
		attackButton.setName(Constants.ATTACK_MESSAGE);
		attackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				gameController.attackButtonActionPerformed(evt);

			}
		});
		gameController.getMapSubPanelPlayer().add(attackButton,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(635, 30, 90, 30));

		fortifyButton.setVisible(false);
		fortifyButton.setFont(resourceMap.getFont("fortifyButton.font"));
		fortifyButton.setText(Constants.FORTIFY_MESSAGE);
		fortifyButton.setName(Constants.FORTIFY_MESSAGE);
		fortifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				gameController.fortifyButtonActionPerformed(evt);
			}
		});
		gameController.getMapSubPanelPlayer().add(fortifyButton,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(735, 30, 90, 30));
		
		cardButton.setVisible(false);
		cardButton.setFont(resourceMap.getFont("CardButton.font")); 
		cardButton.setText("Card"); 
		cardButton.setName("CardButton"); 
		cardButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				//CardButtonMouseClicked(evt);
			}
		});
		gameController.getMapSubPanelPlayer().add(cardButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(799, 25, 220, 25));

		userEntered.setVisible(false);
		userEntered.setFont(resourceMap.getFont("userEntered.font"));
		userEntered.setText("");
		userEntered.setName("userEntered");
		userEntered.setEditable(false);
		userEntered.setBackground(Color.white);
		userEntered.setForeground(Color.black);
		userEntered.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				gameController.userEnteredDataActionPerformed(evt);
			}
		});
		gameController.getMapSubPanelPlayer().add(userEntered, new AbsoluteConstraints(735, 27, 90, 30));

		endButton.setVisible(false);
		endButton.setFont(resourceMap.getFont("EndButton.font"));
		endButton.setText("End");
		endButton.setName("EndButton");
		endButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				gameController.endButtonActionPerformed(evt);
			}
		});
		gameController.getMapSubPanelPlayer().add(endButton, new AbsoluteConstraints(835, 30, -1, 30));

		int mapHeight = gameController.getMapMainPanel().getImageHeight();
		int gap;
		if (mapHeight < 621) {
			mapHeight += 30;
			gap = 30;
		} else {
			mapHeight = 660;
			gap = 20;
		}

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(gameController.getMapMainPanel(), javax.swing.GroupLayout.DEFAULT_SIZE, 988,
						Short.MAX_VALUE)
				.addComponent(gameController.getMapSubPanelPlayer(), javax.swing.GroupLayout.DEFAULT_SIZE, 988,
						Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(gameController.getMapMainPanel(), javax.swing.GroupLayout.PREFERRED_SIZE,
								mapHeight, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(gap).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(gameController.getMapSubPanelPlayer(), javax.swing.GroupLayout.DEFAULT_SIZE, 131,
								Short.MAX_VALUE)));
		pack();
	}

	/**
	 * Update the map details on map view
	 */
	public void updateMapPanel() {
		gameController.getMapMainPanel().repaint();
	}
	
	/**
	 * Update the player details on player view
	 */
	public void updatePlayerPanel() {
		gameController.getMapSubPanelPlayer().repaint();
	}
	
	/**
	 * Update both player and map details on view
	 */
	public void updateMapView() {
		gameController.getMapMainPanel().repaint();
		gameController.getMapSubPanelPlayer().repaint();
	}
	
	/**
	 * Show option to the players to select the no. of armies fight or dice to roll
	 * @param message
	 * @param maximumNo
	 * @param image
	 * @param player
	 * @return integer of the selected option
	 */
	public int showOptionPopup(String message, int maximumNo, String image, String player) {
		ImageIcon icon = new ImageIcon(Utility.getImagePath(image));
		Integer[] options = new Integer[maximumNo];
		for(int i=0;i<maximumNo;i++) {
			options[i] = i+1;
		}
		int noOfDice = JOptionPane.showOptionDialog(null, player+", select the number of armies(dice) to fight",
				message,
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon, options, options[0]);
		return noOfDice+1;
	}
	
	/** 
	 * Once the attacker captures the defending country option given to user to select the no. of armies to move. 
	 * @param maximumNo
	 * @return no. of armies to move
	 */
	public int showInputDialogPopup(int maximumNo) {
		Integer[] options = new Integer[maximumNo];
		for(int i=0;i<maximumNo;i++) {
			options[i] = i+1;
		}
		Object value = JOptionPane.showInputDialog(null, "Select the no. of armies to move to the captured country", 
        		"Move armies", JOptionPane.QUESTION_MESSAGE, null, options, options[options.length-1]);
        int selectedNoOfArmiesToMove = options[options.length-1];
        if(value != null) {
        	selectedNoOfArmiesToMove = (Integer) value;
        }
        return selectedNoOfArmiesToMove;
	}
	
	/**
	 * Option given for user to select/reject the request(action - all out mode)
	 * @param message
	 * @param options
	 * @return
	 */
	public String showOptionPopup(String message, String[] options) {
		int selection = JOptionPane.showOptionDialog(null, message,
				"Selection popup",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		return options[selection];
	}
	
	/**
	 * Exiting from the application
	 */
	public void exitWindow() {
		System.exit(0);
	}

	/**
	 * Returns the statusLabel
	 * 
	 * @return the statusLabel
	 */
	public JLabel getStatusLabel() {
		return statusLabel;
	}

	/**
	 * Sets the statusLabel for this class
	 * 
	 * @param statusLabel
	 *            the statusLabel to set
	 */
	public void setStatusLabel(JLabel statusLabel) {
		this.statusLabel = statusLabel;
	}

	/**
	 * Provides the the endButton to close the application
	 * 
	 * @return the endButton
	 */
	public JButton getEndButton() {
		return endButton;
	}

	/**
	 * Sets the endButton
	 * 
	 * @param endButton
	 *            the endButton to set
	 */
	public void setEndButton(JButton endButton) {
		this.endButton = endButton;
	}

	/**
	 * Returns the fortifyButton
	 * 
	 * @return the fortifyButton
	 */
	public JButton getFortifyButton() {
		return fortifyButton;
	}

	/**
	 * @param fortifyButton
	 *            the fortifyButton to set
	 */
	public void setFortifyButton(JButton fortifyButton) {
		this.fortifyButton = fortifyButton;
	}

	/**
	 * @return the userEntered
	 */
	public JTextField getUserEntered() {
		return userEntered;
	}

	/**
	 * @param userEntered
	 *            the userEntered to set
	 */
	public void setUserEntered(JTextField userEntered) {
		this.userEntered = userEntered;
	}

	/**
	 * @return the attackButton
	 */
	public JButton getAttackButton() {
		return attackButton;
	}

	/**
	 * @param attackButton
	 *            the attackButton to set
	 */
	public void setAttackButton(JButton attackButton) {
		this.attackButton = attackButton;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClicked(MouseEvent event) {
		gameController.mouseClicked(event);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
