package col.cs.risk.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import col.cs.risk.model.phase.GamePhase;

/**
 * This class sets the phase view window size along with the addition of scroll
 * bar and other widgets. It also is an observer class that provides the changes
 * in a text box.
 * 
 * @author Team25
 *
 */

public class PhaseView implements Observer {

	/** Phase View panel */
	private JPanel phaseViewPanel;

	/** Phase Text area */
	private JTextArea phaseTextArea;

	/** Phase Frame */
	private JFrame phaseFrame;

	/** PhaseView Instance */
	private static PhaseView phaseInfoMonitor;

	/**
	 * 
	 * @return instance of PhaseView
	 */
	public static PhaseView getInstance() {
		if (phaseInfoMonitor == null) {
			phaseInfoMonitor = new PhaseView();
		}
		return phaseInfoMonitor;
	}

	/**
	 * function that helps to maintain the frame size of the phase view
	 * 
	 */
	public void showMonitor() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) (screenSize.height * 2 / 2.6);
		int width = (int) (screenSize.width * 2 / 4.9);
		phaseFrame = new JFrame();
		JScrollPane scrollPanel;

		phaseTextArea = new JTextArea("", 40, 40);
		phaseTextArea.setEditable(false);
		scrollPanel = new JScrollPane(phaseTextArea);
		scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		phaseViewPanel = new JPanel();
		phaseViewPanel.add(scrollPanel);

		phaseFrame.setTitle("Phase View");
		phaseFrame.setLocation(5, 5);
		phaseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		phaseFrame.setPreferredSize(new Dimension(width, height));
		phaseFrame.add(phaseViewPanel);
		phaseFrame.setResizable(true);
		phaseFrame.pack();
		phaseFrame.setVisible(true);
	}

	@Override
	public void update(Observable object, Object arg) {
		if (object != null) {
			phaseTextArea.setText(((GamePhase) object).getContent());
		}
		phaseFrame.repaint();
	}
	
	public void dispose() {
		phaseFrame.dispose();
		phaseInfoMonitor = null;
	}
}
