package com.umesh.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.umesh.client.security.AuthAccessDeniedHandler;
import com.umesh.client.security.AuthProvider;
import com.umesh.client.security.AuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private AuthProvider authProvider;

	@Autowired
	private AuthenticationFilter authenticationFilter;
	
	@Autowired
	private AuthAccessDeniedHandler accessDeniedHanlder;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(authenticationFilter, BasicAuthenticationFilter.class);
		http.authenticationProvider(authProvider);
		http.authorizeRequests().anyRequest().authenticated();
		http.exceptionHandling().accessDeniedHandler(accessDeniedHanlder);
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.csrf().disable();
		http.httpBasic().disable();
	}

	
}