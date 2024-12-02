package vn.hoctienganh.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.hoctienganh.entity.Vocabulary;
import vn.hoctienganh.repository.VocabularyRepository;
import vn.hoctienganh.services.VocabularyService;

@Service
public class VocabularyServiceImpl implements VocabularyService {

	@Autowired
	VocabularyRepository vocabularyRepository;

	@Override
	public void deleteById(int id) {
		vocabularyRepository.deleteById(id);
	}

	@Override
	public long count() {
		return vocabularyRepository.count();
	}

	@Override
	public Optional<Vocabulary> findById(int id) {
		return vocabularyRepository.findById(id);
	}

	@Override
	public List<Vocabulary> findAll() {
		return vocabularyRepository.findAll();
	}

	@Override
	public Page<Vocabulary> findAll(Pageable pageable) {
		return vocabularyRepository.findAll(pageable);
	}

	@Override
	public List<Vocabulary> findAll(Sort sort) {
		return vocabularyRepository.findAll(sort);
	}

	@Override
	public <S extends Vocabulary> S save(S entity) {
		return vocabularyRepository.save(entity);
	}

	@Override
	public List<Vocabulary> findWordsByCurriculumName(String name) {
		return vocabularyRepository.findWordsByCurriculumName(name);
	}

	@Override
	public List<Vocabulary> findByCurriculumId(Integer curriculumId) {
		return vocabularyRepository.findByCurriculumId(curriculumId);
	}

	

}
