package jtUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JTGenerateFiles extends JPanel {
	public JTGenerateFiles() {
		JTGenerateFiles this_ref = this;

		setPreferredSize(new Dimension(580, 700));
		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black),
				"Generate *.tex and *.pdf files", TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP));

		JPanel inner = new JPanel();

		inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));

		int height = 75;
		inner.add(Box.createRigidArea(new Dimension(
				getPreferredSize().width - 10, height)));

		int textFieldWidth = 25;

		JLabel labelFilePath = new JLabel("TeX Filepath:");
		JTextField inputFilePath = new JTextField(
				System.getProperty("user.home")
						+ System.getProperty("file.separator")
						+ "output.tex",
				textFieldWidth);

		JButton chooseFile = new JButton("Choose...");

		JButton genTeX = new JButton("Generate TeX");
		JButton genPDF = new JButton("Generate PDF");

		chooseFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(
						new File(System.getProperty("user.home")));
				fileChooser.setSelectedFile(new File("output.tex"));
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.addChoosableFileFilter(
						new FileNameExtensionFilter("TeX Documents",
								"tex"));
				int result = fileChooser.showSaveDialog(this_ref);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					selectedFile = changeExtension(selectedFile,
							".tex");
					inputFilePath
							.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		

		// make fileinput container
		JPanel containerFile = new JPanel(new FlowLayout());

		containerFile.setPreferredSize(new Dimension(
				this.getPreferredSize().width,
				Math.max(labelFilePath.getPreferredSize().height, Math
						.max(inputFilePath.getPreferredSize().height,
								chooseFile
										.getPreferredSize().height))));

		containerFile.add(labelFilePath);
		containerFile.add(inputFilePath);
		containerFile.add(chooseFile);

		inner.add(containerFile);
		height += 25 + containerFile.getPreferredSize().height;

		JPanel containerGenerate = new JPanel(new FlowLayout());

		containerGenerate.setPreferredSize(
				new Dimension(this.getPreferredSize().width,
						Math.max(genTeX.getPreferredSize().height,
								genPDF.getPreferredSize().height)));

		containerGenerate.add(genTeX);
		containerGenerate.add(genPDF);

		inner.add(containerGenerate);
		height += 25 + containerGenerate.getPreferredSize().height;

		// keep compact.
		inner.setPreferredSize(
				new Dimension(getPreferredSize().width, height));

		add(inner);
	}

	private static File changeExtension(File f, String newExtension) {
		int i = f.getName().lastIndexOf('.');
		if (i == -1) i = f.getName().length();
		String name = f.getName().substring(0, i);
		return new File(f.getParent(), name + newExtension);
	}
}
