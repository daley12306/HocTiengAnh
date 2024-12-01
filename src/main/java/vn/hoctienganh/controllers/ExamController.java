package vn.hoctienganh.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.hoctienganh.entity.Question;
import vn.hoctienganh.services.QuestionService;

@Controller
@RequestMapping("/exam")
public class ExamController {
	
	@Autowired
	QuestionService questionService;
	
	private List<Question> questions;
	private int currentQuestionIndex;
	List<Integer> scoreEachCurriculum = new ArrayList<>(List.of(0, 0, 0));
	
	@GetMapping("/start")
	public String start() {
		return "exam/exam_start";
	}
	
	@GetMapping("/take")
	public String take(Model model) {
		questions = questionService.getQuestions();
		currentQuestionIndex = 0;
		model.addAttribute("question", questions.get(currentQuestionIndex));
		model.addAttribute("progress", (currentQuestionIndex + 1) * 10 / 3);
		return "exam/exam_take";
	}
	
	
	@PostMapping("/nextQuestion")
	public String nextQuestion(@RequestParam String selectedAnswer, Model model) {
	    Question currentQuestion = questions.get(currentQuestionIndex);
	    
	    System.out.println("Selected answer: " + selectedAnswer);
	    System.out.println("Correct answer: " + currentQuestion.getCorrectAnswer());
	    
	    if (currentQuestion.getCorrectAnswer().equals(selectedAnswer)) {
			System.out.println("Correct");
			scoreEachCurriculum.set(currentQuestion.getCurriculumId() - 1, scoreEachCurriculum.get(currentQuestion.getCurriculumId() - 1) + 1);
		}

	    currentQuestionIndex++;

	    if (currentQuestionIndex < questions.size()) {
	        Question nextQuestion = questions.get(currentQuestionIndex);
	        model.addAttribute("question", nextQuestion);
	        model.addAttribute("progress", (currentQuestionIndex + 1) * 10 / 3);
	        return "exam/exam_take";  
	    } else {
	    	// Tính toán curriculums phù hợp
			if (scoreEachCurriculum.get(1) == 10 || (scoreEachCurriculum.get(2) > scoreEachCurriculum.get(0)
					&& scoreEachCurriculum.get(2) > scoreEachCurriculum.get(1))) {
				model.addAttribute("curriculum", 3);
				model.addAttribute("curriculumName", "Cao cấp");
			} else if (scoreEachCurriculum.get(0) == 10 || scoreEachCurriculum.get(1) > scoreEachCurriculum.get(0)) {
				model.addAttribute("curriculum", 2);
				model.addAttribute("curriculumName", "Trung cấp");
			} else {
				model.addAttribute("curriculum", 1);
				model.addAttribute("curriculumName", "Sơ cấp");
			}
	    	
	    	model.addAttribute("percentCurriculum1", scoreEachCurriculum.get(0) * 10);
	        model.addAttribute("percentCurriculum2", scoreEachCurriculum.get(1) * 10);
	        model.addAttribute("percentCurriculum3", scoreEachCurriculum.get(2) * 10);
	        model.addAttribute("finalResult", "You have completed the test!");
	        return "exam/exam_result";  
	    }
	}


	@GetMapping("/result")
	public String result() {
		return "exam/exam_result";
	}
	
	@PostMapping("/save")
	public String save() {
		
		return "redirect:/exam/take";
	}
}
