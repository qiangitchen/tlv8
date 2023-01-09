package test.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动测试服务主程序
 * 
 * @author chenqian
 *
 */
@ComponentScan(value = "com.tlv8")
@ServletComponentScan(basePackages = "org.apache.catalina.filters.HttpHeaderSecurityFilter,com.tlv8.system.filter.JurisdictionFilter")
@SpringBootApplication
public class TLv8Application {
	public static void main(String[] args) {
		SpringApplication.run(TLv8Application.class, args);
	}
}
