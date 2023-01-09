package test.server;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorConfigurar implements ErrorPageRegistrar {

	@Override
	public void registerErrorPages(ErrorPageRegistry registry) {
		ErrorPage[] errorPages = new ErrorPage[2];
		errorPages[0] = new ErrorPage(HttpStatus.NOT_FOUND, "/404.jsp");
		errorPages[1] = new ErrorPage(HttpStatus.EXPECTATION_FAILED, "/error.jsp");
		registry.addErrorPages(errorPages);
	}

}
