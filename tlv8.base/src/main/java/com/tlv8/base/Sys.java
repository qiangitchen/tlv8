package com.tlv8.base;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlv8.base.utils.StringArray;

/**
 * 
 * @d JavaTesting控制是否处于java调试中，调试是要在main函数里置为true
 * @author 陈乾
 */
public class Sys {
	public static boolean testing = false;
	public static boolean allowPrintDebugMsg = true;

	static Logger logger = LoggerFactory.getLogger(Sys.class);

	/**
	 * 获取32位大写主键
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
	}

	/**
	 * 获取当前日期
	 */
	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	/**
	 * 获取当前日期时间
	 */
	public static String getCurrentDateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	public static void printMsg(String aMsg) {
		if (allowPrintDebugMsg)
			System.out.println(getCurrentDateTime() + "  " + aMsg);
		logger.debug(aMsg);
	}

	public static void printMsg(int aMsg) {
		if (allowPrintDebugMsg)
			System.out.println(getCurrentDateTime() + "  " + aMsg);
		logger.debug(aMsg + "");
	}

	public static void printMsg(Float aMsg) {
		if (allowPrintDebugMsg)
			System.out.println(getCurrentDateTime() + "  " + aMsg);
		logger.debug(aMsg + "");
	}

	public static void printMsg(Date aMsg) {
		if (allowPrintDebugMsg)
			System.out.println(getCurrentDateTime() + "  " + aMsg);
		logger.debug(aMsg.toString());
	}

	public static void printMsg(Object aMsg) {
		if (allowPrintDebugMsg)
			System.out.println(getCurrentDateTime() + "  " + aMsg);
		logger.debug(aMsg.toString());
	}

	public static void printResult(String aMethodName, Object aResult) {
		if (aResult != null)
			printMsg(String.format("%s: %s", aMethodName, aResult.toString()));
		else
			printMsg(String.format("%s: null", aMethodName));
	}

	public static String packErrMsg(String msg) {
		String result = "jar-exception:%s";
		result = String.format(result, msg);
		System.err.println(getCurrentDateTime() + "  " + result);
		logger.error(result);
		return result;
	}

	public static void printErr(Object msg) {
		System.err.println(getCurrentDateTime() + "  " + msg);
		logger.error(msg.toString());
	}

	/**
	 * 此方法有两个参数，第一个是要查找的字符串数组，第二个是要查找的字符或字符串
	 */
	public static boolean isHave(String[] strs, String s) {
		for (int i = 0; i < strs.length; i++) {
			if (strs[i].indexOf(s) != -1) {// 循环查找字符串数组中的每个字符串中是否包含所有查找的内容
				return true;// 查找到了就返回真，不在继续查询
			}
		}
		return false;// 没找到返回false
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object invoke(String fullMethodName, Object... args) {
		String s = "";
		for (int i = 0; i < args.length; i++) {
			s = String.format("%s,%s(%s)", s, args[i].toString(), args[i].getClass().toString());
		}
		if (s.length() > 0)
			s = s.substring(1, s.length());
		// printMsg(String.format("Sys.invoke(fullMethodName: %s(%s)",
		// fullMethodName, s));
		String cName = extractClassName(fullMethodName);
		String mName = extractMethodName(fullMethodName);
		try {
			Class c = Class.forName(cName);
			Class[] paratype = new Class[args.length];

			int i = 0;
			for (Object o : args) {
				paratype[i] = o.getClass();
				i++;
			}
			Class[] partype = new Class[1];
			Object[] params = new Object[1];
			Method m;
			if ("concat".equals(mName.toLowerCase())) {
				partype[0] = Object[].class;
				String[] pas = new String[args.length];
				int n = 0;
				for (Object o : args) {
					pas[n] = o.toString();
					n++;
				}
				params[0] = pas;
				m = c.getMethod(mName, partype);
				return m.invoke(cName, params);
			} else {
				m = c.getMethod(mName, paratype);
				return m.invoke(cName, args);
			}
		} catch (Exception e) {
			printMsg(String.format("Sys.invoke(fullMethodName: %s(%s))", fullMethodName, s));
			throw new RuntimeException(e);
		}
	}

	public static String extractClassName(String value) {
		String result = "";
		int i = value.lastIndexOf('.');
		result = value.substring(0, i);
		return result;
	}

	public static String extractMethodName(String value) {
		String result = "";
		int i = value.lastIndexOf('.');
		result = value.substring(i + 1, value.length());
		return result;
	}

	/**
	 * 字符串连接
	 */
	public static String concat(Object[] args) {
		String result = "";
		StringArray array = new StringArray();
		for (Object o : args) {
			array.push(o.toString());
		}
		if (array.getLength() > 0) {
			result = array.join("");
		}
		return result;
	}

	public static void main(String[] args) {
		Sys.testing = true;
		printMsg(
				"main-Result:" + invoke("com.tlv8.base.Sys.concat", "ProduceTest", "123456;DYCRM201", "测试").toString());
		printErr("测试错误错误日志输出");
	}

	public static Object tempMethod(Object arg0) {
		return arg0;
	}
}
