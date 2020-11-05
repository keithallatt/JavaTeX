package parameters.jtfield;

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
public class JTIntField extends JTField<Integer> {
	private Integer min, max, step;
	protected JSpinner spinner = null;

	public JTIntField(String fn) {
		this(fn, 0);
	}

	public JTIntField(String fn, Integer ov, Integer min, Integer max, Integer step) {
		super(fn, ov);
		this.min = min;
		this.max = max;
		this.step = step;
	}

	public JTIntField(String fn, Integer ov) {
		this(fn, ov, null, null, 1);
	}

	@Override
	public JPanel generateInputField() {
		if (inputFields != null) return inputFields;

		JLabel label = new JLabel(fieldName + ":");

		int width = inputDimensions.width;
		// int height = inputDimensions.height;

		// generates model for any integer.
		SpinnerModel model = new SpinnerNumberModel(objectValue, min, max, step);
		spinner = new JSpinner(model);

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
		if (spinner == null) return null;
		
		Object ov = spinner.getValue();

		if (ov instanceof Integer) return (Integer) ov;
		throw new RuntimeException("Wrong data type frmo JSpinner.");
	}

}
