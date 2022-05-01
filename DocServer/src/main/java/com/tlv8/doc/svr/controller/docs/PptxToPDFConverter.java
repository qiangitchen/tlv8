package com.tlv8.doc.svr.controller.docs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class PptxToPDFConverter extends Converter {

	public PptxToPDFConverter(InputStream inStream, OutputStream outStream, boolean showMessages,
			boolean closeStreamsWhenComplete) {
		super(inStream, outStream, showMessages, closeStreamsWhenComplete);
	}

	private List<XSLFSlide> slides;

	@Override
	public void convert() throws Exception {
		loading();

		Dimension pgsize = processSlides();

		processing();

		double zoom = 2; // magnify it by 2 as typical slides are low res
		AffineTransform at = new AffineTransform();
		at.setToScale(zoom, zoom);

		Document document = new Document();

		PdfWriter writer = PdfWriter.getInstance(document, outStream);
		document.open();

		for (int i = 0; i < getNumSlides(); i++) {
			BufferedImage bufImg = new BufferedImage((int) Math.ceil(pgsize.width * zoom),
					(int) Math.ceil(pgsize.height * zoom), BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = bufImg.createGraphics();
			graphics.setTransform(at);
			// clear the drawing area
			graphics.setPaint(getSlideBGColor(i));
			graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
			try {
				drawOntoThisGraphic(i, graphics);
			} catch (Exception e) {
				// Just ignore, draw what I have
			}

			Image image = Image.getInstance(bufImg, null);
			document.setPageSize(new Rectangle(image.getScaledWidth(), image.getScaledHeight()));
			document.newPage();
			image.setAbsolutePosition(0, 0);
			document.add(image);
		}
		// Seems like I must close document if not output stream is not complete
		document.close();

		// Not sure what repercussions are there for closing a writer but just
		// do it.
		writer.close();
		finished();

	}

	@SuppressWarnings("resource")
	protected Dimension processSlides() throws IOException {
		InputStream iStream = inStream;
		XMLSlideShow ppt = new XMLSlideShow(iStream);
		Dimension dimension = ppt.getPageSize();
		slides = ppt.getSlides();
		for (int i = 0; i < slides.size(); i++) {
			for (XSLFShape shape : slides.get(i).getShapes()) {
				if (shape instanceof XSLFTextShape) {
					XSLFTextShape txtshape = (XSLFTextShape) shape;
					for (XSLFTextParagraph textPara : txtshape.getTextParagraphs()) {
						List<XSLFTextRun> textRunList = textPara.getTextRuns();
						for (XSLFTextRun textRun : textRunList) {
							textRun.setFontFamily("宋体");
						}
					}
				}
			}
		}
		return dimension;
	}

	protected int getNumSlides() {
		return slides.size();
	}

	protected void drawOntoThisGraphic(int index, Graphics2D graphics) {
		slides.get(index).draw(graphics);
	}

	protected Color getSlideBGColor(int index) {
		return slides.get(index).getBackground().getFillColor();
	}

}
