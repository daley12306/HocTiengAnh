package vn.hoctienganh.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hoctienganh.entity.Vocabulary;
import vn.hoctienganh.repository.VocabularyRepository;

@Service 
public class VocabularyServiceImpl implements VocabularyService{
	@Autowired
	VocabularyRepository vocabularyRepository;

	@Override
	public List<Vocabulary> findByCurriculumId(Integer curriculumId) {
		return vocabularyRepository.findByCurriculumId(curriculumId);
	}

}
