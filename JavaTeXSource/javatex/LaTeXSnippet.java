package javatex;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a snippet of LaTeX code. When a list of LaTeXSnippets are combined
 * and saved as a .tex file, that resulting file should always be a valid
 * snippet. By that logic, any individual LaTeXSnippet should also be valid when
 * enclosed in the correct headers and footers.
 * 
 * @author Keith Allatt
 * @version 2020-10-25
 *
 */
public abstract class LaTeXSnippet implements TeX, Serializable {
	// What type is this snippet? Dictates what can be put where.
	public final SnippetType snippetType;

	// raw code from imports. Can
	protected ArrayList<LaTeXSnippet> subSnippets;

	public LaTeXSnippet(SnippetType snippetType) {
		this.snippetType = snippetType;
		this.subSnippets = new ArrayList<LaTeXSnippet>();
	}

	public LaTeXSnippet() {
		this(SnippetType.GEN);
	}

	/**
	 * @return The subSnippets.
	 */
	public ArrayList<LaTeXSnippet> getSubSnippets() {
		return subSnippets;
	}
	
	public boolean addSnippet(LaTeXSnippet snip) {
		return subSnippets.add(snip);
	}
	
	public boolean removeSnippet(LaTeXSnippet snip) {
		return subSnippets.remove(snip);
	}

	/**
	 * What kind of snippet is this?
	 * 
	 * @author Keith Allatt
	 * @version 2020-10-25
	 *
	 */
	public enum SnippetType {
		/*
		 * USE: \\usepackage type snippet
		 * 
		 * ENV: a defined environment.
		 * 
		 * GEN: a generic use case
		 * 
		 * DOC: a document container.
		 * 
		 * MAT: uses math environment features. 
		 */
		USE, ENV, GEN, DOC;
	}
}
