package dev.fringe;

import javax.servlet.http.HttpServlet;

import io.undertow.servlet.api.ServletInfo;

public class AppServletInfo extends ServletInfo {

	public AppServletInfo(String servletName, Class<? extends HttpServlet> servletClass) {
		super(servletName, servletClass);		
	}

}
