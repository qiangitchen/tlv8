package com.tlv8.docs.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

public class ClientUtils {
	static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	public static String connectString(String url, Map<String, String> params) {
		String result = null;
		try {
			result = convertStreamToString(connectStream(url, params));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static InputStream connectStream(String url, Map<String, String> params) throws Exception {
		InputStream content = null;
		HttpURLConnection conn = connectConn(url, params);
		int resCode = conn.getResponseCode();

		if (resCode == 200)
			content = conn.getInputStream();
		else if (resCode == 500) {
			throw new Exception("内容不存在!");
		}
		return content;
	}

	public static String connectDisposition(String url, Map<String, String> params) throws Exception {
		String contentDis = null;
		HttpURLConnection conn = connectConn(url, params);
		int resCode = conn.getResponseCode();

		if (resCode == 200)
			contentDis = conn.getHeaderField("Content-Disposition");
		else if (resCode == 500) {
			throw new Exception("内容不存在!");
		}
		return contentDis;
	}

	@SuppressWarnings("rawtypes")
	public static HttpURLConnection connectConn(String url, Map<String, String> params) throws Exception {
		HttpURLConnection conn = null;
		String path = url;
		if (path.indexOf("?") < 0) {
			path = path + "?temp=" + UUID.randomUUID().toString().replace("-", "");
		}
		Set set = params.keySet();
		for (Iterator it = set.iterator(); it.hasNext();) {
			String mapkey = (String) it.next();
			path = path + "&" + mapkey + "=" + (String) params.get(mapkey);
		}
		URL myURL = new URL(path);

		if (myURL.getProtocol().toLowerCase().equals("https")) {
			trustAllHosts();
			HttpsURLConnection https = (HttpsURLConnection) myURL.openConnection();
			https.setHostnameVerifier(DO_NOT_VERIFY);
			conn = https;
		} else {
			conn = (HttpURLConnection) myURL.openConnection();
		}
		conn.setDoInput(true);
		conn.setDoOutput(true);

		conn.setConnectTimeout(30000);

		conn.setRequestMethod("POST");
		conn.connect();
		return conn;
	}

	private static void trustAllHosts() {
		TrustManager[] trustAllCerts = { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
		} };
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new SecureRandom());

			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String convertStreamToString(InputStream is) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String line = null;
			try {
				while ((line = reader.readLine()) != null)
					sb.append(line);
			} catch (IOException e) {
				e.printStackTrace();
				try {
					is.close();
				} catch (IOException ee) {
					ee.printStackTrace();
				}
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return sb.toString();
	}

	public static Document transeXml(String xmlstr) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
}
