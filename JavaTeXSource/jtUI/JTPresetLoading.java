package jtUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javatex.JTProblemFrame;

public class JTPresetLoading extends JPanel {
	private JTUserInterface parentUI;

	private JSONObject root;

	public JTPresetLoading(JTUserInterface parent) {
		parentUI = parent;
		
		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black),
				"Choose a Preset", TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP));


		JScrollPane scroll = new JScrollPane();

		DefaultListModel<String> model = new DefaultListModel<String>();

		JSONParser parser = new JSONParser();

		try (Reader reader = new FileReader(System.getProperty("user.dir")
				+ "/JavaTeXSource/javatex/presets/presets.json")) {

			root = (JSONObject) parser.parse(reader);

			JSONArray presets = (JSONArray) root.get("presets");

			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator = presets.iterator();

			while (iterator.hasNext()) {
				JSONObject next = iterator.next();

				String name = (String) next.get("name");
				String classpath = (String) next.get("classpath");

				if (classpath == null) {
					// no class path specified means that we should have parameters
					// structure, and conversion scripts (to and from object).

				} else {
					// if class path exists, then create an object
					try {
						Object o = Class.forName(classpath).getDeclaredConstructor()
								.newInstance();

						if (o instanceof JTProblemFrame) {
							// valid problem frame.

							model.addElement(name);

						} else {
							throw new JTUIErrorDialog(parentUI,
									"Preset doesn't extend JTProblemFrame",
									new RuntimeException(
											"Preset doesn't extend JTProblemFrame"));
						}

					} catch (InstantiationException | IllegalAccessException
							| IllegalArgumentException | InvocationTargetException
							| NoSuchMethodException | SecurityException
							| ClassNotFoundException e) {
						throw new JTUIErrorDialog(parentUI, "Preset Instantiation failed",
								e);
					}
				}

			}

		} catch (IOException e) {
			throw new JTUIErrorDialog(parentUI, "Could not read presets.json", e);
		} catch (ParseException e) {
			throw new JTUIErrorDialog(parentUI, "Could not parse presets.json", e);
		}

		Dimension scrollSize = parentUI.getPrefSize();

		// this size doesn't take into account having a slight marging
		int margin = 30;

		// allows for buttons at the bottom later too.
		scrollSize = new Dimension((int) (scrollSize.getWidth() - margin),
				(int) (scrollSize.getHeight() - margin * 3));

		Dimension insideSize = new Dimension((int) (scrollSize.getWidth() - margin),
				(int) (scrollSize.getHeight() - margin));

		JList<String> listOfPresets = new JList<String>(model);
		listOfPresets.setPreferredSize(insideSize);

		listOfPresets.setVisible(true);

		JPanel container = new JPanel();
		container.add(listOfPresets);
		container.setPreferredSize(insideSize);

		scroll.setViewportView(listOfPresets);

		scroll.add(container);
		scroll.setVisible(true);
		scroll.setPreferredSize(scrollSize);

		add(scroll);

		// now for buttons to select

		JButton selectPreset = new JButton("Select Preset");

		selectPreset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selected = listOfPresets.getSelectedValue();

				JSONArray presets = (JSONArray) root.get("presets");

				@SuppressWarnings("unchecked")
				Iterator<JSONObject> iterator = presets.iterator();

				while (iterator.hasNext()) {
					JSONObject next = iterator.next();

					String name = (String) next.get("name");

					if (name.equals(selected)) {

						String classpath = (String) next.get("classpath");

						if (classpath == null) {
							// no class path specified means that we should have
							// parameters
							// structure, and conversion scripts (to and from object).

						} else {
							// if class path exists, then create an object
							try {
								Object o = Class.forName(classpath)
										.getDeclaredConstructor().newInstance();

								if (o instanceof JTProblemFrame) {
									// set current problem frame
									parentUI.setCurrentProblemFrame((JTProblemFrame) o);

									// set to next tab.
									parentUI.getTabContainer().setSelectedIndex(1);

								} else {
									throw new JTUIErrorDialog(parentUI,
											"Preset doesn't extend JTProblemFrame",
											new RuntimeException(
													"Preset doesn't extend JTProblemFrame"));
								}

							} catch (InstantiationException | IllegalAccessException
									| IllegalArgumentException | InvocationTargetException
									| NoSuchMethodException | SecurityException
									| ClassNotFoundException error) {
								throw new JTUIErrorDialog(parentUI,
										"Preset Initialization failed", error);
							}
						}
					}
				}
			}
		});

		listOfPresets.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getButton() == MouseEvent.BUTTON1)
					if (evt.getSource() instanceof JList<?>) {
						if (evt.getClickCount() == 2) {

							// Double-click detected
							Rectangle r = listOfPresets.getCellBounds(0,
									listOfPresets.getLastVisibleIndex());
							if (r != null && r.contains(evt.getPoint())) {
								// should already be selected but if needed:
								// int index =
								// listOfPresets.locationToIndex(evt.getPoint());
								selectPreset.doClick();
							}

						}
					}
			}
		});

		listOfPresets.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					selectPreset.doClick();
				}
			}
		});

		add(selectPreset);
	}

}
