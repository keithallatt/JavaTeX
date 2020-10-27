package javatex;

/**
 * Represents a bare problem framework, that will allow a named set of inputs
 * such that changing the inputs generates a new LaTeX document snippet.
 * 
 * @author Keith Allatt
 * @version 2020-10-25
 *
 */
public abstract class JTProblemFrame extends LaTeXSnippet {

	/*
	 * Need a set of inputs, named, such that the convert function can take in the
	 * inputs, calculate the output (to the problem) and generate a LaTeXSnippet
	 * that displays all work done.
	 * 
	 * EX: long div using variables a, b
	 * 
	 * createProblem(4, 235, divProblem) -> 
	 * 
	 *       58 R3
	 *     ________
	 * 4  | 235
	 *     -20|
	 *    -----
	 *       35
	 *      -32
	 *    -----
	 *        3
	 *        
	 *  (In LaTeX code of course)
	 */

}
