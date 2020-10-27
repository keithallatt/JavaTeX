package jtUnitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import javatex.LaTeXDocument;
import javatex.LaTeXSnippet;

@SuppressWarnings("static-method")
class TestLaTeXDocument {
	@Test
	void testLaTeXPackageSnippetType() {
		// get the snippet type from the latex package snippet

		// doesn't need to be a valid LaTeX package, just enough to extract information.
		LaTeXDocument docObj = new LaTeXDocument();

		LaTeXSnippet.SnippetType snippetType = docObj.snippetType;

		LaTeXSnippet.SnippetType expected = LaTeXSnippet.SnippetType.DOC;

		assertEquals(expected, snippetType);
	}
}
