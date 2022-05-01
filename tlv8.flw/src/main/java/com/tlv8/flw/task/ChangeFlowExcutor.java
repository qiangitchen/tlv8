package com.tlv8.flw.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;
import com.tlv8.flw.bean.FlowDataBean;
import com.tlv8.system.utils.OrgUtils;

/**
 * 修改执行人
 * 
 * @author 陈乾
 * @update 2021-04-01
 */
@Controller
@Scope("prototype")
public class ChangeFlowExcutor extends FlowDataBean {
	private Data data = new Data();

	public Data getData() {
		return this.data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@ResponseBody
	@RequestMapping("/ChangeFlowExcutorAction")
	public Object execute() throws Exception {
		Sys.printMsg("修改执行人...");
		if (epersonids != null && taskID != null) {
			SqlSession session = DBUtils.getSession("system");
			Connection conn = session.getConnection();
			PreparedStatement ps = null;
			try {
				OrgUtils euser = new OrgUtils(epersonids);
				StringBuilder sqlStr = new StringBuilder();
				sqlStr.append("update SA_TASK set ");
				sqlStr.append("SEPERSONID=?,SEPERSONCODE=?,SEPERSONNAME=?");
				sqlStr.append(",SEPOSID=?,SEPOSCODE=?,SEPOSNAME=?");
				sqlStr.append(",SEDEPTID=?,SEDEPTCODE=?,SEDEPTNAME=?");
				sqlStr.append(",SEOGNID=?,SEOGNCODE=?,SEOGNNAME=?");
				sqlStr.append(",SEFID=?,SEFNAME=?,SCREATETIME=?");
				sqlStr.append(" where sID = ?");
				String sql = sqlStr.toString();
				ps = conn.prepareStatement(sql);
				ps.setString(1, euser.getPersonID());
				ps.setString(2, euser.getPersonCode());
				ps.setString(3, euser.getPersonName());
				ps.setString(4, euser.getPositionID());
				ps.setString(5, euser.getPositionCode());
				ps.setString(6, euser.getPositionName());
				ps.setString(7, euser.getDeptID());
				ps.setString(8, euser.getDeptCode());
				ps.setString(9, euser.getDeptName());
				ps.setString(10, euser.getOgnID());
				ps.setString(11, euser.getOgnCode());
				ps.setString(12, euser.getOgnName());
				ps.setString(13, euser.getPersonFullID());
				ps.setString(14, euser.getPersonFullName());
				ps.setTimestamp(15, new Timestamp(new Date().getTime()));
				ps.setString(16, taskID);
				ps.executeUpdate();
				session.commit(true);
				data.setFlag("true");
			} catch (Exception e) {
				session.rollback(true);
				data.setFlag("false");
				data.setMessage(e.toString());
				e.printStackTrace();
			} finally {
				DBUtils.CloseConn(session, conn, ps, null);
			}
		}
		return this;
	}

}
