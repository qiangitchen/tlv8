package com.tlv8.doc.svr.core.text.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.NumberFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import com.tlv8.doc.svr.core.text.TextReader;

public class ExcelReader extends TextReader {

	public ExcelReader(File file, String extName) {
		super(file, extName);
	}

	@SuppressWarnings("resource")
	@Override
	public String readAll() {
		StringBuffer result = new StringBuffer();
		try {
			if (".xlsx".equals(extName)) {
				InputStream is = new FileInputStream(file);
				XSSFWorkbook workbook = new XSSFWorkbook(is);
				for (int numSheets = 0; numSheets < workbook
						.getNumberOfSheets(); numSheets++) {
					if (null != workbook.getSheetAt(numSheets)) {
						XSSFSheet aSheet = workbook.getSheetAt(numSheets);// 获得一个sheet
						for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet
								.getLastRowNum(); rowNumOfSheet++) {
							if (null != aSheet.getRow(rowNumOfSheet)) {
								XSSFRow aRow = aSheet.getRow(rowNumOfSheet); // 获得一个行
								for (short cellNumOfRow = 0; cellNumOfRow <= aRow
										.getLastCellNum(); cellNumOfRow++) {
									if (null != aRow.getCell(cellNumOfRow)) {
										XSSFCell aCell = aRow
												.getCell(cellNumOfRow);// 获得列值
										if (this.convertCell(aCell).length() > 0) {
											result.append(this
													.convertCell(aCell));
										}
									}
									result.append("\n");
								}
							}
						}
					}
				}
				is.close();
			} else {
				HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
						file));// 创建对Excel工作簿文件的引用
				for (int numSheets = 0; numSheets < workbook
						.getNumberOfSheets(); numSheets++) {
					if (null != workbook.getSheetAt(numSheets)) {
						HSSFSheet aSheet = workbook.getSheetAt(numSheets);// 获得一个sheet
						for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet
								.getLastRowNum(); rowNumOfSheet++) {
							if (null != aSheet.getRow(rowNumOfSheet)) {
								HSSFRow aRow = aSheet.getRow(rowNumOfSheet); // 获得一个行
								for (short cellNumOfRow = 0; cellNumOfRow <= aRow
										.getLastCellNum(); cellNumOfRow++) {
									if (null != aRow.getCell(cellNumOfRow)) {
										HSSFCell aCell = aRow
												.getCell(cellNumOfRow);// 获得列值
										if (this.convertCell(aCell).length() > 0) {
											result.append(this
													.convertCell(aCell));
										}
									}
									result.append("\n");
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}
		return result.toString();
	}

	private String convertCell(Cell cell) {
		NumberFormat formater = NumberFormat.getInstance();
		formater.setGroupingUsed(false);
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		CellType cellType = cell.getCellType();
		if(cellType == CellType.NUMERIC) {
			cellValue = formater.format(cell.getNumericCellValue());
		}else if(cellType == CellType.STRING) {
			cellValue = cell.getStringCellValue();
		}else if(cellType == CellType.BLANK){
			cellValue = cell.getStringCellValue();
		}else if(cellType == CellType.BOOLEAN){
			cellValue = Boolean.valueOf(cell.getBooleanCellValue()).toString();
		}else if(cellType == CellType.ERROR) {
			cellValue = String.valueOf(cell.getErrorCellValue());
		}else{
			cellValue = "";
		}
		return cellValue.trim();
	}

}
