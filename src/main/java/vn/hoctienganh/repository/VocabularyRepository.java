package vn.hoctienganh.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.hoctienganh.entity.Vocabulary;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Integer> {
	Vocabulary findByWord (String word);
	@Query("SELECT v.id AS id, v.word AS word, v.definition AS definition FROM Vocabulary v")
    List<Map<String, Object>> findAllForMatching();
	List<Vocabulary> findTop1ByOrderByIdAsc();
	
}
