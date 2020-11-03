package jtUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javatex.JTDocument;
import javatex.presets.MultiplicationExample;

/**
 * Graphical User Interface to interact with the JavaTeX framework.
 * 
 * @author Keith Allatt
 * @version 2020-10-29
 *
 */
public class JTUserInterface extends JPanel {
	private JPanel problemFrameSelect, problemFrameFieldInput, editWindow, buttonWindow;

	// temporary, until document selector implemented
	private JTDocument doc;
	
	// underscores in case these shadow something in JPanel
	private Dimension minimumSize;
	private Dimension preferredSize;

	
	public JTUserInterface() {
		// create a new UI feel
		doc = new JTDocument();
		
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			throw new JTUIErrorDialog(this, "Error setting Look and Feel", e);
		}
		UIManager.put("TabbedPane.selected", new Color(25, 100, 25));

		minimumSize = new Dimension(500,500);
		preferredSize = new Dimension(800,600);
		
		setMinimumSize(minimumSize);
		setPreferredSize(preferredSize);

		
		JTabbedPane container = new JTabbedPane();

		container.setBackground(new Color(83, 83, 83));
		container.setForeground(new Color(255, 255, 255));

		
		problemFrameSelect = new JTPresetLoading(this);
		problemFrameFieldInput = new JTPresetEditing(this, new MultiplicationExample());
		editWindow = new JPanel();
		buttonWindow = new JTGenerateFiles(this);


		
		
		container.setMinimumSize(minimumSize);
		container.setPreferredSize(preferredSize);

		

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
		return doc;
	}

	public Dimension getPrefSize() {
		return preferredSize;
	}

	public Dimension getMinSize() {
		return minimumSize;
	}
}
