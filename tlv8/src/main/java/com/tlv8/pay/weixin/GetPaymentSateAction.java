package com.tlv8.pay.weixin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tlv8.pay.PaymentInfo;

/**
 * 获取支付状态
 */
@Controller
public class GetPaymentSateAction {

	@RequestMapping("/getPaymentSateAction")
	public Object execute(String forderCode) throws Exception {
		int fstate = 0;
		try {
			PaymentInfo payinfo = new PaymentInfo(forderCode);
			fstate = payinfo.getfState();
		} catch (Exception e) {
		}
		return fstate;
	}

}
