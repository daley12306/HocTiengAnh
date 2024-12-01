package vn.hoctienganh.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VocabularyViewController {
    
    @GetMapping("/vocabularies")
    public String index() {
        System.out.println("Accessing Vocabulary page");
        return "admin/Vocabulary";
    }
} 