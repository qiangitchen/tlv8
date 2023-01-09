package test.server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.tlv8.interceptors.SqlInjectInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:index.jsp");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SqlInjectInterceptor()).addPathPatterns("/**");
	}
	
	@Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter fastjson = new FastJsonHttpMessageConverter();
		List<MediaType> smtypes = new ArrayList<>();
		smtypes.add(new MediaType("text/html;charset=UTF-8"));
		smtypes.add(new MediaType("application/json;charset=UTF-8"));
		fastjson.setSupportedMediaTypes(smtypes);
        converters.add(0, fastjson);
    }
}
