package com.tlv8.doc.clt.doc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.QName;

import com.tlv8.doc.clt.doc.transform.TableMetaData;

/**
 * 文档信息
 * 
 * @author 陈乾
 *
 */
@SuppressWarnings("rawtypes")
public class Docinfo {
	private Map resultData;
	private String state = "edit";

	@SuppressWarnings("unchecked")
	public Docinfo(Map m) {
		this.resultData = new HashMap();
		// 将字段名统一为大写
		for (Object key : m.keySet()) {
			this.resultData.put(key.toString().toUpperCase(), m.get(key));
		}
	}

	public Docinfo() {
		this.resultData = new HashMap();
	}

	public void setState(String state) {
		this.state = new ModifyState(state).getState();
	}

	public String getState() {
		return state;
	}

	@SuppressWarnings("unchecked")
	public void setString(String k, String v) {
		this.resultData.put(k.toUpperCase(), v);
	}

	public String getString(String s) {
		return (String) resultData.get(s.toUpperCase());
	}

	@SuppressWarnings("unchecked")
	public void setInteger(String k, Integer v) {
		this.resultData.put(k.toUpperCase(), v);
	}

	public Integer getInteger(String s) {
		s = s.toUpperCase();
		if (resultData.get(s) == null) {
			return 0;
		}
		if ("java.lang.String".equals(resultData.get(s).getClass())
				|| resultData.get(s).getClass().toString().endsWith("java.lang.String")) {
			return Integer.parseInt((String) resultData.get(s));
		} else {
			return (Integer) resultData.get(s);
		}
	}

	@SuppressWarnings("unchecked")
	public void setInt(String k, int v) {
		this.resultData.put(k.toUpperCase(), v);
	}

	public int getInt(String s) {
		s = s.toUpperCase();
		Integer result;
		if (resultData.get(s) != null) {
			try {
				result = Integer.valueOf((String) resultData.get(s));
			} catch (Exception e) {
				try {
					result = Math.round(Float.valueOf((String) resultData.get(s)));
				} catch (Exception er) {
					result = 0;
				}
			}
			return result;
		} else
			return -1;

	}

	@SuppressWarnings("unchecked")
	public void setFloatObject(String k, Object v) {
		this.resultData.put(k.toUpperCase(), v);
	}

	public Object getFloatObject(String s) {
		return resultData.get(s.toUpperCase());
	}

	@SuppressWarnings("unchecked")
	public void setFloat(String k, Float v) {
		this.resultData.put(k.toUpperCase(), v);
	}

	public Float getFloat(String s) {
		s = s.toUpperCase();
		if (resultData.get(s) != null)
			return Float.parseFloat(String.valueOf(resultData.get(s)));
		else
			return Float.valueOf(0);
	}

	public void setState(ModifyState edit) {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("unchecked")
	public void setFloat(String k, int i) {
		this.resultData.put(k.toUpperCase(), i);
	}

	public Timestamp getDateTime(String s) {
		return (Timestamp) resultData.get(s.toUpperCase());
	}

	@SuppressWarnings("unchecked")
	public void setDateTime(String k, Timestamp sTime) {
		this.resultData.put(k.toUpperCase(), sTime);
	}

	@SuppressWarnings("unchecked")
	public List getKayList() {
		List list = new ArrayList();
		Set s = resultData.keySet();
		Iterator it = s.iterator();
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}

	public TableMetaData getMetaData() {
		return null;
	}

	public Docinfo getTable() {
		return null;
	}

	public QName getProperties() {
		return null;
	}

	public Object getValue(String str1) {
		return null;
	}

	public boolean isModified(String str1) {
		return false;
	}

	public Object getOldValue(String str1) {
		return null;
	}

	public Iterator iterator() {
		return resultData.keySet().iterator();
	}

	@SuppressWarnings("unchecked")
	public void setDateTime(String k, String v) {
		this.resultData.put(k.toUpperCase(), v);
	}
}
