package com.tlv8.doc.svr.lucene.art;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.tlv8.doc.svr.core.TransePath;

public class IndexDeleter {
	/*
	 * 索引删除相关操作
	 */
	public static void deleteIndex(String docID) {
		IndexWriter writer = null;
		try {
			Directory directory = FSDirectory.open(new File(TransePath
					.getIndexPath()));
			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_36, new StandardAnalyzer(Version.LUCENE_36)));
			// 参数是一个选项，可以是一个query，也可以是一个term term就是一个精确查找的值
			// 此时删除的文档并未完全删除，而是存储在回收站中，可以恢复的
			writer.deleteDocuments(new Term("fileID", docID));
		} catch (Exception e) {
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (CorruptIndexException e) {
				} catch (IOException e) {
				}
			}
		}
	}

	public static void deleteIndex(String docID, boolean mace) {
		deleteIndex(docID);
		if (mace) {
			forceDelete();
		}
	}

	@Deprecated
	public static void undelete() {
		try {
			Directory directory = FSDirectory.open(new File(TransePath
					.getIndexPath()));
			// 恢复时必须把reader的只读设为false
			IndexReader reader = IndexReader.open(directory, false);
			reader.undeleteAll();
			reader.close();
		} catch (Exception e) {
		}

	}

	// 清空回收站
	public static void forceDelete() {
		IndexWriter writer = null;
		try {
			Directory directory = FSDirectory.open(new File(TransePath
					.getIndexPath()));
			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_36, new StandardAnalyzer(Version.LUCENE_36)));
		} catch (Exception e) {
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (CorruptIndexException e) {
				} catch (IOException e) {
				}
			}
		}
	}
}
