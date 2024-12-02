package vn.hoctienganh.controllers.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import vn.hoctienganh.entity.Feedback;
import vn.hoctienganh.entity.User;
import vn.hoctienganh.services.EmailService;
import vn.hoctienganh.services.FeedbackService;
import vn.hoctienganh.services.UserService;

@Controller
@RequestMapping("/admin/feedbacks")
public class AdminFeedbackController {
	@Autowired
	FeedbackService feedbackService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	UserService studentService;

	@GetMapping("")
	public String all(Model model) {
		List<Feedback> feedbacks = feedbackService.findAll();
		model.addAttribute("feedbacks", feedbacks);
		return "admin/feedback/feedback_list";
	}
	
	@GetMapping("/detail/{id}")
	public ModelAndView detail(ModelMap model, @PathVariable("id") Long feedbackId) {
		Optional<Feedback> result = feedbackService.findById(feedbackId);
		Feedback feedback = result.get();
		if (feedback == null) {
			return new ModelAndView("redirect:/admin/feedbacks");
		}
		model.addAttribute("feedback", feedback);
		return new ModelAndView("admin/feedback/feedback_detail", model);
	}
	
	@PostMapping("/send/{id}")
	public String send(@PathVariable("id") Long feedbackId,
		@RequestParam String subject,
        @RequestParam String content
        ) {
		Optional<Feedback> result = feedbackService.findById(feedbackId);
		Feedback feedback = result.get();
		feedback.setReply(true);
		feedbackService.save(feedback);
		
		String email = feedback.getEmail();
        String emailSubject = "Feedback Confirmation: " + subject;
        String emailContent = "Hello, \n\nThank you for your feedback! Here are the details:\n\n"
            + content + "\n\nWe will get back to you soon.";
//        String email = "thienphuc7434552@gmail.com";
        emailService.sendEmail(email, emailSubject, emailContent);

        return "redirect:/admin/feedbacks";
    }
}
