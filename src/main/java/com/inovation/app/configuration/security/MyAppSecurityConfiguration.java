package com.inovation.app.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.inovation.app.service.CompteService;

@Configuration
@EnableWebSecurity
public class MyAppSecurityConfiguration extends WebSecurityConfigurerAdapter{
	@Autowired
	CompteService compteService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(compteService).passwordEncoder(NoOpPasswordEncoder.getInstance());
	}

	@Bean
	public AuthenticationSuccessHandler successHandler() {
	    return new MyCustomLoginSuccessHandler("/");
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/images/**").permitAll();
		http.authorizeRequests().antMatchers("/css/**").permitAll();
		http.authorizeRequests().antMatchers("/api/**").permitAll();
		http
		.antMatcher("/**")
		.authorizeRequests().anyRequest().hasAnyRole("USER")
		.and().formLogin().loginPage("/userlogin")
		.successHandler(successHandler())
		.failureUrl("/userlogin?error")
		.permitAll()		
		.and().logout().logoutSuccessUrl("/userlogin");
	
		http.csrf().disable();
		
	}
}
