package com.tlv8.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlv8.base.utils.AesEncryptUtil;
import com.tlv8.base.utils.StringUtils;

public class RequestParams {
	private static final Logger logger = LoggerFactory.getLogger(RequestParams.class);
	private Map<String, RequestParam> params = new HashMap<>();
	private HttpServletRequest request;

	public RequestParams(HttpServletRequest request) {
		this.request = request;
		String query = request.getParameter("query");
		logger.debug(query);
		if (StringUtils.isNotEmpty(query)) {
			query = CodeUtils.getDoubleDecode(query);
			query = AesEncryptUtil.desEncrypt(query);
			init(query);
		}
	}

	public RequestParams(HttpServletRequest request, String expstr) {
		this.request = request;
		logger.debug(expstr);
		expstr = CodeUtils.getDoubleDecode(expstr);
		expstr = AesEncryptUtil.desEncrypt(expstr);
		init(expstr);
	}

	private void init(String expstr) {
		logger.debug(expstr);
		expstr = CodeUtils.getDoubleDecode(expstr);
		logger.debug(expstr);
		String[] pars = expstr.split("&");
		for (int i = 0; i < pars.length; i++) {
			try {
				RequestParam param = new RequestParam(pars[i]);
				params.put(param.getName(), param);
			} catch (Exception e) {
			}
		}
	}

	public String getParamValue(String name) {
		String value = request.getParameter(name);
		if (StringUtils.isEmpty(value)) {
			try {
				value = params.get(name).getValue();
			} catch (Exception e) {
				logger.debug("参数：" + name + ",获取值失败~");
			}
		}
		value = CodeUtils.getDoubleDecode(value);
		return value;
	}

}
