package vn.hoctienganh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.hoctienganh.entity.Vocabulary;


public interface VocabularyRepository extends JpaRepository<Vocabulary, Long>{
	List<Vocabulary> findByCurriculumId(Integer curriculumId);
	@Query(value = "SELECT v.* FROM HocTiengAnh.dbo.vocabulary v "
			+ "JOIN HocTiengAnh.dbo.curriculum c ON v.curriculum_id = c.id "
			+ "WHERE c.name = :curriculumName", nativeQuery = true)
	List<Vocabulary> findWordsByCurriculumName(@Param("curriculumName") String curriculumName);
}

