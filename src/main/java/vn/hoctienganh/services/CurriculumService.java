package vn.hoctienganh.services;

import java.util.List;
import vn.hoctienganh.entity.Curriculum;

public interface CurriculumService {
	Curriculum findByName(String name);
	Integer getCurriculumIdByName(String name);
	List<Curriculum> getAllCurriculums();
	boolean existsById(Integer id);
	Curriculum findById(Integer id);
	List<Curriculum> findAll();
}
