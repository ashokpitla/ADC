package com.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;    

@Controller    
public class HomeController {    
        
	@RequestMapping("/admin")
	public String showAdmin() {
		return "AdminPage";
	}
	@RequestMapping("/view")
	public String showView() {
		return "ViewPage";
	}
	@RequestMapping("/all")
	public String showAll() {
		return "CommonPage";
	}
	
	@RequestMapping("/denied")
	public String showDenied() {
		return "AccessDenied";
	}
}    