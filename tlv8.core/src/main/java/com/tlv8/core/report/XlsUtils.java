package com.tlv8.core.report;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tlv8.base.Sys;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 
 * @author 陈乾
 *
 */
public class XlsUtils {

	public static void main(String[] args) throws Exception {
		 XlsUtils.test();
	}
	
	private static void printlntest(String value)
			throws UnsupportedEncodingException {
		System.out.println(value);
	}

	public static String test() throws Exception {
		String methodName = "com.justep.dyscm.xlsio.Utils.test";
		Sys.printMsg(String.format("%s()", methodName));

		String fileName = "D:\\excel读写测试.xls";
		Sys.printMsg(String.format("输出到:%s", fileName));

		// 生成一个xls文件
		WritableWorkbook wb = XlsUtils.createWorkbook(fileName);
		int i = wb.getNumberOfSheets();
		WritableSheet sheet1 = XlsUtils.createSheet(wb, "Sheet"
				+ Integer.toString(i), i);
		XlsUtils.writeString(sheet1, 0, 0, "标题1");
		XlsUtils.writeString(sheet1, 1, 0, "标题2");
		XlsUtils.writeString(sheet1, 0, 1, "字符串1");
		XlsUtils.writeNum(sheet1, 1, 1, 77);

		WritableSheet sheet2 = XlsUtils.createSheet(wb, "Sheet"
				+ Integer.toString(i + 1), i + 1);
		XlsUtils.writeString(sheet2, 0, 0, "标题1");
		XlsUtils.writeString(sheet2, 1, 0, "标题2");
		XlsUtils.writeString(sheet2, 0, 1, "字符串1");
		XlsUtils.writeNum(sheet2, 3, 1, 88);

		wb.write();
		wb.close();

		// 读取刚才生成的xls文件
		Workbook rb = XlsUtils.getWorkbook(fileName);
		Sheet rs1 = XlsUtils.getSheet(rb, i);

		XlsUtils.printlntest(String.format("sheet1-cells[0,0]=%s", XlsUtils
				.getCellValue(rs1, 0, 0)));
		XlsUtils.printlntest(String.format("sheet1-cells[1,0]=%s", XlsUtils
				.getCellValue(rs1, 1, 0)));
		XlsUtils.printlntest(String.format("sheet1-cells[0,1]=%s", XlsUtils
				.getCellValue(rs1, 0, 1)));
		XlsUtils.printlntest(String.format("sheet1-cells[1,1]=%s", XlsUtils
				.getCellValue(rs1, 1, 1)));
		Sheet rs2 = XlsUtils.getSheet(rb, i + 1);
		XlsUtils.printlntest(String.format("sheet2-cells[0,0]=%s", XlsUtils
				.getCellValue(rs2, 0, 0)));
		XlsUtils.printlntest(String.format("sheet2-cells[1,0]=%s", XlsUtils
				.getCellValue(rs2, 1, 0)));
		XlsUtils.printlntest(String.format("sheet2-cells[0,1]=%s", XlsUtils
				.getCellValue(rs2, 0, 1)));

		rb.close();
		return methodName;
	}

	// 获得一个可读的Workbook
	public static Workbook getWorkbook(String fileName) throws Exception {
		Workbook result = null;
		result = Workbook.getWorkbook(new File(fileName));
		return result;
	}

	// 获得一个可读的Workbook
	public static Workbook getWorkbook(InputStream inputs) throws Exception {
		Workbook result = null;
		result = Workbook.getWorkbook(inputs);
		return result;
	}

	// 获得一个可读的Workbook
	public static Workbook getWorkbook(File file) throws Exception {
		Workbook result = null;
		result = Workbook.getWorkbook(file);
		return result;
	}

	public static Sheet getSheet(Workbook workbook, int sheetNum) {
		Sheet sheet = null;
		sheet = workbook.getSheet(sheetNum);
		return sheet;
	}

	// @ChenQain TODO:获取Excel值
	public static String getCellValue(Sheet sheet, int col, int row) {
		String result = "";
		Cell cell = sheet.getCell(col, row);
		result = cell == null ? "" : cell.getContents();
		DateFormat dataTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			dataTimeFormat.parse(result);
			Date tempTime = dataTimeFormat.parse(result);
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String sss = formatter.format(tempTime);
			result = sss;
		} catch (Exception e) {
			dataTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			try {
				dataTimeFormat.parse(result);
				Date tempTime = dataTimeFormat.parse(result);
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String sss = formatter.format(tempTime);
				result = sss;
			} catch (Exception er) {
				DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					dataFormat.parse(result);
					Date tempTime = dataTimeFormat.parse(result);
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd");
					String sss = formatter.format(tempTime);
					result = sss;
				} catch (Exception de) {
					dataFormat = new SimpleDateFormat("MM/dd/yyyy");
					try {
						dataFormat.parse(result);
						Date tempTime = dataTimeFormat.parse(result);
						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd");
						String sss = formatter.format(tempTime);
						result = sss;
					} catch (Exception der) {
					}
				}
			}
		}
		return result;
	}

	public static String[][] readSheet(Workbook workbook, int sheetNum) {
		String[][] result;
		Sheet sheet = XlsUtils.getSheet(workbook, sheetNum);
		int rows, cols;
		cols = sheet.getColumns();
		rows = sheet.getRows();
		result = new String[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				result[i][j] = XlsUtils.getCellValue(sheet, j, i);
			}
		}
		return result;
	}

	// 创建一个可写的Workbook
	public static WritableWorkbook createWorkbook(String fileName)
			throws Exception, IOException {
		WritableWorkbook result = null;
		Workbook originalBook = null;
		File file = new File(fileName);
		if (file.exists()) {
			originalBook = Workbook.getWorkbook(file);
			result = Workbook.createWorkbook(file, originalBook);
		} else
			result = Workbook.createWorkbook(file);
		return result;
	}

	public static WritableSheet createSheet(WritableWorkbook workbook,
			String sheetName, int sheetNum) {
		WritableSheet sheet = null;
		sheet = workbook.createSheet(sheetName, sheetNum);
		return sheet;
	}

	public static void writeString(WritableSheet sheet, int col, int row,
			String value) throws Exception {
		jxl.write.Label label = new jxl.write.Label(col, row, value);
		sheet.addCell(label);
	}

	public static void writeNum(WritableSheet sheet, int col, int row,
			double value) throws Exception {
		jxl.write.Number num = new jxl.write.Number(col, row, value);
		sheet.addCell(num);
	}

	public static String trimSpace(String value) {
		String result = value;
		// 导出的excel数据含有下面这种空格
		result = result.replaceAll("　", "");
		result = result.replaceAll(" ", "");
		return result;
	}

	public static int colName2Index(String colName) throws Exception {
		int result = -1;
		String cname = colName.toUpperCase();
		if (cname.length() < 1 || cname.length() > 2)
			throw new Exception(String
					.format("列名称错误, 应该是A-ZZ, 实际: %s", colName));
		if (cname.length() == 1) {
			char c = cname.charAt(0);
			result = (int) c - (int) 'A';
		} else {
			char c1 = cname.charAt(0);
			char c2 = cname.charAt(1);
			result = ((int) c1 - (int) 'A' + 1) * 26 + (int) c2 - (int) 'A';
		}
		return result;
	}

	public static boolean StrIsEmpty(String value) {
		String s1 = value;
		// 导出的excel数据含有下面这种空格
		s1 = s1.replaceAll("　", "");
		s1 = s1.replaceAll(" ", "");

		return s1.equalsIgnoreCase("");
	}

}
