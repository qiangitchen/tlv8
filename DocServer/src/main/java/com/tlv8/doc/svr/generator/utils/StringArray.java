package com.tlv8.doc.svr.generator.utils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class StringArray {

	public String[] array;
	public List list = new ArrayList();

	public StringArray(String[] subArray) {
		this.array = subArray;
	}

	public StringArray() {
	}

	public void push(String item) {
		if (list != null)
			list.add(item);
		else {
			String[] data = new String[this.array.length];
			for (int i = 0; i < this.array.length; i++) {
				data[i] = this.array[i];
			}
			this.array = new String[this.array.length + 1];
			for (int i = 0; i < data.length; i++) {
				this.array[i] = data[i];
			}
			this.array[this.array.length] = item;
		}
	}

	public void push(StringArray item) {
		if (item.array != null) {
			array = item.array;
		} else {
			for (int i = 0; i < item.list.size(); i++) {
				list.add(item.list.get(i));
			}
		}
	}

	public int getLength() {
		if (array != null)
			return array.length;
		else
			return list.size();
	}

	public String get(int i) {
		if (array != null)
			return array[i];
		else
			return (String) list.get(i);
	}

	public String pop() {
		if (array != null)
			return array[array.length - 1];
		else
			return (String) list.get(list.size() - 1);
	}

	public String join(String split) {
		StringBuffer result = new StringBuffer();
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				if (i > 0) {
					result.append(split);
				}
				result.append(array[i]);
			}
		} else {
			for (int i = 0; i < list.size(); i++) {
				if (i > 0) {
					result.append(split);
				}
				result.append(list.get(i));
			}
		}
		return result.toString();
	}
}
