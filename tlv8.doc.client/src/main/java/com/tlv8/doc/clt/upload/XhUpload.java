package com.tlv8.doc.clt.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.ActionSupport;

@Controller
public class XhUpload extends ActionSupport {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private static final int BUFFER_SIZE = 16 * 1024;

	private File filedata;
	private String filedataContentType;
	private String filedataFileName;
	private String err;
	private String msg;

	private boolean success;// 和form表单里回调函数success和failure对应，一定要有。
	private String info; // 上传成功与否的提示信息

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public File getFiledata() {
		return filedata;
	}

	public void setFiledata(File filedata) {
		this.filedata = filedata;
	}

	public String getFiledataContentType() {
		return filedataContentType;
	}

	public void setFiledataContentType(String filedataContentType) {
		this.filedataContentType = filedataContentType;
	}

	public String getFiledataFileName() {
		return filedataFileName;
	}

	public void setFiledataFileName(String filedataFileName) {
		this.filedataFileName = filedataFileName;
	}

	private void writeFile(File src, File dest) {
		log.info("-----文件开始写入-----");
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dest), BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];

			// 开始写入
			while (in.read(buffer) > 0) {
				out.write(buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
			info = "上传失败！";
			log.info("-----写入失败！IO异常-----");
		} finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				success = false;
				info = "上传失败";
				log.info("-----写入失败！-----");
			}
		}
		log.info("-----写入成功！-----");

	}

	@ResponseBody
	@RequestMapping("/xhUpload")
	public Object execute() throws Exception {
		if (filedata.length() <= 5124000) {
			log.info("-----要上传的文件名:" + filedataFileName);
			// 获得上传的文件后缀名 并全部转换为小写
			int index = filedataFileName.lastIndexOf(".");
			String type = filedataFileName.substring(index + 1).toLowerCase();

			log.info("------上传的文件种类为" + type);
			// 获得file文件夹的绝对路径
			String toSrc = request.getServletContext().getRealPath("/uploadfile");

			// 定义文件要保存到的路径
			toSrc = toSrc + "/" + type;

			// /路径是否存在 不存在则创建文件夹
			File fDir = new File(toSrc);
			if (!fDir.exists()) {
				fDir.mkdir();
			}

			// 通过时间获取一个不重复的名字
			String thistime = new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date());

			// 定义文件保存的路径
			String url = toSrc + "/" + thistime + "." + type;
			log.info("-----文件保存的路径:" + toSrc);
			// 如果前面加个!则表示上传后立即显示图片 不用单击确定按钮
			msg = "!/JBIZ/uploadfile/" + type + "/" + thistime + "." + type;
			err = "";
			File toFile = new File(url);

			writeFile(this.filedata, toFile);
			success = true;
			info = "上传成功！";
		} else {
			success = false;
			err = "上传失败！上传文件大于5MB！";
			msg = "上传失败！上传文件大于5MB！";
			info = "上传失败！上传文件大于5MB！";
		}

		// 必须加否则会出现下载框
		response.setContentType("text/html;charset=utf-8");
		return this;
	}

}
