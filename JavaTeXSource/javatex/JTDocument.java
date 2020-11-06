package javatex;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashSet;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import jtUI.JTPreviewFiles;
import jtUI.JTUserInterface;
import parameters.JTField;

/**
 * Container for a set of problem sets or snippets. This object will contain a
 * set of sub-snippets, for which each represents a portion of this document.
 * 
 * For generic snippets to be added from the GUI, we must have a snippet with a
 * corresponding JTProblemFrame wrapper.
 * 
 * @author Keith Allatt
 * @version 2020-10-26
 *
 */
public class JTDocument extends JTSnippet {
	/*
	 * Has access to subSnippets and TeXCode, just needs specific lists for specific
	 * cases. Such as imports / document class.
	 */
	private DocumentClass docClass;
	private JTPackage[] dependencies;

	/**
	 * Generate a blank document.
	 */
	public JTDocument() {
		super(JTSnippet.SnippetType.DOC);

		docClass = DocumentClass.REPORT;
	}

	@Override
	public String convert() {
		String docClassStr = docClass.convert();

		String startEnv = "\\begin{document}\n";

		String comment = "% Generated using JavaTeX (https://github.com/keithallatt/JavaTeX)";

		// need to generate body
		String body = "";

		for (JTSnippet ss : subSnippets)
			body += "\n\n" + ss.convert();

		// get dependencies after generating bodies so each class can ensure what
		// packages they need.
		dependencies = getDependencies();

		String depends = "";

		for (JTPackage ltp : dependencies)
			depends += "\n" + ltp.convert();

		String endEnv = "\n\\end{document}";

		String repr = String.join("\n\n", new String[] { docClassStr.trim(), comment,
				depends.trim(), startEnv, body.trim(), endEnv });

		return repr.replaceAll("\n{2,}", "\n\n");
	}

	/**
	 * Creates graphical user interface for editing document structure.
	 * 
	 * @param width:
	 *            panel width of the parent component, used for setting this
	 *            component's width.
	 * @return The JPanel container for this document
	 */
	public JPanel toGUI(JTUserInterface parent) {

		JPanel containment = new JPanel();
		JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		int margin = 10;
		int panelHeight = -margin;

		for (JTSnippet ss : subSnippets) {
			// only consider problem frames.
			if (!(ss instanceof JTProblemFrame)) continue;

			JTProblemFrame pf = (JTProblemFrame) ss;

			JPanel ssContainer = new JPanel();

			String htmlName = "<html>" + ss.getClass();
			for (JTField<?> f : pf.fields) {

				htmlName += "<br/>" + f;
			}
			htmlName += "</html>";

			JLabel name = new JLabel(htmlName);

			JButton editSnip = new JButton("Edit");
			JButton removeSnip = new JButton("Remove");
			JButton previewSnip = new JButton("Preview");
			JButton moveSnipUp = new JButton("<html>&uarr;</html>");
			JButton moveSnipDown = new JButton("<html>&darr;</html>"); 
			JButton other = new JButton("<html>...</html>"); 

			editSnip.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					/*
					 * Need to: Remove ss from subsnippets, set current editing snippet
					 * to ss, then set current tab to editing tab.
					 */
					subSnippets.remove(ss);
					parent.setCurrentProblemFrame(pf);
					parent.getTabContainer().setSelectedIndex(1);
				}
			});

			removeSnip.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					/*
					 * Need to: Remove ss from subsnippets, remove the subsnippet panel,
					 * and revalidate the container.
					 */
					subSnippets.remove(ss);
					panel.remove(ssContainer);
					panel.revalidate();
					panel.repaint();
				}
			});

			previewSnip.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JTPreviewFiles filePreview = new JTPreviewFiles(ss, 30,
							new Dimension(800, 600));

					Object[] options = new Object[] { "Back to editor" };

					JOptionPane.showOptionDialog(null, filePreview, "Preview TeX Output",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
							options, options[0]);
				}
			});

			moveSnipUp.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int i1 = subSnippets.indexOf(ss);

					try {
						// try to swap, if fails, don't worry.
						Collections.swap(subSnippets, i1, i1 - 1);

						// need to remove ss at i1 and place it in i1-1
						panel.remove(ssContainer);
						panel.add(ssContainer, i1 - 1);

						panel.revalidate();
						panel.repaint();
					} catch (IndexOutOfBoundsException aioobe) {
						// failure to swap means that ss was at the top
					}
				}
			});

			moveSnipDown.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int i1 = subSnippets.indexOf(ss);

					try {
						// try to swap, if fails, don't worry.
						Collections.swap(subSnippets, i1, i1 + 1);

						panel.remove(ssContainer);
						panel.add(ssContainer, i1 + 1);

						panel.revalidate();
						panel.repaint();
					} catch (IndexOutOfBoundsException aioobe) {
						// failure to swap means that ss was at the bottom
					}
				}
			});

			int nameHeight = name.getPreferredSize().height + 10;
			int width = parent.getPreferredSize().width - 100;
			width -= editSnip.getPreferredSize().width;
			width -= removeSnip.getPreferredSize().width;
			width -= previewSnip.getPreferredSize().width;
			width -= moveSnipUp.getPreferredSize().width;
			width -= moveSnipDown.getPreferredSize().width;
			width -= other.getPreferredSize().width;
			name.setPreferredSize(new Dimension(width, nameHeight));

			ssContainer.add(name);
			ssContainer.add(Box.createRigidArea(new Dimension(50, 15)));
			ssContainer.add(editSnip);
			ssContainer.add(removeSnip);
			ssContainer.add(previewSnip);
			ssContainer.add(moveSnipUp);
			ssContainer.add(moveSnipDown);
			ssContainer.add(other);

			panel.add(ssContainer);

			panelHeight += margin + ssContainer.getPreferredSize().height;
		}

		panel.setPreferredSize(
				new Dimension(parent.getPreferredSize().width, panelHeight));

		containment.add(panel);

		return containment;
	}

	// Setters

	public void setProperty(DocumentClass.FontSize fontsize) {
		docClass.fontsize = fontsize;
	}

	public void setProperty(DocumentClass.PaperSize papersize) {
		docClass.papersize = papersize;
	}

	public void setProperty(DocumentClass.TitlePage titlepage) {
		docClass.titlepage = titlepage;
	}

	public void setProperty(DocumentClass.PageSides pagesides) {
		docClass.pagesides = pagesides;
	}

	public void setProperty(DocumentClass.OpenSide openside) {
		docClass.openside = openside;
	}

	public void setProperty(String name, boolean value) {
		if (name.equals("fleqn")) docClass.fleqn = value;
		if (name.equals("leqno")) docClass.leqno = value;
		if (name.equals("twocolumn")) docClass.twocolumn = value;
		if (name.equals("landscape")) docClass.landscape = value;
		if (name.equals("draft")) docClass.draft = value;

	}

	/**
	 * Represents all document classes defined by LaTeX.
	 * 
	 * Contains all options for font size, paper size, etc., controllable through
	 * here.
	 * 
	 * If document class changed, options need to be changed too.
	 * 
	 * @author Keith Allatt
	 * @version 2020-10-28
	 *
	 */
	public enum DocumentClass implements TeX {
		ARTICLE("article"), IEEETRAN("IEEEtran"), PROC("proc"), REPORT("report"), BOOK(
				"book"), SLIDES(
						"slides"), MEMOIR("memoir"), LETTER("letter"), BEAMER("beamer");

		String docClass;

		boolean fleqn, leqno, twocolumn, landscape, draft;

		// DEFAULT VALUES.
		FontSize fontsize = FontSize.DEFAULT;
		PaperSize papersize = PaperSize.DEFAULT;
		TitlePage titlepage = TitlePage.DEFAULT;
		PageSides pagesides = PageSides.DEFAULT;
		OpenSide openside = OpenSide.DEFAULT;

		enum FontSize {
			TEN("10pt"), ELEVEN("11pt"), TWELVE("12pt"), DEFAULT(null);

			String size;

			FontSize(String size) {
				this.size = size;
			}

			static FontSize getSize(int n) {
				if (n == 10) return TEN;
				if (n == 11) return ELEVEN;
				if (n == 12) return TWELVE;
				return DEFAULT;
			}
		}

		enum PaperSize {
			A4("a4paper"), A5("a5paper"), B5("b5paper"), LETTER("letterpaper"), EXECUTIVE(
					"executivepaper"), LEGAL("legalpaper"), DEFAULT(null);

			String size;

			PaperSize(String size) {
				this.size = size;
			}
		}

		enum TitlePage {
			TITLE("titlepage"), NOTITLE("notitlepage"), DEFAULT(null);

			String title;

			TitlePage(String title) {
				this.title = title;
			}
		}

		enum PageSides {
			ONESIDE("oneside"), TWOSIDE("twoside"), DEFAULT(null);

			String side;

			PageSides(String side) {
				this.side = side;
			}
		}

		enum OpenSide {
			OPENRIGHT("openright"), OPENANY("openany"), DEFAULT(null);

			String side;

			OpenSide(String side) {
				this.side = side;
			}
		}

		DocumentClass(String docClass) {
			this.docClass = docClass;
		}

		@Override
		public String convert() {
			// general form
			// \documentclass[options...]{docclassname}

			// generate options
			HashSet<String> options = new HashSet<String>();

			// for fleqn, leqno, twocolumn, landscape, draft;

			if (fleqn) options.add("fleqn");
			if (leqno) options.add("leqno");
			if (twocolumn) options.add("twocolumn");
			if (landscape) options.add("landscape");
			if (draft) options.add("draft");

			options.add(fontsize.size);
			options.add(papersize.size);
			options.add(titlepage.title);
			options.add(pagesides.side);
			options.add(openside.side);

			options.remove(null);

			String opt = String.join(",", options);

			if (opt.length() == 0) return "\\documentclass{" + docClass + "}";

			return "\\documentclass[" + opt + "]{" + docClass + "}";
		}

	}
}
