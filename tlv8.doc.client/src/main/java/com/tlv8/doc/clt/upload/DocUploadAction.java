package com.tlv8.doc.clt.upload;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tlv8.doc.clt.doc.AbstractDoc;
import com.tlv8.doc.clt.doc.DocUtils;
import com.tlv8.doc.clt.doc.Docinfo;

@Controller
@Scope("prototype")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DocUploadAction {
	private String uploadInfo;
	private Float Size;
	private String Kind;
	private String fileID;

	public void setUploadInfo(String uploadInfo) {
		this.uploadInfo = uploadInfo;
	}

	public String getUploadInfo() {
		return uploadInfo;
	}

	@ResponseBody
	@RequestMapping(value = "/docUploadAction", method = RequestMethod.POST)
	public Object execute(@RequestParam("Filedata") MultipartFile file) throws Exception {
		try {
			String fileName = file.getOriginalFilename();
			Map<String, String> m = upLoadFiletoDaisy("/", fileName, file.getInputStream());
			Map<String, String> rm = new HashMap<>();
			rm.put("docName", fileName);
			rm.put("kind", file.getContentType());
			rm.put("size", String.valueOf(m.get("Size")));
			rm.put("cacheName", m.get("cacheName"));
			return rm;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{}";
	}

	private Map<String, String> upLoadFiletoDaisy(String DocPath, String DocName, InputStream file) throws Exception {
		Map m = new HashMap();
		m.put("sDocPath", DocPath);
		m.put("sDocName", DocName);
		Docinfo di = new Docinfo(m);
		AbstractDoc doca = new AbstractDoc(di);
		doca.upload(false, file);
		DocUtils.saveDocFlag(DocPath, doca.getsKind(), doca.getScacheName(), doca.getScacheName(), false);
		Map rem = new HashMap();
		rem.put("Kind", doca.getsKind());
		rem.put("cacheName", doca.getScacheName());
		rem.put("Size", doca.getsSize());
		rem.put("sDocName", DocName);

		setKind(doca.getsKind());
		setFileID(doca.getScacheName());
		setSize(doca.getsSize());
		return rem;
	}

	public void setSize(Float size) {
		Size = size;
	}

	public Float getSize() {
		return Size;
	}

	public void setKind(String kind) {
		Kind = kind;
	}

	public String getKind() {
		return Kind;
	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	public String getFileID() {
		return fileID;
	}

}
