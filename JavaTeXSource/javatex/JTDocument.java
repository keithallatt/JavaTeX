package javatex;

import java.awt.Dimension;
import java.util.HashSet;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Container for a set of problem sets or snippets. This object will contain a
 * set of these and
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
	DocumentClass docClass;
	JTPackage[] dependencies;

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

		String repr= String.join("\n\n", new String[] { docClassStr.trim(), comment,
				depends.trim(), startEnv, body.trim(), endEnv });
		
		
		
		return repr.replaceAll("\n{2,}", "\n\n");
	}

	public JPanel toGUI(int width) {
		JPanel containment = new JPanel();
		JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		int margin = 10;
		int panelHeight = -margin;

		for (JTSnippet ss : subSnippets) {
			JPanel ssContainer = new JPanel();

			JLabel name = new JLabel("" + ss.getClass());

			JButton editSnip = new JButton("Edit");
			JButton removeSnip = new JButton("Remove");

			ssContainer.add(name);
			ssContainer.add(Box.createRigidArea(new Dimension(100, 15)));
			ssContainer.add(editSnip);
			ssContainer.add(removeSnip);

			panel.add(ssContainer);

			panelHeight += margin + ssContainer.getPreferredSize().height;
		}
		
		
		panel.setPreferredSize(new Dimension(width, panelHeight));
		
		containment.add(panel);
		
		return containment;
	}

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
