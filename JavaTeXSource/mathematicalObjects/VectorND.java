package mathematicalObjects;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents an N-dimensional vector, or a vector in R^N
 * 
 * @author kallatt
 *
 */
public class VectorND {
	private int dimension;
	private double[] components; // components

	public VectorND() {
		components = new double[0];
		dimension = 0;
	}

	public VectorND(int n) {
		components = new double[n];
		dimension = n;
	}

	public VectorND(double[] n) {
		components = Arrays.copyOf(n, n.length); // copies the array.
		dimension = n.length;
	}

	public VectorND add(VectorND vnd) {
		if (vnd.dimension != dimension)
			throw new RuntimeException("Vector's dimension's are different.");

		double[] newComponents = Arrays.copyOf(components, dimension);

		for (int i = 0; i < dimension; i++)
			newComponents[i] += vnd.components[i];

		return new VectorND(newComponents);
	}

	public VectorND subtract(VectorND vnd) {
		return this.add(vnd.scalarMultiply(-1));
	}

	public VectorND scalarMultiply(double k) {
		double[] newComponents = Arrays.copyOf(components, dimension);

		for (int i = 0; i < dimension; i++)
			newComponents[i] *= k;

		return new VectorND(newComponents);
	}

	public double dotProduct(VectorND vnd) {
		if (vnd.dimension != dimension)
			throw new RuntimeException("Vector's dimension's are different.");

		double d = 0.0;

		for (int i = 0; i < dimension; i++)
			d += this.components[i] * vnd.components[i];

		return d;
	}

	public static VectorND crossProduct(VectorND... nds) {
		if (nds.length == 0)
			throw new RuntimeException("Must have at least 1 vector");
		
		int dimension = nds[0].dimension;
		
		for (VectorND nd : nds)
			if (nd.dimension != dimension) {
				throw new RuntimeException("Vectors not of consistent dimension");
			}
		
		if (nds.length != dimension - 1) 
			throw new RuntimeException("Require N-1 vectors to compute a cross product");
		
		
		double[] newComponents = new double[dimension];
		
		for (int i = 0; i < dimension; i++) {
			// computing the i-th component;
			
			Matrix m = new Matrix(dimension-1, dimension-1);
			
			// put in the vectors:
			
			for (int row = 0; row < dimension-1; row++) {
				// each row is a vector, safe the i-th dimension
				
				ArrayList<Double> vectorMinusDimension = new ArrayList<Double>();
				
				for (double d : nds[row].components)
					vectorMinusDimension.add(d);
				
				vectorMinusDimension.remove(i);
				
				int col = 0;
				for (double d : vectorMinusDimension)
					m.setTerm(row, col++, d);
			}
		
			newComponents[i] = m.determinant()* Math.pow(-1, i);
			
		}
		
		
		return new VectorND(newComponents);
	}
	
	@Override
	public String toString() {
		Matrix m = new Matrix(dimension, 1);
		
		for (int i = 0; i < dimension; i++)
			m.setTerm(i, 0, components[i]);
		
		return m.toString();
		
	}

	public int getDimension() {
		return dimension;
	}

	public double[] getComponents() {
		return Arrays.copyOf(components, dimension);
	}
}
