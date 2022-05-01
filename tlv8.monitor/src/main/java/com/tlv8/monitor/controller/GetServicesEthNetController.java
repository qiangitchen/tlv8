package com.tlv8.monitor.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

@Controller
@RequestMapping("/monitor")
public class GetServicesEthNetController {
	/**
	 * 获取服务器EtherNet信息
	 */

	@ResponseBody
	@RequestMapping("/getServicesEthNet")
	public String execute() throws Exception {
		StringBuffer strbf = new StringBuffer();
		strbf.append("<style>td{border-bottom:1px dotted #c5c3cf;font-size:12px;}</style>");
		SystemInfo si = new SystemInfo();
		HardwareAbstractionLayer hal = si.getHardware();
		List<NetworkIF> networkIFs = hal.getNetworkIFs();
		for (NetworkIF net : networkIFs) {
			strbf.append("<table width='98%' style='table-layout:fixed;border:1px solid blue;float:left;margin-left:5px;margin-top:15px;'>");
			strbf.append("<tr><td width='200px'>" +net.getName() + "IPv4:</td><td>" + Arrays.toString(net.getIPv4addr())+ "</td></tr>");// IP地址
			strbf.append("<tr><td width='200px'>" +net.getName() + "IPv6:</td><td>" + Arrays.toString(net.getIPv6addr())+ "</td></tr>");// IP地址
//			strbf.append("<tr><td>" +net.getName() + "网关广播地址:</td><td>" + net.getBroadcast()+ "</td></tr>");// 网关广播地址
			strbf.append("<tr><td>" +net.getName() + "网卡MAC地址:</td><td>" + net.getMacaddr()+ "</td></tr>");// 网卡MAC地址
//			strbf.append("<tr><td>" +net.getName() + "子网掩码:</td><td>" + net.getNetmask()+ "</td></tr>");// 子网掩码
			strbf.append("<tr><td>" +net.getName() + "网卡描述信息:</td><td>" + net.getDisplayName()+ "</td></tr>");// 网卡描述信息
			strbf.append("<tr><td>" +net.getName() + "网卡类型</td><td>" + net.getIfType()+ "</td></tr>");//
			strbf.append("</table>");
		}
		return strbf.toString();
	}
}
