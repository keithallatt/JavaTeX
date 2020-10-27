package jtUnitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import javatex.LaTeXDocument;
import javatex.LaTeXSnippet;
import javatex.Paragraph;

@SuppressWarnings("static-method")
class TestLaTeXDocument {
	@Test
	void testLaTeXDocumentSnippetType() {
		// get the snippet type from the latex package snippet

		// doesn't need to be a valid LaTeX package, just enough to extract information.
		LaTeXDocument docObj = new LaTeXDocument();

		LaTeXSnippet.SnippetType snippetType = docObj.snippetType;

		LaTeXSnippet.SnippetType expected = LaTeXSnippet.SnippetType.DOC;

		assertEquals(expected, snippetType);
	}

	@Test
	void testLaTeXDocumentHelloWorld() {
		LaTeXDocument ld = new LaTeXDocument();

		Paragraph p = new Paragraph("Hello World!");

		ld.addSnippet(p);

		String helloWorldDoc = ld.convert().trim();
		
		String helloWorldExp = "\\documentclass{report}\n\n\\begin{document}\nHello World!\n\\end{document}";

		assertEquals(helloWorldDoc, helloWorldExp);
	}
}
