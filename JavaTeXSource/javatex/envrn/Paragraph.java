package javatex.envrn;

import javatex.LaTeXSnippet;

public final class Paragraph extends LaTeXSnippet {

	String p;
	
	public Paragraph(String... text) {
		p = String.join("\n\n", text);
	}

	public Paragraph() {
		this("");
	}

	@Override
	public String convert() {
		return p;
	}

}
