package vn.hoctienganh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import vn.hoctienganh.entity.Feedback;
import vn.hoctienganh.entity.User;
import vn.hoctienganh.services.FeedbackService;
import vn.hoctienganh.services.UserService;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
	@Autowired
	FeedbackService feedbackService;
	
	@Autowired
	UserService studentService;
	
	@GetMapping("")
	public String feedback(Model model) {
		Feedback feedback = new Feedback();
		// Lấy thông tin học viên
		User student = studentService.getStudentById(1L);
		feedback.setStudent(student);
		model.addAttribute("feedback", feedback);
		return "feedback";
	}
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("feedback") Feedback feedback) {
		feedbackService.save(feedback);
		return "redirect:/";
	}
}
