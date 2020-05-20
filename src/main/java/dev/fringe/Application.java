package dev.fringe;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;

import org.apache.jasper.deploy.JspPropertyGroup;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

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
import io.undertow.servlet.util.ImmediateInstanceFactory;

public class Application {
	
	public static final String RESOURCE_FILE_DIR = "src/main/webapp";
	public static final String CONTEXT_PATH = "/";
	
    public static void main(String[] args) throws ServletException, IOException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(Application.class.getPackage().getName()+".config");
		DeploymentInfo servletBuilder = Servlets.deployment()
				.setClassLoader(Application.class.getClassLoader())
				.setContextPath(CONTEXT_PATH)
				.setResourceManager(new FileResourceManager(new File(RESOURCE_FILE_DIR), 1024))
				.setDeploymentName(Application.class.getName()+ ".war")
		        .addServlet(Servlets.servlet("DispatcherServlet", DispatcherServlet.class, new ImmediateInstanceFactory(new DispatcherServlet(context)))		        		
				.addMappings("/").setLoadOnStartup(1))
				.addServlet((JspServletBuilder.createServlet("Default Jsp Servlet", "*.jsp")));
				
//	    HashMap<String, TagLibraryInfo> tagLibraryInfo = TldLocator.createTldInfos();
	    JspServletBuilder.setupDeployment(servletBuilder, new HashMap<String, JspPropertyGroup>(), TldLocator.createTldInfos(), new HackInstanceManager());

	    DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
		manager.deploy();

		HttpHandler servletHandler = manager.start();
		PathHandler path = Handlers.path(Handlers.redirect(CONTEXT_PATH)).addPrefixPath(CONTEXT_PATH, servletHandler);
		Undertow server = Undertow.builder().addHttpListener(80, "0.0.0.0").setHandler(path).build();
		server.start();
	}
}
