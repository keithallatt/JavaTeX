package parameters.jthyper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;

import jtUI.JTUIErrorDialog;
import parameters.jtfield.JTField;

public class JTHyperParameterListener implements ActionListener {
	private JTFieldDictionary<JTField<?>> fieldDictionary;

	public JTHyperParameterListener(JTHyperParameter... hyperparams) {
		fieldDictionary = new JTFieldDictionary<JTField<?>>();
		for (JTHyperParameter hp : hyperparams)
			fieldDictionary.trackHyper(hp);
	}

	/**
	 * Only going to be applied to
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// when an event is called,

		Object source = e.getSource();

		if (source instanceof JTHyperParameter) {
			// if we have a valid change:
			JTHyperParameter hp = (JTHyperParameter) source;

			ArrayList<JTField<?>> hpDependencies = fieldDictionary.get(hp);
			
			if (hpDependencies != null)
				for (JTField<?> field : hpDependencies)
					field.notifyOfChange(hp);
			
		} else {
			// non hyperparameter called this
			throw new JTUIErrorDialog(null,
					"HyperParameterListener action on non HyperParameter source",
					new RuntimeException("Non HyperParameter: " + source.toString()));
		}
	}
	
	public void track(JTHyperParameter hyper) {
		fieldDictionary.trackHyper(hyper);
	}
	public void link(JTHyperParameter hyper, JTField<?> field) {
		fieldDictionary.addDependency(hyper, field);
	}

	class JTFieldDictionary<T extends JTField<?>>
			extends Hashtable<JTHyperParameter, ArrayList<T>> {

		/**
		 * Add's a dependency to this parameter listener. If the hyper parameter
		 * specified is not being tracked, then return false.
		 * 
		 * @param hyper:
		 *            The hyper parameter being tracked
		 * @param field:
		 *            The field that depends on that hyper parameter.
		 * @return true if hyper is being tracked, and field is successfully added to
		 *         it's tracking list.
		 */
		public boolean addDependency(JTHyperParameter hyper, T field) {
			ArrayList<T> hyperDepends = get(hyper);

			if (hyperDepends == null) return false;

			return hyperDepends.add(field);
		}

		public void trackHyper(JTHyperParameter hyper) {
			if (get(hyper) == null) put(hyper, new ArrayList<T>());
		}

		/**
		 * Reverse the map to see the hyper parameters any given JTField depends on. The
		 * data structure still uses a hashtable with the keys being the hyper
		 * parameters so that notifying the fields after a change is as easy as one
		 * loop.
		 * 
		 * @return the reverse dependency list.
		 */
		public Hashtable<T, ArrayList<JTHyperParameter>> reverseMap() {
			Hashtable<T, ArrayList<JTHyperParameter>> reverse;

			HashSet<T> allFields = new HashSet<T>();

			for (ArrayList<T> fields : values()) {
				allFields.addAll(fields);
			}

			reverse = new Hashtable<T, ArrayList<JTHyperParameter>>();

			for (ArrayList<T> fields : values()) {
				allFields.addAll(fields);
			}

			for (T f : allFields) {
				ArrayList<JTHyperParameter> hp = new ArrayList<JTHyperParameter>();

				Enumeration<JTHyperParameter> allHyperParameters = keys();

				while (allHyperParameters.hasMoreElements()) {
					JTHyperParameter h = allHyperParameters.nextElement();
					if (get(h).contains(f)) {
						hp.add(h);
					}
				}

				reverse.put(f, hp);
			}

			return reverse;
		}

	}
}
