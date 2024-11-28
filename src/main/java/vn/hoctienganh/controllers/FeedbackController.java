package vn.hoctienganh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import vn.hoctienganh.entity.Feedback;
import vn.hoctienganh.services.FeedbackService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;

    @PostMapping("/save")
    public ResponseEntity<String> saveOrUpdate(@Valid @RequestBody Feedback feedback, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Invalid feedback data", HttpStatus.BAD_REQUEST);
        }
        System.out.println(feedback);
        feedbackService.save(feedback);
        return new ResponseEntity<>("Feedback saved successfully", HttpStatus.OK);
    }
}
