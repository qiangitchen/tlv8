package com.tlv8.mac.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class SerialNumberUtil {

	/**
	 * 用vb脚本获取主板信息（适用于Windows系统）
	 * 
	 * @return
	 */
	public static String getMotherboardSN() {
		String result = "";
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new FileWriter(file);

			String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_BaseBoard\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.SerialNumber \n    exit for  ' do the first cpu only! \nNext \n";

			fw.write(vbs);
			fw.close();
			String path = file.getPath();
			try {
				path = URLDecoder.decode(path, "UTF-8");
			} catch (Exception localException1) {
			}
			Process p = Runtime.getRuntime().exec("cscript //NoLogo " + path);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result = result + line;
			}
			input.close();
		} catch (Exception e) {
			result = OshiHardwareUtils.getMotherboardSN();
		}
		return result.trim();
	}

	/**
	 * 用vb脚本获取硬盘信息（适用于Windows系统）
	 * 
	 * @return
	 */
	public static String getHardDiskSN(String drive) {
		String result = "";
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new FileWriter(file);

			String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\nSet colDrives = objFSO.Drives\nSet objDrive = colDrives.item(\""
					+ drive + "\")\n" + "Wscript.Echo objDrive.SerialNumber";
			fw.write(vbs);
			fw.close();
			String path = file.getPath();
			try {
				path = URLDecoder.decode(path, "UTF-8");
			} catch (Exception localException1) {
			}
			Process p = Runtime.getRuntime().exec("cscript //NoLogo " + path);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result = result + line;
			}
			input.close();
		} catch (Exception e) {
			result = OshiHardwareUtils.getHardDiskSN();
		}
		return result.trim();
	}

	/**
	 * 用vb脚本获取CPU信息（适用于Windows系统）
	 * 
	 * @return
	 */
	public static String getCPUSerial() {
		String result = "";
		try {
			File file = File.createTempFile("tmp", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new FileWriter(file);
			String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_Processor\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.ProcessorId \n    exit for  ' do the first cpu only! \nNext \n";

			fw.write(vbs);
			fw.close();
			String path = file.getPath();
			try {
				path = URLDecoder.decode(path, "UTF-8");
			} catch (Exception localException1) {
			}
			Process p = Runtime.getRuntime().exec("cscript //NoLogo " + path);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result = result + line;
			}
			input.close();
			file.delete();
		} catch (Exception e) {
			result = OshiHardwareUtils.getCPUSerial();
		}
		if ((result.trim().length() < 1) || (result == null)) {
			result = "无CPU_ID被读取";
		}
		return result.trim();
	}

	/**
	 * 执行命令行 返回输出
	 * 
	 * @param cmd
	 * @return
	 */
	public static String executeLinuxCmd(String cmd) throws Exception {
		Runtime run = Runtime.getRuntime();

		Process process = run.exec(cmd);
		InputStream in = process.getInputStream();
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[8192];
		int n;
		while ((n = in.read(b)) != -1) {
			out.append(new String(b, 0, n));
		}

		in.close();
		process.destroy();
		return out.toString();
	}

	/**
	 * 命令行 获取主板信息
	 * 
	 * @param cmd
	 * @param record
	 * @param symbol
	 * @return
	 */
	public static String getSerialNumber(String cmd, String record, String symbol) {
		try {
			String execResult = executeLinuxCmd(cmd);
			String[] infos = execResult.split("\n");
			for (String info : infos) {
				info = info.trim();
				if (info.indexOf(record) != -1) {
					info.replace(" ", "");
					String[] sn = info.split(symbol);
					return sn[1];
				}
			}
		} catch (Exception e) {
			return OshiHardwareUtils.getMotherboardSN();
		}
		return null;
	}

	/**
	 * 获取机器信息
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, String> getAllSn() {
		String os = System.getProperty("os.name");
		os = os.toUpperCase();

		Map snVo = new HashMap();

		String cpuid = "unknown";
		if (("unknown".equalsIgnoreCase(cpuid)) || ("NONE".equalsIgnoreCase(cpuid))) {
			if (os.contains("LINUX"))
				cpuid = getSerialNumber("dmidecode -t processor | grep 'ID'", "ID", ":");
			else if (os.contains("MAC OS")) {
				try {
					cpuid = executeLinuxCmd("sysctl -n machdep.cpu.brand_string");
				} catch (Exception e) {
					cpuid = OshiHardwareUtils.getCPUSerial();
				}
			} else {
				cpuid = getCPUSerial();
			}
		}
		String mainboard = "unknown";
		if (("unknown".equalsIgnoreCase(mainboard)) || ("NONE".equalsIgnoreCase(mainboard))) {
			if (os.contains("LINUX"))
				mainboard = getSerialNumber("dmidecode |grep 'Serial Number'", "Serial Number", ":");
			else if (os.contains("MAC OS"))
				mainboard = getSerialNumber("system_profiler SPHardwareDataType", "Hardware UUID", ":");
			else {
				mainboard = getMotherboardSN();
			}
		}
		String disk = "unknown";
		if (("unknown".equalsIgnoreCase(disk)) || ("NONE".equalsIgnoreCase(disk))) {
			if (os.contains("LINUX"))
				disk = getSerialNumber("fdisk -l", unicodeToCn("\\u78c1\\u76d8\\u6807\\u8bc6\\u7b26"),
						unicodeToCn("\\uff1a"));
			else if (os.contains("MAC OS"))
				disk = getSerialNumber("system_profiler SPStorageDataType", "Volume UUID", ":");
			else {
				disk = getHardDiskSN("c");
			}
		}

		System.out.println("CPU  SN:" + cpuid);
		System.out.println("主板  SN:" + mainboard);
		System.out.println("C盘   SN:" + disk);

		snVo.put("cpuid", trim(cpuid));
		snVo.put("mainboard", trim(mainboard));
		snVo.put("diskid", trim(disk));

		return snVo;
	}

	/**
	 * unicode 转中文
	 * 
	 * @param unicode
	 * @return
	 */
	public static String unicodeToCn(String unicode) {
		String[] strs = unicode.split("\\\\u");
		String returnStr = "";

		for (int i = 1; i < strs.length; i++) {
			returnStr = returnStr + (char) Integer.valueOf(strs[i], 16).intValue();
		}
		return returnStr;
	}

	/**
	 * 中文转 unicode
	 * 
	 * @param cn
	 * @return
	 */
	public static String cnToUnicode(String cn) {
		char[] chars = cn.toCharArray();
		String returnStr = "";
		for (int i = 0; i < chars.length; i++) {
			returnStr = returnStr + "\\u" + Integer.toString(chars[i], 16);
		}
		return returnStr;
	}

	/**
	 * 字符大写并去除空格
	 * 
	 * @param str
	 * @return
	 */
	private static String trim(String str) {
		if (str != null) {
			return str.toUpperCase().replace(" ", "");
		}
		return str;
	}

}