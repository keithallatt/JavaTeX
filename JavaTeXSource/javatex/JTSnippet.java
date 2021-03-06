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
public abstract class JTSnippet implements TeX, Serializable {
	// useful reference.
	public final static JTSnippet PAGE_BREAK = new JTSnippet() {
		@Override
		public String convert() {
			return "\\newpage";
		}
	};

	// What type is this snippet? Dictates what can be put where.
	public final SnippetType snippetType;

	protected ArrayList<JTSnippet> subSnippets;

	/**
	 * Create a snippet of a particular type as defined in LaTeXSnippet.SnippetType.
	 * 
	 * @param snippetType:
	 *            The type of LaTeX snippet this object is.
	 */
	public JTSnippet(SnippetType snippetType) {
		this.snippetType = snippetType;
		this.subSnippets = new ArrayList<JTSnippet>();
	}

	/**
	 * Create a general snippet.
	 */
	public JTSnippet() {
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
	public boolean addSnippet(JTSnippet snip) {
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
	public boolean removeSnippet(JTSnippet snip) {
		return subSnippets.remove(snip);
	}

	/**
	 * Remove all snippets. Resets the contents completely.
	 */
	public void clearSnippets() {
		subSnippets = new ArrayList<JTSnippet>();
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

	public ArrayList<JTSnippet> getSubSnippets() {
		return subSnippets;
	}

	@Override
	public JTPackage[] getDependencies() {
		ArrayList<JTPackage> depends = new ArrayList<JTPackage>();

		for (JTSnippet snip : subSnippets)
			for (JTPackage ltp : snip.getDependencies())
				depends.add(ltp);

		return depends.toArray(new JTPackage[] {});
	}
}
