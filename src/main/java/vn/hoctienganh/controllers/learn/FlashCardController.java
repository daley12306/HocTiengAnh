package vn.hoctienganh.controllers.learn;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.hoctienganh.entity.Vocabulary;
import vn.hoctienganh.services.VocabularyService;

@Controller
@RequestMapping("/study")
public class FlashCardController {
	@Autowired
	private VocabularyService vocabularyService;
	
	@PostMapping("/{id}/flashcard")
	public String studyFlashcard(
	        @PathVariable("id") Long id,  // Nhận id từ URL
	        @RequestParam("curriculum_name") String curriculumName,  // Nhận curriculum_name từ form
	        Model model) {
	    
	    // In ra các giá trị nhận được (hoặc xử lý tùy theo yêu cầu)
	    System.out.println("ID: " + id);
	    System.out.println("Curriculum Name: " + curriculumName);
	 

	    return "flashcard/flashcard"; 
	}
	

			
}
