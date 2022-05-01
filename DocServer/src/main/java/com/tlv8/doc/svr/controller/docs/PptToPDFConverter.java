package com.tlv8.doc.svr.controller.docs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;

public class PptToPDFConverter extends PptxToPDFConverter {

	private List<HSLFSlide> slides;

	public PptToPDFConverter(InputStream inStream, OutputStream outStream, boolean showMessages,
			boolean closeStreamsWhenComplete) {
		super(inStream, outStream, showMessages, closeStreamsWhenComplete);
	}

	@SuppressWarnings("resource")
	@Override
	protected Dimension processSlides() throws IOException {
		HSLFSlideShow ppt = new HSLFSlideShow(inStream);
		Dimension dimension = ppt.getPageSize();
		slides = ppt.getSlides();
		return dimension;
	}

	@Override
	protected int getNumSlides() {
		return slides.size();
	}

	@Override
	protected void drawOntoThisGraphic(int index, Graphics2D graphics) {
		slides.get(index).draw(graphics);
	}

	@Override
	protected Color getSlideBGColor(int index) {
		return slides.get(index).getBackground().getFill().getForegroundColor();
	}

}
