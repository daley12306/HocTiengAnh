package vn.hoctienganh.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.hoctienganh.entity.Vocabulary;
import vn.hoctienganh.services.VocabularyService;

@Controller
@RequestMapping("/vocabularies")
public class VocabularyController {
	@Autowired
    private VocabularyService vocabularyService;

    @GetMapping
    public List<Vocabulary> getAllVocabularies() {
        return vocabularyService.getAllVocabularies();
    }

    @GetMapping("/{id}")
    public Vocabulary getVocabularyById(@PathVariable Integer id) {
        return vocabularyService.getVocabularyById(id);
    }

    @PostMapping
    public Vocabulary saveVocabulary(@RequestBody Vocabulary vocabulary) {
        return vocabularyService.saveVocabulary(vocabulary);
    }

    @DeleteMapping("/{id}")
    public void deleteVocabulary(@PathVariable Integer id) {
        vocabularyService.deleteVocabulary(id);
    }
    @GetMapping("/match-vocabulary")
    public String matchVocabulary(Model model) {
        //List<Vocabulary> vocabularies = vocabularyService.getAllVocabularies();
    	List<Map<String, Object>> vocabularies = vocabularyService.getVocabulariesForMatching();
        model.addAttribute("vocabularies", vocabularies);
        return "matchVocabulary";  // Return the Thymeleaf view name
    }
    
    @GetMapping("/learn")
    public String showWord(Model model) {
        Vocabulary word = vocabularyService.getRandomWord();  // Lấy từ ngẫu nhiên
  
        model.addAttribute("word", word);  // Truyền từ vào model
        return "listening_view";  // Trả về view listening_view.html
    }

    @PostMapping("/learn")
    public String checkAnswer(@RequestParam("written_word") String writtenWord, Model model) {
        Vocabulary word = vocabularyService.getRandomWord();  // Lấy lại từ ngẫu nhiên
        model.addAttribute("word", word);  // Truyền lại từ vào model
        model.addAttribute("writtenWord", writtenWord);  // Truyền từ người dùng nhập vào

        return "listening_view";  // Trả về cùng một view để hiển thị kết quả
    }

     @GetMapping("/vocabulary")
    public String getVocabulary(Model model) {
        var vocabularyList = vocabularyService.getAllVocabulary();
        vocabularyService.updateVocabularyMatches(vocabularyList);
        model.addAttribute("vocabulary", vocabularyList);
        return "vocabulary";
    }

    @GetMapping("/random-example")
    public String getRandomExample(Model model) {
        String[] randomWords = vocabularyService.getRandomExamples();
        String[] randomExampleData = vocabularyService.getRandomExampleWithMatch();

        model.addAttribute("word", randomExampleData[0]);
        model.addAttribute("match", randomExampleData[1]);
        model.addAttribute("example", randomExampleData[2]);
        model.addAttribute("randomWords", randomWords);

        return "randomExample"; // Trả về view "randomExample.html"
    }

}
