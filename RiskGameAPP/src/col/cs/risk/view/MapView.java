package col.cs.risk.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import col.cs.risk.controller.GameController;
import col.cs.risk.model.Constants;

/**
 * Display of map and player details
 * @author Team
 *
 */
public class MapView extends JFrame{

	/** serial version id */
	private static final long serialVersionUID = 3468501561814767862L;

	/** status label */
	private JLabel statusLabel;
	
	/** attack button */
	private JButton attackButton;

	/** fortify button */
	private JButton fortifyButton;

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
	 * Constructor with GameController parameter
	 * @param gameController
	 */
	public MapView(GameController gameController) {
		this();
		this.gameController = gameController;
		gameController.setMapView(this);
		showMap();
	}

	/**
	 * Load Map and player details
	 */
	private void showMap() {
		statusLabel = new JLabel();
		attackButton = new JButton();
		fortifyButton = new JButton();
		endButton = new JButton();
		userEntered = new JTextField();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		ResourceMap resourceMap = Application.getInstance()
				.getContext().getResourceMap(GameController.class);

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
		attackButton.setText(Constants.ATTACK_PHASE_MESSAGE); 
		attackButton.setName(Constants.ATTACK_PHASE_MESSAGE); 
		attackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				gameController.attackButtonActionPerformed(evt);
				
			}
		});
		gameController.getMapSubPanelPlayer().add(attackButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(635, 30, 90, 30));

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
		gameController.getMapSubPanelPlayer().add(fortifyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(735, 30, 90, 30));

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
		if(mapHeight < 621) {
			mapHeight += 30;
			gap = 30;
		} else {
			mapHeight = 660;
			gap = 20;
		}
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(gameController.getMapMainPanel(), javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
				.addComponent(gameController.getMapSubPanelPlayer(), javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(gameController.getMapMainPanel(), javax.swing.GroupLayout.PREFERRED_SIZE, mapHeight,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(gap)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(gameController.getMapSubPanelPlayer(), javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)));
		pack();
	}
	
	/**
	 * Exiting from the application
	 */
	public void exitWindow() {
		System.exit(0);
	}

	/**
	 * @return the statusLabel
	 */
	public JLabel getStatusLabel() {
		return statusLabel;
	}

	/**
	 * @param statusLabel the statusLabel to set
	 */
	public void setStatusLabel(JLabel statusLabel) {
		this.statusLabel = statusLabel;
	}

	/**
	 * @return the endButton
	 */
	public JButton getEndButton() {
		return endButton;
	}

	/**
	 * @param endButton the endButton to set
	 */
	public void setEndButton(JButton endButton) {
		this.endButton = endButton;
	}

	/**
	 * @return the fortifyButton
	 */
	public JButton getFortifyButton() {
		return fortifyButton;
	}

	/**
	 * @param fortifyButton the fortifyButton to set
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
	 * @param userEntered the userEntered to set
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
	 * @param attackButton the attackButton to set
	 */
	public void setAttackButton(JButton attackButton) {
		this.attackButton = attackButton;
	}

}
