package com.tlv8.mobile;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.ActionSupport;

/**
 * 提交手机验证码
 * 
 * @author 陈乾
 *
 */
@Controller
@Scope("prototype")
public class SubmitVerification extends ActionSupport {
	private String code;
	private String re;

	@ResponseBody
	@RequestMapping("/submitVerification")
	public Object execute() {
		HttpSession session = request.getSession();
		System.out.println(code);
		System.out.println(session.getAttribute("SESSION_VERIFICATION_CODE"));
		if (session.getAttribute("SESSION_VERIFICATION_CODE").equals(code)) {
			setRe("true");
		} else {
			setRe("false");
		}
		return this;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setRe(String re) {
		this.re = re;
	}

	public String getRe() {
		return re;
	}

}
