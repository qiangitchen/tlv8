package com.tlv8.core.report;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@SuppressWarnings({ "rawtypes" })
public class ExportExcel {

	public static void exportExcel(OutputStream os, Object objOut)
			throws Exception {
		@SuppressWarnings("unused")
		InputStream intfile = null;
		try {
			// 取得response HttpServletResponse
			HashMap hmOut = (HashMap) objOut;

			// 设置table列名
			String excelName = (String) hmOut.get("excelName");
			String[] excelNameArray = excelName.split(",");

			// 取得key
			String[] excelKeyArray = (String[]) hmOut.get("excelKey");

			WritableWorkbook wwb = Workbook.createWorkbook(os); // 建立excel文件

			int ii = wwb.getNumberOfSheets();
			WritableSheet ws = wwb.createSheet("Sheet" + Integer.toString(ii),
					ii); // 创建一个工作表

			// 设置单元格的文字格式
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLUE);
			WritableCellFormat wcf = new WritableCellFormat(wf);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf.setAlignment(Alignment.CENTRE);
			ws.setRowView(0, 500);

			// 填充数据的内容
			int len = ((String[]) hmOut.get(excelKeyArray[0])).length;
			// 设置列头名
			for (int j = 0; j < excelKeyArray.length; j++) {
				ws.addCell(new Label(j, 0, excelNameArray[j], wcf));
			}
			// 设置内容
			wcf = new WritableCellFormat();
			for (int i = 0; i < len; i++) {
				for (int j = 0; j < excelKeyArray.length; j++) {
					String dataValue = ((String[]) hmOut.get(excelKeyArray[j]))[i];
					// System.out.println(dataValue);
					ws.addCell(new Label(j, i + 1, dataValue, wcf));
				}
			}
			wwb.write();
			wwb.close();

		} catch (IOException e) {
			throw new IOException(e);
		} catch (RowsExceededException e) {
			throw new RowsExceededException();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

}