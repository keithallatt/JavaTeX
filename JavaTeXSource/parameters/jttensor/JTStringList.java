package parameters.jttensor;

import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import parameters.jthyper.JTHyperParameter;

/**
 * Represents a vector in R^n
 * 
 * @author Keith Allatt
 * @version 2020-11-05
 *
 */
public class JTStringList extends JTGeneralizedVector<String> {
	private JTHyperParameter dimensionality;

	private JPanel inputContainers;

	private JTextField[] textFields = null;

	private int textWidth, labelHeight;

	public JTStringList(String fn, String... ov) {
		super(fn, ov);
		textFields = new JTextField[] {};
	}

	@Override
	public JPanel generateInputField() {
		if (inputFields != null) return inputFields;

		inputFields = new JPanel();

		JLabel label = new JLabel(fieldName + ":");

		int width = inputDimensions.width;
		// int height = inputDimensions.height;

		textWidth = width - label.getPreferredSize().width;
		labelHeight = label.getPreferredSize().height;

		inputFields.add(label);
		inputFields.add(generateTextFields());

		return inputFields;

	}

	private JPanel generateTextFields() {
		if (inputContainers == null) inputContainers = new JPanel();

		inputContainers.removeAll();
		// generates model for any integer.
		int dimensions = dimensionality.getObjectValue();

		JTextField[] newtextfields = new JTextField[dimensions];

		int panelHeight = 0;
		int height = labelHeight + 5;

		for (int i = 0; i < Math.min(textFields.length, dimensions); i++) {
			newtextfields[i] = textFields[i];
			panelHeight += 5 + height;
		}

		for (int i = textFields.length; i < dimensions; i++) {
			JTextField textf = new JTextField();

			textf.setMinimumSize(new Dimension(textWidth, height));
			textf.setPreferredSize(new Dimension(textWidth, height));
			textf.setMaximumSize(new Dimension(textWidth, height));

			panelHeight += 5 + height;

			newtextfields[i] = textf;

		}
		textFields = newtextfields;

		inputContainers.setLayout(new BoxLayout(inputContainers, BoxLayout.Y_AXIS));

		for (JTextField sp : textFields)
			inputContainers.add(sp);

		// inputContainers.setBorder(BorderFactory.createLineBorder(Color.black));

		inputContainers.setMinimumSize(new Dimension(textWidth + 5, panelHeight));
		inputContainers.setPreferredSize(new Dimension(textWidth + 5, panelHeight));
		inputContainers.setMaximumSize(new Dimension(textWidth + 5, panelHeight));

		return inputContainers;
	}

	@Override
	public String[] generateObjectFromInput() {
		int dimensions = dimensionality.getObjectValue();
		String[] list = new String[dimensions];

		for (int i = 0; i < dimensions; i++) {
			list[i] = textFields[i].getText();
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
			generateTextFields();

			if (inputFields != null) {
				inputFields.revalidate();
				inputFields.repaint();
			}
		}
	}

	@Override
	public String toString() {
		String[] list = generateObjectFromInput();

		if (list.length > 4) {
			// pull first 2 and last,
			String first, second, last;
			first = list[0];
			second = list[1];
			last = list[list.length - 1];

			return String.format("[%s, %s, ..., %s]", first, second, last);
		}

		return Arrays.toString(list);
	}
}
