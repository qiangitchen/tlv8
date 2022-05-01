package com.tlv8.system.bean;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class HttpBean {
	private Map<String, HttpClient> map = Collections
			.synchronizedMap(new HashMap());

	public HttpBean() {
		clear();
	}

	public HttpClient getClient(String key) {
		synchronized (this.map) {
			if (!this.map.containsKey(key)) {
				HttpClient httpClient = new HttpClient(
						new MultiThreadedHttpConnectionManager());
				this.map.put(key, httpClient);
			}
			return (HttpClient) this.map.get(key);
		}
	}

	public void clear() {
		this.map.clear();
	}
}
