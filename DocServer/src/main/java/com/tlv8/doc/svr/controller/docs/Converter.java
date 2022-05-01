package com.tlv8.doc.svr.controller.docs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Converter {

	private final String LOADING_FORMAT = "\nLoading stream\n\n";
	private final String PROCESSING_FORMAT = "Load completed in %1$dms, now converting...\n\n";
	private final String SAVING_FORMAT = "Conversion took %1$dms.\n\nTotal: %2$dms\n";

	private long startTime;
	private long startOfProcessTime;

	protected InputStream inStream;
	protected OutputStream outStream;

	protected boolean showOutputMessages = false;
	protected boolean closeStreamsWhenComplete = true;

	public Converter(InputStream inStream, OutputStream outStream, boolean showMessages,
			boolean closeStreamsWhenComplete) {
		this.inStream = inStream;
		this.outStream = outStream;
		this.showOutputMessages = showMessages;
		this.closeStreamsWhenComplete = closeStreamsWhenComplete;
	}

	public abstract void convert() throws Exception;

	private void startTime() {
		startTime = System.currentTimeMillis();
		startOfProcessTime = startTime;
	}

	protected void loading() {
		sendToOutputOrNot(String.format(LOADING_FORMAT));
		startTime();
	}

	protected void processing() {
		long currentTime = System.currentTimeMillis();
		long prevProcessTook = currentTime - startOfProcessTime;

		sendToOutputOrNot(String.format(PROCESSING_FORMAT, prevProcessTook));

		startOfProcessTime = System.currentTimeMillis();

	}

	protected void finished() {
		long currentTime = System.currentTimeMillis();
		long timeTaken = currentTime - startTime;
		long prevProcessTook = currentTime - startOfProcessTime;

		startOfProcessTime = System.currentTimeMillis();

		if (closeStreamsWhenComplete) {
			try {
				inStream.close();
				outStream.close();
			} catch (IOException e) {
				// Nothing done
			}
		}

		sendToOutputOrNot(String.format(SAVING_FORMAT, prevProcessTook, timeTaken));
	}

	private void sendToOutputOrNot(String toBePrinted) {
		if (showOutputMessages) {
			actuallySendToOutput(toBePrinted);
		}
	}

	protected void actuallySendToOutput(String toBePrinted) {
		System.out.println(toBePrinted);
	}

	public boolean isWin() {
		String ns = System.getProperty("os.name");
		return ns.toLowerCase().contains("windows");
	}

	protected boolean isChinese(String familyName) {
		return (familyName.equals("隶书") || familyName.equals("宋体") || familyName.equals("微软雅黑")
				|| familyName.equals("黑体") || familyName.equals("楷体") || familyName.equals("新宋体")
				|| familyName.equals("华文行楷") || familyName.equals("华文仿宋") || familyName.equals("宋体扩展")
				|| familyName.contains("宋") || familyName.equals("幼圆") || familyName.equals("华文宋体")
				|| familyName.equals("华文中宋") || familyName.equals("华文行楷") || familyName.equals("楷体_GB2312")
				|| familyName.contains("方正") || familyName.contains("简") || familyName.contains("中"));
	}

}
