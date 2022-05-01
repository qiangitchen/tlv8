package com.tlv8.doc.svr.core.text.rtf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import com.tlv8.doc.svr.core.text.TextReader;

public class RtfReader extends TextReader {
	/*
	 * rtf文件读取
	 */
	public RtfReader(File file, String extName) {
		super(file, extName);
	}

	@Override
	public String readAll() {
		String result = null;
		try {
			DefaultStyledDocument styledDoc = new DefaultStyledDocument();
			new RTFEditorKit().read(new FileInputStream(file), styledDoc, 0);
			result = new String(styledDoc.getText(0, styledDoc.getLength())
					.getBytes("ISO8859_1"));
		} catch (IOException e) {
		} catch (BadLocationException e) {
		}
		return result;
	}
}
