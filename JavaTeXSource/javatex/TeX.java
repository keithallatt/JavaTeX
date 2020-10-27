package javatex;

/**
 * Represents anything that is TeX-convertible.
 * 
 * @author Keith Allatt
 * @version 2020-10-25
 *
 */
public interface TeX {	
	/**
	 * Convert's a given object to TeX code.
	 * 
	 * @return the LaTeX representation of an object.
	 */
	public abstract String convert();

	/**
	 * Validate a TeX snippet. This should use the convert function and wrap it in a
	 * default class (document class 'standalone' / 'report') and attempt to compile
	 * it using a TeX compiler.
	 * 
	 * @return the validity of this TeX statement.
	 */
	public default boolean validate() {
		// encapsulate object within document class.
		// If this object is a document class, this method should be overridden.

		// until implemented, all code is valid.
		return true;
	}
	
	/**
	 * Get the package dependencies.
	 * 
	 * 
	 * 
	 * @return the list of dependencies.
	 */
	public default LaTeXPackage[] getDependencies() {
		return new LaTeXPackage[] {};
	}
}
