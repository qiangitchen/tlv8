package com.tlv8.base.mac.utils;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.HardwareAbstractionLayer;

public class OshiHardwareUtils {
	static SystemInfo systemInfo;
	static HardwareAbstractionLayer hardwareAbstractionLayer;
	static ComputerSystem computerSystem;
	static CentralProcessor processor;
	static {
		systemInfo = new SystemInfo();
		hardwareAbstractionLayer = systemInfo.getHardware();
		computerSystem = hardwareAbstractionLayer.getComputerSystem();
		processor = hardwareAbstractionLayer.getProcessor();
	}

	public static String getCPUSerial() {
		String result = "";
		try {
			result = processor.getProcessorIdentifier().getProcessorID();
		} catch (Exception e) {
		}
		return result;
	}

	public static String getMotherboardSN() {
		String result = "";
		try {
			result = computerSystem.getBaseboard().getSerialNumber();
		} catch (Exception e) {
		}
		return result;
	}

	public static String getHardDiskSN() {
		String result = "";
		try {
			result = computerSystem.getHardwareUUID();
		} catch (Exception e) {
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println("CPU  SN:" + getCPUSerial());
		System.out.println("主板  SN:" + getMotherboardSN());
		System.out.println("C盘   SN:" + getHardDiskSN());
	}
}
