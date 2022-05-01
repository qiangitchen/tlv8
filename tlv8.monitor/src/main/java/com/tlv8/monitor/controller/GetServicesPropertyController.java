package com.tlv8.monitor.controller;

import java.net.InetAddress;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/monitor")
public class GetServicesPropertyController {
	/**
	 * 获取服务器信息
	 */

	@ResponseBody
	@RequestMapping("/getServicesProperty")
	public String execute() throws Exception {
		Runtime r = Runtime.getRuntime();
		Properties props = System.getProperties();
		InetAddress addr;
		addr = InetAddress.getLocalHost();
		String ip = addr.getHostAddress();
		Map<String, String> map = System.getenv();
		// String userName = map.get("USERNAME");// 获取用户名
		String computerName = map.get("COMPUTERNAME");// 获取计算机名
		String userDomain = map.get("USERDOMAIN");// 获取计算机域名
		if (computerName == null) {
			computerName = addr.getHostName();
		}
		StringBuffer strbf = new StringBuffer();
		strbf.append("<style>td{border-bottom:1px dotted blue;font-size:12px;}</style>");
		strbf.append("<table width='100%' style='table-layout:fixed;'>");
		strbf.append("<tr><td width='260px'>计算机名:    </td><td>" + computerName + "</td></tr>");
		// strbf.append("<tr><td>用户名: </td><td>" + userName + "</td></tr>");
		strbf.append("<tr><td>计算机域名:    </td><td>" + userDomain + "</td></tr>");
		strbf.append("<tr><td>本地ip地址:    </td><td>" + ip + "</td></tr>");
		strbf.append("<tr><td>本地主机名:    </td><td>" + addr.getHostName() + "</td></tr>");
		strbf.append("<tr><td>JVM可以使用的总内存:    </td><td>" + r.totalMemory() + "</td></tr>");
		strbf.append("<tr><td>JVM可以使用的剩余内存:    </td><td>" + r.freeMemory() + "</td></tr>");
		strbf.append("<tr><td>JVM可以使用的处理器个数:    </td><td>" + r.availableProcessors() + "</td></tr>");
		// strbf.append("<tr><td>Java的运行环境版本： </td><td>"
		// + props.getProperty("<tr><td>java.version") + "</td></tr>");
		// strbf.append("<tr><td>Java的运行环境供应商： </td><td>"
		// + props.getProperty("<tr><td>java.vendor") + "</td></tr>");
		// strbf.append("<tr><td>Java供应商的URL： </td><td>"
		// + props.getProperty("<tr><td>java.vendor.url") + "</td></tr>");
		strbf.append("<tr><td>Java的安装路径：    </td><td>" + props.getProperty("java.home") + "</td></tr>");
		strbf.append("<tr><td>Java的虚拟机规范版本：   </td><td>" + props.getProperty("java.vm.specification.version")
				+ "</td></tr>");
		strbf.append("<tr><td>Java的虚拟机规范供应商：    </td><td>" + props.getProperty("java.vm.specification.vendor")
				+ "</td></tr>");
		strbf.append(
				"<tr><td>Java的虚拟机规范名称：   </td><td>" + props.getProperty("java.vm.specification.name") + "</td></tr>");
		strbf.append("<tr><td>Java的虚拟机实现版本：    </td><td>" + props.getProperty("java.vm.version") + "</td></tr>");
		strbf.append("<tr><td>Java的虚拟机实现供应商：    </td><td>" + props.getProperty("java.vm.vendor") + "</td></tr>");
		strbf.append("<tr><td>Java的虚拟机实现名称：    </td><td>" + props.getProperty("java.vm.name") + "</td></tr>");
		strbf.append(
				"<tr><td>Java运行时环境规范版本：    </td><td>" + props.getProperty("java.specification.version") + "</td></tr>");
		// strbf.append("<tr><td>Java运行时环境规范供应商： </td><td>"
		// + props.getProperty("java.specification.vender") + "</td></tr>");
		strbf.append(
				"<tr><td>Java运行时环境规范名称：   </td><td>" + props.getProperty("java.specification.name") + "</td></tr>");
		strbf.append("<tr><td>Java的类格式版本号：   </td><td>" + props.getProperty("java.class.version") + "</td></tr>");
		// strbf.append("<tr><td>Java的类路径： </td><td>"
		// + props.getProperty("java.class.path") + "</td></tr>");
		// strbf.append("<tr><td>加载库时搜索的路径列表： "
		// + props.getProperty("java.library.path") + "</td></tr>");
		strbf.append("<tr><td>默认的临时文件路径：    </td><td>" + props.getProperty("java.io.tmpdir") + "</td></tr>");
		// strbf.append("<tr><td>一个或多个扩展目录的路径： </td><td>"
		// + props.getProperty("java.ext.dirs") + "</td></tr>");
		strbf.append("<tr><td>操作系统的名称：    </td><td>" + props.getProperty("os.name") + "</td></tr>");
		strbf.append("<tr><td>操作系统的构架：    </td><td>" + props.getProperty("os.arch") + "</td></tr>");
		strbf.append("<tr><td>操作系统的版本：    </td><td>" + props.getProperty("os.version") + "</td></tr>");
		// strbf.append("<tr><td>文件分隔符： </td><td>"
		// + props.getProperty("file.separator") + "</td></tr>");
		// strbf.append("<tr><td>路径分隔符： </td><td>"
		// + props.getProperty("path.separator") + "</td></tr>");
		// strbf.append("<tr><td>行分隔符： </td><td>"
		// + props.getProperty("line.separator") + "</td></tr>");
		strbf.append("<tr><td>用户的账户名称：    </td><td>" + props.getProperty("user.name") + "</td></tr>");
		strbf.append("<tr><td>用户的主目录：    </td><td>" + props.getProperty("user.home") + "</td></tr>");
		strbf.append("<tr><td>用户的当前工作目录：    </td><td>" + props.getProperty("user.dir") + "</td></tr>");
		strbf.append("</table>");
		return strbf.toString();
	}
}
