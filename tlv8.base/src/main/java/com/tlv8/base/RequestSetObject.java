package com.tlv8.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("rawtypes")
public class RequestSetObject {

	/**
	 * 获取类的所有属性,包括父类的属性
	 * 
	 * @param object
	 * @return
	 */
	public static Field[] getAllFields(Object object) {
		Class clazz = object.getClass();
		List<Field> fieldList = new ArrayList<>();
		while (clazz != null) {
			fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		Field[] fields = new Field[fieldList.size()];
		fieldList.toArray(fields);
		return fields;
	}

	/**
	 * 将请求参数赋给指定的类
	 * 
	 * @param request
	 * @param object
	 * @throws Exception
	 */
	public static void setData(HttpServletRequest request, Object object) throws Exception {
		Class<?> classType = object.getClass();
		Field[] fields = getAllFields(object);
		for (int i = 0; i < fields.length; i++) {
			try {
				Field field = fields[i];
				// 动态生成getter和setter方法
				String fieldName = field.getName();
				String firstChar = fieldName.substring(0, 1).toUpperCase();
				// String getterName = "get" + firstChar +
				// fieldName.substring(1);
				String setterName = "set" + firstChar + fieldName.substring(1);
				// Method getter = classType.getMethod(getterName);
				Class paramclass = field.getType();
				Method setter = classType.getMethod(setterName, new Class[] { paramclass });
				// 执行getter方法获取当前域的值
				// Object result = getter.invoke(object);
				// 执行setter给object赋值
				String fieldValue = request.getParameter(fieldName);
				Object fieldValueObj = fieldValue;
				boolean istext = false;
				if (paramclass == Date.class) {
					if ("".equals(fieldValue)) {
						fieldValueObj = null;
					} else {
						try {
							fieldValueObj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fieldValue);
						} catch (Exception e) {
							try {
								fieldValueObj = new SimpleDateFormat("yyyy-MM-dd").parse(fieldValue);
							} catch (Exception e1) {
								fieldValueObj = null;
							}
						}
					}
					istext = true;
				} else if (paramclass == Integer.class || paramclass == int.class) {
					if (fieldValue == null || "".equals(fieldValue)) {
						fieldValueObj = Integer.parseInt("0");
					} else {
						fieldValueObj = Integer.parseInt(fieldValue);
					}
					istext = true;
				} else if (paramclass == Long.class || paramclass == long.class) {
					if (fieldValue == null || "".equals(fieldValue)) {
						fieldValueObj = Long.parseLong("0");
					} else {
						fieldValueObj = Long.parseLong(fieldValue);
					}
					istext = true;
				} else if (paramclass == Float.class || paramclass == float.class) {
					if (fieldValue == null || "".equals(fieldValue)) {
						fieldValueObj = Float.parseFloat("0");
					} else {
						fieldValueObj = Float.parseFloat(fieldValue);
					}
					istext = true;
				} else if (paramclass == Double.class || paramclass == double.class) {
					if (fieldValue == null || "".equals(fieldValue)) {
						fieldValueObj = Double.parseDouble("0");
					} else {
						fieldValueObj = Double.parseDouble(fieldValue);
					}
					istext = true;
				} else if (paramclass == Boolean.class || paramclass == boolean.class) {
					if (fieldValue == null || "".equals(fieldValue)) {
						fieldValueObj = Boolean.valueOf("false");
					} else {
						fieldValueObj = Boolean.valueOf(fieldValue);
					}
					istext = true;
				} else if (paramclass == String.class) {
					if (fieldValueObj == null) {
						fieldValueObj = "";
					}
					istext = true;
				}
				if (istext) {
					setter.invoke(object, new Object[] { fieldValueObj });
				}
			} catch (Exception e) {
			}
		}
	}

}
