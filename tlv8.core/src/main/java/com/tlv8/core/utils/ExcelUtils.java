package com.tlv8.core.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelUtils {
	public static void main(String[] args) {
		try {
			System.out.println(readSheet(
					getWorkbook(new File(
							"E:\\TuLinv8_win64_jcycg\\workspace\\tlv8\\WebContent\\purchase\\project1\\合同货物清单.xlsx")),
					0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Workbook getWorkbook(MultipartFile excel) throws Exception {
		String[] split = excel.getOriginalFilename().toLowerCase().split("\\.");
		Workbook wb = null;
		// 根据文件后缀（xls/xlsx）进行判断
		if ("xls".equals(split[1])) {
			InputStream fis = excel.getInputStream(); // 文件流对象
			wb = new HSSFWorkbook(fis);
		} else if ("xlsx".equals(split[1])) {
			wb = new XSSFWorkbook(excel.getInputStream());
		} else {
			throw new Exception("文件类型错误!");
		}
		return wb;
	}

	public static Workbook getWorkbook(File excel) throws Exception {
		String[] split = excel.getName().toLowerCase().split("\\.");
		Workbook wb = null;
		// 根据文件后缀（xls/xlsx）进行判断
		if ("xls".equals(split[1])) {
			FileInputStream fis = new FileInputStream(excel); // 文件流对象
			wb = new HSSFWorkbook(fis);
		} else if ("xlsx".equals(split[1])) {
			wb = new XSSFWorkbook(excel);
		} else {
			throw new Exception("文件类型错误!");
		}
		return wb;
	}
	
	public static Workbook getWorkbook(InputStream fis,String filename) throws Exception {
		String[] split = filename.toLowerCase().split("\\.");
		Workbook wb = null;
		// 根据文件后缀（xls/xlsx）进行判断
		if ("xls".equals(split[1])) {
			wb = new HSSFWorkbook(fis);
		} else if ("xlsx".equals(split[1])) {
			wb = new XSSFWorkbook(fis);
		} else {
			throw new Exception("文件类型错误!");
		}
		return wb;
	}

	public static String[][] readSheet(Workbook workbook, int sheetNum) {
		String[][] result;
		Sheet sheet = getSheet(workbook, sheetNum);
		int rows, cols;
		rows = sheet.getLastRowNum();
		cols = sheet.getRow(0).getLastCellNum();
		result = new String[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				try {
					result[i][j] = getCellValue(sheet, j, i);
				} catch (Exception e) {
					result[i][j] = "";// 取值异常默认为空字符
				}
			}
		}
		return result;
	}

	public static Sheet getSheet(Workbook workbook, int sheetNum) {
		Sheet sheet = null;
		sheet = workbook.getSheetAt(sheetNum);
		return sheet;
	}

	public static String getCellValue(Sheet sheet, int col, int row) {
		String result = "";
		Cell cell = sheet.getRow(row).getCell(col);
		result = cell == null ? "" : cell.toString();
		DateFormat dataTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			dataTimeFormat.parse(result);
			Date tempTime = dataTimeFormat.parse(result);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sss = formatter.format(tempTime);
			result = sss;
		} catch (Exception e) {
			dataTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			try {
				dataTimeFormat.parse(result);
				Date tempTime = dataTimeFormat.parse(result);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sss = formatter.format(tempTime);
				result = sss;
			} catch (Exception er) {
				DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					dataFormat.parse(result);
					Date tempTime = dataTimeFormat.parse(result);
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					String sss = formatter.format(tempTime);
					result = sss;
				} catch (Exception de) {
					dataFormat = new SimpleDateFormat("MM/dd/yyyy");
					try {
						dataFormat.parse(result);
						Date tempTime = dataTimeFormat.parse(result);
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						String sss = formatter.format(tempTime);
						result = sss;
					} catch (Exception der) {
					}
				}
			}
		}
		return result;
	}
}
