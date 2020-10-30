package javatex;

import java.util.Arrays;

/**
 * Represents an imported package. This can take in a set of options as strings,
 * and use them to customize imports for a LaTeXDocument.
 * 
 * @author Keith Allatt
 * @version 2020-10-25
 *
 */
public class LaTeXPackage extends LaTeXSnippet {
	private String packageName;
	private String[] options;

	/**
	 * Create an import statement without specified options.
	 * 
	 * @param packageName:
	 *            The package name.
	 */
	public LaTeXPackage(String packageName) {
		this(packageName, new String[] {});
	}

	/**
	 * Create an import statement with specified options.
	 * 
	 * @param kw:
	 *            The package name followed by all specified options.
	 */
	public LaTeXPackage(String... kw) {
		this(kw[0], Arrays.copyOfRange(kw, 1, kw.length));
	}

	/**
	 * Create an import statement with a list of options.
	 * 
	 * @param packageName:
	 *            The package name.
	 * @param options:
	 *            All specified options.
	 */
	public LaTeXPackage(String packageName, String[] options) {
		super(LaTeXSnippet.SnippetType.USE);
		this.packageName = packageName;
		this.options = options;
	}

	@Override
	public String convert() {
		// package import looks like:
		// \\usepackage{package-name} OR
		// \\usepackage[options]{package-name}
		if (options.length == 0)
			return "\\usepackage{" + packageName + "}";
		return "\\usepackage[" + String.join(",", options) + "]{"
				+ packageName + "}";
	}
}
