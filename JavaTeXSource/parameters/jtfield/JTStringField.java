package parameters.jtfield;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * JTField implemented for text input fields. Uses a text field to capture input
 * from the user.
 * 
 * @author Keith Allatt
 * @version 2020-10-29
 *
 */
public class JTStringField extends JTField<String> {
	JTextField textField = null;

	public JTStringField(String fn) {
		super(fn, "");
	}

	public JTStringField(String fn, String ov) {
		super(fn, ov);
	}

	@Override
	public JPanel generateInputField() {
		if (inputFields != null) return inputFields;

		JLabel label = new JLabel(fieldName + ":");

		int width = inputDimensions.width;
		// int height = inputDimensions.height;

		// generates model for any integer.

		textField = new JTextField(objectValue);

		inputFields = new JPanel();

		int labelWidth = label.getPreferredSize().width;
		int height = Math.max(label.getPreferredSize().height,
				textField.getPreferredSize().height);

		int textFieldWidth = width - labelWidth;

		label.setMinimumSize(new Dimension(labelWidth, height));
		label.setPreferredSize(new Dimension(labelWidth, height));
		label.setMaximumSize(new Dimension(labelWidth, height));

		textField.setMinimumSize(new Dimension(textFieldWidth, height));
		textField.setPreferredSize(new Dimension(textFieldWidth, height));
		textField.setMaximumSize(new Dimension(textFieldWidth, height));

		inputFields.add(label);
		inputFields.add(textField);

		return inputFields;
	}

	@Override
	public String generateObjectFromInput() {
		return textField.getText();
	}

}
