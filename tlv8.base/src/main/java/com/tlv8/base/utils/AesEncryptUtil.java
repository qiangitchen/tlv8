package com.tlv8.base.utils;

/**
 * AES 128bit 加密解密工具类
 */

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesEncryptUtil {
	// 使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同！
	private static String KEY = "justep20180829yn";
	private static String IV = "justep20180829yn";
	private static Cipher encipher;
	private static Cipher decipher;

	static {
		try {
			encipher = Cipher.getInstance("AES/CBC/NoPadding");// "算法/模式/补码方式"
			decipher = Cipher.getInstance("AES/CBC/NoPadding");// "算法/模式/补码方式"
			SecretKeySpec keyspec = new SecretKeySpec(KEY.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
			encipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			decipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
		} catch (Exception e) {
		}
	}

	/**
	 * 加密方法
	 * 
	 * @param data 要加密的数据
	 * @return 加密的结果
	 * @throws Exception
	 */
	public static String encrypt(String data) throws Exception {
		try {
//			int blockSize = encipher.getBlockSize();
//			byte[] dataBytes = data.getBytes("UTF-8");
//			int plaintextLength = dataBytes.length;
//			if (plaintextLength % blockSize != 0) {
//				plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
//			}
//			byte[] plaintext = new byte[plaintextLength];
//			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
//			for (int i = dataBytes.length; i < plaintextLength; i++) {
//				plaintext[i] = (" ").getBytes()[0];
//			}
//			byte[] encrypted = encipher.doFinal(plaintext);
//			return new Base64().encodeToString(encrypted);
			return new Base64().encodeToString(data.getBytes("UTF-8"));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 解密方法
	 * 
	 * @param data 要解密的数据
	 * @return 解密的结果
	 * @throws Exception
	 */
	public static String desEncrypt(String data) {
		try {
			byte[] encrypted1 = new Base64().decode(data.getBytes("UTF-8"));
			// encrypted1 = decipher.doFinal(encrypted1);
			String originalString = new String(encrypted1, "UTF-8").trim();
			return originalString;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 测试
	 */
	public static void main(String args[]) throws Exception {
		String test = "123456785656566565哈哈";
		String data = null;
		data = encrypt(test);
		System.out.println(data);
		System.out.println(desEncrypt(data));
		// System.out.println(desEncrypt("lDfWCdj/ag4N5XgBv0QlnA=="));
	}
}
