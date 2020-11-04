package parameters.jtfield;

import javax.swing.JPanel;

/**
 * JTField implemented for a choice or decision. Uses radio buttons to
 * capture input from the user.
 * 
 * @author Keith Allatt
 * @version 2020-10-29
 *
 */ 
class JTChoiceField extends JTField<String> {
	public JTChoiceField(String fn, String ov) {
		super(fn, ov);
	}

	@Override
	public JPanel generateInputField() {
		if (inputFields != null)  return inputFields;
		
		return null;
	}

	@Override
	public String generateObjectFromInput() {
		
		return null;
	}

}
