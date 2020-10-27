package jtUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javatex.LaTeXPackage;
import javatex.LaTeXSnippet;

/**
 * Test LaTeXPackage class.
 * 
 * SuppressWarnings for static methods. Static methods do not run as JUnit5 Test
 * Cases. DO NOT CHANGE THIS.
 * 
 * @author Keith Allatt
 * @version 2020-10-25
 *
 */
@SuppressWarnings("static-method")
class TestLaTeXPackage {
	@Test
	void testBabelPackageConvert() {
		// testing the package input:
		// \\ usepackage [english]{babel}
		String packageName = "babel";
		String[] options = new String[] { "english" };

		String expected = "\\usepackage[english]{babel}";

		LaTeXPackage packageObj = new LaTeXPackage(packageName, options);

		assertEquals(expected, packageObj.convert());
	}

	@Test
	void testCSQuotesPackageConvert() {
		// testing the package input:
		// \\ usepackage [english]{babel}
		String packageName = "csquotes";
		String[] options = new String[] { "autostyle", "english=american" };

		String expected = "\\usepackage[autostyle,english=american]{csquotes}";

		LaTeXPackage packageObj = new LaTeXPackage(packageName, options);

		assertEquals(expected, packageObj.convert());
	}

	@Test
	void testGeometryPackageConvert() {
		// testing the package input:
		// \\ usepackage [english]{babel}
		String packageName = "geometry";
		String[] options = new String[] { "margin=0.5in" };

		String expected = "\\usepackage[margin=0.5in]{geometry}";

		LaTeXPackage packageObj = new LaTeXPackage(packageName, options);

		assertEquals(expected, packageObj.convert());
	}

	@Test
	void testLaTeXPackageSnippetType() {
		// get the snippet type from the latex package snippet

		// doesn't need to be a valid LaTeX package, just enough to extract information.
		LaTeXPackage packageObj = new LaTeXPackage("Sample");

		LaTeXSnippet.SnippetType snippetType = packageObj.snippetType;

		LaTeXSnippet.SnippetType expected = LaTeXSnippet.SnippetType.USE;

		assertEquals(expected, snippetType);
	}
}
