package com.tzh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	@RequestMapping(value="/index.do",method = RequestMethod.GET)
	public String index(){
		return "index";
	}
	@RequestMapping(value="/noAuthority")
	public String noAnthority(){
		return "noAuthority";
	}
}