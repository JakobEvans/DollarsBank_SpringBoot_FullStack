package com.cognixia.jump.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//import com.cognixia.jump.service.MyUserDetailsService;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	// CSRF (Cross-Site Request Forgery)
        // Recommended to use for requests that could be processed by a browser by normal users
        // If only creating a service that is used by non-browser clients, will likely want to disable CSRF protection

    	
        		http.csrf().disable()
        
                    // TODO: Need to configure request authorizations with endpoints
        
                    .authorizeRequests()
                        // endpoints that any user can access
                        .antMatchers(
                        		"/login",
                        		"/api/create-account"
        
                        ).permitAll()
                        // http method options does not required any authentication
                        .antMatchers(HttpMethod.OPTIONS).permitAll()
        
                        // endpoint only a user can access
                        .antMatchers(
                        	    
//                        	    "/api/home",
        
                                "/api/customer",
                                "/api/customer/*",
                                "/api/transaction/*",
                                "/api/transaction",
                                "/api/transaction/withdraw",
                                "/api/transaction/deposit"
        
                     
                        		).hasAuthority("ROLE_USER")
        
                        // all other paths must be authenticated
                        .anyRequest().authenticated()
                        .and().formLogin();
    	
    	
    	//        http.csrf().disable()
//
//            // TODO: Need to configure request authorizations with endpoints
//
//            .authorizeRequests()
//                // endpoints that any user can access
//                .antMatchers(
//                		"/login",
//                		"/api/create-account"
//
//                ).permitAll()
//                // http method options does not required any authentication
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
//
//                // endpoint only a user can access
//                .antMatchers(
//                	    
//                	    "/api/home",
//
//                        "/api/customer",
//                        "/api/customer/*",
//                        "/api/transaction/*",
//                        "/api/transaction",
//                        "/api/transaction/withdraw",
//                        "/api/transaction/deposit"
//
//             
//                		).hasAuthority("ROLE_USER")
//
//                // all other paths must be authenticated
//                .anyRequest().authenticated()
//                .and()
//            // when a user successfully logs in, they are redirected to the previously requested page that required authentication
//            // custom /login page (specified by loginPage()) and everyone is allowed to view it
//             .formLogin()
//                  .permitAll()
//                  .and()
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
//        --------------

    }
    

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    

    	
    
}