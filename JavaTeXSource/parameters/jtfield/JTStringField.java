package parameters.jtfield;

import javax.swing.JPanel;

import parameters.JTField;

/**
 * JTField implemented for text input fields. Uses a text field to
 * capture input from the user.
 * 
 * @author Keith Allatt
 * @version 2020-10-29
 *
 */
public class JTStringField extends JTField<String> {

	public JTStringField(String fn, String ov) {
		super(fn, ov);
	}

	@Override
	public JPanel generateInputField() {

		return null;
	}

	@Override
	public String generateObjectFromInput() {

		return null;
	}

}
