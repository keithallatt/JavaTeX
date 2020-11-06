package javatex.envrn;

import java.util.ArrayList;

import javatex.JTPackage;
import javatex.JTSnippet;

/**
 * Represents the Align environment in LaTeX. Converts a set of equations to a
 * formatted math environment. All math equations and math mode required
 * commands will work in this environment (as per LaTeX's compiler).
 * 
 * @author Keith Allatt
 * @version 2020-10-28
 *
 */
public class Align extends JTSnippet {
	private AlignAt alignment;

	private String alignmentToken = "=";

	private boolean eqno = true;

	private ArrayList<String> equations;

	public Align() {
		// this houses math environments but is not itself one.
		// I.e. this object does not need to be in a math mode environment.
		super();
		alignment = AlignAt.NOWHERE;
		equations = new ArrayList<String>();
	}

	/**
	 * Add an equation to the align environment.
	 * 
	 * @param eqn:
	 *            The equation to be displayed.
	 */
	public void addEquationLine(String eqn) {
		equations.add(eqn);
	}

	/**
	 * Remove a given equation from the align environment.
	 * 
	 * @param eqn:
	 *            The equation to be removed.
	 * @return true if 'equations' contained eqn.
	 */
	public boolean removeEquationLine(String eqn) {
		return equations.remove(eqn);
	}

	/**
	 * Remove all equations and create an empty environment.
	 */
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

	/**
	 * Take an equation 'eqnLine' and add the &amp; symbol to align each equation in
	 * this environment.
	 * 
	 * @param eqnLine:
	 *            The equation to be aligned.
	 * @return the formatted equation.
	 */
	private String addAlignmentCharacter(String eqnLine) {
		if (alignment == AlignAt.NOWHERE) return eqnLine;

		// split along '='
		String[] pieces = eqnLine.split(alignmentToken);

		String repr = pieces[0]; // always will be one

		for (int i = 1; i < pieces.length; i++) {
			if (alignment == AlignAt.FIRST_EQUALS && i == 1)
				repr += "&" + alignmentToken + pieces[i];
			else if (alignment == AlignAt.LAST_EQUALS
					&& i == pieces.length - 1)
				repr += "&" + alignmentToken + pieces[i];
			else if (alignment == AlignAt.ALL_EQUALS)
				repr += "&" + alignmentToken + pieces[i];
			else repr += "=" + pieces[i];
		}
		return repr;
	}

	@Override
	public JTPackage[] getDependencies() {
		return new JTPackage[] { new JTPackage("amsmath"), new JTPackage("amssymb") };
	}

	/**
	 * Alignment enum: options for alignment to various common align points.
	 * 
	 * Note that FIRST_EQUALS, LAST_EQUALS, and ALL_EQUALS accomplish the same task
	 * if the equation lines only have one equal sign.
	 * 
	 * @author Keith Allatt
	 * @version 2020-10-28
	 *
	 */
	public enum AlignAt {
		NOWHERE, FIRST_EQUALS, LAST_EQUALS, ALL_EQUALS;
	}

	/**
	 * Set the alignment to one of not aligned, aligned to the first equal sign,
	 * aligned to the last equal sign, or aligned to all equal signs.
	 * 
	 * @param alignment:
	 *            The alignment to use for this environment.
	 */
	public void setAlignment(AlignAt alignment) {
		this.alignment = alignment;
	}

	/**
	 * Returns whether or not equation numbering is enabled.
	 * 
	 * @return whether or not equation numbering is enabled.
	 */
	public boolean hasEquationNumbering() {
		return eqno;
	}

	/**
	 * Toggles equation numbering.
	 * 
	 * @param eqno:
	 *            Enable/Disable equation numbering.
	 */
	public void setEquationNumbering(boolean eqno) {
		this.eqno = eqno;
	}
}
