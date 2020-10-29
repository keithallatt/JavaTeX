package javatex;

import java.util.ArrayList;

import javax.swing.JPanel;

import javatex.field.JTField;

/**
 * Represents a bare problem framework, that will allow a named set of inputs
 * such that changing the inputs generates a new LaTeX document snippet.
 * 
 * @author Keith Allatt
 * @version 2020-10-25
 *
 */
public abstract class JTProblemFrame extends LaTeXSnippet {
	/*
	 * Need a set of inputs, named, such that the convert function can take in the
	 * inputs, calculate the output (to the problem) and generate a LaTeXSnippet
	 * that displays all work done.
	 */
	protected ArrayList<JTField<?>> fields;
	
	public JTProblemFrame() {
		super();
		fields = new ArrayList<JTField<?>>();
	}
	
	public JTProblemFrame(SnippetType type) {
		super(type);
		fields = new ArrayList<JTField<?>>();
	}

	public abstract JPanel toGUI();

	
}
