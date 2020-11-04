package jtUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import javatex.JTProblemFrame;

public class JTPresetEditing extends JPanel {
	JTUserInterface parentUI;
	private JTProblemFrame currentProblemFrame;

	public JTPresetEditing(JTUserInterface parent, JTProblemFrame defaultProblemFrame) {
		parentUI = parent;

		currentProblemFrame = defaultProblemFrame;

		updatePanelAfterChange();
	}

	public void setCurrentProblemFrame(JTProblemFrame frame) {
		currentProblemFrame = frame;
		updatePanelAfterChange();
	}

	private void updatePanelAfterChange() {
		removeAll();
		
		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black),
				"Edit Preset", TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP));

		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		JLabel label;

		if (currentProblemFrame != null)
			label = new JLabel(currentProblemFrame.getDescription(),
					SwingConstants.CENTER);
		else label = new JLabel("No Preset Selected", SwingConstants.CENTER);

		label.setAlignmentX(Component.CENTER_ALIGNMENT);

		container.add(Box.createRigidArea(new Dimension(100, 15)));

		container.add(label);

		container.add(Box.createRigidArea(new Dimension(100, 15)));

		if (currentProblemFrame != null) container.add(currentProblemFrame.toGUI());


		JButton back = new JButton("Back");
		JButton next = new JButton("Add");

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// easy, just go back a page.
				
				// set to previous tab.
				parentUI.getTabContainer().setSelectedIndex(1);

			}
		});
		
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// when editing, if next, add to doc and set to null.
				parentUI.addToDocument(currentProblemFrame);
				
				currentProblemFrame = null;
				
				updatePanelAfterChange();
				parentUI.getEditWindow().updatePanelAfterChange();
				
				// set to next tab.
				parentUI.getTabContainer().setSelectedIndex(2);

			}
		});
		
		JPanel buttonContainer = new JPanel();
		
		buttonContainer.add(back);
		buttonContainer.add(next);

		container.add(buttonContainer);
		
		add(container);

		
		revalidate();
	}
}
