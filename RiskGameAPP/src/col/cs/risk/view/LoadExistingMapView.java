package col.cs.risk.view;

import java.awt.Button;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import col.cs.risk.controller.LoadExistingMapController;
import col.cs.risk.helper.MapException;
import col.cs.risk.helper.Utility;
import col.cs.risk.model.GameModel;

/**
 * Shows the view of existing map that is chosen by the user. It opens the file
 * picker for the user to choose the existing map file and it responds to the
 * corresponding action for save or cancel button by the user.
 * 
 * @author Team25
 *
 */
public class LoadExistingMapView {

	/**
	 * LoadExistingMapController instance
	 */
	LoadExistingMapController loadExistingGameController;

	/**
	 * Constructor with parameters
	 * 
	 * @param loadExistingGameController
	 */
	public LoadExistingMapView(LoadExistingMapController loadExistingGameController) {
		loadExistingGameController.setLoadingExistingMapView(this);
		this.loadExistingGameController = loadExistingGameController;
	}

	/**
	 * Opens File Picker that allows user to choose the existing map file. it saves
	 * the path of user choosen map file in the helper class.
	 */
	public void openFileChooser() {
		try {
			Thread.sleep(120);
		} catch (InterruptedException e) {
			// do nothing
		}
		File mapInputFile = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(Utility.getResouceMapPath()));
		FileNameExtensionFilter mapFileFilter = new FileNameExtensionFilter("Map Files", "map");
		fileChooser.setFileFilter(mapFileFilter);
		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			mapInputFile = fileChooser.getSelectedFile();
			loadExistingGameController.setModelDetails(true, mapInputFile.getName());
			Utility.saveMapFilePath(mapInputFile.getAbsolutePath());
			Utility.saveMapString();

		} else if (result == JFileChooser.CANCEL_OPTION) {
			System.out.println("No File Choosen");
		}
	}

	/**
	 * It gets the saved map file path that was picked by the user and open the view
	 * for the modification of the map. in this user can do edit the map, save the
	 * edited map, or cancel the modification process.
	 * 
	 * @param choosenMapString
	 * 
	 */
	public void showModificationView(String choosenMapString) {
		JFrame dataFrame = new JFrame("Main Data Show");
		dataFrame.setLayout(new FlowLayout());
		dataFrame.setSize(1200, 600);
		dataFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				dataFrame.setVisible(false);
				loadExistingGameController.exitModificaitonView();
			}
		});
		JPanel editPanel = new JPanel();
		/* Text Area to Show the map text */
		JTextArea area = new JTextArea(17, 68);
		area.setLineWrap(true);
		area.setText(choosenMapString);
		
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpane.getViewport().add(area);
		editPanel.add(scrollpane);
		dataFrame.add(editPanel);
		/** Save Map button */
		Button b1 = new Button("Save");
		GameModel gameModel = new GameModel();
		b1.addActionListener((new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String result = area.getText();
				if (loadExistingGameController.startGameController.isEmptyDetails(result)) {
					Utility.showMessagePopUp("File Should not be empty");
				} else if (!gameModel.isTagsCorrect(result)) {
					Utility.showMessagePopUp("All Tags Should be there");
				} else if (!gameModel.isContinentInTerrirotiesValid(result)) {
					Utility.showMessagePopUp("Add continents which are present in the continent section");
				} else if (!gameModel.isAllTerritoriesHaveAdjacents(result)) {
					Utility.showMessagePopUp("Atleast add one adjacent country ");
				} else
					try {
						if (Utility.isConnectedMap(result)) {
							dataFrame.setVisible(false);
							loadExistingGameController.actionPerformedOnSave(result);
						}
					} catch (MapException ex) {
						Utility.showMessagePopUp(ex.getMessage());
					}
			}

		}));
		/** Cancel Map Modification button */
		Button b2 = new Button("Cancel");
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dataFrame.setVisible(false);
				if (gameModel.isTagsCorrect(area.getText()) && gameModel.isContinentInTerrirotiesValid(area.getText())
						&& gameModel.isAllTerritoriesHaveAdjacents(area.getText())) {
					loadExistingGameController.actionPerformedOnCancel(area.getText());
				} else {
					loadExistingGameController.loadMapConstructionView();
				}
			}

		});
		dataFrame.add(b1);
		dataFrame.add(b2);
		dataFrame.setVisible(true);
	}
}
