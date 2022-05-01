package com.tlv8.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.tlv8.base.utils.Xml;

/**
 * @d 返回数据
 * @author 陈乾
 */
public class Data {
	private String flag = null;// 操作是否成功
	private String data = null;// 返回的数据
	private String message = null;// 返回的信息
	private String table = null;
	private String relation = null;
	private String rowid = null;
	private int page = 1;
	private int allpage = 1;
	private int allrows = 1;
	private String gridid = "";

	public Data() {

	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFlag() {
		return flag;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public static List<String> getRowId(String rowids) {
		String[] rowid = rowids.split(",");
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < rowid.length; i++) {
			result.add(rowid[i]);
		}
		return result;
	}

	public static HashMap<String, String> getCell(String cells)
			throws SAXException, IOException, ParserConfigurationException {
		HashMap<String, String> result = Xml.XmlStrToMap(cells);
		return result;
	}

	public void setAllpage(int allpage) {
		this.allpage = allpage;
	}

	public int getAllpage() {
		return allpage;
	}

	public int getAllrows() {
		return allrows;
	}

	public void setAllrows(int allrows) {
		this.allrows = allrows;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPage() {
		return page;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getRowid() {
		return rowid;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getTable() {
		return table;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getRelation() {
		return relation;
	}

	public void setGridid(String gridid) {
		this.gridid = gridid;
	}

	public String getGridid() {
		return gridid;
	}
}
