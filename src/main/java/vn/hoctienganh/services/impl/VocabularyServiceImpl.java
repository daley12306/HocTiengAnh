package vn.hoctienganh.services.impl;

import java.time.Duration;
import java.time.LocalDateTime;
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
	public int calculateMemLevel(Vocabulary vocabulary) {
		double baseDecayRate = Math.max(0.2 , 0.3 - (vocabulary.getLearnCount() * 0.01));
		double reviewFactor = Math.min( 0.8 + (vocabulary.getLearnCount() * 0.02), 0.9);
		int maxMemLevel = 10; 

		long timeSinceReview = Duration.between(vocabulary.getLastLearn(), LocalDateTime.now()).toDays();
		double adjustedDecayRate = baseDecayRate / Math.pow(vocabulary.getLearnCount() + 1, reviewFactor);
		double retentionRate = Math.exp(-adjustedDecayRate * timeSinceReview);

		int currentMemLevel = vocabulary.getMemLevel();
		if (retentionRate > 0.8) {
			currentMemLevel = Math.min(currentMemLevel + 1, maxMemLevel);
		} else if (retentionRate < 0.5) {
			currentMemLevel = Math.max(currentMemLevel - 1, 0);
		}

		return currentMemLevel;
	}

	public void updateMemLevel(Vocabulary v) {
        int updatedMemLevel = calculateMemLevel(v);
        v.setMemLevel(updatedMemLevel);
        vocabularyRepository.save(v);
    }
	
	

}
