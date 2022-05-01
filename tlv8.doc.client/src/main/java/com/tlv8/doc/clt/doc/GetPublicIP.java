package com.tlv8.doc.clt.doc;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JOptionPane;

/**
 * GetPublicIP 类
 * 
 * @author YeeYang
 * 
 * @version 1.0
 * 
 */
public class GetPublicIP {

	/**/
	/**
	 * 主程序入口
	 * 
	 * @param args
	 *            输入参数数组
	 */
	public static void main(String[] args) {
		String pIP = getIP("http://www.bliao.com/ip.phtml", "IP_Temp.tmp");
		JOptionPane.showConfirmDialog(null, pIP, "您的外网IP地址",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
	}

	/**/
	/**
	 * 获取已下载的文件里的字符串(IP地址)
	 * 
	 * @return 获取的外网IP地址
	 * @throws Exception
	 */
	public static String getIP(String urlStr, String tempSaveStr) {

		// 下载操作 - 开始 ：下载网络文件获取相关IP地址并保存为临时文件IP.shtml
		int chByte = 0; // 读入输入流的数据长度
		URL url = null; // 网络的url地址
		HttpURLConnection httpConn = null; // http连接
		InputStream in = null; // 输入流
		FileOutputStream out = null; // 文件输出流
		try {
			url = new URL(urlStr);
			httpConn = (HttpURLConnection) url.openConnection();
			HttpURLConnection.setFollowRedirects(true);
			httpConn.setRequestMethod("GET");
			httpConn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");

			in = httpConn.getInputStream();
			out = new FileOutputStream(new File("tempSaveStr"));

			chByte = in.read();
			while (chByte != -1) {
				out.write(chByte);
				// System.out.println(chByte);
				chByte = in.read();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
				httpConn.disconnect();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		// 下载操作 - 结束

		// 获取IP操作 - 开始 : 从临时文件IP.shtml中读取IP地址
		String IP = null;
		try {
			BufferedReader br = new BufferedReader(
					new FileReader("tempSaveStr"));
			IP = br.readLine();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取IP操作 - 结束

		// 删除操作 - 开始 ：删除临时文件IP.shtml
		try {
			java.io.File myDelFile = new java.io.File("tempSaveStr");
			myDelFile.delete();
		} catch (Exception e) {
			System.out.println("Wrong Del");
			e.printStackTrace();
		}
		// 删除操作 - 结束

		return IP;
	}
}
