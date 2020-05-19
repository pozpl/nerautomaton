package com.pozpl.nerannotator.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EntryController {


	@RequestMapping("/")
	public String getRoot(){
		return "forward:index.html";
	}
}
