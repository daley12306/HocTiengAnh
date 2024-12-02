package vn.hoctienganh.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.hoctienganh.entity.Vocabulary;

@Service
public interface VocabularyService {
	void deleteById(int id);

	long count();

	Optional<Vocabulary> findById(int id);
	
	List<Vocabulary> findAll();

	Page<Vocabulary> findAll(Pageable pageable);

	List<Vocabulary> findAll(Sort sort);

	<S extends Vocabulary> S save(S entity);
	
	List<Vocabulary> findWordsByCurriculumName(String name);

}
