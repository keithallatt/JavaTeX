package javatex;

import javax.swing.JPanel;

import javatex.envrn.Align;
import javatex.envrn.Paragraph;

public final class MultiplicationExample extends JTProblemFrame {
	public static void main(String[]args) {
		LaTeXDocument docWme = new LaTeXDocument();
		MultiplicationExample me = new MultiplicationExample(3, 5);
		
		docWme.addSnippet(me);
		
		
		System.out.println(docWme.convert());
	}

	public MultiplicationExample(int a, int b) {
		super(LaTeXSnippet.SnippetType.MAT);

		fields.add(new JTField<Integer>("a", a) {

			@Override
			public JPanel generateInputField() {

				return null;
			}

			@Override
			public Integer generateObjectFromInput() {

				return null;
			}
		});

		fields.add(new JTField<Integer>("b", b) {

			@Override
			public JPanel generateInputField() {

				return null;
			}

			@Override
			public Integer generateObjectFromInput() {

				return null;
			}
		});

	}

	@Override
	public String convert() {
		int a = 0;
		int b = 0;

		for (JTField<?> f : fields) {
			if (f.getFieldName().equals("a")) {
				Object oa = f.getObjectValue();

				if (oa instanceof Integer) {
					// ensures oa is Integer

					a = (Integer) oa;
				} else {
					// throw exception
				}
			} else if (f.getFieldName().equals("b")) {
				Object ob = f.getObjectValue();

				if (ob instanceof Integer) {
					// ensures oa is Integer

					b = (Integer) ob;
				} else {
					// throw exception
				}
			}
		}
		
		// should ensure a and b are positive for this example.
		a = Math.abs(a);
		b = Math.abs(b);

		clearSnippets();

		// explanation of multiplication problem
		Paragraph explanation = new Paragraph(
				"To multiply two numbers, consider the problem as a repeated addition.",
				"Therefore $a\\times b$ can be thought of as adding $a$ to itself $b$ times.");

		addSnippet(explanation);

		Align math = new Align();

		math.addEquationLine(
				"a \\times b = a + ... + a \\; (b\\text{ times})");

		int sum = a;
		b--;

		while (b >= 0) {
			String eqnLine = "= "+sum;
			for (int i = 0; i < b; i++)
				eqnLine += "+"+a;
			sum += a;
			b--;
			math.addEquationLine(eqnLine);
		}
		
		math.setEquationNumbering(false);
		math.setAlignment(Align.AlignAt.FIRST_EQUALS);
		
		addSnippet(math);
				
		return String.join("\n\n", new String[] {explanation.convert(), math.convert()});
	}
}
