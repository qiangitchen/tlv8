package com.tlv8.doc.svr.core.text.pdf;

import java.io.File;
import java.io.IOException;

public class ConvertPdf {
	private static String INPUT_PATH;
	private static String PROJECT_PATH;

	public static boolean convertToHtml(String file, String project) {
		INPUT_PATH = file;
		PROJECT_PATH = project;
		if (checkContentType() == 0) {
			return toHtml();
		}
		return false;
	}

	private static int checkContentType() {
		String type = INPUT_PATH.substring(INPUT_PATH.lastIndexOf(".") + 1, INPUT_PATH.length()).toLowerCase();
		if (type.equals("pdf"))
			return 0;
		else
			return 9;
	}

	private static boolean toHtml() {
		boolean flag = false;
		if (new File(INPUT_PATH).isFile()) {
			try {
				String cmd = "cmd /c start E:\\doc\\xpdf\\pdftohtml.bat \"" + PROJECT_PATH + "\" \"" + INPUT_PATH + "\"";
				Runtime.getRuntime().exec(cmd);
				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	public static void main(String []args){
		ConvertPdf.convertToHtml("E:\\604-root.pdf","E:\\604-root.html");
	}
}
