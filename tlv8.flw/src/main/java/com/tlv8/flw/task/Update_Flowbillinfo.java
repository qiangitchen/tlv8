package com.tlv8.flw.task;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;

/**
 * @author ChenQian
 * @category code}更新流程单据信息
 */
@Controller
@Scope("prototype")
@SuppressWarnings("deprecation")
public class Update_Flowbillinfo extends ActionSupport {
	private String tablename;
	private String fid;
	private String billitem;
	Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/Update_Flowbillinfo")
	public Object execute() throws Exception {
		try {
			System.out.println(tablename + ":" + fid + ":" + billitem);
			data.setData(setaction(tablename, fid, billitem));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
		}
		return this;
	}

	@SuppressWarnings("resource")
	public String setaction(String tablename, String fid, String billitem)
			throws Exception {
		String result = "";
		String custercode = "";
		String custername = "";
		String fbillcode = "";
		String fmakername = "";
		Connection conn = null;
		Statement stmt = null;
		Statement stmt2 = null;
		ResultSet rs = null;
		String[] item = billitem.split(",");
		if (item.length < 3) {
			result = "参数个数不正确或者所需参数为空！";
			System.out.println(result);
			return result;
		}

		String sql = "select " + billitem + " from " + tablename
				+ " where fid = '" + fid + "'";
		String tsql = "select fMAKERNAME from " + tablename + " where fid ='"
				+ fid + "'";
		String upsql = "";
		String nmsql = "";
		String upmsql = "";
		conn = DBUtils.getAppConn("system");
		try {
			stmt = conn.createStatement();
			try {
				// System.out.println(sql);
				rs = stmt.executeQuery(sql);
				if (!rs.next()) {
					result = "找不到单据信息！";
					System.out.println(result);
					return result;
				} else {
					custercode = rs.getString(item[0]);
					custername = rs.getString(item[1]);
					fbillcode = rs.getString(item[2]);
					System.out.println(custercode + "-" + custername + "-"
							+ fbillcode);
					upsql = "update dysys.sa_task set sProjectName ='"
							+ custercode + "',sCustomerName ='" + custername
							+ "',sPlanName ='" + fbillcode
							+ "' where sdata1 = '" + fid + "'";
					nmsql = "update dysys.sa_task set sname = sname||'["
							+ custercode + "," + custername + "," + fbillcode
							+ "]' where sdata1 = '" + fid
							+ "' and sname not like '%[%'";
					stmt2 = conn.createStatement();
					try {
						// stmt = conn.createStatement();
						// System.out.println(upsql);
						stmt2.executeUpdate(upsql);
						// System.out.println(nmsql);
						stmt2.executeUpdate(nmsql);
						rs = stmt2.executeQuery(tsql);
						if (!rs.next()) {
							result = "找不到单据制单人信息！";
							System.out.println(result);
							return result;
						} else {
							fmakername = rs.getString("fMAKERNAME");
							upmsql = "update dysys.sa_task set SFMAKERNAME ='"
									+ fmakername + "' where sdata1 = '" + fid
									+ "'";
							// System.out.println(upmsql);
							stmt2.executeUpdate(upmsql);
						}
					} catch (Exception e) {
						result = "执行失败!原因:" + e.toString();
						System.out.println(result);
						return result;
					} finally {
						DBUtils.closeConn(null, stmt2, null);
					}
				}
			} finally {
				DBUtils.closeConn(null, stmt, rs);
			}

		} catch (Exception e) {
			result = "获取单据信息失败!原因:" + e.toString();
			System.out.println(result);
			return result;
		} finally {
			DBUtils.closeConn(conn, null, null);
		}
		return result;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		try {
			this.tablename = URLDecoder.decode(tablename, "UTF-8");
		} catch (Exception e) {
		}
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		try {
			this.fid = URLDecoder.decode(fid, "UTF-8");
		} catch (Exception e) {
		}
	}

	public String getBillitem() {
		return billitem;
	}

	public void setBillitem(String billitem) {
		try {
			this.billitem = URLDecoder.decode(billitem, "UTF-8");
		} catch (Exception e) {
		}
	}

}
