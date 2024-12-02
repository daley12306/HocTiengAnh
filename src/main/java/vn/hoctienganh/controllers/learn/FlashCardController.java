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
	
	// Phương thức cũ để hiển thị trang flashcard lần đầu tiên
	@PostMapping("/{id}/flashcard")
	public String studyFlashcard(
	        @PathVariable("id") Long id,  
	        @RequestParam(value = "currentIndex", defaultValue = "0") int currentIndex, 
	        @RequestParam("curriculum_name") String curriculumName, 
	        Model model) {
	    List<Vocabulary> vocabularyList = vocabularyService.findWordsByCurriculumName(curriculumName);
	    currentIndex = Math.max(0, Math.min(currentIndex, vocabularyList.size() - 1));  // Đảm bảo currentIndex không vượt quá giới hạn
	    model.addAttribute("vocabularyList", vocabularyList);
	    model.addAttribute("currentIndex", currentIndex);
	    model.addAttribute("curriculumName", curriculumName);
	    return "flashcard/flashcard";  // Trả về giao diện flashcard
	}

	// Phương thức chuyển đến flashcard tiếp theo
	@PostMapping("/{id}/flashcard/next")
	public String nextFlashcard(
	        @PathVariable("id") Long id,  
	        @RequestParam("currentIndex") int currentIndex, 
	        @RequestParam("curriculum_name") String curriculumName, 
	        Model model) {
	    List<Vocabulary> vocabularyList = vocabularyService.findWordsByCurriculumName(curriculumName);
	    
	    // Kiểm tra nếu đã đến cuối danh sách
	    if (currentIndex == vocabularyList.size()-1) {
	        return "redirect:/study/{id}/congratulations";  // Chuyển tới trang chúc mừng
	    }

	    // Nếu chưa đến cuối danh sách, chuyển tới flashcard tiếp theo
	    currentIndex = Math.min(currentIndex + 1, vocabularyList.size() - 1); 
	    model.addAttribute("vocabularyList", vocabularyList);
	    model.addAttribute("currentIndex", currentIndex);
	    model.addAttribute("curriculumName", curriculumName);
	    return "flashcard/flashcard";  // Trả về giao diện flashcard
	}


	@PostMapping("/{id}/flashcard/previous")
	public String previousFlashcard(
	        @PathVariable("id") Long id,  
	        @RequestParam("currentIndex") int currentIndex, 
	        @RequestParam("curriculum_name") String curriculumName, 
	        Model model) {
	    List<Vocabulary> vocabularyList = vocabularyService.findWordsByCurriculumName(curriculumName);
	    currentIndex = Math.max(currentIndex - 1, 0);  // Giới hạn index

	    model.addAttribute("vocabularyList", vocabularyList);
	    model.addAttribute("currentIndex", currentIndex);
	    model.addAttribute("curriculumName", curriculumName);
	    return "flashcard/flashcard";  // Trả về giao diện flashcard
	}	
}
