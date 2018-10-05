package col.cs.risk.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import col.cs.risk.controller.StartGameController;

/**
 * 
 * @author Team Loads the home page
 *
 */
public class HomePageViewLoader {

	/**
	 * 
	 * @param startController Loads the Game home page
	 */
	public static void loadHomePage(StartGameController startController) {

		GroupLayout groupLayout = new GroupLayout(startController.getStartJPanel());
		startController.getStartJPanel().setLayout(groupLayout);

		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addComponent(startController.getGameImage())
								.addContainerGap(10, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup().addGap(100).addGroup(groupLayout
								.createParallelGroup(Alignment.CENTER)
								.addComponent(startController.getStartGameButton(), GroupLayout.DEFAULT_SIZE, 100, 500)
								.addGap(50)
								.addComponent(startController.getConstructMapButton(), GroupLayout.DEFAULT_SIZE, 100,
										500)
								.addGap(50)
								.addComponent(startController.getLoadExistingMapButton(), GroupLayout.DEFAULT_SIZE, 100,
										500)
								.addGap(50)
								.addComponent(startController.getExitButton(), GroupLayout.DEFAULT_SIZE, 100, 500)
								.addGap(50)).addContainerGap()))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(startController.getGameImage())
						.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
						.addComponent(startController.getStartGameButton())
						.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
						.addComponent(startController.getConstructMapButton())
						.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
						.addComponent(startController.getLoadExistingMapButton())
						.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
						.addComponent(startController.getExitButton()).addContainerGap()));

		startController.add(startController.getStartJPanel(), BorderLayout.CENTER);
		startController.pack();
	}

}