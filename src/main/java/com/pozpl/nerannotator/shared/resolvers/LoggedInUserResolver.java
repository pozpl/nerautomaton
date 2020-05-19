package com.pozpl.nerannotator.shared.resolvers;

import com.pozpl.nerannotator.auth.dao.repo.UserRepository;
import com.pozpl.nerannotator.auth.dao.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.security.Principal;

public class LoggedInUserResolver implements HandlerMethodArgumentResolver {

	private UserRepository userRepository;

	@Autowired
	public LoggedInUserResolver(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(User.class);
	}

	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
								  WebDataBinderFactory binderFactory) throws Exception {

		final  Principal principal = webRequest.getUserPrincipal();
		if(principal != null){
			final String username = principal.getName();
			final User user = userRepository.findByUsername(username);
			return user;
		}

		return null;
	}
}
