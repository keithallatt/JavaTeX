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

	/**
	 * Create a generic ProblemFrame.
	 */
	public JTProblemFrame() {
		super();
		fields = new ArrayList<JTField<?>>();
	}

	/**
	 * Create a ProblemFrame with a particular snippet type. 
	 * 
	 * @param type: The snippet type to use.
	 */
	public JTProblemFrame(SnippetType type) {
		super(type);
		fields = new ArrayList<JTField<?>>();
	}

	/**
	 * Create a container for all fields required according to their data types.
	 * Each individual component of this container is itself a container, containing
	 * a particular field's input fields with appropriate labels.
	 * 
	 * @return the container of JTField input containers.
	 */
	public abstract JPanel toGUI();
}
