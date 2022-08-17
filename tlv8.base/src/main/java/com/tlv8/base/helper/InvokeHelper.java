package com.tlv8.base.helper;

import java.lang.reflect.Method;

import com.tlv8.base.Sys;

/**
 * @author ChenQian
 */
public class InvokeHelper {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object invoke(String fullMethodName, Object... args) {

		String s = "";
		for (int i = 0; i < args.length; i++) {
			s = String.format("%s,%s(%s)", s, args[i].toString(), args[i].getClass().toString());
		}
		if (s.length() > 0)
			s = s.substring(1, s.length());
		Sys.printMsg(String.format("Sys.invoke(fullMethodName: %s(%s))", fullMethodName, s));

		String cName = Sys.extractClassName(fullMethodName);
		String mName = Sys.extractMethodName(fullMethodName);
		try {
			Class c = Class.forName(cName);
			Class[] paratype = new Class[args.length];

			int i = 0;
			for (Object o : args) {
				paratype[i] = o.getClass();
				i++;
			}
			Method m;
			m = c.getMethod(mName, paratype);
			return m.invoke(cName, args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		Sys.printMsg(invoke("com.justep.yn.utils.MD5Util.encode", "123456"));
	}
}
