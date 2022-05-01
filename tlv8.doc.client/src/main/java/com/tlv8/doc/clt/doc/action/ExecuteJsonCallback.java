package com.tlv8.doc.clt.doc.action;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.alibaba.fastjson.JSON;

public class ExecuteJsonCallback implements ExecuteCallback {
	public Object execute(InputStream paramInputStream, String paramString) {
		try {
			byte[] arrayOfByte = new byte[8192];
			ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
			int i = 0;
			while ((i = paramInputStream.read(arrayOfByte)) > 0)
				localByteArrayOutputStream.write(arrayOfByte, 0, i);
			String str = new String(localByteArrayOutputStream.toByteArray(), "UTF-8");
			return JSON.parseObject(str);
		} catch (Exception localException) {
			new ActionExecuteException(localException.getMessage(), localException);
		}
		return null;
	}
}