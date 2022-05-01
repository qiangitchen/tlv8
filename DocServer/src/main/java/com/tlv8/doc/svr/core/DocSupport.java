package com.tlv8.doc.svr.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tlv8.doc.svr.core.inter.IDoc;

public abstract class DocSupport implements IDoc {
	/**
	 * 实现IDoc的获取新的DocPath方法
	 * 
	 * @see com.tlv8.doc.svr.core.inter.IDoc#getNewDocPath()
	 */
	public String getNewDocPath() {
		String docPath = new SimpleDateFormat("yyyy/MM/dd/HH/mm")
				.format(new Date());
		return docPath;
	}
}
