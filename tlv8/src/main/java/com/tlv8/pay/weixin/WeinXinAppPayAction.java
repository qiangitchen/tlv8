package com.tlv8.pay.weixin;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.utils.IDUtils;
import com.tlv8.base.utils.MD5Util;
import com.tlv8.pay.PaymentInfo;
import com.tlv8.system.bean.ContextBean;

/**
 * 微信（手机）支付调用
 */
@Controller
public class WeinXinAppPayAction {
	public String time_stamp = "" + new Date().getTime() / 1000;// 精确到秒
	public String nonce_str = IDUtils.getGUID();// 随机字符串

	private String eledeId;
	private int modelId;
	private int userId = -1;
	private String userName;
	private String packageValue = "Sign=WXPay";

	@RequestMapping("/weinXinAppPayAction")
	public void execute(HttpServletRequest request, HttpServletResponse response,String ordername,double money) throws Exception {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		JSONObject json = new JSONObject();
		PrintWriter outhtml = response.getWriter();
		ContextBean context = ContextBean.getContext(request);
		try {
			PaymentInfo paymentinfo = new PaymentInfo();
			paymentinfo.setfEledeId(eledeId);
			paymentinfo.setfOrderName(ordername);
			paymentinfo.setMoney(money);
			paymentinfo.setfRemark("微信支付");
			paymentinfo.setUserId(context.getCurrentPersonID());
			paymentinfo.setUserName(context.getCurrentPersonName());
			paymentinfo.setModelId(modelId);
			paymentinfo.Insert();
			WeiXinAppPay wpay = new WeiXinAppPay();
			wpay.product_id = paymentinfo.getfOrderCode();
			wpay.out_trade_no = paymentinfo.getfOrderCode();
			wpay.body = paymentinfo.getfOrderName();
			wpay.total_fee = Math.round(paymentinfo.getMoney() * 100);
			wpay.spbill_create_ip = getRemoteAddr(request);
			Element weixinr = wpay.getActionResult();
			json.put("appid", WeixinPayConfig.mappid);
			json.put("partnerid", WeixinPayConfig.mmch_id);
			json.put("prepayid", getPrepayid(weixinr));
			json.put("noncestr", nonce_str);
			json.put("timestamp", time_stamp);
			json.put("package", packageValue);
			json.put("sign", getSign(weixinr));
			outhtml.write(json.toString());
		} catch (Exception e) {
			outhtml.write("");
		}
		outhtml.close();
	}

	protected String getNonceStr(Element weixinr) throws Exception {
		return weixinr.elementText("nonce_str");
	}

	private String getPrepayid(Element weixinr) throws Exception {
		return weixinr.elementText("prepay_id");
	}

	private String getTemp(Element weixinr) throws Exception {
		return "appid=" + WeixinPayConfig.mappid + "&noncestr=" + nonce_str + "&package=" + packageValue + "&partnerid="
				+ WeixinPayConfig.mmch_id + "&prepayid=" + getPrepayid(weixinr) + "&timestamp=" + time_stamp;
	}

	private String getSign(Element weixinr) throws Exception {
		return MD5Util.encode(getTemp(weixinr) + "&key=" + WeixinPayConfig.key);
		// return weixinr.elementText("sign");
	}

	private static String getRemoteAddr(HttpServletRequest req) {
		String ip = req.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		return ip;
	}

	public void setEledeId(String eledeId) {
		this.eledeId = eledeId;
	}

	public String getEledeId() {
		return eledeId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public int getModelId() {
		return modelId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserName(String userName) {
		try {
			this.userName = URLDecoder.decode(userName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.userName = userName;
		}
	}

	public String getUserName() {
		return userName;
	}

}
