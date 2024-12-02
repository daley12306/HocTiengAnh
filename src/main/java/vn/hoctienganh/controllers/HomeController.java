package vn.hoctienganh.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String index() {
		return "home";
	}
	@GetMapping("/chart")
	public String chart() {
		return "chart";
	}
	@GetMapping("/dictionary")
	public String dictionary() {
		return "dictionary";
	}
}
