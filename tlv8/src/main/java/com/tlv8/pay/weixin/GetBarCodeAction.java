package com.tlv8.pay.weixin;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tlv8.pay.PaymentInfo;

/**
 * PC支付调用
 * 
 * @author chenqian
 *
 */
@Controller
public class GetBarCodeAction {

	@RequestMapping("/getBarCodeAction")
	public void execute(HttpServletResponse response, String orderCode) throws Exception {
		try {
			PaymentInfo paymentinfo = new PaymentInfo(orderCode);
			WeiXinPay wpay = new WeiXinPay();
			wpay.product_id = paymentinfo.getfOrderCode();
			wpay.out_trade_no = paymentinfo.getfOrderCode();
			wpay.body = paymentinfo.getfOrderName();
			wpay.total_fee = Math.round(paymentinfo.getMoney() * 100);
			String content = wpay.getCodeUrl();
			File file = ZxingBarCode.enCode(content, 300, 300);
			FileInputStream is = new FileInputStream(file);
			response.setContentType("image/png");
			response.addHeader("Content-Length", "" + file.length());
			OutputStream outs = response.getOutputStream();
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			is.close();
			// 清空response
			response.reset();
			outs.write(buffer);
			outs.flush();
			file.delete();// 删除临时文件
			outs.close();
		} catch (Exception e) {
		}
	}

}
