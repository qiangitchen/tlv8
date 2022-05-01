package com.tlv8.doc.svr.core.text.ppt;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;

import com.tlv8.doc.svr.core.text.TextReader;

@SuppressWarnings("resource")
public class PPTReader extends TextReader {

	public PPTReader(File file, String extName) {
		super(file, extName);
	}

	@Override
	public String readAll() {
		StringBuffer result = new StringBuffer();
		try {

			if (".pptx".equals(extName)) {
				OPCPackage slideshow = POIXMLDocument.openPackage(file.getCanonicalPath());
				XSLFPowerPointExtractor pointext = new XSLFPowerPointExtractor(slideshow);
				result.append(pointext.getText());
			} else {
				FileInputStream is = new FileInputStream(file);
				PowerPointExtractor extractor = new PowerPointExtractor(is);
				result.append(extractor.getText());
				is.close();
			}
		} catch (Exception e) {
		}
		return result.toString();
	}

}
