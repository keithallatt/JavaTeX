package javatex.presets;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import javatex.JTProblemFrame;
import mathematicalObjects.Matrix;
import parameters.JTField;
import parameters.jthyper.JTHyperParameter;
import parameters.jthyper.JTHyperParameterListener;
import parameters.jttensor.JTMatrix;

public class MatrixMultPreset extends JTProblemFrame {
	JTMatrix mat1, mat2;
	
	public MatrixMultPreset() {
		JTHyperParameterListener hyperListener = new JTHyperParameterListener();

		JTHyperParameter n = new JTHyperParameter("n", 1);
		JTHyperParameter k = new JTHyperParameter("k", 1);
		JTHyperParameter m = new JTHyperParameter("m", 1);

		hyperparameters.add(n);
		hyperparameters.add(k);
		hyperparameters.add(m);

		n.setHyperListener(hyperListener);
		k.setHyperListener(hyperListener);
		m.setHyperListener(hyperListener);

		 mat1 = new JTMatrix("Matrix (LHS)", new Double[][] {});
		
		mat1.attachHyperParameterWidth(k);
		mat1.attachHyperParameterHeight(n);
		
		hyperListener.link(n, mat1);
		hyperListener.link(k, mat1);
		
		fields.add(mat1);
		
		 mat2 = new JTMatrix("Matrix (RHS)", new Double[][] {});
		
		mat2.attachHyperParameterWidth(m);
		mat2.attachHyperParameterHeight(k);
		
		hyperListener.link(m, mat2);
		hyperListener.link(k, mat2);
		
		fields.add(mat2);
	}

	@Override
	public String convert() {
		Matrix m1 = mat1.generateMatrixFromInput();
		Matrix m2 = mat2.generateMatrixFromInput();
		
		System.out.println(m1);
		System.out.println(m2);
		
		Matrix m3 = m1.times(m2);
		
		return m3.toString();
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
		return "Multiply two matrices of dimension (N x K) and (K x M)";
	}
}
