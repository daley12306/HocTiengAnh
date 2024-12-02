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
  
  public void updateVocabularyMatches(List<Vocabulary> vocabularyList) {
        for (Vocabulary vocabulary : vocabularyList) {
            String word = vocabulary.getWord();
            String example = vocabulary.getExample();

            if (example.contains(word)) {
                String updatedExample = example.replace(word, "____");
                vocabulary.setMatch(updatedExample);
            } else {
                vocabulary.setMatch(example);
            }
        }
    }

    // Lấy 2 từ vựng ngẫu nhiên
    public String[] getRandomExamples() {
        List<Vocabulary> vocabularyList = getAllVocabulary();
        if (vocabularyList.size() < 2) {
            return new String[] {"Not enough words", "Not enough words"};
        }

        Collections.shuffle(vocabularyList);
        return new String[] {vocabularyList.get(0).getWord(), vocabularyList.get(1).getWord()};
    }

    // Lấy câu ví dụ ngẫu nhiên và từ vựng đã được match
    public String[] getRandomExampleWithMatch() {
        List<Vocabulary> vocabularyList = getAllVocabulary();
        updateVocabularyMatches(vocabularyList);

        if (vocabularyList.isEmpty()) {
            return new String[] {"No vocabulary available", "", ""};
        }

        Random random = new Random();
        Vocabulary randomVocabulary = vocabularyList.get(random.nextInt(vocabularyList.size()));

        return new String[] {randomVocabulary.getWord(), randomVocabulary.getMatch(), randomVocabulary.getExample(),};
    }
}
