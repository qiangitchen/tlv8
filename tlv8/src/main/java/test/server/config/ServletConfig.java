package test.server.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bstek.ureport.console.UReportServlet;

@Configuration
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ServletConfig {

	@Bean
	public ServletRegistrationBean webServletRegistrationBean() {
		ServletRegistrationBean bean = new ServletRegistrationBean();
		bean.setServlet(new UReportServlet());
		bean.addUrlMappings("/ureport/*");
		bean.setName("ureportServlet");
		return bean;
	}
}
