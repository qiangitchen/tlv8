package com.tlv8.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.Data;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

@Controller
@RequestMapping("/monitor")
public class MemoryInfoController {
	/**
	 * 获取服务器内存使用率信息[json]
	 */
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/MemoryInfo")
	public Object execute() throws Exception {
		SystemInfo si = new SystemInfo();
		HardwareAbstractionLayer hal = si.getHardware();
		GlobalMemory memory = hal.getMemory();
		JSONObject json = new JSONObject();
		long useable = memory.getAvailable();
		long total = memory.getTotal();
		long used = total - useable;
		json.put("used", used);
		json.put("total", total);
		Double dused = Double.parseDouble(String.valueOf(used));
		Double dtotal = Double.parseDouble(String.valueOf(total));
		long usedpex = Math.round(dused / dtotal * 100);
		json.put("usedpex", usedpex);
		data.setData(json.toString());
		data.setFlag("true");
		return data;
	}

}
