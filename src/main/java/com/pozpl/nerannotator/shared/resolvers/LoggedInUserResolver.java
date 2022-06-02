package com.pozpl.nerannotator.shared.resolvers;

import auth.dao.repo.UserRepository;
import auth.dao.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.security.Principal;
import java.util.Optional;

public class LoggedInUserResolver implements HandlerMethodArgumentResolver {

	private final UserRepository userRepository;

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
			final Optional<User> userOpt = userRepository.findByUsername(username);
			return userOpt.get();//null is internal to method signature
		}

		return null;
	}
}
