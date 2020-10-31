package jtUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;

import javatex.JTDocument;

public class JTGenerateFiles extends JPanel {
	private final String whichPDFLaTeX = "/Library/TeX/texbin/pdflatex";

	private JTUserInterface parentUI;

	public JTGenerateFiles(JTUserInterface parent) {
		parentUI = parent;

		// works for MacOSX
		String documentsFolder = FileSystemView.getFileSystemView()
				.getDefaultDirectory().getPath()
				+ System.getProperty("file.separator") + "Documents";

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

		JLabel labelFilePath = new JLabel("TeX Filepath:");
		JLabel texFilePath = new JLabel("<path>");

		JButton chooseFile = new JButton("Choose...");

		JButton genTeX = new JButton("Generate TeX");
		JButton genPDF = new JButton("Generate PDF");

		chooseFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(
						new File(System.getProperty("user.home")));
				fileChooser.setSelectedFile(new File("output"));
				fileChooser.setAcceptAllFileFilterUsed(false);

				int result = fileChooser.showSaveDialog(this_ref);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();

					texFilePath
							.setText(changeExtension(selectedFile, "")
									.getAbsolutePath());
				}
			}
		});

		genTeX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateTeX(texFilePath);
			}
		});

		genPDF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String texFile = generateTeX(texFilePath);
				
				System.out.println(texFile);

				if (texFile == null)
					return;
				
				
				// need all aux files to be placed in working directory.
				// this has the added benefit of not creating a mess.
				// 
				
				String workingDir = System.getProperty("user.dir");
				
				String[] cmd = new String[] { whichPDFLaTeX,
						"-synctex=1", "-interaction=nonstopmode",
						"-output-directory='"+texFilePath.getText().trim()+"'",
						texFile };
				
				System.out.println(String.join(" ", cmd));
				
				try {
					// Runtime.getRuntime().exec(cmd, null, );
					
					System.out.println(getOutputFromProgram(cmd, new File(texFilePath.getText().trim())));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// make fileinput container
		JPanel containerFile = new JPanel(new FlowLayout());

		containerFile.setPreferredSize(new Dimension(
				this.getPreferredSize().width,
				Math.max(labelFilePath.getPreferredSize().height,
						Math.max(
								texFilePath.getPreferredSize().height,
								chooseFile
										.getPreferredSize().height))));

		containerFile.add(labelFilePath);

		containerFile.add(texFilePath);

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

	private String generateTeX(JLabel texFilePath) {
		JTDocument document = parentUI.returnDocument();
		String filename = null;
		try {
			String directoryName = texFilePath.getText();
			filename = directoryName
					+ System.getProperty("file.separator")
					+ "source.tex";

			File directoryFile = new File(directoryName);
			if (!directoryFile.exists()) directoryFile.mkdirs();

			FileOutputStream outputStream = new FileOutputStream(
					filename.trim());
			byte[] strToBytes = document.convert().getBytes();
			outputStream.write(strToBytes);

			outputStream.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return filename;
	}
	
	public static String getOutputFromProgram(String[] program, File file) throws IOException {
		file.setReadable(true);
		file.setWritable(true);
		file.setExecutable(true);
	    Process proc = Runtime.getRuntime().exec(program, null, file);
	    return Stream.of(proc.getErrorStream(), proc.getInputStream()).parallel().map((InputStream isForOutput) -> {
	        StringBuilder output = new StringBuilder();
	        try (BufferedReader br = new BufferedReader(new InputStreamReader(isForOutput))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                output.append(line);
	                output.append("\n");
	            }
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	        return output;
	    }).collect(Collectors.joining());
	}
}
