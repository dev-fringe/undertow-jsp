package dev.fringe;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;

import org.apache.jasper.deploy.JspPropertyGroup;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.jsp.HackInstanceManager;
import io.undertow.jsp.JspServletBuilder;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

public class Application {
	
	public static final String RESOURCE_FILE_DIR = "src/main/webapp";
	public static final String CONTEXT_PATH = "/";
	public static final String WAR_NAME = Application.class.getName()+ ".war";
	public static final String SERVLET_NAME= Application.class.getName();
	
    public static void main(String[] args) throws ServletException, IOException {
		DeploymentInfo servletBuilder = Servlets.deployment()
				.setClassLoader(Application.class.getClassLoader())
				.setContextPath(CONTEXT_PATH)
				.setResourceManager(new FileResourceManager(new File(RESOURCE_FILE_DIR), 1024))
				.setDeploymentName(WAR_NAME)
				.addServlet(new AppServletInfo(SERVLET_NAME, org.springframework.web.servlet.DispatcherServlet.class)
				.addInitParam("contextConfigLocation", "classpath:/spring-servlet.xml")
				.addMappings("/").setLoadOnStartup(1))
				.addServlet((JspServletBuilder.createServlet("Default Jsp Servlet", "*.jsp")));
				
//	    HashMap<String, TagLibraryInfo> tagLibraryInfo = TldLocator.createTldInfos();
	    JspServletBuilder.setupDeployment(servletBuilder, new HashMap<String, JspPropertyGroup>(), TldLocator.createTldInfos(), new HackInstanceManager());

	    DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
		manager.deploy();

		HttpHandler servletHandler = manager.start();
		PathHandler path = Handlers.path(Handlers.redirect(CONTEXT_PATH))
				.addPrefixPath(CONTEXT_PATH, servletHandler);
		Undertow server = Undertow.builder()
				.addHttpListener(80, "localhost")
				.setHandler(path)
				.build();
		server.start();
	}
}
