package com.tlv8.monitor.controller;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.HardwareAbstractionLayer;

@Controller
@RequestMapping("/monitor")
public class GetServicesCPUController {
	/**
	 * 获取服务器CPU信息[html]
	 */

	@ResponseBody
	//@RequestMapping(value = "/getServicesCPU", produces = "application/json;charset=UTF-8")
	@RequestMapping("/getServicesCPU")
	public String execute() throws Exception {
		StringBuffer strbf = new StringBuffer();
		strbf.append("<style>td{border-bottom:1px dotted blue;font-size:12px;}</style>");
		SystemInfo si = new SystemInfo();
		HardwareAbstractionLayer hal = si.getHardware();
		CentralProcessor processor = hal.getProcessor();
		strbf.append(
				"<table width='100%' style='table-layout:fixed;border:1px solid green;float:left;margin-left:5px;margin-top:5px;'>");
		strbf.append("<tr><td width='100px'></td><td></td></tr>");
		strbf.append("<tr><td>CPU:        </td><td>" + processor.getProcessorIdentifier().getName() + "</td></tr>");
		strbf.append("<tr><td>CPU的数量:    </td><td>" + processor.getPhysicalPackageCount() + "</td></tr>");
		strbf.append("<tr><td>CPU核心数:    </td><td>" + processor.getPhysicalProcessorCount() + "</td></tr>");
		strbf.append("<tr><td>CPU逻辑数:    </td><td>" + processor.getLogicalProcessorCount() + "</td></tr>");
		strbf.append("<tr><td>CPU标识符:    </td><td>" + processor.getProcessorIdentifier() + "</td></tr>");
		long[] prevTicks = processor.getSystemCpuLoadTicks();
		// strbf.append("<tr><td colspan='2'>" + Arrays.toString(prevTicks) +
		// "</td></tr>");
		// 睡眠1s
		TimeUnit.SECONDS.sleep(1);
		long[] ticks = processor.getSystemCpuLoadTicks();
		// strbf.append("<tr><td colspan='2'>" + Arrays.toString(ticks) +
		// "</td></tr>");
		long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
		long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
		long sys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
		long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
		long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
		long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
		long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
		long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
		long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;
		// strbf.append("<tr><td colspan='2'>" + String.format(
		// "User: %.1f%% Nice: %.1f%% System: %.1f%% Idle: %.1f%% IOwait: %.1f%%
		// IRQ: %.1f%% SoftIRQ: %.1f%% Steal: %.1f%%%n",
		// 100d * user / totalCpu, 100d * nice / totalCpu, 100d * sys /
		// totalCpu, 100d * idle / totalCpu,
		// 100d * iowait / totalCpu, 100d * irq / totalCpu, 100d * softirq /
		// totalCpu, 100d * steal / totalCpu)
		// + "</td></tr>");
		double tusep = 1.0 - (idle * 1.0 / totalCpu);
		String color = "green";
		if (tusep > 20) {
			color = "blue";
		}
		if (tusep > 50) {
			color = "red";
		}
		DecimalFormat df = new DecimalFormat("#.##%");
		strbf.append("<tr><td>cpu系统使用率:    </td><td>" + df.format(sys * 1.0 / totalCpu) + "</td></tr>");
		strbf.append("<tr><td>cpu用户使用率:    </td><td>" + df.format(user * 1.0 / totalCpu) + "</td></tr>");
		strbf.append("<tr><td>cpu当前等待率:    </td><td>" + df.format(iowait * 1.0 / totalCpu) + "</td></tr>");
		strbf.append("<tr><td>cpu当前使用率:    </td><td style='font-weight:bold;color:" + color + ";'>"
				+ new DecimalFormat("#.##%").format(tusep) + "</td></tr>");
		strbf.append("</table>");
		return strbf.toString();
	}
}
