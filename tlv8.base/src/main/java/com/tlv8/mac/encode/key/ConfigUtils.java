package com.tlv8.mac.encode.key;

import java.security.Key;

public class ConfigUtils {
	protected static Key getKey() {
		try {
			ConfigUtils cof = new ConfigUtils();
			return cof.getKey(traceKey().toString().getBytes());
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * 
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws java.lang.Exception
	 */
	private Key getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];
		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
		return key;
	}

	/**
	 * 十六进制字符串转化为2进制
	 * 
	 * @param hex
	 * @return
	 */
	public byte[] hex2byte(String hex) {
		byte[] ret = new byte[8];
		byte[] tmp = hex.getBytes();
		for (int i = 0; i < 8; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	/**
	 * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
	 * 
	 * @param src0
	 *            byte
	 * @param src1
	 *            byte
	 * @return byte
	 */
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	private static String traceKey() {
		StringBuffer strb = new StringBuffer();
		strb.append("A");
		strb.append("7");
		strb.append("9");
		strb.append("C");
		strb.append("O");
		strb.append("0");
		strb.append("9");
		strb.append("6");
		strb.append("E");
		strb.append("C");
		strb.append("D");
		strb.append("E");
		strb.append("B");
		strb.append("C");
		strb.append("E");
		strb.append("9");
		strb.append("2");
		strb.append("3");
		return strb.toString();
	}
}
