package parameters;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 * JTField implemented for integer input fields. Uses a spinner object to
 * capture input from the user.
 * 
 * @author Keith Allatt
 * @version 2020-10-29
 *
 */
public final class JTIntField extends JTField<Integer> {
	private Integer defaultVal, min, max, step;

	public JTIntField(String fn, Integer ov, Integer defaultVal,
			Integer min, Integer max, Integer step) {
		this(fn, ov);
		this.defaultVal = defaultVal;
		this.min = min;
		this.max = max;
		this.step = step;
	}

	public JTIntField(String fn, Integer ov) {
		super(fn, ov);
		this.defaultVal = 0;
		this.step = 1;
	}

	@Override
	public JPanel generateInputField() {
		JLabel label = new JLabel(fieldName + ":");

		int width = inputDimensions.width;
		// int height = inputDimensions.height;

		// generates model for any integer.
		SpinnerModel model = new SpinnerNumberModel(defaultVal, min,
				max, step);
		JSpinner spinner = new JSpinner(model);

		inputFields = new JPanel();

		int labelWidth = label.getPreferredSize().width;
		int height = Math.max(label.getPreferredSize().height,
				spinner.getPreferredSize().height);

		int spinnerWidth = width - labelWidth;

		label.setMinimumSize(new Dimension(labelWidth, height));
		label.setPreferredSize(new Dimension(labelWidth, height));
		label.setMaximumSize(new Dimension(labelWidth, height));

		spinner.setMinimumSize(new Dimension(spinnerWidth, height));
		spinner.setPreferredSize(new Dimension(spinnerWidth, height));
		spinner.setMaximumSize(new Dimension(spinnerWidth, height));

		inputFields.add(label);
		inputFields.add(spinner);

		return inputFields;
	}

	@Override
	public Integer generateObjectFromInput() {
		// get the jpanel stored in JTField.
		// called inputFields
		try {
			Component inputSpinnerComp = inputFields.getComponent(1);

			// if all done correctly, inputSpinner should be a JSpinner.
			if (inputSpinnerComp instanceof JSpinner) {
				JSpinner inputSpinner = (JSpinner) inputSpinnerComp;

				Object ov = inputSpinner.getValue();

				if (ov instanceof Integer) return (Integer) ov;
				throw new RuntimeException(
						"Wrong data type frmo JSpinner.");

			}
			throw new RuntimeException(
					"Wrong data type from JPanel.");
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			throw new RuntimeException("Cannot extract child.",
					aioobe);
		}
	}

}
