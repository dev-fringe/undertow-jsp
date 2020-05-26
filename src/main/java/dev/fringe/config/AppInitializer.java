package dev.fringe.config;

import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		registration.setInitParameter("dispatchOptionsRequest", "true");
		registration.setAsyncSupported(true);
	}

	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebMvcConfig.class };
	}

	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { SecurityConfig.class, WebSocketConfig.class };
	}

	protected String getServletName() {
		return "default-servlet";
	}

	protected Filter[] getServletFilters() {
	    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	    characterEncodingFilter.setEncoding(StandardCharsets.UTF_8.name());
	    return new Filter[] { characterEncodingFilter };
	}
}