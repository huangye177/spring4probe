package rest.yummynoodlebar.config;

import java.util.Set;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

/*
 * using a web container that supports the Servlet 3 specification such as Tomcat 7+, 
 * it's possible to initialize the underlying web infrastructure for the application 
 * without writing a line of XML.
 * 
 * NOTICE: THIS CLASS IS NOT USED, BECAUSE A CONFLICT (MULTI-CONTEXTLOADER) WITH com.yummynoodlebar.configWebAppInitializer
 */
public class WebAppInitializer implements WebApplicationInitializer
{

    private static Logger LOG = LoggerFactory.getLogger(WebAppInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext)
    {
        // WebApplicationContext rootContext =
        // createRootContext(servletContext);
        //
        // configureSpringMvc(servletContext, rootContext);
        //
        // configureSpringSecurity(servletContext, rootContext);
    }

    /*
     * sets up your root Spring Application Context
     */
    private WebApplicationContext createRootContext(ServletContext servletContext)
    {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();

        /*
         * Spring Security relies on a Servlet filter, which is applied before
         * the Spring MVC Dispatcher Servlet gets involved in processing
         * incoming requests
         */
        rootContext.register(CoreConfig.class, SecurityConfig.class);
        rootContext.refresh();

        servletContext.addListener(new ContextLoaderListener(rootContext));
        servletContext.setInitParameter("defaultHtmlEscape", "true");

        return rootContext;
    }

    /*
     * then finally request the configuration of SpringMvc
     */
    private void configureSpringMvc(ServletContext servletContext, WebApplicationContext rootContext)
    {
        AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
        mvcContext.register(MVCConfig.class);

        mvcContext.setParent(rootContext);

        /*
         * using the servletContext you can dynamically initialise the Spring
         * MVC DispatcherServlet, in this case mapping the DispatcherServlet to
         * the root of the newly registered application.
         */
        ServletRegistration.Dynamic appServlet = servletContext.addServlet(
                "webservice", new DispatcherServlet(mvcContext));
        appServlet.setLoadOnStartup(1);

        Set<String> mappingConflicts = appServlet.addMapping("/");

        if (!mappingConflicts.isEmpty())
        {
            for (String s : mappingConflicts)
            {
                LOG.error("*** Mapping conflict: " + s);
            }
            throw new IllegalStateException(
                    "'webservice' cannot be mapped to '/'");
        }
    }

    /*
     * sets up a Spring DelegatingFilterProxy with the rootContext and is called
     * from the onStartup() method.
     */
    private void configureSpringSecurity(ServletContext servletContext, WebApplicationContext rootContext)
    {
        /*
         * The name springSecurityFilterChain for the filter chain is important
         * as this means that the filter will pass all calls down to a Spring
         * Bean named "springSecurityFilterChain" that it finds in the
         * rootContext
         */
        FilterRegistration.Dynamic springSecurity = servletContext.addFilter("springSecurityFilterChain",
                new DelegatingFilterProxy("springSecurityFilterChain", rootContext));

        springSecurity.addMappingForUrlPatterns(null, true, "/*");
    }
}