package javatex.field;

import javax.swing.JPanel;

/**
 * JTField implemented for boolean input fields. Uses a check button to
 * capture input from the user.
 * 
 * @author Keith Allatt
 * @version 2020-10-29
 *
 */
public class JTBoolField extends JTField<Boolean> {
	public JTBoolField(String fn, Boolean ov) {
		super(fn, ov);
	}

	@Override
	public JPanel generateInputField() {

		return null;
	}

	@Override
	public Boolean generateObjectFromInput() {

		return null;
	}

}
