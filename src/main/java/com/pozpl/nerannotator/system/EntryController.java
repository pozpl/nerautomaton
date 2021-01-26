package com.pozpl.nerannotator.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class EntryController {


	@RequestMapping("/")
	public RedirectView getRoot(){
		return new RedirectView("index.html");
	}

	@RequestMapping("/#/*")
	public RedirectView handleReload(){
		return new RedirectView("/index.html");
	}

	@GetMapping("/health")
	@ResponseBody
	public String isAlive(){
		return "OK";
	}
}
