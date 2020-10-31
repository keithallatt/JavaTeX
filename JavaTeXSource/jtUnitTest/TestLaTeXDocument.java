package jtUnitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import javatex.JTDocument;
import javatex.JTSnippet;
import javatex.envrn.Paragraph;

/**
 * Test LaTeXDocument class.
 * 
 * SuppressWarnings for static methods. Static methods do not run as JUnit5 Test
 * Cases. DO NOT MODIFY.
 * 
 * @author Keith Allatt
 * @version 2020-10-25
 *
 */
@SuppressWarnings("static-method")
class TestLaTeXDocument {
	@Test
	/**
	 * Test if the hello world document can correctly be generated.
	 */
	void testLaTeXDocumentHelloWorld() {
		JTDocument ld = new JTDocument();

		Paragraph p = new Paragraph("Hello World!");

		ld.addSnippet(p);

		String helloWorldDoc = ld.convert().trim();

		String helloWorldExp = "\\documentclass{report}\n\n\\begin{document}\nHello World!\n\\end{document}";

		assertEquals(helloWorldDoc, helloWorldExp);
	}

	@Test
	/**
	 * Test if snippet type has correctly been set as SnippetType.DOC.
	 */
	void testLaTeXDocumentSnippetType() {
		// get the snippet type from the latex package snippet

		// doesn't need to be a valid LaTeX package, just enough to extract information.
		JTDocument docObj = new JTDocument();

		JTSnippet.SnippetType snippetType = docObj.snippetType;

		JTSnippet.SnippetType expected = JTSnippet.SnippetType.DOC;

		assertEquals(expected, snippetType);
	}
}
