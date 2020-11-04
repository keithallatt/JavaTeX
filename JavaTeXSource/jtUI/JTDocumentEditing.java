package jtUI;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import javatex.JTDocument;

public class JTDocumentEditing extends JPanel {
	JTUserInterface parentUI;

	public JTDocumentEditing(JTUserInterface parent) {
		parentUI = parent;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		updatePanelAfterChange();
	}

	public void updatePanelAfterChange() {
		removeAll();

		setAlignmentX(Component.LEFT_ALIGNMENT);

		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black), "Edit Document Structure",
				TitledBorder.LEFT, TitledBorder.ABOVE_TOP));

		JTDocument doc = parentUI.returnDocument();

		add(doc.toGUI(parentUI));
	}
}
