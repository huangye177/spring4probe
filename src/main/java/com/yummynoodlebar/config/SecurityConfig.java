package com.yummynoodlebar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    /*
     * convert from old API
     * "registerAuthentication(AuthenticationManagerBuilder auth)" into
     * "configure(AuthenticationManagerBuilder auth)"
     * 
     * this method is overridden from WebSecurityConfigurerAdapter in order to
     * configure an in-memory database of users, their passwords and associated
     * roles
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication()
                .withUser("http").password("http").roles("USER");
    }

    /*
     * this method provides a fine grained fluent API for controlling how the
     * security system will be applied
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        /*
         * URL level protection (old method name: http.authorizeUrls())
         * 
         * protecting the /checkout and /order/* urls, ensuring that only users
         * with the USER role can access them
         * 
         * The formLogin() method call instructs Spring Security that users will
         * login via an HTML form. We give no further information on how this
         * will work, and so Spring Security will generate a new HTML form and
         * URL for you available on /login
         */
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/login", "/addToBasket", "/showBasket", "/upload").permitAll()
                .antMatchers("/order/**").hasRole("USER")
                .antMatchers("/checkout").hasRole("USER")
                .anyRequest().anonymous()
                // All remaining URLs require that the user be successfully
                // authenticated
                // .anyRequest().authenticated()
                .and()
                // This will generate a login form if none is supplied.
                .formLogin()
                .permitAll();
    }

    /*
     * Ignore any request that starts with "/resources/". This is similar to
     * configuring http@security=none when using the XML namespace
     * configuration.
     */
    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web
                .ignoring()
                .antMatchers("/resources/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }
}
