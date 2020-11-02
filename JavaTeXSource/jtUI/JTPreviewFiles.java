package jtUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javatex.JTDocument;

/**
 * Create a preview option so users can view what code will be saved to disk. No
 * code changes are allowed at this point. If changes are desired, they can be
 * made after the code is saved to file.
 * 
 * @author Keith Allatt
 * @version 2020-11-01
 *
 */
public class JTPreviewFiles extends JPanel {
	public JTPreviewFiles(JTDocument doc, int numLinesPerPage, Dimension size) {
		setLayout(new BorderLayout());

		String docContents = doc.convert();

		// num bytes.
		float numBytes = docContents.getBytes().length;

		// B, KB, MB, GB, TB, PB, EB.
		int stepsUp = 0;

		for (int i = 0; i < 6; i++)
			if (numBytes >= 1024) {
				stepsUp++;
				numBytes /= 1024f;
			}
		numBytes = (100 * numBytes) / 100f;

		String numBytesRepr = "" + numBytes; // B for bytes

		// ensures that there is a period in the representation
		// this only happens when there is an exact or near exact groupings of
		// bytes into any of the higher units (like 1024 bytes is exactly 1 kb)
		if (numBytesRepr.matches("\\d+\\.0$"))
			numBytesRepr = numBytesRepr.substring(0, numBytesRepr.indexOf("."));

		// check to see

		numBytesRepr += new String[] { " bytes", " KB", " MB", " GB", " TB", " PB",
				" EB" }[stepsUp];

		// convert numBytes to the appropriate value.

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setText(docContents);

		JScrollPane scrollPane = new JScrollPane(textArea);

		scrollPane.setPreferredSize(size);
		add(scrollPane, BorderLayout.NORTH);

		add(new JLabel("File Size: " + numBytesRepr), BorderLayout.SOUTH);
	}
}
