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

public class PhaseView implements Observer {

	private JPanel phaseViewPanel;

	private JTextArea phaseTextArea;

	private JFrame phaseFrame;

	private static PhaseView phaseInfoMonitor;

	public static PhaseView getInstance() {
		if(phaseInfoMonitor == null) {
			phaseInfoMonitor = new PhaseView();
		}
		return phaseInfoMonitor;
	}

	public void showMonitor() {
		phaseTextArea = new JTextArea("", 40, 40);
		phaseTextArea.setEditable(false);

		JScrollPane scrollPanel;
		scrollPanel = new JScrollPane(phaseTextArea);
		scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		phaseViewPanel = new JPanel();
		phaseViewPanel.add(scrollPanel);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) (screenSize.height * 2 / 2.6);
		int width = (int) (screenSize.width * 2 / 4.9);
		phaseFrame = new JFrame();
		phaseFrame.setTitle("Phase Information");
		phaseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		phaseFrame.setPreferredSize(new Dimension(width, height));
		phaseFrame.add(phaseViewPanel);
		phaseFrame.setResizable(true);
		phaseFrame.pack();
		phaseFrame.setVisible(true);
	}

	@Override
	public void update(Observable object, Object arg) {
		if(object != null) {
			phaseTextArea.setText(((GamePhase)object).getContent());
		}
		phaseFrame.repaint();
	}
}
