package com.tlv8.doc.svr.core.text.ppt;

import java.io.File;

import org.apache.poi.hslf.extractor.QuickButCruddyTextExtractor;

import com.tlv8.doc.svr.core.text.TextReader;

public class PPTReader extends TextReader {

	public PPTReader(File file, String extName) {
		super(file, extName);
	}

	@Override
	public String readAll() {
		String result = "";
		try {
			result = new QuickButCruddyTextExtractor(file.getAbsolutePath()).getTextAsString();
		}catch (Exception e){
		}
		return result;
	}

}
