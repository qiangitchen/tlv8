package com.tlv8.base;

/**
 * 操作系统类型
 * 
 * @author chenqian
 *
 */
public class OS {
	public static enum OSType {
		OSUndefined, OSLinux, OSWindows, OSMacintosh, OSUnknown,
	};

	private static OSType osType = OSType.OSUndefined;

	public static final boolean isWindows() {
		return getOSType() == OSType.OSWindows;
	}

	public static final boolean isMacintosh() {
		return getOSType() == OSType.OSMacintosh;
	}

	public static final boolean isLinux() {
		return getOSType() == OSType.OSLinux;
	}

	public static final OSType getOSType() {
		if (osType == OSType.OSUndefined) {
			String os = System.getProperty("os.name").toLowerCase();
			if (os.startsWith("windows"))
				osType = OSType.OSWindows;
			else if (os.startsWith("linux"))
				osType = OSType.OSLinux;
			else if (os.startsWith("mac"))
				osType = OSType.OSMacintosh;
			else
				osType = OSType.OSUnknown;
		}
		return osType;
	}
	
	public static String getOSName() {
		String osname = System.getProperty("os.name");
		return osname;
	}
	
	public static double getOSVersion() {
		String osversion = System.getProperty("os.version");
		return Double.parseDouble(osversion);
	}
}
