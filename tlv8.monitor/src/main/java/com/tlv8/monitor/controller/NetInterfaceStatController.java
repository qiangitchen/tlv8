package com.tlv8.monitor.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.Data;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

@Controller
@RequestMapping("/monitor")
public class NetInterfaceStatController {

	/**
	 * 获取服务器网络收发包信息[json]
	 */

	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/NetInterfaceStat")
	public Object execute() throws Exception {
		SystemInfo si = new SystemInfo();
		HardwareAbstractionLayer hal = si.getHardware();
		List<NetworkIF> networkIFs = hal.getNetworkIFs();
		JSONObject json = new JSONObject();
		for (NetworkIF net : networkIFs) {
			boolean hasData = net.getBytesRecv() > 0 || net.getBytesSent() > 0 || net.getPacketsRecv() > 0
					|| net.getPacketsSent() > 0;
			if (hasData) {
				json.put("rxPackets", net.getPacketsRecv());
				json.put("txPackets", net.getPacketsSent());
			}
		}
		data.setData(json.toString());
		data.setFlag("true");
		return data;
	}

}
