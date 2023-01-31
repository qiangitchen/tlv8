package test.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * 启动测试服务主程序
 * 
 * @author chenqian
 *
 */
@ComponentScans(value = { @ComponentScan(value = "com.tlv8"), @ComponentScan(value = "test.server") })
@SpringBootApplication
public class TLv8Application {
	public static void main(String[] args) {
		SpringApplication.run(TLv8Application.class, args);
	}
}
