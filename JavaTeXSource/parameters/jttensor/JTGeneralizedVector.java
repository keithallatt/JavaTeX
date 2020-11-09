package parameters.jttensor;

import parameters.JTField;

public abstract class JTGeneralizedVector<T> extends JTField<T[]> {

	public JTGeneralizedVector(String fn, T[] ov) {
		super(fn, ov);
	}
}
 