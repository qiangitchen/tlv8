package com.tlv8.monitor.controller;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.Data;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.HardwareAbstractionLayer;

@Controller
@RequestMapping("/monitor")
public class CPUInfoController {
	/**
	 * 获取服务器CPU信息[json]
	 */
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/CPUInfo")
	public Object execute() throws Exception {
		SystemInfo si = new SystemInfo();
		HardwareAbstractionLayer hal = si.getHardware();
		CentralProcessor processor = hal.getProcessor();
		JSONObject json = JSON.parseObject(JSON.toJSONString(processor));
		json.put("name", processor.getProcessorIdentifier().getName());
		long[] prevTicks = processor.getSystemCpuLoadTicks();
		TimeUnit.SECONDS.sleep(1);
		long[] ticks = processor.getSystemCpuLoadTicks();
		long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
		long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
		long sys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
		long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
		long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
		long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
		long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
		long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
		long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;
		json.put("user", user);
		json.put("nice", nice);
		json.put("sys", sys);
		json.put("idle", idle);
		json.put("iowait", iowait);
		json.put("irq", irq);
		json.put("softirq", softirq);
		json.put("steal", steal);
		json.put("totalCpu", totalCpu);
		DecimalFormat df = new DecimalFormat("#.##%");
		json.put("sysused", df.format(sys * 1.0 / totalCpu));
		json.put("userused", df.format(user * 1.0 / totalCpu));
		double tusep = 1.0 - (idle * 1.0 / totalCpu);
		json.put("usedpex", df.format(tusep));
		data.setData(json.toString());
		data.setFlag("true");
		return data;
	}
}
