package com.tlv8.core.grid;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.apache.poi.ss.usermodel.Workbook;
import org.dom4j.Element;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.helper.DataTypeHelper;
import com.tlv8.base.utils.IDUtils;
import com.tlv8.core.report.MappingConfig;
import com.tlv8.core.utils.ExcelUtils;

@Controller
@RequestMapping("/core")
@Scope("prototype")
public class GridImportController extends GridCoreController {
	private int startLine = 1;// 开始行
	private int endLine = -1;// 结束行
	private String dbkey;// 数据连接
	private String table;// 表名
	private String relation;// 字段
	private String useNormal;// 是否使用 默认配置 impConfirm.xml
	private Element config;
	private HttpServletRequest request;

	String inputpage = "/common/gridReport/import-compent";
	String successpage = "/common/gridReport/import-success";

	@RequestMapping(value = "/importGridData", method = RequestMethod.POST)
	public Object execute(@RequestParam("upload") MultipartFile file, HttpServletRequest request, int startLine,
			int endLine, String srcPath, String dbkey, String table, String relation, String confirmXmlName,
			String useNormal) {
		String expStatus = "";
		try {
			String fileName = file.getOriginalFilename();
			if (fileName == null || "".equals(fileName)) {
				expStatus = "导入失败：未选择文件或文件类型不正确！";
				request.setAttribute("expStatus", expStatus);
				return inputpage;
			} else if (fileName.toLowerCase().indexOf("xls") < 0 && !"".equals(fileName)) {
				expStatus = "导入失败：文件类型不正确！正确的导入文件为Excel";
				request.setAttribute("expStatus", expStatus);
				return inputpage;
			}
			this.request = request;
			this.startLine = startLine;
			this.endLine = endLine;
			this.dbkey = dbkey;
			this.table = table;
			this.relation = deCode(relation);
			srcPath = deCode(srcPath);
			confirmXmlName = deCode(confirmXmlName);
			confirmXmlName = (confirmXmlName == null || "".equals(confirmXmlName)) ? "impConfirm.xml" : confirmXmlName;
			if (srcPath == null || "undefined".equals(srcPath)) {
				srcPath = "";
			} else {
				srcPath = srcPath.replace(request.getContextPath(), "");
				String cPath = request.getServletContext().getRealPath(srcPath + "/" + confirmXmlName);
				try {
					config = MappingConfig.getConfig(cPath);
				} catch (Exception e) {
					Sys.printMsg(e);
				}
			}
		} catch (Exception e) {
			Sys.printErr(e);
			e.printStackTrace();
		}
		try {
			impInit(file.getInputStream(), file.getOriginalFilename());// 开始导入操作
		} catch (Exception e) {
			expStatus = e.getMessage();
			request.setAttribute("expStatus", expStatus);
			return inputpage;
		}
		expStatus = "导入完成";
		return successpage;
	}

	private void impInit(InputStream ins, String filename) throws Exception {
		String[][] page0 = null;
		Workbook wb1;
		try {
			wb1 = ExcelUtils.getWorkbook(ins, filename);
			page0 = ExcelUtils.readSheet(wb1, 0);
			WriteData(page0);
		} catch (Exception e) {
			Sys.packErrMsg(e.getMessage());
			throw new Exception(Sys.packErrMsg(String.format("导入数据时出错, 详细: %s", e.getMessage())));
		}
	}

	// 写数据操作
	private void WriteData(String[][] condata) throws Exception {
		String AllimpCells = getCells();
		if (dbkey == null || "".equals(dbkey))
			dbkey = "system";
		if ("true".equals(useNormal)) {
			startLine = MappingConfig.getAttributeInt(config, "row", "start") - 1;
			endLine = MappingConfig.getAttributeInt(config, "row", "end");
		} else {
			startLine = (startLine > 1) ? startLine - 1 : 0;
			endLine = (endLine == -1) ? condata.length : endLine - 1;
		}
		if (startLine == -9999 || endLine == -9999) {
			throw new Exception("导入数据时出错,数据行配置错误!");
		}
		int okcout = (endLine == -1) ? (condata.length + 1) - (startLine - 1) : endLine - startLine;
		request.setAttribute("totalCount", okcout);
		table = (table != null && !"".equals(table)) ? table
				: MappingConfig.getAttributeValue(config, "concept", "name");
		if (table == null || "".equals(table)) {
			throw new Exception("导入数据时出错,需要导入表名未配置或配置错误!");
		}
		endLine = (endLine == -1) ? condata.length : endLine - 1;
		String rowKey = ("system".equals(dbkey)) ? "sID" : "fID";
		String[] cells = AllimpCells.split(",");
		SqlSession session = DBUtils.getSession(dbkey);
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = session.getConnection();
			Map<String, String> dataTypes = DataTypeHelper.getColumnsDataType(conn, table, cells);
			for (int i = startLine; i <= endLine; i++) {
				String fID = IDUtils.getGUID();
				String sql = "insert into " + table + "(" + rowKey + "," + AllimpCells + ",version)values('" + fID
						+ "'";
				for (int n = 0; n < cells.length; n++) {
					sql += ",?";
				}
				sql += ",0)";
				ps = conn.prepareStatement(sql);
				for (int n = 0; n < cells.length; n++) {
					String dataType = dataTypes.get(cells[n]);
					praperTimeVal(ps, n + 1, condata[i][n], dataType);
				}
				ps.executeUpdate();
			}
			session.commit(true);
		} catch (Exception e) {
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, conn, ps, null);
		}
		// System.out.println(okcout);
		request.setAttribute("fokCount", okcout);
	}

	// 获取导入字段
	private String getCells() throws Exception {
		String drelation = relation;
		if ("true".equals(useNormal)) {
			drelation = MappingConfig.getAttributeValue(config, "relation", "name");
			if (drelation == null || "".equals(drelation)) {
				drelation = relation;
			}
		} else {
			drelation = relation;
		}
		if ("".equals(drelation) || drelation == null) {
			throw new Exception("导入数据时出错,列名未配置或配置错误!");
		}
		String result = "";
		String[] cell = drelation.split(",");
		for (int i = 0; i < cell.length; i++) {
			if (!"No".equals(cell[i]) && !"master_check".equals(cell[i])) {
				result += "," + cell[i];
			}
		}
		result = result.replaceFirst(",", "");
		return result;
	}

}