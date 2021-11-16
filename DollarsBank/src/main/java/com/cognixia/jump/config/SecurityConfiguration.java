package com.cognixia.jump.config;

import com.cognixia.jump.filter.JwtRequestFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
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

    @Autowired
	private JwtRequestFilter jwtRequestFilter;

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
        
                    .authorizeRequests()
                        // endpoints that any user can access
                        .antMatchers(
                        		"/api/login",
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
                        .and().sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // make sure to call filter for jwt on request before filter for checking username and password is used
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    	
    	//        http.csrf().disable()


    }

    // provides method for Spring Security to create and access the AuthenticationManager object needed
	// when authenticating users accessing our APIs (used in Autowire within HelloController class)
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}
    
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}