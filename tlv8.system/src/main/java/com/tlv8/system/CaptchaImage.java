package com.tlv8.system;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.SecurityCode;
import com.tlv8.base.SecurityImage;

/**
 * 验证码
 */
@Controller
public class CaptchaImage {

	@ResponseBody
	@RequestMapping("/captchaimage")
	public void execute(HttpServletRequest req, HttpServletResponse httpServletResponse) throws Exception {
		// 如果开启Hard模式，可以不区分大小写
		// String securityCode =
		// SecurityCode.getSecurityCode(4,SecurityCodeLevel.Hard,
		// false).toLowerCase();

		// 获取默认难度和长度的验证码
		String securityCode = SecurityCode.getSecurityCode();
		ByteArrayInputStream imageStream = SecurityImage.getImageAsInputStream(securityCode);
		// 放入session中
		req.getSession().setAttribute("SESSION_SECURITY_CODE", securityCode);

		httpServletResponse.setContentType("image/jpeg");
		OutputStream os = httpServletResponse.getOutputStream();
		int bytesRead;
		byte[] buf = new byte[1024];
		while ((bytesRead = imageStream.read(buf)) != -1) {
			os.write(buf, 0, bytesRead);
		}
		os.flush();
		os.close();
		imageStream.close();
	}

}