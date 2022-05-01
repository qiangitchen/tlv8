package com.tlv8.layim;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tlv8.base.db.DBUtils;
import com.tlv8.doc.clt.doc.Doc;
import com.tlv8.doc.clt.doc.DocDBHelper;
import com.tlv8.doc.clt.doc.Docs;

@Controller
@RequestMapping("/layim")
public class UploadImage {

	@ResponseBody
	@RequestMapping("/uploadImage")
	public Object execute(@RequestParam("file") MultipartFile file) throws Exception {
		String fileID = "new";
		Map<String, Object> json = new HashMap<String, Object>();
		try {
			String docID = DocDBHelper.addDocData("/root/即时通讯/" + new SimpleDateFormat("yyyy/MM/dd").format(new Date()),
					file.getOriginalFilename(), file.getContentType(), String.valueOf(file.getSize()), fileID);
			Doc doc = Docs.queryDocById(docID);
			doc.upload(false, file.getInputStream());
			doc.setsFileID("");
			doc.commitFile();
			fileID = doc.getsFileID();
			DBUtils.execUpdateQuery("system",
					"update SA_DOCNODE set SFILEID='" + fileID + "' where SID = '" + docID + "'");
			json.put("code", 0);
			json.put("msg", "");
			Map<String, String> data = new HashMap<String, String>();
			data.put("src", "/DocServer/repository/file/view/" + fileID + "/last/content");
			json.put("data", data);
		} catch (Exception e) {
			json.put("code", -1);
			json.put("msg", e.getMessage());
			e.printStackTrace();
		}
		return json;
	}

}
