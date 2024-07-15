package com.vt.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import com.vt.project.security.JwtAuthenticationEntryPoint;
import com.vt.project.security.JwtAuthenticationFilter;

import jakarta.security.auth.message.config.AuthConfig;

@Configuration
public class WebSecurityConfig {

	@Autowired
	private JwtAuthenticationFilter filter;

	@Autowired
	private JwtAuthenticationEntryPoint point;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/login").permitAll().requestMatchers(toH2Console())
						.permitAll().requestMatchers("/student/**").authenticated().anyRequest().authenticated())
				.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

		http.headers().frameOptions().disable();

		return http.build();
	}
}
