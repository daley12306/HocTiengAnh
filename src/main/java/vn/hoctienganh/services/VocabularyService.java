package vn.hoctienganh.services;

import java.util.List;

import vn.hoctienganh.entity.Vocabulary;

public interface VocabularyService {
	List<Vocabulary> findByCurriculumId(Integer curriculumId);
}
