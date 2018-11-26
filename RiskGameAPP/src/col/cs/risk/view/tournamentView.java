package col.cs.risk.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class tournamentView extends JFrame {
	
	private JPanel Panel;
	
	
	
	
	public tournamentView() {
		initComponents();
		setLocationRelativeTo(null);
	}
	
	private void initComponents() {
		
		Panel = new javax.swing.JPanel();
		Panel.setForeground(Color.BLACK);
		String[] behaviour = { "Cheater", "Random", "Benevolent", "Aggressive" };
		JComboBox behaviourPlayer1 = new JComboBox(behaviour);
		JComboBox behaviourPlayer2 = new JComboBox(behaviour);
		JComboBox behaviourPlayer3 = new JComboBox(behaviour);
		JComboBox behaviourPlayer4 = new JComboBox(behaviour);
		Integer[] numberOfMaps = { 1, 2, 3, 4, 5 };
		JComboBox numberOfMapsCombo = new JComboBox(numberOfMaps);
		Integer[] numberOfStrategies = { 2, 3, 4 };
		JComboBox playerNumCombobox = new JComboBox(numberOfStrategies);
		Integer[] numberOfGames = { 1, 2, 3, 4, 5 };
		JComboBox turnNumCombobox = new JComboBox();
		for (int i = 10; i <= 40; i++) {
			turnNumCombobox.addItem(new Integer(i));
		}
		
		Panel.setBackground(new java.awt.Color(1, 1, 1));
		Panel.setName("jPanel1");
		
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				
			}

		});
		
		JLabel descriptionLabel = new JLabel("Choose Proper Values To Proceed ");
		descriptionLabel.setForeground(Color.WHITE);

		JLabel mapLabel = new JLabel("Map");
		mapLabel.setForeground(Color.WHITE);

		JLabel strategiesLabel = new JLabel("Player Strategies");
		strategiesLabel.setForeground(Color.WHITE);

		JLabel gameLabel = new JLabel("Game");
		gameLabel.setForeground(Color.WHITE);

		JLabel numberOfTurnsLabel = new JLabel("Number of Allowed turns");
		numberOfTurnsLabel.setForeground(Color.WHITE);

		JLabel playerOne = new JLabel("Player 1");
		playerOne.setForeground(Color.WHITE);

		JLabel playerTwo = new JLabel("Player 2");
		playerTwo.setForeground(Color.WHITE);

		JLabel playerThree = new JLabel("Player 3");
		playerThree.setForeground(Color.WHITE);

		JLabel playerFour = new JLabel("Player 4");
		playerFour.setForeground(Color.WHITE);

}
}