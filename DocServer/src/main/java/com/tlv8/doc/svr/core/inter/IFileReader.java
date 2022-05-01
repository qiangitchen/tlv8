package com.tlv8.doc.svr.core.inter;

import java.io.IOException;

public interface IFileReader {
	public String getCharset() throws IOException;

	public String readAll();
}
