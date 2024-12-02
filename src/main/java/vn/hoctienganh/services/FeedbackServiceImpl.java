package vn.hoctienganh.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.hoctienganh.entity.Feedback;
import vn.hoctienganh.repository.FeedbackRepository;

@Service
public class FeedbackServiceImpl implements FeedbackService{
	@Autowired
	FeedbackRepository feedbackRepository;

	public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
		this.feedbackRepository = feedbackRepository;
	}

	@Override
	public <S extends Feedback> S save(S entity) {
		return feedbackRepository.save(entity);
	}

	@Override
	public List<Feedback> findAll(Sort sort) {
		return feedbackRepository.findAll(sort);
	}

	@Override
	public Page<Feedback> findAll(Pageable pageable) {
		return feedbackRepository.findAll(pageable);
	}

	@Override
	public List<Feedback> findAll() {
		return feedbackRepository.findAll();
	}

	@Override
	public Optional<Feedback> findById(Long id) {
		return feedbackRepository.findById(id);
	}

	@Override
	public long count() {
		return feedbackRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		feedbackRepository.deleteById(id);
	}
	
	
}
