package col.cs.risk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.IllegalComponentStateException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import col.cs.risk.controller.StartGameController;

public class StartGameType extends JFrame {
	
	private JButton singleMode;
	
	private JButton tournamentMode;
	
	private JPanel startJPanel;
	
	/** Game controller */
	private StartGameController startGameController;
		
	
	public StartGameType() {
		setTitle("Mode Choice");
	}
	
	public StartGameType(StartGameController gameController)
	{
		this();
		this.startGameController = gameController;
		
		initComponents();
	}
	
	public void initComponents() {
		
		singleMode = new JButton();
		tournamentMode = new JButton();
		startJPanel = new JPanel();
		startJPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		setBackground(new Color(1, 1, 1));
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				exitForm(event);
		}
	});
	startJPanel.setName("startJPanel");
	
	singleMode.setName("singlemode");
	singleMode.setText("Single Mode");
	singleMode.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent event) {
			startGameController.setPlayers();
		}
	});
	
	
	tournamentMode.setName("tournamentmode");
	tournamentMode.setText("Tournament Mode");
	tournamentMode.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent event) {
			
			System.out.println("Tournament Mode Selected");
			//startController.startGameButtonActionPerformed(event);
		}
	});
	
	loadModePage();

}
		
	
	private void loadModePage() {

		GroupLayout groupLayout = new GroupLayout(startJPanel);
		startJPanel.setLayout(groupLayout);

		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(100)
								.addGroup(groupLayout.createParallelGroup(Alignment.CENTER)
										.addComponent(singleMode, GroupLayout.DEFAULT_SIZE, 100, 500).addGap(50)
										.addComponent(tournamentMode, GroupLayout.DEFAULT_SIZE, 100, 500).addGap(50)
										.addGap(50))
								.addContainerGap()))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE).addComponent(singleMode)
				.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE).addComponent(tournamentMode)
				.addContainerGap()));

		add(startJPanel, BorderLayout.CENTER);
		pack();
	}
	
	@Override
	public void setBackground(Color bgColor) {
		synchronized (getTreeLock()) {
			if ((bgColor != null) && (bgColor.getAlpha() < 255) && !isUndecorated()) {
				throw new IllegalComponentStateException("The frame is decorated");
			}
			super.setBackground(bgColor);
		}
	}
	
	
	private void exitForm(java.awt.event.WindowEvent event) {
		System.exit(0);
	}
}
