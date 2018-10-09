package col.cs.risk.view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import col.cs.risk.controller.StartGameController;
import col.cs.risk.helper.Utility;

public class LoadExistingMapView {

	public LoadExistingMapView(StartGameController startGameController) {
		// TODO Auto-generated constructor stub
		startGameController.setLoadingExistingMapView(this);

	}

	public void openFileChooser() {
		File mapInputFile = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		FileNameExtensionFilter mapFileFilter = new FileNameExtensionFilter("Map Files", "map");
		fileChooser.setFileFilter(mapFileFilter);
		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			mapInputFile = fileChooser.getSelectedFile();
			Utility.saveMapFilePath(mapInputFile.getAbsolutePath());
			Utility.saveMapString();
			
			System.out.println("Map String" + Utility.baseMapString);

		} else if (result == JFileChooser.CANCEL_OPTION) {
			System.out.println("No File Choosen");
		}
		// TODO Auto-generated method stub

	}

	public void showModificationView() {
		// TODO Auto-generated method stub
		openFileChooser();
		
	}

}
