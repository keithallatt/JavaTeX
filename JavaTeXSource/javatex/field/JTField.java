package javatex.field;

import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * Represents an input field for a given JavaTeX problem set.
 * 
 * Key properties: 1) All fields must have a unique name in any given problem
 * set. 2) All fields must define how to create a graphical input field that
 * they'll be able to read from. 3) All fields must define how to take the raw
 * input fields as objects and convert them into an object of type T.
 * 
 * @author Keith Allatt
 * @version 2020-10-27
 *
 */
public abstract class JTField<T> {

	protected String fieldName;
	protected T objectValue;

	protected JPanel inputFields;
	
	protected static Dimension inputDimensions = new Dimension(300,50);

	public JTField(String fn, T ov) {
		fieldName = fn;
		objectValue = ov;
	}

	public void setObjectValue(T object) {
		objectValue = object;
	}

	public T getObjectValue() {
		return objectValue;
	}

	public void setFieldName(String field) {
		fieldName = field;
	}

	public String getFieldName() {
		return fieldName;
	}
	
	public abstract JPanel generateInputField();

	public abstract T generateObjectFromInput();
}
