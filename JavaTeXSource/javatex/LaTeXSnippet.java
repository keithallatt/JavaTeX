package javatex;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a snippet of LaTeX code. When a list of LaTeXSnippets are combined
 * and saved as a .tex file, that resulting file should always be a valid
 * snippet. By that logic, any individual LaTeXSnippet should also be valid when
 * enclosed in the correct headers and footers (or in a particular environment).
 * 
 * @author Keith Allatt
 * @version 2020-10-25
 *
 */
public abstract class LaTeXSnippet implements TeX, Serializable {
	// useful reference.
	public final LaTeXSnippet PAGE_BREAK = new LaTeXSnippet() {
		@Override
		public String convert() {
			return "\\newpage";
		}
	};

	// What type is this snippet? Dictates what can be put where.
	public final SnippetType snippetType;

	protected ArrayList<LaTeXSnippet> subSnippets;

	/**
	 * Create a snippet of a particular type as defined in LaTeXSnippet.SnippetType.
	 * 
	 * @param snippetType:
	 *            The type of LaTeX snippet this object is.
	 */
	public LaTeXSnippet(SnippetType snippetType) {
		this.snippetType = snippetType;
		this.subSnippets = new ArrayList<LaTeXSnippet>();
	}

	/**
	 * Create a general snippet.
	 */
	public LaTeXSnippet() {
		this(SnippetType.GEN);
	}

	/**
	 * Expected to be overridden for classes requiring specific type sub-snippets or
	 * even specific object types. This implementation only checks for validity, no
	 * type checking occurs.
	 * 
	 * @param snip:
	 *            The LaTeXSnippet to be added to this one (as a sub piece.
	 * @return True if snip is a valid LaTeXSnippet. False otherwise.
	 */
	public boolean addSnippet(LaTeXSnippet snip) {
		if (snip.validate()) return subSnippets.add(snip);
		return false;
	}

	/**
	 * Remove a snippet from this container.
	 * 
	 * @param snip:
	 *            The snippet to be removed.
	 * @return true if this snippet contained snip.
	 */
	public boolean removeSnippet(LaTeXSnippet snip) {
		return subSnippets.remove(snip);
	}

	public void clearSnippets() {
		subSnippets = new ArrayList<LaTeXSnippet>();
	}

	/**
	 * What kind of snippet is a particular LaTeXSnippet?
	 * 
	 * USE: \\usepackage type snippet
	 * 
	 * ENV: a defined environment.
	 * 
	 * GEN: a generic use case
	 * 
	 * DOC: a document container.
	 * 
	 * MAT: uses math environment features.
	 * 
	 * 
	 * @author Keith Allatt
	 * @version 2020-10-25
	 *
	 */

	public enum SnippetType {
		USE, ENV, GEN, DOC, MAT;
	}

	//// Setters and Getters

	public ArrayList<LaTeXSnippet> getSubSnippets() {
		return subSnippets;
	}

	@Override
	public LaTeXPackage[] getDependencies() {
		ArrayList<LaTeXPackage> depends = new ArrayList<LaTeXPackage>();

		for (LaTeXSnippet snip : subSnippets)
			for (LaTeXPackage ltp : snip.getDependencies())
				depends.add(ltp);

		return depends.toArray(new LaTeXPackage[] {});
	}
}
