package com.tlv8.doc.clt.doc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Test {
	public static void upload() throws Exception {
		Map m = new HashMap();
		m.put("sDocPath", "/");
		m.put("sDocName", "测试.txt");
		Docinfo di = new Docinfo(m);
		File file = new File("d:/test.txt");
		AbstractDoc doca = new AbstractDoc(di);
		doca.upload(false, file);
		System.out.println(doca.getsKind());
		System.out.println(doca.getScacheName());
		System.out.println(doca.getsSize());
	}

	public static void main(String[] args) {
		try {
			upload();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Map m = new HashMap();
		m.put("sDocPath", "/");
		m.put("sFileID", "35");
		Docinfo di = new Docinfo(m);
		AbstractDoc doca = new AbstractDoc(di);
		File file = new File("d:/resule.doc");
		OutputStream outputStream;
		try {
			outputStream = new FileOutputStream(file);
			doca.download(false, outputStream, null, null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
