package com.tlv8.doc.svr.lucene.art;

import java.io.File;
import java.text.SimpleDateFormat;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.tlv8.doc.svr.core.FileReader;
import com.tlv8.doc.svr.core.TransePath;
import com.tlv8.doc.svr.core.io.atr.FileAttribute;

public class IndexUpdater {
	/*
	 * 
	 */
	public static void update(FileAttribute fatr) throws Exception {
		/* 指明要索引文件 */
		File file = new File(TransePath.getFileAbsolutePath(fatr.getFileID(),
				fatr.getFilePath()));
		/* 这里放索引文件的位置 */
		File indexDir = new File(TransePath.getIndexPath());
		Directory dir = FSDirectory.open(indexDir);// 将索引存放在磁盘上
		Analyzer lucenAnalyzer = new StandardAnalyzer(Version.LUCENE_36);// 分析器
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_36,
				lucenAnalyzer);
		iwc.setOpenMode(OpenMode.CREATE);// 创建新的索引文件create 表示创建或追加到已有索引库
		IndexWriter indexWriter = new IndexWriter(dir, iwc);// 把文档写入到索引库
		if (file.isFile()) {
			String temp = FileReader.readAll(file, fatr.getFileExt());
			Document document = new Document();
			Field fileID = new Field("fileID", fatr.getFileID(),
					Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
			Field filePath = new Field("filePath", fatr.getFilePath(),
					Field.Store.YES, Field.Index.NO);
			Field fileSize = new Field("fileSize", fatr.getFileSize() + "",
					Field.Store.YES, Field.Index.NO);
			Field version = new Field("version", fatr.getVersion() + "",
					Field.Store.YES, Field.Index.NO);
			Field addTime = new Field("addTime", new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(fatr.getAddTime()),
					Field.Store.YES, Field.Index.NO);
			Field FieldBody = new Field("body", temp, Field.Store.YES,
					Field.Index.ANALYZED,
					Field.TermVector.WITH_POSITIONS_OFFSETS);
			document.add(fileID);
			document.add(filePath);
			document.add(fileSize);
			document.add(version);
			document.add(addTime);
			document.add(FieldBody);
			indexWriter.updateDocument(new Term("fileID", fatr.getFileID()),
					document);
		}
		indexWriter.close();
	}
}
