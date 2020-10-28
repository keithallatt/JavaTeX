package javatex.envrn;

import java.util.ArrayList;

import javatex.LaTeXPackage;
import javatex.LaTeXSnippet;

/**
 * Represents the Align environment in
 * 
 * @author Keith Allatt
 * @version 2020-10-28
 *
 */
public class Align extends LaTeXSnippet {
	AlignAt alignment;
	String alignmentToken = "=";
	private boolean eqno = true;

	ArrayList<String> equations;

	public Align() {
		// this houses math environments but is not itself one.
		// I.e. this object does not need to be in a math mode environment.
		super();
		alignment = AlignAt.NOWHERE;
		equations = new ArrayList<String>();
	}

	public boolean addEquationLine(String eqn) {
		return equations.add(eqn);
	}

	public boolean removeEquationLine(String eqn) {
		return equations.remove(eqn);
	}

	public void clearEquationLine() {
		equations = new ArrayList<String>();
	}

	@Override
	public String convert() {
		// header -> \\begin{align}
		String repr = "";
		if (hasEquationNumbering()) repr = "\\begin{align}";
		else repr = "\\begin{align*}";

		boolean firstLine = true;
		for (String eqnLine : equations) {
			if (firstLine) {
				firstLine = false;
			} else {
				repr += " \\\\";
			}
			repr += "\n" + addAlignmentCharacter(eqnLine);
		}

		
		
		if (hasEquationNumbering()) repr += "\n\\end{align}";
		else repr += "\n\\end{align*}";

		return repr;
	}

	private String addAlignmentCharacter(String eqnLine) {
		if (alignment == AlignAt.NOWHERE) return eqnLine;

		// split along '='
		String[] pieces = eqnLine.split(alignmentToken);

		String repr = pieces[0]; // always will be one

		for (int i = 1; i < pieces.length; i++) {
			if (alignment == AlignAt.FIRST_EQUALS && i == 1)
				repr += "&="+pieces[i];
			else if (alignment == AlignAt.LAST_EQUALS && i == pieces.length - 1)
				repr += "&="+pieces[i];
			else if (alignment == AlignAt.ALL_EQUALS)
				repr += "&="+pieces[i];
			else
				repr += "="+pieces[i];
		}
		return repr;
	}

	@Override
	public LaTeXPackage[] getDependencies() {
		return new LaTeXPackage[] { new LaTeXPackage("amsmath") };
	}

	public enum AlignAt {
		NOWHERE, FIRST_EQUALS, LAST_EQUALS, ALL_EQUALS;
	}

	public void setAlignment(AlignAt alignment) {
		this.alignment = alignment;
	}

	/**
	 * @return The eqno.
	 */
	public boolean hasEquationNumbering() {
		return eqno;
	}

	/**
	 * @param eqno The eqno to set.
	 */
	public void setEquationNumbering(boolean eqno) {
		this.eqno = eqno;
	}
}
