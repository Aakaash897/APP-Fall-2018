package col.cs.risk.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import col.cs.risk.controller.GameController;
import col.cs.risk.helper.Utility;
import col.cs.risk.model.GameModel;

/**
 * It handles the display of rolling dice screen.
 * 
 * @author Team25
 * 
 */
public class RolledDiceView extends JFrame {

	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 1L;

	/** Rolled Dice Screen */
	JPanel panel;

	/** label for attacking dice1 */
	JLabel attackDiceLabel1;

	/** label for attacking dice2 */
	JLabel attackDiceLabel2;

	/** label for attacking dice3 */
	JLabel attackDiceLabel3;

	/** label for defending dice1 */
	JLabel defendDiceLabel1;

	/** label for defending dice2 */
	JLabel defendDiceLabel2;

	/** Ok button */
	JButton okButton;

	/** Game Controller Instance */
	GameController gameController;

	/** Image icons for the both dices */
	ImageIcon attackingImageIcon1 = new ImageIcon(Utility.getDicePath("dice_red_1.png"));
	ImageIcon attackingImageIcon2 = new ImageIcon(Utility.getDicePath("dice_red_2.png"));
	ImageIcon attackingImageIcon3 = new ImageIcon(Utility.getDicePath("dice_red_3.png"));
	ImageIcon attackingImageIcon4 = new ImageIcon(Utility.getDicePath("dice_red_4.png"));
	ImageIcon attackingImageIcon5 = new ImageIcon(Utility.getDicePath("dice_red_5.png"));
	ImageIcon attackingImageIcon6 = new ImageIcon(Utility.getDicePath("dice_red_6.png"));
	ImageIcon defendingImageIcon1 = new ImageIcon(Utility.getDicePath("dice_white_1.png"));
	ImageIcon defendingImageIcon2 = new ImageIcon(Utility.getDicePath("dice_white_2.png"));
	ImageIcon defendingImageIcon3 = new ImageIcon(Utility.getDicePath("dice_white_3.png"));
	ImageIcon defendingImageIcon4 = new ImageIcon(Utility.getDicePath("dice_white_4.png"));
	ImageIcon defendingImageIcon5 = new ImageIcon(Utility.getDicePath("dice_white_5.png"));
	ImageIcon defendingImageIcon6 = new ImageIcon(Utility.getDicePath("dice_white_6.png"));

	/** Constructor */
	public RolledDiceView() {
		setTitle("Rolled Dice");
	}

	/**
	 * Constructor to initialize components
	 * 
	 * @param gameController
	 */
	public RolledDiceView(GameController gameController) {
		this();
		this.gameController = gameController;
		initComponents();
	}

	/**
	 * Initialize components relating to the RollDiceVIew which make the view
	 *
	 */
	private void initComponents() {

		getContentPane().setBackground(new Color(238, 232, 170));
		getContentPane().setLayout(null);
		setResizable(false);
		setSize(450, 300);

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				exitForm();
			}
		});

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getWidth() / 2, dim.height / 2 - this.getHeight() / 2);

		panel = new JPanel();
		panel.setBounds(0, 0, 444, 271);
		getContentPane().add(panel);
		panel.setLayout(null);

		attackDiceLabel1 = new JLabel();
		attackDiceLabel1.setBounds(50, 50, 50, 50);
		panel.add(attackDiceLabel1);
		attackDiceLabel1.setVisible(false);

		attackDiceLabel2 = new JLabel();
		attackDiceLabel2.setBounds(150, 50, 50, 50);
		panel.add(attackDiceLabel2);
		attackDiceLabel2.setVisible(false);

		attackDiceLabel3 = new JLabel();
		attackDiceLabel3.setBounds(250, 50, 50, 50);
		panel.add(attackDiceLabel3);
		attackDiceLabel3.setVisible(false);

		defendDiceLabel1 = new JLabel();
		defendDiceLabel1.setBounds(50, 150, 50, 50);
		panel.add(defendDiceLabel1);
		defendDiceLabel1.setVisible(false);

		defendDiceLabel2 = new JLabel();
		defendDiceLabel2.setBounds(150, 150, 50, 50);
		panel.add(defendDiceLabel2);
		defendDiceLabel2.setVisible(false);

		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				exitForm();
				gameController.updateDiceAction();
			}
		});
		okButton.setBounds(200, 230, 50, 30);
		panel.add(okButton);
		okButton.setVisible(false);

		getContentPane().add(panel);
	}

	/**
	 * resets all dice labels and shows the ok button
	 *
	 */
	private void reset() {
		attackDiceLabel1.setVisible(false);
		attackDiceLabel2.setVisible(false);
		attackDiceLabel3.setVisible(false);
		defendDiceLabel1.setVisible(false);
		defendDiceLabel2.setVisible(false);
		okButton.setVisible(true);
	}

	/**
	 * gets the dice list for the attacking and defending player, does the action
	 * according to the dice rolled
	 *
	 */
	public void showRolledDiceList(GameModel gameModel) {
		reset();
		setVisible(true);
		HashMap<Integer, Integer> diceList = gameController.getGameModel().getCurrentPlayer().getAttackingDiceList();
		Set<Integer> keys = diceList.keySet();
		for (Integer diceNo : keys) {
			switch (diceNo) {
			case 1:
				attackDiceLabel1.setIcon(getSelectedAttackDiceImage(diceList.get(diceNo)));
				attackDiceLabel1.setVisible(true);
				break;
			case 2:
				attackDiceLabel2.setIcon(getSelectedAttackDiceImage(diceList.get(diceNo)));
				attackDiceLabel2.setVisible(true);
				break;
			case 3:
				attackDiceLabel3.setIcon(getSelectedAttackDiceImage(diceList.get(diceNo)));
				attackDiceLabel3.setVisible(true);
				break;
			default:
				break;
			}
		}

		diceList = gameController.getGameModel().getCurrentPlayer().getDefendingDiceList();
		keys = diceList.keySet();
		for (Integer diceNo : keys) {
			switch (diceNo) {
			case 1:
				defendDiceLabel1.setIcon(getSelectedDefendDiceImage(diceList.get(diceNo)));
				defendDiceLabel1.setVisible(true);
				break;
			case 2:
				defendDiceLabel2.setIcon(getSelectedDefendDiceImage(diceList.get(diceNo)));
				defendDiceLabel2.setVisible(true);
				break;
			default:
				break;
			}
		}
		repaint();
		if (gameController.getGameModel().getCurrentPlayer().isAutomatic()) {
			hideRolledDiceList();
		}
	}

	/**
	 * For the automatic run , it invokes methods in game controller to do the
	 * automatic actions according to the mode
	 * 
	 *
	 */
	private void hideRolledDiceList() {
		TimerTask task = new TimerTask() {
			public void run() {
				exitForm();
				cancel();
				gameController.updateDiceAction();
			}
		};
		Timer timer = new Timer("Timer");

		long delay = 1000L;
		timer.schedule(task, delay);
	}

	/**
	 * Sets the dice image according to the rolled dice number for the attacking
	 * player
	 *
	 */
	private ImageIcon getSelectedAttackDiceImage(int num) {
		ImageIcon imageIcon = null;
		switch (num) {
		case 1:
			imageIcon = attackingImageIcon1;
			break;
		case 2:
			imageIcon = attackingImageIcon2;
			break;
		case 3:
			imageIcon = attackingImageIcon3;
			break;
		case 4:
			imageIcon = attackingImageIcon4;
			break;
		case 5:
			imageIcon = attackingImageIcon5;
			break;
		case 6:
			imageIcon = attackingImageIcon6;
			break;
		default:
			break;
		}
		return imageIcon;
	}

	/**
	 * Sets the dice image according to the rolled dice number for the defending
	 * player
	 *
	 */
	private ImageIcon getSelectedDefendDiceImage(int num) {
		ImageIcon imageIcon = null;
		switch (num) {
		case 1:
			imageIcon = defendingImageIcon1;
			break;
		case 2:
			imageIcon = defendingImageIcon2;
			break;
		case 3:
			imageIcon = defendingImageIcon3;
			break;
		case 4:
			imageIcon = defendingImageIcon4;
			break;
		case 5:
			imageIcon = defendingImageIcon5;
			break;
		case 6:
			imageIcon = defendingImageIcon6;
			break;
		default:
			break;
		}
		return imageIcon;
	}

	/**
	 * clear and reset all the labels to null
	 * 
	 */
	public void clear() {
		setVisible(false);
		panel = null;
		attackDiceLabel1 = null;
		attackDiceLabel2 = null;
		attackDiceLabel3 = null;
		defendDiceLabel1 = null;
		defendDiceLabel2 = null;
		okButton = null;
	}

	/**
	 * Exit from Dice screen.
	 *
	 * @param evt
	 *            the event
	 * 
	 */
	public void exitForm() {
		setVisible(false);
	}

}
