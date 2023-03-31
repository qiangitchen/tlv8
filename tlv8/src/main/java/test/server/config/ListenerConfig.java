package test.server.config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tlv8.sa.TaskLimitListener;
import com.tlv8.system.online.OnlineInitServerlet;

@Configuration
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ListenerConfig {

	@Bean
	public ServletListenerRegistrationBean onlineInitLlistenerRegistrationBean() {
		ServletListenerRegistrationBean srb = new ServletListenerRegistrationBean();
		srb.setListener(new OnlineInitServerlet());
		return srb;
	}
	
	/**
	 * 任务时限监听
	 * @return
	 */
	@Bean
	public ServletListenerRegistrationBean taskonlineInitLlistenerRegistrationBean() {
		ServletListenerRegistrationBean srb = new ServletListenerRegistrationBean();
		srb.setListener(new TaskLimitListener());
		return srb;
	}
}
