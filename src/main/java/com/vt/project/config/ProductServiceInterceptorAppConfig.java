package com.vt.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.servlet.configuration.WebMvcSecurityConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import com.vt.project.intercepter.JwtInterceptor;

@Component
public class ProductServiceInterceptorAppConfig extends WebMvcSecurityConfiguration {
	@Autowired
	JwtInterceptor jwtInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor);
	}
}
