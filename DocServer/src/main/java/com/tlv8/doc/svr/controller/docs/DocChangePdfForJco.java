package com.tlv8.doc.svr.controller.docs;

import com.tlv8.doc.svr.controller.utils.Word2PdfAsposeUtil;

public class DocChangePdfForJco {

	public static Converter newConverter(String name) {
		if (name.equals("wps")) {
			return new WConverter();
		} else if (name.equals("pdfcreator")) {
			return new WConverter();
		}
		return null;
	}

	public synchronized static boolean convert(String word, String pdf) {
		return newConverter("pdfcreator").convert(word, pdf);
	}

	public interface Converter {

		boolean convert(String word, String pdf);
	}

	public static class WConverter implements Converter {

		public synchronized boolean convert(String word, String pdf) {
			return Word2PdfAsposeUtil.doc2pdf(word, pdf);
		}
	}

	public static void main(String[] args) {
		convert("d:\\4.doc", "d:\\4.pdf");
	}
}
