package col.cs.risk.view;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import col.cs.risk.controller.LoadExistingMapController;

import col.cs.risk.helper.Utility;

public class LoadExistingMapView {

	/**
	 * Constructor with parameters
	 * 
	 * @param loadExistingGameController
	 */
	public LoadExistingMapView(LoadExistingMapController loadExistingGameController) {
		// TODO Auto-generated constructor stub
		loadExistingGameController.setLoadingExistingMapView(this);

	}

	/**
	 * Opens File Picker
	 */
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

	/**
	 * Open picked map for modification
	 * 
	 * @param mapString
	 * 
	 */
	public void showModificationView(String choosenMapString) {
		// TODO Auto-generated method stub
		JFrame dataFrame = new JFrame("Main Data Show");
		dataFrame.setLayout(new FlowLayout());
		dataFrame.setSize(1200, 600);
		dataFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		b1.addActionListener((new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String result = area.getText();
				/**
				 * try { BufferedWriter bufferedWriter = new BufferedWriter(new
				 * FileWriter(choosenMapString)); bufferedWriter.write(result);
				 * bufferedWriter.close(); } catch (IOException ex) { ex.printStackTrace(); }
				 * 
				 */

			}
		}));
		Button b2 = new Button("Cancel");
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

		});
		dataFrame.add(b1);
		dataFrame.add(b2);
		dataFrame.setVisible(true);

	}

}
