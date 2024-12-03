package vn.hoctienganh.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@GetMapping("/home")
	public String index() {
		return "user/home";
	}
	@GetMapping("/chart")
	public String chart() {
		return "chart";
	}
	@GetMapping("/dictionary")
	public String dictionary() {
		return "dictionary";
	}
	@GetMapping("/admin/home")
	public String home_admin() {
		return "admin/home_admin";
	}
}
