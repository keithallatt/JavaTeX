package javatex.presets;

import java.awt.GridLayout;

import javax.swing.JPanel;

import javatex.JTProblemFrame;
import javatex.JTSnippet;
import javatex.envrn.Align;
import javatex.envrn.Paragraph;
import parameters.JTField;
import parameters.jtfield.JTIntField;

/**
 * Represents an example problem frame, in which multiplication is represented
 * as repeated addition. Currently this example produces output and displays it
 * to the console. In the final product this will be written to file and a pdf
 * will be generated from it.
 * 
 * @author Keith Allatt
 * @version 2020-10-28
 *
 */
public final class MultiplicationExample extends JTProblemFrame {
	public MultiplicationExample() {
		this(2,3);
	}
	
	public MultiplicationExample(int a, int b) {
		super(JTSnippet.SnippetType.MAT);

		// set a=2, b=3 by default, a and b must be within 1 and 15 inclusive.
		fields.add(new JTIntField("a", a, 1, 15, 1));
		fields.add(new JTIntField("b", b, 1, 15, 1));
	}

	@Override
	public JPanel toGUI() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));

		for (JTField<?> f : fields) {
			panel.add(f.generateInputField());
		}

		return panel;
	}

	@Override
	public String convert() {
		clearSnippets();
		
		int a = 0;
		int b = 0;

		for (JTField<?> f : fields) {
			// we know f is a JTIntField from the usage.
			JTIntField intF = (JTIntField) f;
			if (intF.getFieldName().equals("a")) {
				a = intF.generateObjectFromInput();
			} else if (intF.getFieldName().equals("b")) {
				b = intF.generateObjectFromInput();
			}
		}

		clearSnippets();

		// explanation of multiplication problem
		Paragraph explanation = new Paragraph(
				"To multiply two numbers, consider the problem as a repeated addition.",
				"Therefore $a\\times b$ can be thought of as adding $a$ to itself $b$ times.");

		addSnippet(explanation);

		Align math = new Align();

		// general form at top of align environment
		math.addEquationLine(
				"a \\times b = a + ... + a \\; (b\\text{ times})");

		int sum = a;
		int numTimes = b;
		numTimes--;

		boolean shownAtimesB = false;
		while (numTimes >= 0) {
			// each subsequent line adds the first two terms and maintains the rest.
			String eqnLine = (numTimes == 0 ? "\\therefore "+a+" \\times "+b : "") + (shownAtimesB ? "" : ""+a+" \\times "+b) + " = " + sum;
			shownAtimesB = true;
			for (int i = 0; i < numTimes; i++)
				eqnLine += "+" + a;
			sum += a;
			numTimes--;
			math.addEquationLine(eqnLine);
		}

		math.setEquationNumbering(false);
		math.setAlignment(Align.AlignAt.FIRST_EQUALS);

		addSnippet(math);

		return String.join("\n\n", new String[] {
				explanation.convert(), math.convert() });
	}

	@Override
	public String getDescription() {
		return "<html>Show the process of multiplying <i>a</i> by <i>b</i>.</html>";
	}
}
