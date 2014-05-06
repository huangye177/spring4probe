package com.yummynoodlebar.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*
 * AbstractAnnotationConfigDispatcherServletInitializer performs setup of the 
 * Spring DispatcherServlet and ContextLoader, 
 * that are a standard part of Spring web applications
 * 
 * The DispatcherServlet also orchestrates how each incoming request is channelled to the appropriate handler 
 * method on the available controllers
 */
public class WebAppInitializer extends
        AbstractAnnotationConfigDispatcherServletInitializer
{
    /*
     * getRootConfigClasses method provides a set of Spring Configuration
     * classes to construct the root application context. This context will be
     * shared by all elements of the application, including Servlets, Filters
     * and Context Listeners
     */
    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return new Class<?>[] { PersistenceConfig.class, CoreConfig.class };
    }

    /*
     * getServletConfigClasses again returns a list of Spring Configuration
     * classes, in this case, just the ones that are used as Servlet delegates
     */
    @Override
    protected Class<?>[] getServletConfigClasses()
    {
        return new Class<?>[] { WebConfig.class };
    }

    /*
     * extra configuration to map the servlet URL context
     */
    @Override
    protected String[] getServletMappings()
    {
        return new String[] { "/" };
    }

    /*
     * add a standard filter
     */
    @Override
    protected Filter[] getServletFilters()
    {

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        return new Filter[] { characterEncodingFilter };
    }
}
