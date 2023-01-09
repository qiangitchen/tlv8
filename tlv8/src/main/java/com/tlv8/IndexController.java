package com.tlv8;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public void index(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.sendRedirect("index.jsp");
	}
	
}
