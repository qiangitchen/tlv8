package com.tlv8.doc.svr.lucene;

import java.io.File;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.tlv8.doc.svr.core.FileReader;

public class IndexWriterDemo {
	public static void main(String[] args) throws Exception {
		/* 指明要索引文件夹的位置,这里是C盘的source文件夹下 */
		File fileDir = new File("F:/YunJieDocSvr_src/data/doc/document");
		/* 这里放索引文件的位置 */
		File indexDir = new File("F:/YunJieDocSvr_src/data/doc/lucene");
		Directory dir = FSDirectory.open(indexDir);// 将索引存放在磁盘上
		Analyzer lucenAnalyzer = new StandardAnalyzer(Version.LUCENE_36);// 分析器
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_36,
				lucenAnalyzer);
		iwc.setOpenMode(OpenMode.CREATE);// 创建新的索引文件create 表示创建或追加到已有索引库
		IndexWriter indexWriter = new IndexWriter(dir, iwc);// 把文档写入到索引库
		File[] textFiles = fileDir.listFiles();// 得到索引文件夹下所有文件
		long startTime = new Date().getTime();
		// 增加document到检索去
		for (int i = 0; i < textFiles.length; i++) {
			System.out.println(":;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
			System.out.println("File" + textFiles[i].getCanonicalPath()
					+ "正在被索引...");
			String temp = FileReader.readAll(textFiles[i], ".dps");
			System.out.println(temp);
			Document document = new Document();
			Field FieldPath = new Field("path", textFiles[i].getPath(),
					Field.Store.YES, Field.Index.NO);
			Field FieldBody = new Field("body", temp, Field.Store.YES,
					Field.Index.ANALYZED,
					Field.TermVector.WITH_POSITIONS_OFFSETS);
			NumericField modifiField = new NumericField("modified");// 所以key为modified
			modifiField.setLongValue(fileDir.lastModified());
			document.add(FieldPath);
			document.add(FieldBody);
			document.add(modifiField);
			indexWriter.addDocument(document);
		}
		indexWriter.close();
		// 计算一下索引的时间
		long endTime = new Date().getTime();
		System.out.println("花了" + (endTime - startTime) + "毫秒把文档添加到索引里面去"
				+ fileDir.getPath());
	}

}
