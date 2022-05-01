package com.tlv8.doc.svr.lucene.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.tlv8.doc.svr.core.TransePath;
import com.tlv8.doc.svr.core.inter.IFileSearcher;
import com.tlv8.doc.svr.core.io.atr.DocQueryParam;
import com.tlv8.doc.svr.core.io.atr.FileAttribute;
import com.tlv8.doc.svr.lucene.utils.List2StringArray;

public class IndexQuery implements IFileSearcher {
	/*
	 * 多条件搜索
	 */
	public List<FileAttribute> searchByParam(DocQueryParam param) {
		List<FileAttribute> relist = new ArrayList<FileAttribute>();
		try {
			IndexReader reader = IndexReader.open(FSDirectory.open(new File(
					TransePath.getIndexPath())));
			IndexSearcher searcher = new IndexSearcher(reader);// 检索工具
			ScoreDoc[] hits = null;
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
			List<String> fieldlist = new ArrayList<String>();
			String[] queries = param.getSearchKey();
			// 多个关键字搜索内容
			for (int i = 0; i < queries.length; i++) {
				fieldlist.add("body");
			}
			Filter filter = null;
			if (param.getSeachFolder() != null) {
				// 使用TermRangeFilter进行范围过滤搜索
				// 1.域 2.起始位置 3.结束位置 4.是否包含起始位置 5.是否包含结束位置
				// filter = new TermRangeFilter("filename", "chs.a",
				// "chs.txt",true, true);
				// 使用NumericRangeFilter进行过滤
				// filter = NumericRangeFilter.newIntRange("size", 10, 5000,
				// true,true);
				// 使用QueryWrapperFilter进行过滤
				filter = new QueryWrapperFilter(new WildcardQuery(new Term(
						"filePath", param.getSeachFolder())));
			}
			String[] fields = List2StringArray.transe(fieldlist);
			Query query = MultiFieldQueryParser.parse(Version.LUCENE_36,
					queries, fields, analyzer);
			if (searcher != null) {
				TopDocs results = searcher.search(query, filter, 10000);// 只取排名前n的搜索结果
				hits = results.scoreDocs;
				for (int i = 0; i < hits.length; i++) {
					Document document = searcher.doc(hits[i].doc);
					Date addTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.parse(document.get("addTime"));
					// 时间段过滤//
					if (param.getStartTime() != null) {
						if (addTime.getTime() < param.getStartTime().getTime()) {
							continue;
						}
					}
					if (param.getEndTime() != null) {
						if (addTime.getTime() > param.getStartTime().getTime()) {
							continue;
						}
					}

					// end//
					FileAttribute fileattr = new FileAttribute();
					fileattr.setFileID(document.get("fileID"));
					fileattr.setFilePath(document.get("filePath"));
					fileattr.setFileSize(Float.parseFloat(document
							.get("fileSize")));
					fileattr.setVersion(Integer.parseInt(document
							.get("version")));
					fileattr.setAddTime(addTime);
					relist.add(fileattr);
				}
				searcher.close();
				reader.close();
			}
		} catch (Exception e) {
		}
		return relist;
	}

	/*
	 * 单词搜索
	 */
	public List<FileAttribute> searchByKeyWords(String keywords)
			throws Exception {
		List<FileAttribute> relist = new ArrayList<FileAttribute>();
		IndexReader reader = IndexReader.open(FSDirectory.open(new File(
				TransePath.getIndexPath())));
		IndexSearcher searcher = new IndexSearcher(reader);// 检索工具
		ScoreDoc[] hits = null;
		Query query = null;
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
		try {
			QueryParser qp = new QueryParser(Version.LUCENE_36, "body",
					analyzer);// 用于解析用户输入的工具
			query = qp.parse(keywords);
		} catch (Exception e) {
		}
		if (searcher != null) {
			TopDocs results = searcher.search(query, 10000);// 只取排名前n的搜索结果
			hits = results.scoreDocs;
			for (int i = 0; i < hits.length; i++) {
				Document document = searcher.doc(hits[i].doc);
				FileAttribute fileattr = new FileAttribute();
				fileattr.setFileID(document.get("fileID"));
				fileattr.setFilePath(document.get("filePath"));
				fileattr.setFileSize(Float.parseFloat(document.get("fileSize")));
				fileattr.setVersion(Integer.parseInt(document.get("version")));
				fileattr.setAddTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(document.get("addTime")));
				relist.add(fileattr);
			}
			searcher.close();
			reader.close();
		}
		return relist;
	}
}
