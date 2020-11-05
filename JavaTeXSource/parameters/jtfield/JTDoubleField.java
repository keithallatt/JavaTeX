package parameters.jtfield;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 * JTField implemented for floating point decimal input fields. Uses a text
 * field to capture input from the user. (Input is then parsed into a double).
 * 
 * @author Keith Allatt
 * @version 2020-10-29
 *
 */
public class JTDoubleField extends JTField<Double> {
	private JSpinner spinner = null;
	
	public JTDoubleField(String fn) {
		this(fn, 0.0d);
	}

	public JTDoubleField(String fn, Double ov) {
		super(fn, ov);
	}

	@Override
	public JPanel generateInputField() {
		if (inputFields != null) return inputFields;

		JLabel label = new JLabel(fieldName + ":");

		int width = inputDimensions.width;
		// int height = inputDimensions.height;

		// generates model for any integer.
		SpinnerModel model = new SpinnerNumberModel(0.0, -1000.0, 1000.0, 0.1);
		spinner = new JSpinner(model);
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner);
		spinner.setEditor(editor);

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
	public Double generateObjectFromInput() {
		if (spinner == null) return null;
		
		Object ov = spinner.getValue();

		if (ov instanceof Double) return (Double) ov;
		throw new RuntimeException("Wrong data type frmo JSpinner.");
	}

}
