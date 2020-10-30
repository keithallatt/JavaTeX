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

		int height = 100;
		inner.add(Box.createRigidArea(new Dimension(
				getPreferredSize().width - 10, height)));

		int textFieldWidth = 25;

		JComponent[] components = new JComponent[] {
				new JLabel("TeX Filepath:"),
				new JTextField(System.getProperty("user.home")+System.getProperty("file.separator")+"output.tex",
						textFieldWidth),
				new JButton("Choose..."), new JButton("Generate TeX"),
				new JButton("Generate PDF"), new JLabel("") };

		((JButton) components[2])
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setCurrentDirectory(new File(
								System.getProperty("user.home")));
						fileChooser.setSelectedFile(new File("output.tex"));
						fileChooser.setAcceptAllFileFilterUsed(false);
						fileChooser.addChoosableFileFilter(
								new FileNameExtensionFilter(
										"TeX Documents", "tex"));
						int result = fileChooser
								.showSaveDialog(this_ref);
						if (result == JFileChooser.APPROVE_OPTION) {
							File selectedFile = fileChooser
									.getSelectedFile();
							selectedFile = changeExtension(selectedFile, ".tex");
							((JTextField) components[1]).setText(
									selectedFile.getAbsolutePath());
						}
					}
				});

		// make groupings of three

		for (int i = 0; i < components.length; i += 3) {
			JPanel container = new JPanel(new FlowLayout());

			container.setPreferredSize(new Dimension(
					this.getPreferredSize().width,
					Math.max(components[i].getPreferredSize().height,
							components[i + 1]
									.getPreferredSize().height)));

			container.add(components[i]);
			container.add(components[i + 1]);
			container.add(components[i + 2]);

			inner.add(container);

			height += 25 + container.getPreferredSize().height;
		}

		// keep compact.
		inner.setPreferredSize(
				new Dimension(getPreferredSize().width, height));

		add(inner);
	}

	private static File changeExtension(File f, String newExtension) {
		int i = f.getName().lastIndexOf('.');
		if (i == -1)
			i = f.getName().length();
		String name = f.getName().substring(0, i);
		return new File(f.getParent(), name + newExtension);
	}
}
