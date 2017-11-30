package com.ls.springmvc.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 处理全局异常（所有Controller）
 * @author acer
 *
 */
@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler
	public ModelAndView exceptionHandler(Exception ex) {
		ModelAndView mv = new ModelAndView("errors/500");
		mv.addObject("exception", ex);
		System.out.println("global exception handle...");
		return mv;
	}
}
