package com.tlv8.oa.mail;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

/**
 * 保存 发送 邮件
 */
@Controller
@Scope("prototype")
public class SendMailAction extends ActionSupport {
	private Data data = new Data();
	private String fconsigneeid;
	private String fconsigneecode;
	private String fconsignee;
	private String femailname;
	private String fjinfo;
	private String ftext;
	private String actype;
	private String rowid;

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/sendMailAction")
	public Object execute() throws Exception {
		String newid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
		String cuID = newid;
		ContextBean context = ContextBean.getContext(request);
		String personid = context.getPersonID();
		String personcode = context.getPersonCode();
		String personname = context.getPersonName();
		String ognname = context.getCurrentOgnName();
		String deptname = context.getCurrentDeptName();
		String sql = "insert into OA_EM_SendEmail(FID,VERSION,FSTATE,FCREATTIME,"
				+ "FEMAILNAME,FCONSIGNEEID,FCONSIGNEECODE,FCONSIGNEE,FTEXT,"
				+ "FSENDPERID,FSENDPERCODE,FSENDPERNAME,FSENDOGN,FSENDDEPT,FFJID) " + "values('" + newid
				+ "',0,'已保存',?," + "?,?,?,?,?," + "?,?,?,?,?,?)";
		String uSql = "update OA_EM_SendEmail set FEMAILNAME = ?," + "FCONSIGNEEID = ?,FCONSIGNEECODE=?,FCONSIGNEE=?,"
				+ "FTEXT=? where FID = ?";
		SqlSession session = DBUtils.getSession("oa");
		Connection conn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			boolean ishave = false;
			if (rowid != null && !"".equals(rowid)) {
				ishave = true;
				cuID = rowid;
			}
			setRowid(cuID);
			if (ishave) {
				ps = conn.prepareStatement(uSql);
				ps.setString(1, femailname);
				ps.setString(2, fconsigneeid);
				ps.setString(3, fconsigneecode);
				ps.setString(4, fconsignee);
				ps.setString(5, ftext);
				ps.setString(6, rowid);
			} else {
				ps = conn.prepareStatement(sql);
				ps.setTimestamp(1, new Timestamp(new Date().getTime()));
				ps.setString(2, femailname);
				ps.setString(3, fconsigneeid);
				ps.setString(4, fconsigneecode);
				ps.setString(5, fconsignee);
				ps.setString(6, ftext);

				ps.setString(7, personid);
				ps.setString(8, personcode);
				ps.setString(9, personname);
				ps.setString(10, ognname);
				ps.setString(11, deptname);
				ps.setString(12, fjinfo);
			}
			ps.executeUpdate();
			conn.commit();
			if ("send".equals(actype)) {
				String[] revicePerID = fconsigneeid.split(",");
				String[] revicePerName = fconsignee.split(",");
				String sSql = "";
				if (ishave) {
					sSql = "insert into OA_EM_ReceiveEmail(" + "FID,VERSION,FSENDTIME,"
							+ "FEMAILNAME,FCONSIGNEEID,FCONSIGNEECODE,FCONSIGNEE,FTEXT,FREPLYSTATE,FQUREY,"
							+ "FSENDPERID,FSENDPERCODE,FSENDPERNAME,FSENDOGN,FSENDDEPT,FFJID) (select "
							+ "?,0,?,?,?,?,?,?,'未回复','未查看'," + "?,?,?,?,?,FFJID FROM OA_EM_SENDEMAIL WHERE FID ='"
							+ rowid + "')";
				} else {
					sSql = "insert into OA_EM_ReceiveEmail(" + "FID,VERSION,FSENDTIME,"
							+ "FEMAILNAME,FCONSIGNEEID,FCONSIGNEECODE,FCONSIGNEE,FTEXT,FREPLYSTATE,FQUREY,"
							+ "FSENDPERID,FSENDPERCODE,FSENDPERNAME,FSENDOGN,FSENDDEPT,FFJID) values ("
							+ "?,0,?,?,?,?,?,?,'未回复','未查看'," + "?,?,?,?,?,'" + fjinfo + "')";
				}
				int sendCount = revicePerID.length;
				for (int i = 0; i < sendCount; i++) {
					ps = conn.prepareStatement(sSql);
					ps.setString(1, UUID.randomUUID().toString().toUpperCase().replaceAll("-", ""));
					ps.setTimestamp(2, new Timestamp(new Date().getTime()));
					ps.setString(3, femailname);
					ps.setString(4, revicePerID[i]);
					ps.setString(5, "");
					ps.setString(6, revicePerName[i]);
					ps.setString(7, ftext);
					ps.setString(8, personid);
					ps.setString(9, personcode);
					ps.setString(10, personname);
					ps.setString(11, ognname);
					ps.setString(12, deptname);
					ps.executeUpdate();
					DBUtils.closeConn(null, null, ps, null);
				}
				String sendSql = "update OA_EM_SendEmail set FSENDTIME=? ,FSTATE='已发送' where FID =?";
				PreparedStatement ps1 = conn.prepareStatement(sendSql);
				ps1.setTimestamp(1, new Timestamp(new Date().getTime()));
				ps1.setString(2, cuID);
				ps1.executeUpdate();
				ps1.close();
				conn.commit();
			}
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			conn.rollback();
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, st, rs);
		}
		return super.execute();
	}

	public String getFconsigneeid() {
		return fconsigneeid;
	}

	public void setFconsigneeid(String fconsigneeid) {
		try {
			this.fconsigneeid = URLDecoder.decode(fconsigneeid, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getFconsigneecode() {
		return fconsigneecode;
	}

	public void setFconsigneecode(String fconsigneecode) {
		try {
			this.fconsigneecode = URLDecoder.decode(fconsigneecode, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getFconsignee() {
		return fconsignee;
	}

	public void setFconsignee(String fconsignee) {
		try {
			this.fconsignee = URLDecoder.decode(fconsignee, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getFemailname() {
		return femailname;
	}

	public void setFemailname(String femailname) {
		try {
			this.femailname = URLDecoder.decode(femailname, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getFtext() {
		return ftext;
	}

	public void setFtext(String ftext) {
		try {
			this.ftext = URLDecoder.decode(ftext, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getActype() {
		return actype;
	}

	public void setActype(String actype) {
		this.actype = actype;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getRowid() {
		return rowid;
	}

	public void setFjinfo(String fjinfo) {
		try {
			this.fjinfo = URLDecoder.decode(fjinfo, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getFjinfo() {
		return fjinfo;
	}

}
