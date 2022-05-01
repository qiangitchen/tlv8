package com.tlv8.doc.svr.lucene;

import java.util.HashMap;
import java.util.Map;

import com.tlv8.doc.svr.core.io.atr.FileAttribute;
import com.tlv8.doc.svr.lucene.art.IndexDeleter;
import com.tlv8.doc.svr.lucene.art.IndexWrite;

public class LuceneService {
	private static boolean svIsStarted = false;
	private static Map<String, FileAttribute> handMap = new HashMap<String, FileAttribute>();
	private static Map<String, String> moveMap = new HashMap<String, String>();

	/*
	 * 加入要处理的索引数据
	 * 
	 * @see:添加
	 */
	public static void addHandFile(String fileID, FileAttribute fatt) {
		handMap.put(fileID, fatt);
	}

	/*
	 * 需要删除索引的文件ID
	 */
	public static void addMoveID(String fileID) {
		moveMap.put(fileID, fileID);
	}

	/*
	 * 启动服务
	 */
	public synchronized static void start() {
		if (thread == null) {
			initServer();
		}
		if (!svIsStarted) {
			svIsStarted = true;
			thread.start();
		}
	}

	@SuppressWarnings("deprecation")
	public synchronized static void stop() {
		if (svIsStarted) {
			svIsStarted = false;
			thread.stop();
			thread = null;
		}
	}

	private static Thread thread = null;

	/*
	 * 初始化服务
	 */
	private static void initServer() {
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (svIsStarted) {
					try {
						for (String hindexk : handMap.keySet()) {
							FileAttribute hindexv = handMap.get(hindexk);
							try {
								IndexWrite.write(hindexv);// 创建索引
								handMap.remove(hindexk);// 处理完的信息移除
							} catch (Exception e) {
							}
						}
						try {
							Thread.sleep(100);// 写与删间隔一下
						} catch (InterruptedException e) {
						}
						for (String mindexk : moveMap.keySet()) {
							String mindexv = moveMap.get(mindexk);
							try {
								IndexDeleter.deleteIndex(mindexv, true);// 删除索引
								moveMap.remove(mindexk);
							} catch (Exception e) {
							}
						}
					} catch (Exception e) {
					}
				}
			}
		});
	}
}
