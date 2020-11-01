package jtUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import javatex.JTDocument;

public class JTGenerateFiles extends JPanel {

	private JTUserInterface parentUI;

	private File fileSelected = null;

	public JTGenerateFiles(JTUserInterface parent) {
		parentUI = parent;

		// works for MacOSX
		String documentsFolder = FileSystemView.getFileSystemView().getDefaultDirectory()
				.getPath() + System.getProperty("file.separator") + "Documents";

		JTGenerateFiles this_ref = this;

		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black),
				"Generate *.tex files", TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP));

		JButton genTeX = new JButton("Generate TeX");

		JLabel selectedLabel = new JLabel("Select a file");

		genTeX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(documentsFolder));
				fileChooser.setSelectedFile(new File("source.tex"));
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.addChoosableFileFilter(
						new FileNameExtensionFilter("TeX Documents", "tex"));

				int result = fileChooser.showSaveDialog(this_ref);
				if (result == JFileChooser.APPROVE_OPTION) {
					fileSelected = fileChooser.getSelectedFile();

					fileSelected = changeExtension(fileSelected, ".tex");

					selectedLabel.setText(fileSelected.getAbsolutePath());
				}

				generateTeX(fileSelected);
			}
		});
		
		JButton previewTeX = new JButton("Preview Generated Code");

		previewTeX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTPreviewFiles filePreview = new JTPreviewFiles(parentUI.returnDocument(), 30, new Dimension(800,600));
				
				Object[] options = new Object[] {"Save", "Back to editor"};
				
				int result = JOptionPane.showOptionDialog(null,
						filePreview,
                        "JOptionPane Example : ",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				
				if (result == JFileChooser.APPROVE_OPTION) {
					genTeX.doClick();
				}
			}
		});

		// make fileinput container
		JPanel containerFile = new JPanel(new FlowLayout());

		containerFile.add(selectedLabel);
		containerFile.add(genTeX);
		containerFile.add(previewTeX);

		add(containerFile);

		revalidate();
	}

	private static File changeExtension(File f, String newExtension) {
		int i = f.getName().lastIndexOf('.');
		if (i == -1) i = f.getName().length();
		String name = f.getName().substring(0, i);
		return new File(f.getParent(), name + newExtension);
	}

	private void generateTeX(File texFile) {
		if (texFile == null) return; // don't run on null file.

		JTDocument document = parentUI.returnDocument();

		try {
			FileOutputStream outputStream = new FileOutputStream(texFile);
			byte[] strToBytes = document.convert().getBytes();
			outputStream.write(strToBytes);

			outputStream.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
