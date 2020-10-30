package javatex.envrn;

import javatex.LaTeXSnippet;

/**
 * Represents quite possibly the simplest LaTeXSnippet implementation. This
 * object packs a collection of stings into
 * 
 * @author Keith Allatt
 * @version 2020-10-29
 *
 */
public final class Paragraph extends LaTeXSnippet {
	private String text;

	/**
	 * Create a set of paragraphs linked as one section. Each String input gets
	 * separated by two newlines to separate paragraphs within this environment.
	 * 
	 * @param text: The text pieces to concatenate into a grouping of paragraphs.
	 */
	public Paragraph(String... text) {
		setText(text);
	}

	/**
	 * Create a blank environment.
	 */
	public Paragraph() {
		this("");
	}

	@Override
	public String convert() {
		return text;
	}

	//// Setters and Getters.

	public String getText() {
		return text;
	}

	public void setText(String... text) {
		this.text = String.join("\n\n", text);
	}

}
