package parameters.jttensor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.plaf.basic.BasicSpinnerUI;

import parameters.JTField;
import parameters.jthyper.JTHyperParameter;

public class JTMatrix extends JTField<Double[][]> {
	private JTHyperParameter matrixWidth;
	private JTHyperParameter matrixHeight;

	private JPanel inputContainers;

	private JSpinner[][] spinners = null;

	private int spinnerWidth, labelHeight;

	public JTMatrix(String fn, Double[][] ov) {
		super(fn, ov);
		spinners = new JSpinner[][] {};

	}

	@SuppressWarnings("static-method")
	public void hideSpinnerArrow(JSpinner spinner) {
		Dimension d = spinner.getPreferredSize();
		d.width = 30;
		spinner.setUI(new BasicSpinnerUI() {
			@Override
			protected Component createNextButton() {
				return null;
			}

			@Override
			protected Component createPreviousButton() {
				return null;
			}
		});
		spinner.setPreferredSize(d);
	}

	@Override
	public JPanel generateInputField() {
		if (inputFields != null) return inputFields;

		inputFields = new JPanel();

		JLabel label = new JLabel(fieldName + ":");

		int width = inputDimensions.width;
		// int height = inputDimensions.height;

		spinnerWidth = (width - label.getPreferredSize().width);
		labelHeight = label.getPreferredSize().height;

		inputFields.add(label);
		inputFields.add(generateVectorFields());

		return inputFields;

	}

	private JPanel generateVectorFields() {
		if (inputContainers == null) inputContainers = new JPanel();

		inputContainers.removeAll();
		// generates model for any integer.
		int matWidth = matrixWidth.getObjectValue();
		int matHeight = matrixHeight.getObjectValue();

		JSpinner[][] newspinners = new JSpinner[matWidth][matHeight];

		int panelHeight = 0;
		int height = labelHeight + 5;

		for (int i = 0; i < Math.min(spinners.length, matHeight); i++) {
			for (int j = 0; j < Math.min(spinners[0].length, matWidth); j++)
				newspinners[i][j] = spinners[i][j];
			panelHeight += 5 + height;
		}

		int j_s = 0;
		if (spinners.length != 0) // if i == 0, then no spinners exist.
			j_s = spinners[0].length;

		// temporary
		for (int i = 0; i < matHeight; i++) {
			for (int j = 0; j < matWidth; j++) {
				SpinnerModel model = new SpinnerNumberModel(0.0, null, null, 0.1);
				JSpinner spinner = new JSpinner(model);
				JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner);
				spinner.setEditor(editor);

				spinner.setMinimumSize(new Dimension(spinnerWidth / matWidth, height));
				spinner.setPreferredSize(new Dimension(spinnerWidth / matWidth, height));
				spinner.setMaximumSize(new Dimension(spinnerWidth / matWidth, height));

				hideSpinnerArrow(spinner);

				if (newspinners[i][j] == null) newspinners[i][j] = spinner;
			}
			panelHeight += 5 + height;
		}
		spinners = newspinners;

		inputContainers.setLayout(new GridLayout(matHeight, matWidth));

		for (JSpinner[] sp : spinners)
			for (JSpinner s : sp)
				inputContainers.add(s);

		// inputContainers.setBorder(BorderFactory.createLineBorder(Color.black));

		inputContainers.setMinimumSize(new Dimension(spinnerWidth + 5, panelHeight));
		inputContainers.setPreferredSize(new Dimension(spinnerWidth + 5, panelHeight));
		inputContainers.setMaximumSize(new Dimension(spinnerWidth + 5, panelHeight));

		return inputContainers;
	}

	@Override
	public Double[][] generateObjectFromInput() {
		int w = matrixWidth.getObjectValue();
		int h = matrixHeight.getObjectValue();
		Double[][] list = new Double[w][h];

		for (int i = 0; i < w; i++)
			for (int j = 0; j < h; j++)
				list[i][j] = (Double) spinners[i][j].getValue();

		return list;
	}

	public void attachHyperParameterWidth(JTHyperParameter n) {
		matrixWidth = n;
	}

	public void attachHyperParameterHeight(JTHyperParameter n) {
		matrixHeight = n;
	}

	@Override
	public void notifyOfChange(JTHyperParameter changedValue) {
		if (changedValue == matrixWidth || changedValue == matrixHeight) {
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
		Double[][] list = generateObjectFromInput();

		String repr = "";
		int numLines = 0;

		for (Double[] sublist : list) {
			if (sublist.length > 4) {
				// pull first 2 and last,
				double first, second, last;
				first = sublist[0];
				second = sublist[1];
				last = sublist[list.length - 1];

				DecimalFormat format = new DecimalFormat("0.###");

				repr += String.format("[%s, %s, ..., %s]", format.format(first),
						format.format(second), format.format(last)) + "\n";
			} else {
				repr += Arrays.toString(sublist) + "\n";
			}
			numLines++;
			if (numLines > 4)
				break;
		}
		repr = repr.trim();
		
		repr = "<html>" + String.join("<br />", repr.split("\n")) + "</html>";
		
		return repr;
	}
}
