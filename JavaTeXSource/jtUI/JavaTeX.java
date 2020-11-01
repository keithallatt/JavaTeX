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
		JFrame frame = new JFrame("JavaTeX");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTUserInterface jtui = new JTUserInterface();
		frame.getContentPane().add(jtui);
		frame.pack();
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(800, 620));
		frame.setMaximumSize(new Dimension(800, 620));
	}
}
