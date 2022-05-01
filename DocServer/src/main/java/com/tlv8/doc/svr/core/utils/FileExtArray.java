package com.tlv8.doc.svr.core.utils;

public class FileExtArray {
	public static String[] textArray = new String[] { ".txt", ".java", ".c", ".cpp", ".py", ".xml", ".json", ".log",
			".htm", ".html", ".php", ".jsp", ".wps" };
	public static String[] rtfArray = new String[] { ".rtf" };
	public static String[] wordArray = new String[] { ".doc", ".docx", ".wps", ".wpt", ".dot" };
	public static String[] pptArray = new String[] { ".ppt", ".pptx", ".dps", ".dpt", ".pot", ".pps" };
	public static String[] excelArray = new String[] { ".xls", ".xlsx", ".et", ".ett", ".xlt" };
	public static String[] pdfArray = new String[] { ".pdf" };

	public static boolean checkFileExt(String fileExt, String[] fileEndings) {
		for (String aEnd : fileEndings) {
			if (fileExt.endsWith(aEnd))
				return true;
		}
		return false;
	}

	public static String getExtName(String filename) {
		return filename.substring(filename.lastIndexOf(".")).toLowerCase();// 文件扩展名统一为小写
	}
}
