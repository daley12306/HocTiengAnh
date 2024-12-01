package vn.hoctienganh.services;

import java.util.List;
import java.util.Map;

import vn.hoctienganh.entity.Vocabulary;

public interface VocabularyService {
	List<Vocabulary> getAllVocabularies();
    Vocabulary getVocabularyById(Integer id);
    Vocabulary saveVocabulary(Vocabulary vocabulary);
    void deleteVocabulary(Integer id);
    List<Map<String, Object>> getVocabulariesForMatching();
    Vocabulary getVocabularyByWord(String word);
    Vocabulary getRandomWord();
}
