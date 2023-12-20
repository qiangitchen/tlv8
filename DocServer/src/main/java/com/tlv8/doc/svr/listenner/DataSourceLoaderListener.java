package com.tlv8.doc.svr.listenner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlv8.doc.svr.generator.utils.DBUtils;

public class DataSourceLoaderListener implements ServletContextListener {
	final static Logger logger = LoggerFactory.getLogger(DataSourceLoaderListener.class);
	protected boolean started = false;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		started = false;
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		printWellcome(event);
		started = true;
		datalisten();
	}

	private void datalisten() {
		try {
			logger.info("启动数据库监听...");
			new Thread(new Runnable() {

				@Override
				public void run() {
					while (started) {
						Connection conn = null;
						Statement stm = null;
						try {
							conn = DBUtils.getAppConn();
							stm = conn.createStatement();
							stm.executeQuery("select * from Doc_Admin");
						} catch (Exception e) {
						} finally {
							try {
								DBUtils.CloseConn(conn, stm, null);
							} catch (Exception e) {
							}
						}
						try {
							Thread.sleep(5 * 60 * 1000);// 5分钟检测一次
						} catch (InterruptedException e) {
						}
					}
				}
			}).start();
			logger.info("启动数据库监听完成.");
		} catch (Exception e) {
			logger.error("启动数据库监听异常:");
			e.printStackTrace();
		}
	}

	private void printWellcome(ServletContextEvent event) {
		try {
			String context = new BufferedReader(
					new InputStreamReader(DataSourceLoaderListener.class.getResourceAsStream("wellcome"))).lines()
							.collect(Collectors.joining(System.lineSeparator()));
			context = context.replace("${dir}", event.getServletContext().getRealPath(""));
			try {
				context = context.replace("${db}", DBUtils.getAppConn().getMetaData().getDriverName());
			} catch (Exception e) {
				context = context.replace("${db}", "检测异常");
			}
			System.out.println(context);
		} catch (Exception e) {
		}
	}

}
