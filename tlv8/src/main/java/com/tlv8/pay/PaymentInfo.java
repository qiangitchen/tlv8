package com.tlv8.pay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.ibatis.session.SqlSession;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.IDUtils;
import com.tlv8.code.CodeUtils;

/**
 * 支付订单信息
 * @author 陈乾
 */
public class PaymentInfo {
	private static String addSql = "insert into PaymentInfo(fID,fEledeId,fOrderCode,fOrderName,money,fRemark,fState,fReturnText,version,UserId,UserName,modelId)values(?,?,?,?,?,?,?,?,?,?,?,?)";
	private static String upSql = "update PaymentInfo set fEledeId=?,fOrderCode=?,fOrderName=?,money=?,fRemark=?,fState=?,fReturnText=?,version=? where fID = ?";
	private static String codeQuerySql = "select fID,fEledeId,fOrderCode,fOrderName,money,fRemark,fState,fReturnText,version,UserId,UserName,modelId from PaymentInfo where fOrderCode=?";
	private String fID = IDUtils.getGUID();
	private String fEledeId;
	private String fOrderCode = CodeUtils.getOrderCode();
	private String fOrderName;
	private double money;
	private String fRemark;
	private int fState = 0;
	private String fReturnText;
	private int version = 0;
	private String UserId;
	private String UserName;
	private int modelId;// 33:在线课程,32:离线视频,99:钱包充值

	public PaymentInfo() {
	}

	public PaymentInfo(String fOrderCode) throws Exception {
		SqlSession session = DBUtils.getSession("oa");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(codeQuerySql);
			ps.setString(1, fOrderCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				this.fID = rs.getString(1);
				this.fEledeId = rs.getString(2);
				this.fOrderCode = rs.getString(3);
				this.fOrderName = rs.getString(4);
				this.money = rs.getDouble(5);
				this.fRemark = rs.getString(6);
				this.fState = rs.getInt(7);
				this.fReturnText = rs.getString(8);
				this.fState = rs.getInt(9);
				this.UserId = rs.getString(10);
				this.UserName = rs.getString(11);
				this.modelId = rs.getInt(12);
			} else {
				throw new Exception("指定的单号不存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session,conn, ps, rs);
		}

	}

	public void Insert() {
		SqlSession session = DBUtils.getSession("oa");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(addSql);
			ps.setString(1, fID);
			ps.setString(2, fEledeId);
			ps.setString(3, fOrderCode);
			ps.setString(4, fOrderName);
			ps.setDouble(5, money);
			ps.setString(6, fRemark);
			ps.setInt(7, fState);
			ps.setString(8, fReturnText);
			ps.setInt(9, version);
			ps.setString(10, UserId);
			ps.setString(11, UserName);
			ps.setInt(12, modelId);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session,conn, ps, null);
		}
	}

	public void Update() {
		SqlSession session = DBUtils.getSession("oa");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(upSql);
			ps.setString(1, fEledeId);
			ps.setString(2, fOrderCode);
			ps.setString(3, fOrderName);
			ps.setDouble(4, money);
			ps.setString(5, fRemark);
			ps.setInt(6, fState);
			ps.setString(7, fReturnText);
			ps.setInt(8, version);
			ps.setString(9, fID);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session,conn, ps, null);
		}
	}

	public void Delete() {
		try {
			DBUtils.execdeleteQuery("edu", "delete from dbo.Edu_PaymentInfo where fID = '" + fID + "'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getfID() {
		return fID;
	}

	public void setfID(String fID) {
		this.fID = fID;
	}

	public String getfEledeId() {
		return fEledeId;
	}

	public void setfEledeId(String fEledeId) {
		this.fEledeId = fEledeId;
	}

	public String getfOrderCode() {
		return fOrderCode;
	}

	public void setfOrderCode(String fOrderCode) {
		this.fOrderCode = fOrderCode;
	}

	public String getfOrderName() {
		return fOrderName;
	}

	public void setfOrderName(String fOrderName) {
		this.fOrderName = fOrderName;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getfRemark() {
		return fRemark;
	}

	public void setfRemark(String fRemark) {
		this.fRemark = fRemark;
	}

	public int getfState() {
		return fState;
	}

	public void setfState(int fState) {
		this.fState = fState;
	}

	public String getfReturnText() {
		return fReturnText;
	}

	public void setfReturnText(String fReturnText) {
		this.fReturnText = fReturnText;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getUserId() {
		return UserId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public int getModelId() {
		return modelId;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserName() {
		return UserName;
	}

}
