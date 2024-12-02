package vn.hoctienganh.repository;

import java.util.List;
import java.util.Map;

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
	Vocabulary findByWord (String word);
	@Query("SELECT v.id AS id, v.word AS word, v.definition AS definition FROM Vocabulary v")
    List<Map<String, Object>> findAllForMatching();
	List<Vocabulary> findTop1ByOrderByIdAsc();
}

