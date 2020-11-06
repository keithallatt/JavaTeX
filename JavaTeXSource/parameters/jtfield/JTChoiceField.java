package parameters.jtfield;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import parameters.JTField;

/**
 * JTField implemented for a choice or decision. Uses radio buttons to capture
 * input from the user.
 * 
 * @author Keith Allatt
 * @version 2020-10-29
 *
 */
public class JTChoiceField extends JTField<String> {
	private String[] choices;
	private int defaultIndex;
	private JComboBox<String> comboBox = null;

	public JTChoiceField(String fn) {
		this(fn, 0, new String[] { "" });
	}

	public JTChoiceField(String fn, int objectValueIndex, String[] choices) {
		super(fn, choices[objectValueIndex]);
		defaultIndex = objectValueIndex;
		this.choices = choices;
	}

	@Override
	public JPanel generateInputField() {
		if (inputFields != null) return inputFields;

		JLabel label = new JLabel(fieldName + ":");

		int width = inputDimensions.width;
		// int height = inputDimensions.height;

		// generates model for any integer.

		comboBox = new JComboBox<String>(choices);
		comboBox.setSelectedIndex(defaultIndex);

		inputFields = new JPanel();

		int labelWidth = label.getPreferredSize().width;
		int height = Math.max(label.getPreferredSize().height,
				comboBox.getPreferredSize().height);

		int spinnerWidth = width - labelWidth;

		label.setMinimumSize(new Dimension(labelWidth, height));
		label.setPreferredSize(new Dimension(labelWidth, height));
		label.setMaximumSize(new Dimension(labelWidth, height));

		comboBox.setMinimumSize(new Dimension(spinnerWidth, height));
		comboBox.setPreferredSize(new Dimension(spinnerWidth, height));
		comboBox.setMaximumSize(new Dimension(spinnerWidth, height));

		inputFields.add(label);
		inputFields.add(comboBox);

		return inputFields;
	}

	@Override
	public String generateObjectFromInput() {
		return comboBox.getSelectedItem().toString();
	}

}
