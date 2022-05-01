package com.tlv8.doc.svr.core.text.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import com.tlv8.doc.svr.core.inter.IFileReader;
import com.tlv8.doc.svr.core.text.TextReader;

@SuppressWarnings({ "resource", "deprecation" })
public class WordReader extends TextReader implements IFileReader {
	/*
	 * word文件读取
	 */

	public WordReader(File file, String extName) {
		super(file, extName);
	}

	@Override
	public String readAll() {
		String result = "";
		try {
			if (".docx".equals(extName)) {
				// word 2007 +
				OPCPackage opcPackage = POIXMLDocument.openPackage(file
						.getCanonicalPath());
				POIXMLTextExtractor extractor = new XWPFWordExtractor(
						opcPackage);
				result = extractor.getText();
			} else {
				InputStream is = new FileInputStream(file);
				// word 2003
				WordExtractor extractor = new WordExtractor(is);
				// 页眉+文档所有的文本+页脚
				result = extractor.getHeaderText() + extractor.getText()
						+ extractor.getFooterText();
				is.close();
			}
		} catch (Exception e) {
		}
		return result;
	}

}
