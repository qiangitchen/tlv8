package com.tlv8.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.tlv8.base.db.DBUtils;

public class SendMail {
	// 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
	// PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）,
	// 对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
	private String myEmailAccount = "tlv8mail@163.com";
	private String myEmailPassword = "GHTJPRMRUHEKQSGN";
	// 发件人的名称（昵称）
	private String myEmailName = "tlv8";

	// 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
	private String myEmailSMTPHost = "smtp.163.com";
	// PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
	// 如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
	// 取消下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
	/*
	 * // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接, // 需要改为对应邮箱的 SMTP
	 * 服务器的端口, 具体可查看对应邮箱服务的帮助, // QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
	 */
	private String myEmailSMTPPort = "465";
	private boolean myEmailSMTPSSL = true;

	private String myEmailSMTPProtocol = "smtp";

	static SendMail instance;

	public static SendMail getInstance() {
		if (instance == null) {
			instance = new SendMail();
		}
		return instance;
	}

	private SendMail() {
		try {
			String sql = "select SMAIL,SACCOUNT,SNAME,STYPE,SSENDHOST,SSENDPOST,SISSSL from sa_mailset where SISPUB = 'true'";
			List<Map<String, Object>> list = DBUtils.selectForList("system", sql);
			if (list.size() > 0) {
				Map<String, Object> map = list.get(0);
				init(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SendMail(String userid) {
		try {
			String sql = "select SMAIL,SACCOUNT,SNAME,STYPE,SSENDHOST,SSENDPOST,SISSSL from sa_mailset where FCREATEPSNID = ?";
			List<Object> p = new ArrayList<Object>();
			p.add(userid);
			List<Map<String, Object>> list = DBUtils.selectList("system", sql, p);
			if (list.size() > 0) {
				Map<String, Object> map = list.get(0);
				init(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init(Map<String, Object> map) {
		myEmailAccount = (String) map.get("SMAIL");
		myEmailPassword = (String) map.get("SACCOUNT");
		myEmailName = (String) map.get("SNAME");
		if (myEmailName == null || "".equals(myEmailName)) {
			myEmailName = "tlv8";
		}
		//myEmailSMTPProtocol = (String) map.get("STYPE");
		myEmailSMTPHost = (String) map.get("SSENDHOST");
		myEmailSMTPPort = (String) map.get("SSENDPOST");
		myEmailSMTPSSL = Boolean.valueOf((String) map.get("SSENDPOST"));
	}

	/**
	 * 
	 * @param receiveName        收件人名称
	 * @param receiveMailAccount 收件邮箱地址
	 * @param mailTitle          邮件主题
	 * @param mailBody           邮件内容
	 * @throws Exception
	 */
	public void send(String receiveName, String receiveMailAccount, String mailTitle, String mailBody)
			throws Exception {
		// 1. 创建参数配置, 用于连接邮件服务器的参数配置
		Properties props = new Properties(); // 参数配置
		props.setProperty("mail.transport.protocol", myEmailSMTPProtocol); // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.smtp.host", myEmailSMTPHost); // 发件人的邮箱的 SMTP 服务器地址
		props.setProperty("mail.smtp.auth", "true"); // 需要请求认证

		if (myEmailSMTPSSL) {
			props.setProperty("mail.smtp.port", myEmailSMTPPort);
			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.socketFactory.port", myEmailSMTPPort);
		}

		// 2. 根据配置创建会话对象, 用于和邮件服务器交互
		Session session = Session.getInstance(props);
		// 设置为debug模式, 可以查看详细的发送 log
		session.setDebug(true);

		// 3. 创建一封邮件
		MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount, receiveName, mailTitle,
				mailBody);

		// 4. 根据 Session 获取邮件传输对象
		Transport transport = session.getTransport();

		// 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
		//
		// PS_01: 如果连接服务器失败, 都会在控制台输出相应失败原因的log。
		// 仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接,
		// 根据给出的错误类型到对应邮件服务器的帮助网站上查看具体失败原因。
		//
		// PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
		// (1) 邮箱没有开启 SMTP 服务;
		// (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
		// (3) 邮箱服务器要求必须要使用 SSL 安全连接;
		// (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
		// (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
		//
		transport.connect(myEmailAccount, myEmailPassword);

		// 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人,
		// 密送人
		transport.sendMessage(message, message.getAllRecipients());

		// 7. 关闭连接
		transport.close();
	}

	/**
	 * 创建一封只包含文本的简单邮件
	 *
	 * @param session     和服务器交互的会话
	 * @param sendMail    发件人邮箱
	 * @param receiveMail 收件人邮箱
	 * @param receiveName 收件人邮箱
	 * @param mailTitle   邮件主题
	 * @param mailBody    邮件内容
	 * @return
	 * @throws Exception
	 */
	private MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String receiveName,
			String mailTitle, String mailBody) throws Exception {
		// 1. 创建一封邮件
		MimeMessage message = new MimeMessage(session);

		// 2. From: 发件人
		message.setFrom(new InternetAddress(sendMail, myEmailName, "UTF-8"));

		// 3. To: 收件人（可以增加多个收件人、抄送、密送）
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receiveName, "UTF-8"));

		// 4. Subject: 邮件主题
		message.setSubject(mailTitle, "UTF-8");

		// 5. Content: 邮件正文（可以使用html标签）
		message.setContent(mailBody, "text/html;charset=UTF-8");
		// 6. 设置发件时间
		message.setSentDate(new Date());

		// 7. 保存设置
		message.saveChanges();

		return message;
	}
}
