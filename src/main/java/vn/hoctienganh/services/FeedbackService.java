package vn.hoctienganh.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.hoctienganh.entity.Feedback;

public interface FeedbackService {

	void deleteById(Long id);

	long count();

	Optional<Feedback> findById(Long id);

	List<Feedback> findAll();

	Page<Feedback> findAll(Pageable pageable);

	List<Feedback> findAll(Sort sort);

	<S extends Feedback> S save(S entity);

}
