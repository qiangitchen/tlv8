package com.tlv8.system.online;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.tlv8.base.db.DataSourceUtils;

public class OnlineInitServerlet implements ServletContextListener {
	/**
	 * tomcat 启动时 自动清除 Online
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent event) {
		System.out.println("");
		System.out.println("======  =    ===   ===     =========");
		System.out.println("  ||    ||    \\\\    //     ||     ||");
		System.out.println("  ||    ||     \\\\  //       =======");
		System.out.println("  ||    ||      \\\\//       ||     ||");
		System.out.println("  ||    ======   \\/        =========");
		System.out.println("");
		DataSourceUtils.startListener();// 数据库监听
	}
}
