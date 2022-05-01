package com.tlv8.doc.svr.controller.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xslf.usermodel.*;
import org.apache.poi.hslf.usermodel.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Image;

public class PdfConverUtil {

	public static boolean pptxToPdf(String pptPath, String pdfDir) {

		if (StringUtils.isEmpty(pptPath)) {
			throw new RuntimeException("word文档路径不能为空");
		}

		if (StringUtils.isEmpty(pdfDir)) {
			throw new RuntimeException("pdf目录不能为空");
		}

		String pdfPath = pdfDir + "te." + "pdf";

		Document document = null;

		XMLSlideShow slideShow = null;

		FileOutputStream fileOutputStream = null;

		PdfWriter pdfWriter = null;

		try {
			// 使用输入流pptx文件
			slideShow = new XMLSlideShow(new FileInputStream(pptPath));
			// 获取幻灯片的尺寸
			Dimension dimension = slideShow.getPageSize();
			// 新增pdf输出流，准备讲ppt写出
			fileOutputStream = new FileOutputStream(pdfPath);
			// 创建一个写内容的容器
			document = new Document();
			// 使用输出流写入
			pdfWriter = PdfWriter.getInstance(document, fileOutputStream);
			// 使用之前必须打开<You have to open the document before you can write content.>
			document.open();

			PdfPTable pdfPTable = new PdfPTable(1);
			// 获取幻灯片
			List<XSLFSlide> slideList = slideShow.getSlides();

			for (int i = 0, row = slideList.size(); i < row; i++) {
				// 获取每一页幻灯片
				XSLFSlide slide = slideList.get(i);

				for (XSLFShape shape : slide.getShapes()) {
					// 判断是否是文本
					if (shape instanceof XSLFTextShape) {
						// 设置字体, 解决中文乱码
						XSLFTextShape textShape = (XSLFTextShape) shape;
						for (XSLFTextParagraph textParagraph : textShape.getTextParagraphs()) {
							for (XSLFTextRun textRun : textParagraph.getTextRuns()) {
								textRun.setFontFamily("宋体");
							}
						}
					}
				}

				// 根据幻灯片尺寸创建图形对象
				BufferedImage bufferedImage = new BufferedImage((int) dimension.getWidth(), (int) dimension.getHeight(),
						BufferedImage.TYPE_INT_RGB);

				Graphics2D graphics2d = bufferedImage.createGraphics();

				graphics2d.setPaint(Color.white);
				graphics2d.setFont(new java.awt.Font("宋体", java.awt.Font.PLAIN, 12));

				// 把内容写入图形对象
				slide.draw(graphics2d);

				graphics2d.dispose();

				// 封装到Image对象中
				Image image = Image.getInstance(bufferedImage, null);
				image.scalePercent(50f);

				// 写入单元格
				pdfPTable.addCell(new PdfPCell(image, true));
				document.add(image);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (document != null) {
					document.close();
				}
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
				if (pdfWriter != null) {
					pdfWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public static boolean pptToPdf(String pptPath, String pdfDir) {

		if (StringUtils.isEmpty(pptPath)) {
			throw new RuntimeException("word文档路径不能为空");
		}

		if (StringUtils.isEmpty(pdfDir)) {
			throw new RuntimeException("pdf目录不能为空");
		}

		String pdfPath = pdfDir + "te." + "pdf";

		Document document = null;
		HSLFSlideShow hslfSlideShow = null;
		FileOutputStream fileOutputStream = null;
		PdfWriter pdfWriter = null;

		try {
			// 使用输入流ppt文件
			hslfSlideShow = new HSLFSlideShow(new FileInputStream(pptPath));

			// 获取ppt文件页面
			Dimension dimension = hslfSlideShow.getPageSize();

			fileOutputStream = new FileOutputStream(pdfPath);

			document = new Document();

			// pdfWriter实例
			pdfWriter = PdfWriter.getInstance(document, fileOutputStream);

			document.open();

			PdfPTable pdfPTable = new PdfPTable(1);

			List<HSLFSlide> hslfSlideList = hslfSlideShow.getSlides();

			for (int i = 0; i < hslfSlideList.size(); i++) {
				HSLFSlide hslfSlide = hslfSlideList.get(i);

				for (HSLFShape shape : hslfSlide.getShapes()) {

					if (shape instanceof HSLFTextShape) {
						// 设置字体, 解决中文乱码
						HSLFTextShape textShape = (HSLFTextShape) shape;

						for (HSLFTextParagraph textParagraph : textShape.getTextParagraphs()) {
							for (HSLFTextRun textRun : textParagraph.getTextRuns()) {
								textRun.setFontFamily("宋体");
							}
						}
					}

				}
				BufferedImage bufferedImage = new BufferedImage((int) dimension.getWidth(), (int) dimension.getHeight(),
						BufferedImage.TYPE_INT_RGB);

				Graphics2D graphics2d = bufferedImage.createGraphics();

				graphics2d.setPaint(Color.white);
				graphics2d.setFont(new java.awt.Font("宋体", java.awt.Font.PLAIN, 12));

				hslfSlide.draw(graphics2d);

				graphics2d.dispose();

				Image image = Image.getInstance(bufferedImage, null);
				image.scalePercent(50f);

				// 写入单元格
				pdfPTable.addCell(new PdfPCell(image, true));
				document.add(image);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (document != null) {
					document.close();
				}
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
				if (pdfWriter != null) {
					pdfWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public static boolean ppt2pdf(InputStream in, OutputStream out) {
		Document document = null;
		HSLFSlideShow hslfSlideShow = null;
		PdfWriter pdfWriter = null;
		try {
			// 使用输入流ppt文件
			hslfSlideShow = new HSLFSlideShow(in);
			// 获取ppt文件页面
			Dimension dimension = hslfSlideShow.getPageSize();

			document = new Document();

			// pdfWriter实例
			pdfWriter = PdfWriter.getInstance(document, out);

			document.open();

			PdfPTable pdfPTable = new PdfPTable(1);

			List<HSLFSlide> hslfSlideList = hslfSlideShow.getSlides();

			for (int i = 0; i < hslfSlideList.size(); i++) {
				HSLFSlide hslfSlide = hslfSlideList.get(i);

				for (HSLFShape shape : hslfSlide.getShapes()) {

					if (shape instanceof HSLFTextShape) {
						// 设置字体, 解决中文乱码
						HSLFTextShape textShape = (HSLFTextShape) shape;

						for (HSLFTextParagraph textParagraph : textShape.getTextParagraphs()) {
							for (HSLFTextRun textRun : textParagraph.getTextRuns()) {
								textRun.setFontFamily("宋体");
							}
						}
					}

				}
				BufferedImage bufferedImage = new BufferedImage((int) dimension.getWidth(), (int) dimension.getHeight(),
						BufferedImage.TYPE_INT_RGB);

				Graphics2D graphics2d = bufferedImage.createGraphics();

				graphics2d.setPaint(Color.white);
				graphics2d.setFont(new java.awt.Font("宋体", java.awt.Font.PLAIN, 12));

				hslfSlide.draw(graphics2d);

				graphics2d.dispose();

				Image image = Image.getInstance(bufferedImage, null);
				image.scalePercent(50f);

				// 写入单元格
				pdfPTable.addCell(new PdfPCell(image, true));
				document.add(image);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (document != null) {
				document.close();
			}
			if (pdfWriter != null) {
				pdfWriter.close();
			}
			if (hslfSlideShow != null) {
				try {
					hslfSlideShow.close();
				} catch (IOException e) {
				}
			}
		}
		return true;
	}

	public static boolean pptx2pdf(InputStream in, OutputStream out) {
		Document document = null;
		XMLSlideShow slideShow = null;
		PdfWriter pdfWriter = null;
		try {
			// 使用输入流pptx文件
			slideShow = new XMLSlideShow(in);
			// 获取幻灯片的尺寸
			Dimension dimension = slideShow.getPageSize();
			// 创建一个写内容的容器
			document = new Document();
			// 使用输出流写入
			pdfWriter = PdfWriter.getInstance(document, out);
			// 使用之前必须打开<You have to open the document before you can write content.>
			document.open();

			PdfPTable pdfPTable = new PdfPTable(1);
			// 获取幻灯片
			List<XSLFSlide> slideList = slideShow.getSlides();

			for (int i = 0, row = slideList.size(); i < row; i++) {
				// 获取每一页幻灯片
				XSLFSlide slide = slideList.get(i);

				for (XSLFShape shape : slide.getShapes()) {
					// 判断是否是文本
					if (shape instanceof XSLFTextShape) {
						// 设置字体, 解决中文乱码
						XSLFTextShape textShape = (XSLFTextShape) shape;
						for (XSLFTextParagraph textParagraph : textShape.getTextParagraphs()) {
							for (XSLFTextRun textRun : textParagraph.getTextRuns()) {
								textRun.setFontFamily("宋体");
							}
						}
					}
				}

				// 根据幻灯片尺寸创建图形对象
				BufferedImage bufferedImage = new BufferedImage((int) dimension.getWidth(), (int) dimension.getHeight(),
						BufferedImage.TYPE_INT_RGB);

				Graphics2D graphics2d = bufferedImage.createGraphics();

				graphics2d.setPaint(Color.white);
				graphics2d.setFont(new java.awt.Font("宋体", java.awt.Font.PLAIN, 12));

				// 把内容写入图形对象
				slide.draw(graphics2d);

				graphics2d.dispose();

				// 封装到Image对象中
				Image image = Image.getInstance(bufferedImage, null);
				image.scalePercent(50f);

				// 写入单元格
				pdfPTable.addCell(new PdfPCell(image, true));
				document.add(image);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (document != null) {
				document.close();
			}
			if (pdfWriter != null) {
				pdfWriter.close();
			}
			if (slideShow != null) {
				try {
					slideShow.close();
				} catch (IOException e) {
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		boolean successful = false;
		// ppt to pdf
		successful = pptxToPdf("D:\\test1.pptx", "D:\\");

		// pptx to pdf
		// successful = PdfConvertUtil.pptxToPdf("D:\\360_js\\测321pt.pptx",
		// "D:\\360_js");

		System.out.println("转换" + (successful ? "成功" : "失败"));
	}

}
