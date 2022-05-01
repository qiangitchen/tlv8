package com.tlv8.monitor.controller;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import oshi.SystemInfo;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

@Controller
@RequestMapping("/monitor")
public class GetServicesFilesystemController {
	/**
	 * 获取服务器硬盘信息
	 */

	@ResponseBody
	@RequestMapping("/getServicesFilesystem")
	public String execute() throws Exception {
		StringBuffer strbf = new StringBuffer();
		strbf.append("<style>td{border-bottom:1px dotted blue;font-size:12px;}</style>");
		SystemInfo si = new SystemInfo();
		OperatingSystem os = si.getOperatingSystem();
		FileSystem fileSystem = os.getFileSystem();
		List<OSFileStore> fsArray = fileSystem.getFileStores();
		for (OSFileStore fs : fsArray) {
			if (fs.getDescription().contains("CD")) {
				continue;
			}
			long usable = fs.getUsableSpace();
			long total = fs.getTotalSpace();
			long used = total - usable;
			strbf.append(
					"<table width='280px' style='table-layout:fixed;border:1px solid blue;float:left;margin-left:5px;margin-top:5px;'>");
			// strbf.append("<tr><td colspan='2' align='center'
			// style='background:#cedfdd;font-weight:bold;'>分区的盘符名称"
			// + i + "</td></tr>");
			strbf.append("<tr><td>盘符名称:    </td><td>" + fs.getName() + "</td></tr>");
			strbf.append("<tr><td>盘符描述:    </td><td>" + fs.getDescription() + "</td></tr>");
			strbf.append("<tr><td>盘符标志:    </td><td>" + fs.getLabel() + "</td></tr>");//
			// 文件系统类型，比如 FAT32、NTFS
			strbf.append("<tr><td>盘符类型:    </td><td>" + fs.getType() + "</td></tr>");
			// 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
			// strbf.append("<tr><td>盘符类型名: </td><td>" + fs.getType() +
			// "</td></tr>");
			// strbf.append("<tr><td>盘符文件系统类型: </td><td>" + fs.getOptions() +
			// "</td></tr>");
			strbf.append("<tr><td>共    </td><td>" + FormatUtil.formatBytes(total) + "</td></tr>");
			strbf.append("<tr><td>可用:    </td><td>" + FormatUtil.formatBytes(usable) + "</td></tr>");
			// strbf.append("<tr><td>可用大小: </td><td>" +
			// FormatUtil.formatBytes(fs.getFreeInodes()) + "</td></tr>");
			strbf.append("<tr><td>已用:    </td><td>" + FormatUtil.formatBytes(used) + "</td></tr>");
			double usePercent = 100d * used / total;
			String color = "green";
			if (usePercent > 70) {
				color = "blue";
			}
			if (usePercent > 90) {
				color = "red";
			}
			DecimalFormat df = new DecimalFormat("#.##");
			strbf.append("<tr><td>资源的利用率:    </td><td style='font-weight:bold;color:" + color + ";'>"
					+ df.format(usePercent) + "%</td></tr>");
			strbf.append("</table>");
		}
		return strbf.toString();
	}
}
