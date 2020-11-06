package parameters.jtfield;

import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import parameters.JTField;

/**
 * JTField implemented for boolean input fields. Uses a check button to capture
 * input from the user.
 * 
 * @author Keith Allatt
 * @version 2020-10-29
 *
 */
public class JTBoolField extends JTField<Boolean> {
	private JCheckBox check = null;

	public JTBoolField(String fn) {
		this(fn, false);
	}

	public JTBoolField(String fn, Boolean ov) {
		super(fn, ov);
	}

	@Override
	public JPanel generateInputField() {
		if (inputFields != null) return inputFields;

		JLabel label = new JLabel(fieldName + ":");

		int width = inputDimensions.width;
		// int height = inputDimensions.height;

		// generates model for any integer.

		check = new JCheckBox();

		inputFields = new JPanel();

		int labelWidth = label.getPreferredSize().width;
		int height = Math.max(label.getPreferredSize().height,
				check.getPreferredSize().height);

		int checkWidth = width - labelWidth;

		label.setMinimumSize(new Dimension(labelWidth, height));
		label.setPreferredSize(new Dimension(labelWidth, height));
		label.setMaximumSize(new Dimension(labelWidth, height));

		check.setMinimumSize(new Dimension(checkWidth, height));
		check.setPreferredSize(new Dimension(checkWidth, height));
		check.setMaximumSize(new Dimension(checkWidth, height));

		inputFields.add(label);
		inputFields.add(check);

		return inputFields;
	}

	@Override
	public Boolean generateObjectFromInput() {
		if (check == null) return null;
		return check.isSelected();
	}

}
