package jtUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;

import javatex.JTProblemFrame;
import javatex.example.MultiplicationExample;

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
		
		frame.setMinimumSize(new Dimension(800,620));
		frame.setMaximumSize(new Dimension(800,620));
		
	}

	JTProblemFrame currentProblemFrame;

	JPanel problemFrameSelect, problemFrameFieldInput, editWindow,
			buttonWindow;

	public JTUserInterface() {
		JTabbedPane container = new JTabbedPane();
		
		// temporary
		currentProblemFrame = new MultiplicationExample();

		problemFrameSelect = new JPanel();
		problemFrameFieldInput = new JPanel();
		editWindow = new JPanel();
		buttonWindow = new JTGenerateFiles();
		
		setMinimumSize(new Dimension(500,500));
		setPreferredSize(new Dimension(800,600));
		
		container.setMinimumSize(new Dimension(500,500));
		container.setPreferredSize(new Dimension(800,600));
		
		problemFrameFieldInput.add(currentProblemFrame.toGUI());
		
		container.addTab("Preset Selection", null, problemFrameSelect,
                "Select a preset document");
		container.setMnemonicAt(0, KeyEvent.VK_1);
		container.addTab("Preset Configuration", null, problemFrameFieldInput,
                "Set field variables");
		container.setMnemonicAt(1, KeyEvent.VK_2);
		container.addTab("Edit", null, editWindow,
                "Edit problem frames in the document.");
		container.setMnemonicAt(2, KeyEvent.VK_3);
		container.addTab("Generate", null, buttonWindow,
                "Publish the ");
		container.setMnemonicAt(3, KeyEvent.VK_4);

		add(container);
	}
}
