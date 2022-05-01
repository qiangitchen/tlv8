package com.tlv8.doc.svr.core;

import java.io.File;

import com.tlv8.doc.svr.core.inter.IFileReader;
import com.tlv8.doc.svr.core.text.TextReader;
import com.tlv8.doc.svr.core.text.excel.ExcelReader;
import com.tlv8.doc.svr.core.text.pdf.PDFReader;
import com.tlv8.doc.svr.core.text.ppt.PPTReader;
import com.tlv8.doc.svr.core.text.rtf.RtfReader;
import com.tlv8.doc.svr.core.text.word.WordReader;
import com.tlv8.doc.svr.core.utils.FileExtArray;

public class FileReader {
	/**
	 * 读取文件内容
	 */
	public static String readAll(File file, String extName) {
		IFileReader filer = getReader(file, extName);
		if (filer != null) {
			return filer.readAll();
		}
		return "";
	}

	/**
	 * 获取读取文件接口
	 */
	public static IFileReader getReader(File file, String extName) {
		IFileReader filereader = null;
		if (FileExtArray.checkFileExt(extName, FileExtArray.wordArray)) {
			filereader = new WordReader(file, extName);
		} else if (FileExtArray.checkFileExt(extName, FileExtArray.excelArray)) {
			filereader = new ExcelReader(file, extName);
		} else if (FileExtArray.checkFileExt(extName, FileExtArray.pptArray)) {
			filereader = new PPTReader(file, extName);
		} else if (FileExtArray.checkFileExt(extName, FileExtArray.pdfArray)) {
			filereader = new PDFReader(file, extName);
		} else if (FileExtArray.checkFileExt(extName, FileExtArray.rtfArray)) {
			filereader = new RtfReader(file, extName);
		} else if (FileExtArray.checkFileExt(extName, FileExtArray.textArray)) {
			filereader = new TextReader(file, extName);
		}
		return filereader;
	}

	/**
	 * 判断是否为支持检索的文件类型
	 */
	public static boolean isIndexFile(String extName) {
		if (FileExtArray.checkFileExt(extName, FileExtArray.wordArray)) {
			return true;
		} else if (FileExtArray.checkFileExt(extName, FileExtArray.excelArray)) {
			return true;
		} else if (FileExtArray.checkFileExt(extName, FileExtArray.pptArray)) {
			return true;
		} else if (FileExtArray.checkFileExt(extName, FileExtArray.pdfArray)) {
			return true;
		} else if (FileExtArray.checkFileExt(extName, FileExtArray.textArray)) {
			return true;
		}
		return false;
	}
}
