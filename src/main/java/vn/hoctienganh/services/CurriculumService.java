package vn.hoctienganh.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.hoctienganh.entity.Curriculum;

public interface CurriculumService {
	void deleteById(int id);

	long count();

	Optional<Curriculum> findById(int id);
	
	List<Curriculum> findAll();

	Page<Curriculum> findAll(Pageable pageable);

	List<Curriculum> findAll(Sort sort);

	<S extends Curriculum> S save(S entity);
	
	List<String> getCurriculumNamesByStudyRecordId(int studyRecordId);
}
