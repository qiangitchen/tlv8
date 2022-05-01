package com.tlv8.doc.clt.doc;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import com.tlv8.doc.clt.doc.transform.TransformConfig;

public class Utils {

	public static boolean isNotEmptyString(String paramString) {
		return (paramString != null) && (paramString.trim().length() != 0);
	}

	public static boolean isNotNull(InputStream paramObject) {
		return paramObject != null;
	}

	public static boolean isEmptyString(String paramString) {
		return (null == paramString) || ("".equals(paramString));
	}

	public static boolean isNull(Object paramObject) {
		return paramObject == null;
	}

	public static boolean isNotNull(Object paramObject) {
		return paramObject != null;
	}

	public static void check(boolean paramBoolean, Object paramObject)
			throws ModelException {
		if (!paramBoolean) {
			ModelException localModelException = new ModelException(""
					+ paramObject);
			jdField_a_of_type_OrgApacheLog4jLogger.error("check error!",
					localModelException);
			throw localModelException;
		}

	}

	public static void checkNotNull(String paramString, Object paramObject)
			throws ModelException {
		if (paramObject == null) {
			ModelException localModelException = new ModelException(String
					.format("%s不能为空!", new Object[] { paramString }));
			jdField_a_of_type_OrgApacheLog4jLogger.error("检查 " + paramString
					+ " 时报错！", localModelException);
			throw localModelException;
		}

	}

	public static void checkNotExist(String paramString, Object paramObject)
			throws ModelException {
		if (paramObject == null) {
			ModelException localModelException = new ModelException(String
					.format("%s不存在!", new Object[] { paramString }));
			jdField_a_of_type_OrgApacheLog4jLogger.error("检查 " + paramString
					+ " 时报错！", localModelException);
			throw localModelException;
		}
	}

	public static void checkNotEmptyString(String paramString1,
			String paramString2) {
		checkNotEmptyString(paramString1, paramString2, null);
	}

	public static void checkNotEmptyString(String paramString1,
			String paramString2, Object paramLogger) {
		if (isEmptyString(paramString2)) {
			ModelException localModelException = new ModelException(String
					.format("%s不能为空!", new Object[] { paramString1 }));
			if (!isNull(paramLogger))
				jdField_a_of_type_OrgApacheLog4jLogger.error("检查 "
						+ paramString1 + " 时报错！", localModelException);
			try {
				throw localModelException;
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void checkNotEmptyString(String string, List<?> paramTable,
			Object logger) {
		// TODO Auto-generated method stub

	}

	public static boolean isNotEmptyString(TransformConfig paramString) {
		return (paramString != null) && (paramString.trim().length() != 0);
	}

	public static String moveRepeatStr(String ms, String cs) {
		int index = 0;
		while (ms.indexOf(cs) > -1) {
			index = ms.indexOf(cs);
			ms = ms.replace(cs, "");
		}
		ms = ms.substring(0, index) + cs + ms.substring(index);
		return ms;
	}

	public static String getID() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

}
