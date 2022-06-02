package com.pozpl.nerannotator.auth.impl.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ResourcesController {


	@GetMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@GetMapping("/login")
	public Principal login(Principal user) {
		return user;
	}
}
