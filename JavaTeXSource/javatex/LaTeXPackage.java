package javatex;

import java.util.Arrays;

/**
 * Represents an imported package.
 * 
 * @author Keith Allatt
 * @version 2020-10-25
 *
 */
public class LaTeXPackage extends LaTeXSnippet {

	private String packageName;
	private String[] options;

	public LaTeXPackage(String packageName) {
		this(packageName, new String[] {});
	}

	public LaTeXPackage(String... kw) {
		this(kw[0], Arrays.copyOfRange(kw, 1, kw.length));
	}

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
		if (options.length == 0) return "\\usepackage{" + packageName + "}";
		return "\\usepackage[" + String.join(",", options) + "]{" + packageName + "}";
	}
}
