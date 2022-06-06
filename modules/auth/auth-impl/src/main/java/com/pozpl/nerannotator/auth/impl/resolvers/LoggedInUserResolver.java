package com.pozpl.nerannotator.auth.impl.resolvers;

import com.pozpl.neraannotator.user.api.IUserService;
import com.pozpl.neraannotator.user.api.UserIntDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.security.Principal;
import java.util.Optional;

@Service
public class LoggedInUserResolver implements HandlerMethodArgumentResolver {

	private final IUserService userRepository;

	@Autowired
	public LoggedInUserResolver(IUserService userRepository) {
		this.userRepository = userRepository;
	}

	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(UserIntDto.class);
	}

	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
								  WebDataBinderFactory binderFactory) throws Exception {

		final  Principal principal = webRequest.getUserPrincipal();
		if(principal != null){
			final String username = principal.getName();
			final Optional<UserIntDto> userOpt = userRepository.findByUserName(username);
			return userOpt.get();//null is internal to method signature
		}

		return null;
	}
}
