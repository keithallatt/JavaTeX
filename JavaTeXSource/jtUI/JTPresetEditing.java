package jtUI;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import javatex.JTProblemFrame;

public class JTPresetEditing extends JPanel {
	JTUserInterface parentUI;
	private JTProblemFrame currentProblemFrame;

	public JTPresetEditing(JTUserInterface parent, JTProblemFrame defaultProblemFrame) {
		parentUI = parent;

		currentProblemFrame = defaultProblemFrame;

		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		JLabel label = new JLabel(currentProblemFrame.getDescription(),
				SwingConstants.CENTER);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);

		container.add(Box.createRigidArea(new Dimension(100, 15)));

		container.add(label);

		container.add(Box.createRigidArea(new Dimension(100, 15)));

		container.add(currentProblemFrame.toGUI());

		add(container);
	}
}
