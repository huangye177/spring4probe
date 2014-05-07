package com.yummynoodlebar.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/*
 * inserts Spring Security into the web context setup. 
 * 
 * This could be done in WebAppInitializer, however a better option in this case is to 
 * add this second web app initializer class specifically for the security setup
 * 
 * This configures the Spring Security filter chain and manages inserting it into the web context
 * 
 * It is important that the Spring Security setup is done before the DispatcherServlet configuration 
 * in WebAppInitializer. 
 * The @Order annotation from Spring Core can be used to manage the order of execution
 */
@Order(1)
public class SecurityWebAppInitializer
        extends AbstractSecurityWebApplicationInitializer
{}
