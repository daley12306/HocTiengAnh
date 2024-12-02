package vn.hoctienganh.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.hoctienganh.services.CurriculumService;

@Controller
@RequestMapping("/")
public class LearnController {
	@Autowired
    CurriculumService curriculumService;
	
	@GetMapping("study/{id}")
    public String index(@PathVariable("id") int studyRecordId, Model model) {
        List<String> curriculumNames = curriculumService.getCurriculumNamesByStudyRecordId(studyRecordId);
        model.addAttribute("curriculumNames", curriculumNames);
        return "/user/study";
    }
	
	@GetMapping("study/{id}/congratulations")
	public String showCongratulations(@PathVariable("id") Long id, 
	                                  Model model) {

	    model.addAttribute("message", "Chúc mừng bạn đã hoàn thành!");
	    model.addAttribute("id", id); 
	    return "user/congratulations";
	}
}
