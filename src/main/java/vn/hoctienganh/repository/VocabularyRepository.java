package vn.hoctienganh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.hoctienganh.entity.Vocabulary;


public interface VocabularyRepository extends JpaRepository<Vocabulary, Long>{
	List<Vocabulary> findByCurriculumId(Integer curriculumId);
}
