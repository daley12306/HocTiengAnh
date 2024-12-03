package vn.hoctienganh.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.hoctienganh.entity.Vocabulary;

@Service
public interface VocabularyService {
	void deleteById(int id);

	long count();

	Optional<Vocabulary> findById(int id);
	
	List<Vocabulary> findAll();

	Page<Vocabulary> findAll(Pageable pageable);

	List<Vocabulary> findAll(Sort sort);

	<S extends Vocabulary> S save(S entity);
	
	List<Vocabulary> findWordsByCurriculumName(String name);

	List<Vocabulary> findByCurriculumId(Integer curriculumId);

	List<Vocabulary> getAllVocabularies();
    Vocabulary getVocabularyById(Integer id);
    Vocabulary saveVocabulary(Vocabulary vocabulary);
    void deleteVocabulary(Integer id);
    List<Map<String, Object>> getVocabulariesForMatching();
    Vocabulary getVocabularyByWord(String word);
    Vocabulary getRandomWord();
 
    void updateVocabularyMatches(List<Vocabulary> vocabularyList);
    String[] getRandomExamples();
    String[] getRandomExampleWithMatch();
    
	// Thêm mới từ vựng.
	Vocabulary addVocabularyToCurriculum(Integer curriculumId, Vocabulary vocabulary);
	// Cập nhật từ vựng dựa trên ID.
	Vocabulary updateVocabulary(Integer id, Vocabulary vocabulary);

	void validateVocabularyData(Vocabulary vocabulary);
	
	Vocabulary findById(Integer id);
	
	boolean isWordExists(String word);
}
