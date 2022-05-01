package com.tlv8.pay.weixin;

import java.io.File;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import com.tlv8.base.utils.IDUtils;

public class ZxingBarCode {

	@SuppressWarnings("deprecation")
	public static File enCode(String content, int width, int height) {
		File file = new File(IDUtils.getGUID() + ".png");
		try {
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 容错级别
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
					BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
			MatrixToImageWriter.writeToFile(bitMatrix, "png", file);// 输出图像
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

}
