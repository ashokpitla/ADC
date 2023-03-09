package com.app;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class MainController {

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(@RequestParam("username") String username,	@RequestParam("password") String password, HttpSession session,	Model model) {
		if(username.equalsIgnoreCase("ashok") && password.equalsIgnoreCase("ashok1234")) {
			session.setAttribute("username", username);
			return "success";
		} else {
			model.addAttribute("error", "Invalid user/password");
			return "index";
		}
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session,HttpServletRequest request,HttpServletResponse response) {
		session=request.getSession(false);
		session.invalidate();
		return "redirect:/login";
	}

}