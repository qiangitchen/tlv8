package test.server.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tlv8.opm.filter.JurisdictionFilter;

/**
 * Filter配置
 *
 */
@Configuration
@SuppressWarnings({ "rawtypes", "unchecked" })
public class FilterConfig {
	
	@Bean
	public FilterRegistrationBean jurisdictionFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new JurisdictionFilter());
		registration.addUrlPatterns("/*");
		registration.setName("jurisdiction");
		registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
		return registration;
	}

}
