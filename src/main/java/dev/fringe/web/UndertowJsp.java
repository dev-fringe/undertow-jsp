package dev.fringe.web;

import java.util.HashMap;
import javax.servlet.ServletException;
import org.apache.jasper.deploy.JspPropertyGroup;
import org.apache.jasper.deploy.TagLibraryInfo;
import org.apache.jasper.servlet.JspServlet;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.jsp.HackInstanceManager;
import io.undertow.jsp.JspFileHandler;
import io.undertow.jsp.JspServletBuilder;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceManager;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;
import io.undertow.servlet.api.ServletInfo;

public class UndertowJsp {

    public static final String KEY = "io.undertow.message";

    public static void main(String[] args) throws ServletException {

//        final PathHandler servletPath = new PathHandler();
        final ServletContainer container = ServletContainer.Factory.newInstance();

        ResourceManager rm = new ClassPathResourceManager(UndertowJsp.class.getClassLoader(), UndertowJsp.class.getPackage());
        DeploymentInfo builder = new DeploymentInfo()
                .setClassLoader(UndertowJsp.class.getClassLoader())
                .setContextPath("/servletContext")
//                .setClassIntrospecter(TestClassIntrospector.INSTANCE)
                .setDeploymentName("servletContext.war")
                .setResourceManager(rm)
                .addServlet(JspServletBuilder.createServlet("Default Jsp Servlet", "*.jsp"))
                .addServlet(new ServletInfo("jsp-file", JspServlet.class)
                                .addHandlerChainWrapper(JspFileHandler.jspFileHandlerWrapper("/*.jsp"))
                               .addMapping("/jspFile"));
        JspServletBuilder.setupDeployment(builder, new HashMap<String, JspPropertyGroup>(), new HashMap<String, TagLibraryInfo>(), new HackInstanceManager());
        DeploymentManager manager = container.addDeployment(builder);
        manager.deploy();
//        servletPath.addPrefixPath(builder.getContextPath(), manager.start());
        PathHandler path = Handlers.path().addPrefixPath("/", manager.start());
        Undertow.builder().addHttpListener(8090, "0.0.0.0").setHandler(path).build().start();
        System.setProperty(KEY, "Hello JSP!");
	}
}
