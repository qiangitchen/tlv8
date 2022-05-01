package com.tlv8.monitor.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.util.FormatUtil;

@Controller
@RequestMapping("/monitor")
public class GetServicesNetController {
	/**
	 * 获取服务器Net信息
	 */

	@ResponseBody
	@RequestMapping("/getServicesNet")
	public String execute() throws Exception {
		StringBuffer strbf = new StringBuffer();
		strbf.append("<style>td{border-bottom:1px dotted blue;font-size:12px;}</style>");
		SystemInfo si = new SystemInfo();
		HardwareAbstractionLayer hal = si.getHardware();
		List<NetworkIF> networkIFs = hal.getNetworkIFs();
		for (NetworkIF net : networkIFs) {
			String name = net.getName();
			strbf.append(
					"<table width='98%' style='table-layout:fixed;border:1px solid gray;float:left;margin-left:5px;margin-top:5px;'>");
			strbf.append("<tr><td width='200px'></td><td></td></tr>");
			strbf.append("<tr><td>网络设备名:    </td><td>" + net.getName() + "</td></tr>");// 网络设备名
			strbf.append("<tr><td>MAC地址:    </td><td>" + net.getMacaddr() + "</td></tr>");// 网络设备名
			strbf.append("<tr><td>IPv4:    </td><td>" + Arrays.toString(net.getIPv4addr()) + "</td></tr>");// IP地址
			strbf.append("<tr><td>IPv6:    </td><td>" + Arrays.toString(net.getIPv6addr()) + "</td></tr>");// 子网掩码
			boolean hasData = net.getBytesRecv() > 0 || net.getBytesSent() > 0 || net.getPacketsRecv() > 0
					|| net.getPacketsSent() > 0;
			if (hasData) {
				strbf.append("<tr><td>" + name + "接收的总包裹数:</td><td>" + net.getPacketsRecv() + "</td></tr>");// 接收的总包裹数
				strbf.append("<tr><td>" + name + "发送的总包裹数:</td><td>" + net.getPacketsSent() + "</td></tr>");// 发送的总包裹数
				strbf.append("<tr><td>" + name + "接收到的总字节数:</td><td>" + FormatUtil.formatBytes(net.getBytesRecv())
						+ "</td></tr>");// 接收到的总字节数
				strbf.append("<tr><td>" + name + "发送的总字节数:</td><td>" + FormatUtil.formatBytes(net.getBytesSent())
						+ "</td></tr>");// 发送的总字节数
				strbf.append("<tr><td>" + name + "接收到的错误包数:</td><td>" + net.getInErrors() + "</td></tr>");// 接收到的错误包数
				strbf.append("<tr><td>" + name + "发送数据包时的错误数:</td><td>" + net.getOutErrors() + "</td></tr>");// 发送数据包时的错误数
				strbf.append("<tr><td>" + name + "接收时丢弃的包数:</td><td>" + net.getInDrops() + "</td></tr>");// 接收时丢弃的包数
				// strbf.append("<tr><td>" + name + "发送时丢弃的包数:</td><td>" + net.getOutErrors() +
				// "</td></tr>");// 发送时丢弃的包数
			}
			strbf.append("</table>");
		}
		return strbf.toString();
	}
}
