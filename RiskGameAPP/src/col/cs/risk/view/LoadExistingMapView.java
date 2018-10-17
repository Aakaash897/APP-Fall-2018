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

public class LoadExistingMapView {

	/**
	 * LoadExistingMapController instance
	 */
	LoadExistingMapController loadExistingGameController;

	/** To show error status */
	/**
	 * private JTextArea errorStatus;
	 */
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
	 * Opens File Picker
	 */
	public void openFileChooser() {
		try {
			Thread.sleep(120);
		} catch (InterruptedException e) {
			//do nothing
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
	 * Open picked map for modification
	 * 
	 * @param mapString
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
		JTextArea area = new JTextArea(17, 68);
		area.setLineWrap(true);
		area.setText(choosenMapString);
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpane.getViewport().add(area);
		editPanel.add(scrollpane);
		dataFrame.add(editPanel);
		Button b1 = new Button("Save");
		GameModel gameModel = new GameModel();
		b1.addActionListener((new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String result = area.getText();
				if (loadExistingGameController.startGameController.isEmptyDetails(result)) {
					Utility.showPopUp("File Should not be empty");
				} else if (!gameModel.isTagsCorrect(result)) {
					Utility.showPopUp("All Tags Should be there");
				} else if (!gameModel.checkContinentsAreValid(result)) {
					Utility.showPopUp("Add continents which are present in the continent section");
				} else if (!gameModel.isAllTerritoriesConnected(result)) {
					Utility.showPopUp("Atleast add one adjacent country ");
				} else
					try {
						if(Utility.isConnectedMap(result)) {
							dataFrame.setVisible(false);
							loadExistingGameController.actionPerformedOnSave(result);
						}
					} catch (MapException ex) {
						Utility.showPopUp(ex.getMessage());
					}
			}

		}));
		Button b2 = new Button("Cancel");
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dataFrame.setVisible(false);
				if (gameModel.isTagsCorrect(area.getText()) && gameModel.checkContinentsAreValid(area.getText())
						&& gameModel.isAllTerritoriesConnected(area.getText())) {
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
