package parameters.jthyper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;

import parameters.JTField;

public class JTHyperParameterListener implements ActionListener {
	
	
	public JTHyperParameterListener() {
		
	}
	
	
	/**
	 * Only going to be applied to 
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	
	
	
	class JTFieldDictionary<H, F>
			extends Hashtable<JTHyperParameter<H>, ArrayList<JTField<F>>> {

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
		public boolean addDependency(JTHyperParameter<H> hyper, JTField<F> field) {
			ArrayList<JTField<F>> hyperDepends = get(hyper);

			if (hyperDepends == null) return false;

			return hyperDepends.add(field);
		}

		/**
		 * Reverse the map to see the hyper parameters any given JTField depends on. The
		 * data structure still uses a hashtable with the keys being the hyper
		 * parameters so that notifying the fields after a change is as easy as one
		 * loop.
		 * 
		 * @return the reverse dependency list.
		 */
		public Hashtable<JTField<F>, ArrayList<JTHyperParameter<H>>> reverseMap() {
			Hashtable<JTField<F>, ArrayList<JTHyperParameter<H>>> reverse;

			HashSet<JTField<F>> allFields = new HashSet<JTField<F>>();

			for (ArrayList<JTField<F>> fields : values()) {
				allFields.addAll(fields);
			}

			reverse = new Hashtable<JTField<F>, ArrayList<JTHyperParameter<H>>>();

			for (ArrayList<JTField<F>> fields : values()) {
				allFields.addAll(fields);
			}

			for (JTField<F> f : allFields) {
				ArrayList<JTHyperParameter<H>> hp = new ArrayList<JTHyperParameter<H>>();

				Enumeration<JTHyperParameter<H>> allHyperParameters = keys();

				while (allHyperParameters.hasMoreElements()) {
					JTHyperParameter<H> h = allHyperParameters.nextElement();
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
