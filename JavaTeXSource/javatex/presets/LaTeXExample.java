package javatex.presets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import javatex.JTProblemFrame;

/**
 * Very simple example JTProblem frame for which no inputs are required, which
 * gives the beginning
 * 
 * @author Keith Allatt
 * @version 2020-11-03
 *
 */
public class LaTeXExample extends JTProblemFrame {

	@Override
	public String convert() {
		return "LaTeX (\\LaTeX) is a software system for document preparation. When writing, "
				+ "the writer uses plain text as opposed to the formatted text found in ``What You See "
				+ "Is What You Get\'\' word processors like Microsoft Word, LibreOffice Writer and Apple "
				+ "Pages. The writer uses markup tagging conventions to define the general structure of "
				+ "a document (such as article, book, and letter), to stylize text throughout a document "
				+ "(such as bold and italics), and to add citations and cross-references. A TeX distribution "
				+ "such as TeX Live or MikTeX is used to produce an output file (such as PDF or DVI) suitable "
				+ "for printing or digital distribution.\n"
				+ "LaTeX is widely used in academia for the communication and publication of scientific "
				+ "documents in many fields, including mathematics, statistics, computer science, engineering, "
				+ "physics, economics, linguistics, quantitative psychology, philosophy, and political science. "
				+ "It also has a prominent role in the preparation and publication of books and articles that "
				+ "contain complex multilingual materials, such as Sanskrit and Greek. LaTeX uses the TeX "
				+ "typesetting program for formatting its output, and is itself written in the TeX macro language.\n"
				+ "LaTeX can be used as a standalone document preparation system, or as an intermediate format. "
				+ "In the latter role, for example, it is sometimes used as part of a pipeline for translating "
				+ "DocBook and other XML-based formats to PDF. The typesetting system offers programmable desktop "
				+ "publishing features and extensive facilities for automating most aspects of typesetting and "
				+ "desktop publishing, including numbering and cross-referencing of tables and figures, chapter "
				+ "and section headings, the inclusion of graphics, page layout, indexing and bibliographies.\n"
				+ "Like TeX, LaTeX started as a writing tool for mathematicians and computer scientists, but even "
				+ "from early in its development, it has also been taken up by scholars who needed to write "
				+ "documents that include complex math expressions or non-Latin scripts, such as Arabic, "
				+ "Devanagari and Chinese.\n"
				+ "LaTeX is intended to provide a high-level, descriptive markup language that accesses the power "
				+ "of TeX in an easier way for writers. In essence, TeX handles the layout side, while LaTeX handles "
				+ "the content side for document processing. LaTeX comprises a collection of TeX macros and a "
				+ "program to process LaTeX documents, and because the plain TeX formatting commands are elementary, "
				+ "it provides authors with ready-made commands for formatting and layout requirements such as chapter "
				+ "headings, footnotes, cross-references and bibliographies.\n"
				+ "LaTeX was originally written in the early 1980s by Leslie Lamport at SRI International. "
				+ "The current version is LaTeX2e, released in 1994, but updated in 2020. LaTeX3 (LATEX3) is "
				+ "under long-term development since the early 1990s. LaTeX is free software and is distributed "
				+ "under the LaTeX Project Public License (LPPL).";
	}

	@Override
	public JPanel toGUI() {
		// this JPanel won't need anything since there isn't any fields to input.

		JLabel label = new JLabel("No input required.");

		JPanel panel = new JPanel();
		panel.add(label);

		return panel;
	}

	@Override
	public String getDescription() {
		return "Add a snippet of text from LaTeX's wikipedia page.";
	}

}
