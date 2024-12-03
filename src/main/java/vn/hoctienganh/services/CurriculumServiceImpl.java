package vn.hoctienganh.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.hoctienganh.entity.Curriculum;
import vn.hoctienganh.repository.CurriculumRepository;

@Service
public class CurriculumServiceImpl implements CurriculumService {
	@Autowired
	CurriculumRepository curriculumRepository;


	@Override
	public long count() {
		return curriculumRepository.count();
	}

	@Override
	public Optional<Curriculum> findById(int id) {
		return curriculumRepository.findById(id);
	}

	@Override
	public List<Curriculum> findAll() {
		return curriculumRepository.findAll();
	}

	@Override
	public Page<Curriculum> findAll(Pageable pageable) {
		return curriculumRepository.findAll(pageable);
	}

	@Override
	public List<Curriculum> findAll(Sort sort) {
		return curriculumRepository.findAll(sort);
	}

	@Override
	public <S extends Curriculum> S save(S entity) {
		return curriculumRepository.save(entity);
	}

	@Override
	public void deleteById(int id) {
		curriculumRepository.deleteById(id);	
	}

	@Override
	public List<String> getCurriculumNamesByStudyRecordId(int studyRecordId) {
        return curriculumRepository.findCurriculumNamesByStudyRecordId(studyRecordId);
    }

	@Override
	public Curriculum findByName(String name) {
		return curriculumRepository.findByName(name);
	}

	@Override
	public Integer getCurriculumIdByName(String name) {
		Curriculum curriculum = curriculumRepository.findByName(name);
        return curriculum != null ? curriculum.getId() : null;
	}

	@Override
	public List<Curriculum> getAllCurriculums() {
		return curriculumRepository.findAll();
	}

	@Override
	public boolean existsById(Integer id) {
		return curriculumRepository.existsById(id);
	}

	@Override
	public Curriculum findById(Integer id) {
		Optional<Curriculum> curriculum = curriculumRepository.findById(id);
        return curriculum.orElse(null);
	}

}
