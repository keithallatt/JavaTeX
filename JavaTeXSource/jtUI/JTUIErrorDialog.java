package jtUI;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class JTUIErrorDialog extends RuntimeException {
	URI uri;

	public JTUIErrorDialog(JComponent parent, String name, Throwable error) {
		super(name, error);

		final JPanel panel = new JPanel();

		JLabel message = new JLabel("<html>If this error dialog has appeared, <br/>"
				+ "please save this error report and submit it <br/> as an issue on Github</html>");

		JButton button = new JButton("<html><a>Issues Page</a></html>");

		try {
			uri = new URI("https://github.com/keithallatt/JavaTeX/issues/new");
		} catch (URISyntaxException e) {
			return;
		}

		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setOpaque(false);
		button.setBackground(Color.WHITE);
		button.addActionListener(new OpenUrlAction());
		button.setToolTipText(uri.toString());

		StringWriter sw = new StringWriter();
		error.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setText(name + "\n\n" + exceptionAsString);

		JScrollPane scrollPane = new JScrollPane(textArea);

		scrollPane.setPreferredSize(new Dimension(500, 500));
		panel.setPreferredSize(new Dimension(500, 500));

		panel.add(message);
		panel.add(button);

		panel.add(scrollPane);

		String[] options = new String[] { "Ok" };

		JOptionPane.showOptionDialog(parent, panel, name, JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.ERROR_MESSAGE, null, options, options[0]);

	}

	class OpenUrlAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (uri != null) if (Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(uri);
				} catch (IOException e1) {
				}
			} else {
			}
		}
	}
}
