package com.pozpl.nerannotator.config;


import com.pozpl.nerannotator.controllers.resolver.LoggedInUserResolver;
import com.pozpl.nerannotator.persistence.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

	UserRepository userRepository;

	@Autowired
	public MvcConfig(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new LoggedInUserResolver(userRepository));
	}

}
