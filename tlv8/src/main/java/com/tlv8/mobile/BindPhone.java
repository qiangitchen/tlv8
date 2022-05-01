package com.tlv8.mobile;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

/**
 * 绑定手机号
 * 
 * @author 陈乾
 *
 */
@Controller
@Scope("prototype")
public class BindPhone extends ActionSupport {
	private String phoneNumber;
	private String code;
	private String re;

	@ResponseBody
	@RequestMapping("/BindPhone")
	public Object execute() {
		ContextBean context = ContextBean.getContext(request);
		String personid = context.getPersonID();
		HttpSession session = request.getSession();
		System.out.println(code);
		System.out.println(session.getAttribute("SESSION_VERIFICATION_CODE"));
		try {
			if (session.getAttribute("SESSION_VERIFICATION_CODE").equals(code)) {
				String sql = "update SA_OPPERSON set smobilephone='" + phoneNumber + "' where sid='" + personid + "'";
				DBUtils.execUpdateQuery("system", sql);
				setRe("true");
			} else {
				setRe("false");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
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
