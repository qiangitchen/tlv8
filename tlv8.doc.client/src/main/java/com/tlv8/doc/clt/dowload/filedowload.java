package com.tlv8.doc.clt.dowload;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
import com.tlv8.doc.clt.doc.AbstractDoc;
import com.tlv8.doc.clt.doc.Docinfo;

/**
 * @author 陈乾
 */
@Controller
@Scope("prototype")
public class filedowload extends ActionSupport {
	private String fileID;
	private String contentType;
	private String filename;
	private String url = "";

	public String getFileID() {
		return this.fileID;
	}

	public void setFileID(String fileID) {
		try {
			this.fileID = URLDecoder.decode(fileID, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public InputStream getInputStream() throws Exception {
		String realPath = request.getServletContext().getRealPath("");
		String urlPath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath();
		// System.out.println(urlPath);
		@SuppressWarnings("unused")
		String path = realPath + "\\uploadfile\\";
		// System.out.println(path + inputPath);
		@SuppressWarnings("unused")
		String fileRealName = "";
		String DocPath = "";
		try {
			String SQL = "select SKEYWORDS,SKIND,SDOCNAME,SDOCDISPLAYPATH from SA_DOCNODE where SFILEID = '"
					+ getFileID() + "'";
			List Li = DBUtils.execQueryforList("system", SQL);
			if (Li.size()>0) {
				Map rs = (Map)Li.get(0);
				fileRealName = (String)rs.get("SKEYWORDS");
				contentType = (String)rs.get("SKIND");
				filename = (String)rs.get("SDOCNAME");
				DocPath = (String)rs.get("SDOCDISPLAYPATH");
			} else {
				Sys.packErrMsg("给定fileID不存在!");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		setUrl(urlPath + "/uploadfile/" + "biz-api.jar");
		//System.out.println(getUrl() + ">>" + getFileID() + ">>" + filename);
		Map m = new HashMap();
		m.put("sDocPath", DocPath);
		m.put("sFileID", fileID.split("-")[0]);
		Docinfo di = new Docinfo(m);
		AbstractDoc doca = new AbstractDoc(di);
		//HttpServletResponse response = (HttpServletResponse)ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);
		ByteArrayOutputStream  out = new ByteArrayOutputStream ();
		try {
			 doca.download(false, out,null, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//response.setCharacterEncoding("UTF-8");
		 //return ServletActionContext.getServletContext().getResourceAsStream(path);
		return new ByteArrayInputStream(out.toByteArray());
	}

	@ResponseBody
	@RequestMapping("/filedowload")
	public Object execute() throws Exception {
		// System.out.println(url);
		return this;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setFilename(String filename) {
		try {
			this.filename = URLDecoder.decode(filename, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getFilename() {
		try {
			return URLEncoder.encode(filename, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return filename;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}

}
