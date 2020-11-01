package jtUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javatex.JTDocument;
import javatex.JTProblemFrame;
import javatex.presets.MultiplicationExample;

/**
 * Graphical User Interface to interact with the JavaTeX framework.
 * 
 * @author Keith Allatt
 * @version 2020-10-29
 *
 */
public class JTUserInterface extends JPanel {
	public static void main(String[] args) {
		JFrame frame = new JFrame("JavaTeX");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTUserInterface jtui = new JTUserInterface();

		frame.getContentPane().add(jtui);

		frame.pack();
		frame.setVisible(true);

		frame.setMinimumSize(new Dimension(800, 620));
		frame.setMaximumSize(new Dimension(800, 620));

	}

	private JTProblemFrame currentProblemFrame;

	private JPanel problemFrameSelect, problemFrameFieldInput, editWindow, buttonWindow;

	public JTUserInterface() {
		// create a new UI feel

		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		UIManager.put("TabbedPane.selected", new Color(25, 100, 25));

		JTabbedPane container = new JTabbedPane();

		container.setBackground(new Color(83, 83, 83));
		container.setForeground(new Color(255, 255, 255));

		// temporary
		currentProblemFrame = new MultiplicationExample();

		problemFrameSelect = new JPanel();
		problemFrameFieldInput = new JPanel();
		editWindow = new JPanel();
		buttonWindow = new JTGenerateFiles(this);

		setMinimumSize(new Dimension(500, 500));
		setPreferredSize(new Dimension(800, 600));

		container.setMinimumSize(new Dimension(500, 500));
		container.setPreferredSize(new Dimension(800, 600));

		problemFrameFieldInput.add(currentProblemFrame.toGUI());

		container.addTab("Preset Selection", null, problemFrameSelect,
				"Select a preset document");

		container.addTab("Preset Configuration", null, problemFrameFieldInput,
				"Set field variables");

		container.addTab("Edit Document", null, editWindow,
				"Edit problem frames in the document.");

		container.addTab("Generate Files", null, buttonWindow, "Write LaTeX files");

		add(container);
	}

	/**
	 * Currently only bundles the current JTProblemFrame as a document.
	 * 
	 * @return
	 */
	public JTDocument returnDocument() {
		JTDocument doc = new JTDocument();
		doc.addSnippet(currentProblemFrame);
		return doc;
	}
}
