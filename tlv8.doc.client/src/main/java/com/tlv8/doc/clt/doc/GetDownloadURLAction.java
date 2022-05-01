package com.tlv8.doc.clt.doc;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class GetDownloadURLAction extends ActionSupport {
	private String fileID;
	private String filename;
	private String url;

	public String getFileID() {
		return fileID;
	}

	public void setFileID(String fileID) {
		try {
			this.fileID = URLDecoder.decode(fileID, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		try {
			this.filename = URLDecoder.decode(filename, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@ResponseBody
	@RequestMapping("/GetDownloadURLAction")
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object execute() throws Exception {
		try {
			String SQL = "select SDOCDISPLAYPATH from SA_DOCNODE where SFILEID = '"
					+ fileID + "'";
			List Li = DBUtils.execQueryforList("system", SQL);
			if (Li.size() > 0) {
				Map rs = (Map) Li.get(0);
				String DocPath = (String) rs.get("SDOCDISPLAYPATH");
				Map m = new HashMap();
				m.put("sDocPath", DocPath);
				m.put("sFileID", fileID);
				Docinfo di = new Docinfo(m);
				AbstractDoc doca = new AbstractDoc(di);
				setUrl(doca.getdownloadURL(false, null, null) + "&filename="
						+ filename);
			}
			if (Li.size() == 0) {
				Sys.packErrMsg("给定fileID不存在!");
				setUrl("err");
			}
		} catch (Exception e) {
			setUrl("err");
			e.printStackTrace();
		}
		return this;
	}

}
