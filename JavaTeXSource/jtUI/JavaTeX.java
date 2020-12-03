package jtUI;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Main Class, creates the UI and all underlying code.
 * 
 * @author Keith Allatt
 * @version 2020-11-01
 *
 */
public class JavaTeX {
	public static void main(String[] args) {
		JFrame frame;
		JTUserInterface jtui = null;
		try {
			frame = new JFrame("JavaTeX");
			jtui = new JTUserInterface();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(jtui);
			frame.pack();
			frame.setVisible(true);
			frame.setMinimumSize(new Dimension(800, 620));
			frame.setMaximumSize(new Dimension(800, 620));			
		} catch (Exception e) {
			/*
			 * if any errors occur that aren't caught properly, they'll come here since
			 * all code except variable declaration happens inside.
			 */
			if (jtui == null)
				throw new JTUIErrorDialog(null, "UI not initialized Exception", e);
			throw new JTUIErrorDialog(jtui, "Uncaught Exception", e);
		}
	}
}
