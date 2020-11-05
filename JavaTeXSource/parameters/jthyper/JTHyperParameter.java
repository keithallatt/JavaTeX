package parameters.jthyper;

import java.awt.event.ActionEvent;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import parameters.jtfield.JTIntField;

/**
 * Represents a hyperparameter used for inputs to other fields.
 * 
 * For the purposes of this project, a hyper parameter is always an integer, as
 * it will represent dimensionality or a similar quantity, which typically is a
 * natural number / integer.
 * 
 * @author Keith Allatt
 * @version 2020-11-04
 *
 */
public final class JTHyperParameter extends JTIntField {

	public JTHyperParameter(String fn, Integer ov) {
		// JTInt field with a minimum value of 0 (0 dimensionality)
		super(fn, ov, 0, null, 1);

		// so that spinner is not null.
		generateInputField();

		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				onChange();
			}
		});
	}

	// reference
	private JTHyperParameterListener hyperListener;

	public void onChange() {
		ActionEvent change = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"Hyper Parameter changed");
		
		// set object value on change.
		objectValue = (Integer) spinner.getValue();

		if (hyperListener != null) hyperListener.actionPerformed(change);
	}

	public void setHyperListener(JTHyperParameterListener listener) {
		hyperListener = listener;
		hyperListener.track(this);
	}
}
