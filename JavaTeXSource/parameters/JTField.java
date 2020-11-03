package parameters;

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

	protected static Dimension inputDimensions = new Dimension(300, 50);

	/**
	 * Create a JTField with a default object value.
	 * 
	 * @param fn:
	 *            The field name.
	 * @param ov:
	 *            The object value.
	 */
	public JTField(String fn, T ov) {
		fieldName = fn;
		objectValue = ov;
	}

	/**
	 * Generate a JPanel containing the appropriate labels and input fields in order
	 * to create an object of type T. Ensure that the global variable inputFields is
	 * assigned this generated object so that the method generateObjectFromInput()
	 * functions correctly.
	 * 
	 * @return The JPanel container for this JTField's input fields.
	 */
	public abstract JPanel generateInputField();

	/**
	 * Generate an object of type T from the container used to gain input from the
	 * user.
	 * 
	 * @return An object of type T defined by this JTField's input field.
	 */
	public abstract T generateObjectFromInput();

	//// Setters and Getters.

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

	@Override
	public int hashCode() {
		// Needed to be used in hash sets.
		return fieldName.hashCode();
	}
}
