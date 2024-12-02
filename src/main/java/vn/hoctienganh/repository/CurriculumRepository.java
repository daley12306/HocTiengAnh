package vn.hoctienganh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.hoctienganh.entity.Curriculum;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer>{
	@Query("SELECT c.name FROM StudyRecord sr JOIN sr.curriculums c WHERE sr.id = :studyRecordId")
    List<String> findCurriculumNamesByStudyRecordId(@Param("studyRecordId") int studyRecordId);
	
	Curriculum findByName(String name);
}
