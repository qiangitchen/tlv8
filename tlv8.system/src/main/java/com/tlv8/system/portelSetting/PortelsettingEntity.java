package com.tlv8.system.portelSetting;

public class PortelsettingEntity {
	private String SID = null;
	private String SPSNDESKINFO = null;
	private String SPERSONID = null;
	private String SPORTALTYPE = null;
	private int SORDER = 0;// 排序
	private short SISOPENWHENLONGING = 0;// 登录是否打开。0为不打开

	public short getSISOPENWHENLONGING() {
		return SISOPENWHENLONGING;
	}

	public void setSISOPENWHENLONGING(short sISOPENWHENLONGING) {
		SISOPENWHENLONGING = sISOPENWHENLONGING;
	}

	public String getSID() {
		return SID;
	}

	public void setSID(String sID) {
		SID = sID;
	}

	public String getSPSNDESKINFO() {
		return SPSNDESKINFO;
	}

	public void setSPSNDESKINFO(String sPSNDESKINFO) {
		SPSNDESKINFO = sPSNDESKINFO;
	}

	public String getSPERSONID() {
		return SPERSONID;
	}

	public void setSPERSONID(String sPERSONID) {
		SPERSONID = sPERSONID;
	}

	public String getSPORTALTYPE() {
		return SPORTALTYPE;
	}

	public void setSPORTALTYPE(String sPORTALTYPE) {
		SPORTALTYPE = sPORTALTYPE;
	}

	public int getSORDER() {
		return SORDER;
	}

	public void setSORDER(int sORDER) {
		SORDER = sORDER;
	}
}
