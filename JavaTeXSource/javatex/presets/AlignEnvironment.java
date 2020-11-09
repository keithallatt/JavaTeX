package javatex.presets;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import javatex.JTPackage;
import javatex.JTProblemFrame;
import javatex.envrn.Align;
import parameters.JTField;
import parameters.jthyper.JTHyperParameter;
import parameters.jthyper.JTHyperParameterListener;
import parameters.jttensor.JTStringList;

public class AlignEnvironment extends JTProblemFrame {
	private JTStringList vec;
	private Align align = new Align();
	
	public AlignEnvironment() {
		JTHyperParameterListener hyperListener = new JTHyperParameterListener();
		
		JTHyperParameter n = new JTHyperParameter("Number of Lines", 1);
		
		hyperparameters.add(n);

		vec = new JTStringList("Align Env.");

		vec.attachHyperParameter(n);
		
		// connect n to vec
		n.setHyperListener(hyperListener);
		hyperListener.link(n, vec);
		
		fields.add(vec);
	}
		
	@Override
	public String convert() {
		align = new Align();
		
		// until other field implemented
		align.setAlignment(Align.AlignAt.FIRST_EQUALS);
		
		for (String line : vec.generateObjectFromInput())
			if (!line.trim().equals("")) align.addEquationLine(line);
		
		return align.convert();
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
		return "Create a set of equations.";
	}
	
	@Override 
	public JTPackage[] getDependencies() {
		return align.getDependencies();
	}


}
