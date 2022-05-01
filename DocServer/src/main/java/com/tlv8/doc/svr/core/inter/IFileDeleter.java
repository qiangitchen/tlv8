package com.tlv8.doc.svr.core.inter;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface IFileDeleter {

	public Map deleteVersion(String docID, long version);

	public List<Map> delete(String docID);
}
