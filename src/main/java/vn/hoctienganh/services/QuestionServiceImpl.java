package vn.hoctienganh.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hoctienganh.entity.Question;
import vn.hoctienganh.entity.Vocabulary;

@Service
public class QuestionServiceImpl implements QuestionService {
	@Autowired
	VocabularyService vocabularyService;
	
	@Override
	public List<Question> getQuestions() {
		List<Vocabulary> list = vocabularyService.findByCurriculumId(1);
		Collections.shuffle(list);
		List<Vocabulary> beginnerWords = list.subList(0, 40);
		list = vocabularyService.findByCurriculumId(2);
		Collections.shuffle(list);
		List<Vocabulary> intermediateWords = list.subList(0, 40);
		list = vocabularyService.findByCurriculumId(3);
		Collections.shuffle(list);
		List<Vocabulary> advancedWords = list.subList(0, 40);
		
		List<Question> questions = new ArrayList<>();
		for (int i = 0; i < 40; i += 4) {
			Vocabulary word = beginnerWords.get(i);
			Question question = new Question();
			question.setContent("What is the meaning of '" + word.getWord() + "'?");
			List<String> choices = new ArrayList<>();
			for (int j = 0; j < 4; j++) {
				choices.add(beginnerWords.get(i + j).getDefinition());
			}
			Collections.shuffle(choices);
			question.setChoices(choices);
			question.setCorrectAnswer(word.getDefinition());
			question.setCurriculumId(1);
			questions.add(question);
		}
		
		for (int i = 0; i < 40; i += 4) {
			Vocabulary word = intermediateWords.get(i);
			Question question = new Question();
			question.setContent("What is the meaning of '" + word.getWord() + "'?");
			List<String> choices = new ArrayList<>();
			for (int j = 0; j < 4; j++) {
				choices.add(intermediateWords.get(i + j).getDefinition());
			}
			Collections.shuffle(choices);
			question.setChoices(choices);
			question.setCorrectAnswer(word.getDefinition());
			question.setCurriculumId(2);
			questions.add(question);
		}
		
		for (int i = 0; i < 40; i += 4) {
			Vocabulary word = advancedWords.get(i);
			Question question = new Question();
			question.setContent("What is the meaning of '" + word.getWord() + "'?");
			List<String> choices = new ArrayList<>();
			for (int j = 0; j < 4; j++) {
				choices.add(advancedWords.get(i + j).getDefinition());
			}
			Collections.shuffle(choices);
			question.setChoices(choices);
			question.setCorrectAnswer(word.getDefinition());
			question.setCurriculumId(3);
			questions.add(question);
		}
		Collections.shuffle(questions);
		return questions;
	}

}
