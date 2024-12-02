package vn.hoctienganh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.hoctienganh.entity.Vocabulary;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Integer> {
	@Query(value = "SELECT v.word FROM HocTiengAnh.dbo.vocabulary v "
			+ "JOIN HocTiengAnh.dbo.curriculum c ON v.curriculum_id = c.id "
			+ "WHERE c.name = :curriculumName", nativeQuery = true)
	List<Vocabulary> findWordsByCurriculumName(@Param("curriculumName") String curriculumName);

}
