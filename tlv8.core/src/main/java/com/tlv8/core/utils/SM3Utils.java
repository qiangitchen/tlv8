package com.tlv8.core.utils;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Arrays;

public class SM3Utils {
	private static final String ENCODING = "UTF-8";
	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	/**
	 * sm3算法加密
	 * 
	 * @explain
	 * @param paramStr 待加密字符串
	 * @return 返回加密后，固定长度=32的16进制字符串
	 */
	public static String encrypt(String paramStr) {
		// 将返回的hash值转换成16进制字符串
		String resultHexString = "";
		try {
			// 将字符串转换成byte数组
			byte[] srcData = paramStr.getBytes(ENCODING);
			// 调用hash()
			byte[] resultHash = hash(srcData);
			// 将返回的hash值转换成16进制字符串
			resultHexString = ByteUtils.toHexString(resultHash);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return resultHexString;
	}

	/**
	 * 返回长度=32的byte数组
	 * 
	 * @explain 生成对应的hash值
	 * @param srcData
	 * @return
	 */
	public static byte[] hash(byte[] srcData) {
		SM3Digest digest = new SM3Digest();
		digest.update(srcData, 0, srcData.length);
		byte[] hash = new byte[digest.getDigestSize()];
		digest.doFinal(hash, 0);
		return hash;
	}

	/**
	 * sm3算法加密
	 * 
	 * @explain
	 * @param paramStr 待加密字符串
	 * @param key      密钥
	 * @return 返回加密后，固定长度=32的16进制字符串
	 */
	public static String encryptPlus(String paramStr, String key) {
		// 将返回的hash值转换成16进制字符串
		String resultHexString = "";
		try {
			// 将字符串转换成byte数组
			byte[] srcData = paramStr.getBytes(ENCODING);
			// 调用hash()
			byte[] resultHash = hmac(srcData, key.getBytes(ENCODING));
			// 将返回的hash值转换成16进制字符串
			resultHexString = ByteUtils.toHexString(resultHash);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return resultHexString;
	}

	/**
	 * 通过密钥进行加密
	 * 
	 * @explain 指定密钥进行加密
	 * @param key     密钥
	 * @param srcData 被加密的byte数组
	 * @return
	 */
	public static byte[] hmac(byte[] key, byte[] srcData) {
		KeyParameter keyParameter = new KeyParameter(key);
		SM3Digest digest = new SM3Digest();
		HMac mac = new HMac(digest);
		mac.init(keyParameter);
		mac.update(srcData, 0, srcData.length);
		byte[] result = new byte[mac.getMacSize()];
		mac.doFinal(result, 0);
		return result;
	}

	/**
	 * 判断源数据与加密数据是否一致
	 * 
	 * @explain 通过验证原数组和生成的hash数组是否为同一数组，验证2者是否为同一数据
	 * @param srcStr       原字符串
	 * @param sm3HexString 16进制字符串
	 * @return 校验结果
	 */
	public static boolean verify(String srcStr, String sm3HexString) {
		boolean flag = false;
		try {
			byte[] srcData = srcStr.getBytes(ENCODING);
			byte[] sm3Hash = ByteUtils.fromHexString(sm3HexString);
			byte[] newHash = hash(srcData);
			if (Arrays.equals(newHash, sm3Hash)) {
				flag = true;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static void main(String[] args) {
//		String str = "{\"test\":\"001\"}";
//		// 秘钥
//		String key = "key";
//		String hex = SM3Utils.encryptPlus(str, key);
//		System.out.println("参数:" + str);
//		System.out.println("密文:" + hex);
		System.out.println(encrypt("123456"));
	}
}
