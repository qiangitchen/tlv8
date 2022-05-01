package com.tlv8.doc.svr.core.text.pdf;

import java.io.File;
import java.io.FileInputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.tlv8.doc.svr.core.text.TextReader;

public class PDFReader extends TextReader {

	public PDFReader(File file, String extName) {
		super(file, extName);
	}

	@Override
	public String readAll() {
		String result = "";
		FileInputStream is = null;
		PDDocument document = null;
		try {
			document = PDDocument.load(file);
			PDFTextStripper textStripper = new PDFTextStripper();
			result = textStripper.getText(document);
		} catch (Exception e) {
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
			if (document != null) {
				try {
					document.close();
				} catch (Exception e) {
				}
			}
		}
		return result;
	}

}
