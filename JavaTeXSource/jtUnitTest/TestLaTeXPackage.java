package jtUnitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import javatex.JTPackage;
import javatex.JTSnippet;

/**
 * Test LaTeXPackage class.
 * 
 * SuppressWarnings for static methods. Static methods do not run as JUnit5 Test
 * Cases. DO NOT MODIFY.
 * 
 * @author Keith Allatt
 * @version 2020-10-25
 *
 */
@SuppressWarnings("static-method")
class TestLaTeXPackage {
	@Test
	/**
	 * Test importing the Babel package.
	 */
	void testBabelPackageConvert() {
		// testing the package input:
		// \\ usepackage [english]{babel}
		String packageName = "babel";
		String[] options = new String[] { "english" };

		String expected = "\\usepackage[english]{babel}";

		JTPackage packageObj = new JTPackage(packageName, options);

		assertEquals(expected, packageObj.convert());
	}

	@Test
	/**
	 * Test importing the CSQuotes package
	 */
	void testCSQuotesPackageConvert() {
		// testing the package input:
		// \\ usepackage [english]{babel}
		String packageName = "csquotes";
		String[] options = new String[] { "autostyle", "english=american" };

		String expected = "\\usepackage[autostyle,english=american]{csquotes}";

		JTPackage packageObj = new JTPackage(packageName, options);

		assertEquals(expected, packageObj.convert());
	}

	@Test
	void testGeometryPackageConvert() {
		// testing the package input:
		// \\ usepackage [english]{babel}
		String packageName = "geometry";
		String[] options = new String[] { "margin=0.5in" };

		String expected = "\\usepackage[margin=0.5in]{geometry}";

		JTPackage packageObj = new JTPackage(packageName, options);

		assertEquals(expected, packageObj.convert());
	}

	@Test
	/**
	 * Test if the snippet type has correctly been set as SnippetType.USE.
	 */
	void testLaTeXPackageSnippetType() {
		// get the snippet type from the latex package snippet

		// doesn't need to be a valid LaTeX package, just enough to extract information.
		JTPackage packageObj = new JTPackage("Sample");

		JTSnippet.SnippetType snippetType = packageObj.snippetType;

		JTSnippet.SnippetType expected = JTSnippet.SnippetType.USE;

		assertEquals(expected, snippetType);
	}
}
