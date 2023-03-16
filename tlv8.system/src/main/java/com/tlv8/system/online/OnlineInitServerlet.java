package com.tlv8.system.online;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;
import com.tlv8.mac.License;

public class OnlineInitServerlet implements ServletContextListener {
	/**
	 * tomcat 启动时 自动清除 Online
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent event) {
		System.out.println("======  =    ===   ===     =========");
		System.out.println("  ||    ||    \\\\    //     ||     ||");
		System.out.println("  ||    ||     \\\\  //       =======");
		System.out.println("  ||    ||      \\\\//       ||     ||");
		System.out.println("  ||    ======   \\/        =========");
		System.out.println("..............................................");
		Sys.printMsg("初始化Online... ...");
		try {
			String MachineCode = License.getMachineCode();
			DBUtils.execdeleteQuery("system", "delete from SA_ONLINEINFO where SMACHINECODE ='" + MachineCode + "'");
			DBUtils.execdeleteQuery("system", "delete from SA_ONLINEINFO where SLOGINIP ='127.0.0.1'");
			DBUtils.execdeleteQuery("system", "delete from SA_ONLINEINFO where SLOGINIP ='localhost'");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Sys.printMsg("初始化完成.");
		System.out.println("..............................................");
	}
}
