package com.ls.springmvc.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ftl")
public class FreemarkerController {

	@RequestMapping("/simple")
	public String hello(Model model) {
		model.addAttribute("message", "Hello World!");
		return "simple";
	}

	
}
