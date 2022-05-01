package com.tlv8.base.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 字符数组操作 类似JS数组
 * 
 * @author 陈乾
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class StringArray {

	public List<String> list = new ArrayList();

	public StringArray(String[] subArray) {
		for (int i = 0; i < subArray.length; i++) {
			list.add(subArray[i]);
		}
	}

	public StringArray(List list) {
		this.list = list;
	}

	public StringArray() {
	}

	/**
	 * 追加数组内容
	 * 
	 * @param item String
	 */
	public void push(String item) {
		if (list == null)
			list = new ArrayList();
		list.add(item);
	}

	/**
	 * 追加数组内容
	 * 
	 * @param item StringArray
	 */
	public void push(StringArray item) {
		for (int i = 0; i < item.list.size(); i++) {
			list.add(item.list.get(i));
		}
	}

	/**
	 * 获取数组长度
	 * 
	 * @return int
	 */
	public int getLength() {
		return list.size();
	}

	/**
	 * 获取数组指定位置的内容
	 * 
	 * @param i
	 * @return String
	 */
	public String get(int i) {
		return list.get(i);
	}

	/**
	 * 删除数组最后一个
	 * 
	 * @return
	 */
	public String pop() {
		return list.get(list.size() - 1);
	}

	/**
	 * 将数组以指定的分隔符连接成字符串
	 * 
	 * @param split
	 * @return String
	 */
	public String join(String split) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				result.append(split);
			}
			result.append(list.get(i));
		}
		return result.toString();
	}

	public int indexOfIgnoreCase(String s) {
		for (int i = 0; i < list.size(); i++) {
			if (s.equalsIgnoreCase(list.get(i))) {
				return i;
			}
		}
		return -1;
	}

	public int indexOf(String s) {
		for (int i = 0; i < list.size(); i++) {
			if (s.equals(list.get(i))) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 转换为JSON字符
	 */
	public String toJson() {
		return JSON.toJSONString(list);
	}

	/**
	 * 转换为JSON对象
	 */
	public JSONArray toJSON() {
		return JSON.parseArray(toJson());
	}
}
