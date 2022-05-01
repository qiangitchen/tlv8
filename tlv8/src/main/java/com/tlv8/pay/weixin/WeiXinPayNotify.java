package com.tlv8.pay.weixin;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tlv8.pay.PaymentInfo;

/**
 * 微信支付返回通知
 * 
 * @author 陈乾
 *
 */
@Controller
public class WeiXinPayNotify {

	@RequestMapping("/WeiXinPayNotify")
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> params = WeiXinReturnTanse.pase(request.getInputStream(), "utf-8");
		PrintWriter out = response.getWriter();
		if ("SUCCESS".equals(params.get("result_code")) && "SUCCESS".equals(params.get("return_code"))) {
			// 验证成功
			if (WeiXinReturnTanse.verify(params)) {
				String out_trade_no = params.get("out_trade_no");// 自定义订单号
				String transaction_id = params.get("transaction_id");// 返回订单号
				try {
					PaymentInfo paymentinfo = new PaymentInfo(out_trade_no);
					paymentinfo.setfReturnText(transaction_id);
					paymentinfo.setfState(1);// 支付成功
					paymentinfo.Update();
				} catch (Exception e) {
				}
				out.print("SUCCESS");
			} else {
				out.print("FAIL");
			}
		} else {
			out.print("FAIL");
		}
		out.close();
	}
}
