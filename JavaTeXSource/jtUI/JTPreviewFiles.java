package jtUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import javatex.JTDocument;

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

		if (numBytesRepr.matches("\\d+\\.0$"))
			numBytesRepr = numBytesRepr.substring(0,numBytesRepr.indexOf("."));

		// check to see

		numBytesRepr += new String[] { " bytes", " KB", " MB", " GB", " TB", " PB",
				" EB" }[stepsUp];

		// convert numBytes to the appropriate value.

		int numNewLines = (int) (docContents.chars().filter(ch -> ch == '\n').count()
				+ 1); // + 1 for last line.

		int numPages = (int) Math.ceil((float) numNewLines / (float) numLinesPerPage);

		String[] chunks = new String[numPages];

		int chunkCounter = 0;
		int lineCounter = 0;
		String currentChunk = "<html>";
		for (String docChunk : docContents.split("\n")) {
			currentChunk += docChunk;
			lineCounter++;
			lineCounter %= numLinesPerPage;

			if (lineCounter == 0) {
				currentChunk += "</html>";
				chunks[chunkCounter++] = currentChunk;
				currentChunk = "<html>";
			} else currentChunk += "<br/>";
		}

		if (lineCounter != 0) chunks[chunkCounter] = currentChunk;

		JTabbedPane tabs = new JTabbedPane();
		
		tabs.setBackground(new Color(83, 83, 83));
		tabs.setForeground(new Color(255, 255, 255));


		// each chunk is formatted as
		/*
		 * <html>Line 1<br/>Line 2<br/>Line 3</html>
		 * 
		 */
		
		int i = 1;
		for (String chunk : chunks) {
			JLabel label = new JLabel(chunk);

			JPanel labelContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
			labelContainer.add(label);

			tabs.addTab("Page " + i, null, labelContainer, "Select a preset document");
			i++;
		}

		tabs.setPreferredSize(size);

		add(tabs, BorderLayout.NORTH);

		add(new JLabel("File Size: " + numBytesRepr), BorderLayout.SOUTH);
	}
}
