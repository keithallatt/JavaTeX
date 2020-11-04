package jtUI;

import java.awt.Color;
import java.awt.Dimension;

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
	private JTPresetLoading problemFrameSelect;
	private JTPresetEditing problemFrameFieldInput;
	private JPanel editWindow;
	private JTGenerateFiles buttonWindow;

	// temporary, until document selector implemented
	private JTDocument doc;

	// underscores in case these shadow something in JPanel
	private Dimension minimumSize;
	private Dimension preferredSize;
	
	private JTabbedPane tabContainer;

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

		minimumSize = new Dimension(500, 500);
		preferredSize = new Dimension(800, 600);

		setMinimumSize(minimumSize);
		setPreferredSize(preferredSize);

		tabContainer = new JTabbedPane();

		tabContainer.setBackground(new Color(83, 83, 83));
		tabContainer.setForeground(new Color(255, 255, 255));

		problemFrameSelect = new JTPresetLoading(this);
		problemFrameFieldInput = new JTPresetEditing(this, null);
		editWindow = new JPanel();
		buttonWindow = new JTGenerateFiles(this);

		tabContainer.setMinimumSize(minimumSize);
		tabContainer.setPreferredSize(preferredSize);

		tabContainer.addTab("Preset Selection", null, problemFrameSelect,
				"Select a preset document");

		tabContainer.addTab("Preset Configuration", null, problemFrameFieldInput,
				"Set field variables");

		tabContainer.addTab("Edit Document", null, editWindow,
				"Edit problem frames in the document.");

		tabContainer.addTab("Generate Files", null, buttonWindow, "Write LaTeX files");

		add(tabContainer);
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

	public void setCurrentProblemFrame(JTProblemFrame o) {
		problemFrameFieldInput.setCurrentProblemFrame(o);
	}

	public JTabbedPane getTabContainer() {
		return tabContainer;
	}
}
