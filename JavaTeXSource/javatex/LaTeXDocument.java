package javatex;

import java.util.Set;

import javatex.envrn.Paragraph;

import java.util.HashSet;

/**
 * Container for a
 * 
 * @author Keith Allatt
 * @version 2020-10-26
 *
 */
public class LaTeXDocument extends LaTeXSnippet {
	public static void main(String[]args) {
		LaTeXDocument ld = new LaTeXDocument();
		
		Paragraph p = new Paragraph("Hello World!");
		
		ld.addSnippet(p);
		
		System.out.println(ld.convert());
	}
	

	/*
	 * Has access to subSnippets and TeXCode, just needs specific lists for specific
	 * cases. Such as imports / document class.
	 */
	DocumentClass docClass;
	LaTeXPackage[] dependencies;
	

	public LaTeXDocument() {
		super(LaTeXSnippet.SnippetType.DOC);
		
		docClass = DocumentClass.REPORT;
	}

	private void pullDependencies() {
		// iterate over all snippets and gather their dependencies
		Set<LaTeXPackage> dependencies = new HashSet<LaTeXPackage>();

		// write dependencies to the array after.
		this.dependencies = dependencies.toArray(new LaTeXPackage[] {});
	}

	@Override
	public String convert() {
		String docClassStr = docClass.convert();
		
		pullDependencies();
		
		String depends = "";
		
		for (LaTeXPackage ltp : dependencies)
			depends += "\n"+ltp.convert();
		
		String startEnv = "\\begin{document}";
		
		// need to generate body
		String body = "";
		
		for (LaTeXSnippet ss : subSnippets)
			body += "\n"+ss.convert();
		
		
		String endEnv = "\\end{document}";
		
		return String.join("\n", new String[] {docClassStr.trim(), depends.trim(), startEnv.trim(), body.trim(), endEnv.trim()});
	}

	enum DocumentClass implements TeX {
		ARTICLE("article"), IEEETRAN("IEEEtran"), PROC("proc"), REPORT("report"), BOOK("book"), SLIDES(
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
			A4("a4paper"), A5("a5paper"), B5("b5paper"), LETTER("letterpaper"), EXECUTIVE("executivepaper"), LEGAL(
					"legalpaper"), DEFAULT(null);

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
			
			if (opt.length() == 0)
				return "\\documentclass{"+docClass+"}";
			
			return "\\documentclass["+opt+"]{"+docClass+"}";
		}

	}
}
