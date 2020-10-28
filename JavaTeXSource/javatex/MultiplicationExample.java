package javatex;

import javax.swing.JPanel;

import javatex.envrn.Paragraph;

public final class MultiplicationExample extends JTProblemFrame {

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

		// explanation

		Paragraph explanation = new Paragraph(
				"To multiply two numbers, consider the problem as a repeated addition.",
				"Therefore $a\\times b$ can be thought of as adding $a$ to itself $b$ times.");

		
		
		return null;
	}
}
