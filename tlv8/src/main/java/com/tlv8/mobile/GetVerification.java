package com.tlv8.mobile;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

/**
 * 获取手机验证码
 * 
 * @author 陈乾
 *
 */
@Controller
@Scope("prototype")
public class GetVerification extends ActionSupport {
	private int re;
	private String phoneNumber;

	@ResponseBody
	@RequestMapping("/getVerification")
	@SuppressWarnings({ "unused", "rawtypes" })
	public Object execute() {
		ContextBean context = ContextBean.getContext(request);
		String personid = context.getPersonID();
		String personname = context.getCurrentPersonName();
		try {
			if (phoneNumber == null || phoneNumber.equals("")) {
				Map result = (Map) DBUtils
						.execQueryforList("system", "select smobilephone from SA_OPPERSON where sid='" + personid + "'")
						.get(0);
				phoneNumber = result.get("SMOBILEPHONE").toString();
			}
			HttpSession session = request.getSession();
			String content = "【协同办公平台】" + session.getAttribute("SESSION_VERIFICATION_CODE") + "(登录验证码)";
//			re=ShortMessage.SendSM(personid, personname,phoneNumber,content);
			System.out.println(session.getAttribute("SESSION_VERIFICATION_CODE"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this;
	}

	public void setRe(int re) {
		this.re = re;
	}

	public int getRe() {
		return re;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

}
