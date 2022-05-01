package com.tlv8.doc.svr.core.io.atr;

import java.util.Date;

public class DocQueryParam {
	private String[] searchKey;// 查询关键字
	private String seachFolder;// 搜索的位置docPath
	/*
	 * 创建索引的时间段查询
	 */
	private Date startTime;
	private Date endTime;

	public String[] getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String[] searchKey) {
		this.searchKey = searchKey;
	}

	public String getSeachFolder() {
		return seachFolder;
	}

	public void setSeachFolder(String seachFolder) {
		this.seachFolder = seachFolder;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
