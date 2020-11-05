package parameters.jttensor;

import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import parameters.jtfield.JTField;
import parameters.jthyper.JTHyperParameter;

/**
 * Represents a vector in R^n
 * 
 * @author Keith Allatt
 * @version 2020-11-05
 *
 */
public class JTVector extends JTField<Double[]> {
	private JTHyperParameter dimensionality;

	private JPanel inputContainers;

	private JSpinner[] spinners = null;

	private int spinnerWidth, labelHeight;

	public JTVector(String fn, Double... ov) {
		super(fn, ov);

		spinners = new JSpinner[] {

		};
	}

	@Override
	public JPanel generateInputField() {
		if (inputFields != null) return inputFields;

		inputFields = new JPanel();

		JLabel label = new JLabel(fieldName + ":");

		int width = inputDimensions.width;
		// int height = inputDimensions.height;

		spinnerWidth = width - label.getPreferredSize().width;
		labelHeight = label.getPreferredSize().height;

		inputFields.add(label);
		inputFields.add(generateVectorFields());

		return inputFields;

	}

	private JPanel generateVectorFields() {
		if (inputContainers == null) inputContainers = new JPanel();

		inputContainers.removeAll();
		// generates model for any integer.
		int dimensions = dimensionality.getObjectValue();

		JSpinner[] newspinners = new JSpinner[dimensions];

		int panelHeight = 0;
		int height = labelHeight + 5;

		for (int i = 0; i < Math.min(spinners.length, dimensions); i++) {
			newspinners[i] = spinners[i];
			panelHeight += 5 + height;
		}

		for (int i = spinners.length; i < dimensions; i++) {
			SpinnerModel model = new SpinnerNumberModel(0.0, -1000.0, 1000.0, 0.1);
			JSpinner spinner = new JSpinner(model);
			JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner);
			spinner.setEditor(editor);

			spinner.setMinimumSize(new Dimension(spinnerWidth, height));
			spinner.setPreferredSize(new Dimension(spinnerWidth, height));
			spinner.setMaximumSize(new Dimension(spinnerWidth, height));

			panelHeight += 5 + height;

			newspinners[i] = spinner;

		}
		spinners = newspinners;

		inputContainers.setLayout(new BoxLayout(inputContainers, BoxLayout.Y_AXIS));

		for (JSpinner sp : spinners)
			inputContainers.add(sp);

		// inputContainers.setBorder(BorderFactory.createLineBorder(Color.black));

		inputContainers.setMinimumSize(new Dimension(spinnerWidth + 5, panelHeight));
		inputContainers.setPreferredSize(new Dimension(spinnerWidth + 5, panelHeight));
		inputContainers.setMaximumSize(new Dimension(spinnerWidth + 5, panelHeight));

		return inputContainers;
	}

	@Override
	public Double[] generateObjectFromInput() {
		int dimensions = dimensionality.getObjectValue();
		Double[] list = new Double[dimensions];

		for (int i = 0; i < dimensions; i++) {
			list[i] = (Double) spinners[i].getValue();
		}

		return list;
	}

	/**
	 * Connect this object with a hyper parameter that this field will use to
	 * control it's dimensionality.
	 * 
	 */
	public void attachHyperParameter(JTHyperParameter param) {
		dimensionality = param;
		notifyOfChange(dimensionality);
	}

	@Override
	public void notifyOfChange(JTHyperParameter changedValue) {
		if (changedValue == dimensionality) {
			// fix later
			generateInputField();
			generateVectorFields();

			if (inputFields != null) {
				inputFields.revalidate();
				inputFields.repaint();
			}
		}
	}

	@Override
	public String toString() {
		Double[] list = generateObjectFromInput();

		if (list.length > 4) {
			// pull first 2 and last,
			double first, second, last;
			first = list[0];
			second = list[1];
			last = list[list.length - 1];

			DecimalFormat format = new DecimalFormat("0.###");

			return String.format("[%s, %s, ..., %s]", format.format(first),
					format.format(second), format.format(last));
		}

		return Arrays.toString(list);
	}
}
