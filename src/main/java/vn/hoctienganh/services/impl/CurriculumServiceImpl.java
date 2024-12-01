package vn.hoctienganh.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.hoctienganh.entity.Curriculum;
import vn.hoctienganh.repository.CurriculumRepository;
import vn.hoctienganh.services.CurriculumService;

@Service
public class CurriculumServiceImpl implements CurriculumService {

    @Autowired
    private CurriculumRepository curriculumRepository;

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

    @Override
    public List<Curriculum> findAll() {
    	try {
            System.out.println("=== Đang truy vấn database ===");
            List<Curriculum> result = curriculumRepository.findAll();
            System.out.println("Kết quả từ database: " + result);
            return result;
        } catch (Exception e) {
            System.err.println("=== LỖI KHI TRUY VẤN DATABASE ===");
            e.printStackTrace();
            throw e;
        }
    }
}
