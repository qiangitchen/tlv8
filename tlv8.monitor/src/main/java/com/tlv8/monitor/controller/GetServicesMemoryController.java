package com.tlv8.monitor.controller;

import java.text.DecimalFormat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.FormatUtil;

@Controller
@RequestMapping("/monitor")
public class GetServicesMemoryController {
	/**
	 * 获取服务器内存信息
	 */

	@ResponseBody
	@RequestMapping("/getServicesMemory")
	public String execute() throws Exception {
		StringBuffer strbf = new StringBuffer();
		strbf.append("<style>td{border-bottom:1px dotted blue;font-size:12px;}</style>");
		SystemInfo si = new SystemInfo();
		HardwareAbstractionLayer hal = si.getHardware();
		GlobalMemory memory = hal.getMemory();
		long useable = memory.getAvailable();
		long total = memory.getTotal();
		long used = total - useable;
		strbf.append("<table width='100%' style='table-layout:fixed;'>");
		strbf.append("<tr><td width='200px'>内存总量:    </td><td>" + FormatUtil.formatBytes(total) + "</td></tr>");
		strbf.append("<tr><td>内存已使用量:    </td><td>" + FormatUtil.formatBytes(used) + "</td></tr>");
		strbf.append("<tr><td>内存可使用量:    </td><td>" + FormatUtil.formatBytes(useable) + "</td></tr>");
		strbf.append("<tr><td>交换区总量:    </td><td>" + FormatUtil.formatBytes(memory.getVirtualMemory().getSwapTotal())
				+ "</td></tr>");
		strbf.append("<tr><td>当前交换区使用量:    </td><td>" + FormatUtil.formatBytes(memory.getVirtualMemory().getSwapUsed())
				+ "</td></tr>");
		Double usedpex = (Double.valueOf(String.valueOf(used)) / Double.valueOf(String.valueOf(total))) * 100D;
		String color = "green";
		if (usedpex > 50) {
			color = "blue";
		}
		if (usedpex > 90) {
			color = "red";
		}
		DecimalFormat df = new DecimalFormat("0.00");
		strbf.append("<tr><td style='font-weight:bold;'>当前使用率:    </td><td style='font-weight:bold;color:" + color
				+ ";'>" + df.format(usedpex) + "%</td></tr>");
		strbf.append("</table>");
		return strbf.toString();
	}
}
