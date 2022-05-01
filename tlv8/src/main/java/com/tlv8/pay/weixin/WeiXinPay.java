package com.tlv8.pay.weixin;

import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.tlv8.base.utils.IDUtils;
import com.tlv8.base.utils.MD5Util;
import com.tlv8.pay.utils.HttpClientUtil;

public class WeiXinPay {
	public String time_stamp = "" + new Date().getTime() / 1000;//
	// 时间戳(系统时间秒)
	public String nonce_str = IDUtils.getGUID();// 随机字符串
	public String product_id = "" + Math.round(Math.random() * 100000) + 1;// 商品ID(订单号)
	public String body = "测试";
	public String out_trade_no = "" + Math.round(Math.random() * 100000) + 1;
	public long total_fee = 1;// 支付金额 {单位为【分】}
	public String spbill_create_ip = "";// 终端IP

	public String getTemp() {
		return "appid=" + WeixinPayConfig.appid + "&body=" + body + "&mch_id="
				+ WeixinPayConfig.mch_id + "&nonce_str=" + nonce_str
				+ "&notify_url=" + WeixinPayConfig.notify_url
				+ "&out_trade_no=" + out_trade_no + "&product_id=" + product_id
				+ "&spbill_create_ip=" + spbill_create_ip + "&total_fee="
				+ total_fee + "&trade_type=" + WeixinPayConfig.trade_type;
	}

	public String getSign() {
		return MD5Util.encode(getTemp() + "&key=" + WeixinPayConfig.key);
	}

	public String getActionURL() {
		return "https://api.mch.weixin.qq.com/pay/unifiedorder" + "?"
				+ getTemp() + "&sign=" + getSign();
	}

	public Element getActionResult() throws Exception {
		String params = "<xml> " + "<appid>" + WeixinPayConfig.appid
				+ "</appid>" + "<body>" + body + "</body> " + "<mch_id>"
				+ WeixinPayConfig.mch_id + "</mch_id>" + "<nonce_str>"
				+ nonce_str + "</nonce_str>" + "<notify_url>"
				+ WeixinPayConfig.notify_url + "</notify_url>"
				+ "<out_trade_no>" + out_trade_no
				+ "</out_trade_no><product_id>" + product_id + "</product_id>"
				+ "<spbill_create_ip>" + spbill_create_ip
				+ "</spbill_create_ip>" + "<total_fee>" + total_fee
				+ "</total_fee>" + "<trade_type>" + WeixinPayConfig.trade_type
				+ "</trade_type>" + "<sign>" + getSign() + "</sign>" + "</xml>";
		String res = HttpClientUtil.httpPost(
				"https://api.mch.weixin.qq.com/pay/unifiedorder", params);
		System.out.println(res);
		Document document = DocumentHelper.parseText(res);
		Element element = document.getRootElement();
		return element;
	}

	public String getCodeUrl() throws Exception {
		Element element = getActionResult();
		return element.elementText("code_url");
	}

	public String getPrepayId() throws Exception {
		Element element = getActionResult();
		return element.elementText("prepay_id");
	}

	public static void main(String[] args) {
		try {
			System.out.println(new WeiXinPay().getCodeUrl());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
