package com.tlv8.pay.alipay;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tlv8.pay.PaymentInfo;
import com.tlv8.pay.alipay.sign.RSA;
import com.tlv8.system.bean.ContextBean;

/**
 * 手机APP调用支付宝支付
 */
@Controller
public class AlipayAppPayAction {
	private String eledeId;
	private int modelId;
	private int userId = -1;
	private String userName;

	@RequestMapping("/alipayAppPayAction")
	public void execute(HttpServletRequest request, HttpServletResponse response, String ordername, double money)
			throws Exception {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		String result = "NONE";
		PrintWriter outhtml = response.getWriter();
		ContextBean context = ContextBean.getContext(request);
		try {
			PaymentInfo paymentinfo = new PaymentInfo();
			paymentinfo.setfEledeId(eledeId);
			paymentinfo.setfOrderName(ordername);
			paymentinfo.setMoney(money);
			paymentinfo.setUserId(context.getCurrentPersonID());
			paymentinfo.setUserName(context.getCurrentPersonName());
			paymentinfo.setModelId(modelId);
			paymentinfo.Insert();

			String ap = paySign(paymentinfo.getfOrderName(), paymentinfo.getfOrderName(), paymentinfo.getMoney(),
					paymentinfo.getfOrderCode(), AlipayConfig.payUrl);
			if ("".equals(ap)) {
				result = "生成订单失败！";
			} else if (paymentinfo.getfState() == 1) {
				result = "该条课程已付款！";
			} else {
				result = ap;
			}
			outhtml.write(result);
		} catch (Exception e) {
			outhtml.write("");
		}
		outhtml.close();
	}

	/**
	 * paySign. 创建支付签名
	 * 
	 */
	public String paySign(String title, String describe, double cost, String mercial_no, String payUrl) {

		String costs = String.valueOf(cost);
		String orderInfo = getOrderInfo(title, describe, costs, mercial_no, payUrl);

		/**
		 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
		 */
		String sign = RSA.sign(orderInfo, AlipayConfig.mob_key, "UTF-8");
		try {
			/**
			 * 仅需对sign 做URL编码
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/**
		 * 完整的符合支付宝参数规范的订单信息
		 */
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + "sign_type=\"RSA\"";

//		Response response = new Response();
//		response.setMessage("success");
//		response.setObject(payInfo);

//		return JsonUtil.beanToString(response);
		return payInfo;
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	private static String getOrderInfo(String subject, String body, String price, String mercial_no, String payUrl) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + AlipayConfig.partner + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + AlipayConfig.seller_email + "\"";

		// 商户网站唯一订单号 (可使用数据库id字段的值)
		orderInfo += "&out_trade_no=" + "\"" + mercial_no + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		// orderInfo += "&notify_url=" + "\"" +
		// "http://ganhualin.51vip.biz/rms/Business/Pay/Alipay/notify_url.jsp" + "\"";
		orderInfo += "&notify_url=" + "\"" + payUrl + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认12小时，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"12h\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		// orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	public String getEledeId() {
		return eledeId;
	}

	public void setEledeId(String eledeId) {
		this.eledeId = eledeId;
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
