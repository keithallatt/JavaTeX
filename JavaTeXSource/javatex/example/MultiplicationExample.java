package javatex.example;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javatex.*;
import javatex.envrn.*;
import javatex.field.JTField;
import javatex.field.JTIntField;

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
	public static void main(String[] args) {
		LaTeXDocument docWme = new LaTeXDocument();
		MultiplicationExample me = new MultiplicationExample(22, 9);

		JFrame frame = new JFrame("Multiplication Example");
		frame.getContentPane().add(me.toGUI());

		frame.pack();
		frame.setVisible(true);
	}

	public MultiplicationExample(int a, int b) {
		super(LaTeXSnippet.SnippetType.MAT);

		fields.add(new JTIntField("a", a, 2, 1, 15, 1));
		fields.add(new JTIntField("b", a, 3, 1, 15, 1));
	}

	@Override
	public JPanel toGUI() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));

		for (JTField<?> f : fields) {
			panel.add(f.generateInputField());
		}

		return panel;
	}

	@Override
	public String convert() {
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

		// should ensure a and b are positive for this example.
		a = Math.abs(a);
		b = Math.abs(b);

		// make the larger one a.
		if (b > a) {
			int t = a;
			a = b;
			b = t;
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
		b--;

		while (b >= 0) {
			// each subsequent line adds the first two terms and maintains the rest.
			String eqnLine = "= " + sum;
			for (int i = 0; i < b; i++)
				eqnLine += "+" + a;
			sum += a;
			b--;
			math.addEquationLine(eqnLine);
		}

		math.setEquationNumbering(false);
		math.setAlignment(Align.AlignAt.FIRST_EQUALS);

		addSnippet(math);

		return String.join("\n\n", new String[] {
				explanation.convert(), math.convert() });
	}
}
