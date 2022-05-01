package com.tlv8.doc.svr.controller.utils;

import java.io.InputStream;
import java.io.OutputStream;

import com.spire.xls.FileFormat;
import com.spire.xls.Workbook;

public class ExcelToPDFUtils {

	public static boolean excel2pdf(InputStream in, OutputStream out) {
		// 加载Excel文档
		Workbook wb = new Workbook();
		wb.loadFromStream(in);
		// 调用方法保存为PDF格式
		wb.saveToStream(out, FileFormat.PDF);
		return true;
	}

	public static boolean excel2pdf(String excelpath, String pdfpath) {
		// 加载Excel文档
		Workbook wb = new Workbook();
		wb.loadFromFile(excelpath);
		// 调用方法保存为PDF格式
		wb.saveToFile(pdfpath, FileFormat.PDF);
		return true;
	}
}
