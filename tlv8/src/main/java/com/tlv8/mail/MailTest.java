package com.tlv8.mail;

/**
 * 邮箱发送测试
 * 
 * @author chenqian
 *
 */
public class MailTest {

	public static void main(String[] args) {
		try {
			SendMail.getInstance().send("qianpou", "qianpou@163.com", "邮件测试", "这个一个测试的邮件【请忽略】");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
