package vn.hoctienganh.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.hoctienganh.services.VocabularyService;

@Controller
public class VocabularyController {

    private final VocabularyService vocabularyService;

    public VocabularyController(VocabularyService vocabularyService) {
        this.vocabularyService = vocabularyService;
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
