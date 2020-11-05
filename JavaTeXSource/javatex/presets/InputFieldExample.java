package javatex.presets;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import javatex.JTProblemFrame;
import parameters.jtfield.JTField;
import parameters.jtfield.JTIntField;
import parameters.jthyper.JTHyperParameter;
import parameters.jthyper.JTHyperParameterListener;
import parameters.jttensor.JTVector;

public class InputFieldExample extends JTProblemFrame {

	public InputFieldExample() {
		// 
		JTHyperParameterListener hyperListener = new JTHyperParameterListener();
		
		JTHyperParameter n = new JTHyperParameter("n", 1);
		
		hyperparameters.add(n);
		
		fields.add(new JTIntField("Number", 2, 1, 100, 1));

		JTVector vec = new JTVector("Vector", 1.0);

		vec.attachHyperParameter(n);
		
		// connect n to vec
		n.setHyperListener(hyperListener);
		hyperListener.link(n, vec);
		
		fields.add(vec);

	}

	@Override
	public String convert() {
		return "";
	}

	@Override
	public JPanel toGUI() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 1;
	    c.weighty = 0;
	    c.gridx = 0;
		
	    c.gridy = 0;
	    
		for (JTHyperParameter hp : hyperparameters) {
			panel.add(hp.generateInputField(), c);
			c.gridy++;
		}

		for (JTField<?> f : fields) {
			panel.add(f.generateInputField(), c);
			c.gridy++;
		}

		return panel;
	}

	@Override
	public String getDescription() {

		return "Sample input fields to try it out. Nothing is placed in the document.";
	}

}
