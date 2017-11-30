package com.ls.springmvc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cookie")
public class CookieController {

	@RequestMapping("/list")
	public String getCookies(){
		return "cookies";
	}
	
	@RequestMapping("/lifecycle")
	public String lifecycle(){
		return "lifecycle";
	}
	
	@RequestMapping("/readme")
	public String readme(){
		return "readme";
	}
	
	// ����ֲ��쳣
	@ExceptionHandler
    public ModelAndView exceptionHandler(Exception ex){
        ModelAndView mv = new ModelAndView("errors/500");
        mv.addObject("exception", ex);
        System.out.println("part exception handle...");
        return mv;
    }
	
    @RequestMapping("/exception")
    public String exception(){
        int i = 5/0;
        return null;
    }
    
    @RequestMapping("/exception_in_page")
	public String pageException(){
		return "exception";
	}
	
}
