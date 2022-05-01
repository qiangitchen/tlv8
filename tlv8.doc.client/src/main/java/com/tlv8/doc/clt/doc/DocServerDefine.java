package com.tlv8.doc.clt.doc;

public class DocServerDefine {
	private Docinfo info;
	private DocServerDefines defines;

	public DocServerDefine(Docinfo r, DocServerDefines docServerDefines) {
		this.defines = docServerDefines;
		this.info = r;
	}

	public String getsRootDocId() {
		return info.getString("SA_DOCNODE");
	}

	public String getsID() {
		return info.getString("SA_DOCNAMESPACE");
	}

	public void setsID(String sID) {
		info.setString("SA_DOCNAMESPACE", sID);
	}

	public String getsDisplayName() {
		return info.getString("SDISPLAYNAME");
	}

	public void setsDisplayName(String sDisplayName) {
		info.setString("SDISPLAYNAME", sDisplayName);
	}

	public String getsUrl() {
		String sUrl = info.getString("SURL");
		if (sUrl != null && sUrl.endsWith("/")) {
			return sUrl.substring(0, sUrl.length() - 1);
		}
		return sUrl;
	}

	public void setsUrl(String sUrl) {
		info.setString("SURL", sUrl);
	}

	public String getsAccessMode() {
		return info.getString("SACCESSMODE");
	}

	public void setsAccessMode(String sAccessMode) {
		info.setString("SACCESSMODE", sAccessMode);
	}

	public int getsFlag() {
		return info.getInt("SFLAG");
	}

	public void setsFlag(int sFlag) {
		info.setInt("SFLAG", sFlag);
	}

	public DocServerDefines getDefines() {
		return defines;
	}
}
