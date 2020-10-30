package javatex.field;

import javax.swing.JPanel;

/**
 * JTField implemented for floating point decimal input fields. Uses a text
 * field to capture input from the user. (Input is then parsed into a double).
 * 
 * @author Keith Allatt
 * @version 2020-10-29
 *
 */
public class JTDoubleField extends JTField<Double> {

	public JTDoubleField(String fn, Double ov) {
		super(fn, ov);
	}

	@Override
	public JPanel generateInputField() {

		return null;
	}

	@Override
	public Double generateObjectFromInput() {

		return null;
	}

}
