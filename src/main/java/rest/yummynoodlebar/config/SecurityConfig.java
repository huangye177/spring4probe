package rest.yummynoodlebar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    // protected void registerAuthentication(AuthenticationManagerBuilder auth)
    // throws Exception
    // {
    // auth.inMemoryAuthentication()
    // .withUser("letsnosh").password("noshing").roles("USER");
    // }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws
            Exception
    {
        auth.inMemoryAuthentication()
                .withUser("http").password("http").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        /*
         * URL level protection (old method name: http.authorizeUrls())
         */
        http.authorizeRequests()
                .antMatchers("/aggregators/**").hasRole("USER")
                .anyRequest().anonymous()
                .and()
                .httpBasic();
    }
}