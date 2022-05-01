package com.tlv8.doc.svr.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class TestQuery {

	public static void main(String[] args) throws ParseException, IOException {
		String index = "F:/YunJieDocSvr_src/data/doc/lucene";// 搜索的索引路径
		IndexReader reader = IndexReader
				.open(FSDirectory.open(new File(index)));
		IndexSearcher searcher = new IndexSearcher(reader);// 检索工具
		ScoreDoc[] hits = null;
		String queryString = "测试"; // 搜索的索引名称
		Query query = null;
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
		try {
			QueryParser qp = new QueryParser(Version.LUCENE_36, "body",
					analyzer);// 用于解析用户输入的工具
			query = qp.parse(queryString);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (searcher != null) {
			TopDocs results = searcher.search(query, 10);// 只取排名前十的搜索结果
			hits = results.scoreDocs;
			Document document = null;
			for (int i = 0; i < hits.length; i++) {
				document = searcher.doc(hits[i].doc);
				String body = document.get("body");
				String path = document.get("path");
				String modifiedtime = document.get("modifiField");
				System.out.println(body + "        ");
				System.out.println(path);
				System.out.println(modifiedtime);
			}
			if (hits.length > 0) {
				System.out.println("找到" + hits.length + "条结果");

			}
			searcher.close();
			reader.close();
		}

	}

}
