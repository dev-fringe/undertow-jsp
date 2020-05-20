package dev.fringe.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebMvcConfig.class};
	}

	protected String[] getServletMappings() {
		return new String[] { "/app/*" };
	}

	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {SecurityConfig.class};
	}

	protected String getServletName() {
	    return "default-servlet";
	}

    protected Filter[] getServletFilters() {
      CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
      characterEncodingFilter.setEncoding("UTF-8");
      return new Filter[] { characterEncodingFilter};
    }
}