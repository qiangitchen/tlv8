package com.tlv8.doc.clt.doc;

import java.sql.Timestamp;

public class DocPermission {

	private Docinfo info;
	private DocPermissions.DocPermissionsItem docPermissionsItem;
	private DocDirPermissions docDirPermissions;

	public DocPermission(Docinfo info,
			DocPermissions.DocPermissionsItem docPermissionsItem) {
		this.info = info;
		this.docPermissionsItem = docPermissionsItem;
	}

	public DocPermission(Docinfo r, DocDirPermissions docDirPermissions2) {
		this.info = r;
		this.docDirPermissions = docDirPermissions2;
	}

	protected DocPermission reRaload(Docinfo info,
			DocPermissions.DocPermissionsItem docPermissionsItem) {
		this.info = info;
		this.docPermissionsItem = docPermissionsItem;
		return this;
	}

	public DocPermissions.DocPermissionsItem getDocPermissionsItem() {
		return docPermissionsItem;
	}

	public DocDirPermissions getDocDirPermissions() {
		return docDirPermissions;
	}

	public String getsID() {
		return info.getString("SA_DocAuth");
	}

	public void setsID(String sID) {
		info.setString("SA_DocAuth", sID);
	}

	public String getsDocPath() {
		return info.getString("sDocPath");
	}

	public void setsDocPath(String sDocPath) {
		info.setString("sDocPath", sDocPath);
	}

	public String getsAuthorizerFID() {
		return info.getString("sAuthorizerFID");
	}

	public void setsAuthorizerFID(String sAuthorizerFID) {
		info.setString("sAuthorizerFID", sAuthorizerFID);
	}

	public String getsAuthorizerName() {
		return info.getString("sAuthorizerName");
	}

	public void setsAuthorizerName(String sAuthorizerName) {
		info.setString("sAuthorizerName", sAuthorizerName);
	}

	public String getsAuthorizeeFID() {
		return info.getString("sAuthorizeeFID");
	}

	public void setsAuthorizeeFID(String sAuthorizeeFID) {
		info.setString("sAuthorizeeFID", sAuthorizeeFID);
	}

	public String getsAuthorizeeName() {
		return info.getString("sAuthorizeeName");
	}

	public void setsAuthorizeeName(String sAuthorizeeName) {
		info.setString("sAuthorizeeName", sAuthorizeeName);
	}

	public Timestamp getsGrantTime() {
		Timestamp t = info.getDateTime("sGrantTime");
		return t;// Utils.isNull(t) ? t : CommonUtils.getCurrentDateTime();
	}

	public void setsGrantTime(Timestamp sGrantTime) {
		info.setDateTime("sGrantTime", sGrantTime);
	}

	public int getsAccess() {
		return info.getInt("sAccess");
	}

	public void setsAccess(int sAccess) {
		info.setInt("sAccess", sAccess);
	}

}
