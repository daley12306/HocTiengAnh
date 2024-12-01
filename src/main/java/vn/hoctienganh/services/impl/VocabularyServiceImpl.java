package vn.hoctienganh.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hoctienganh.entity.Vocabulary;
import vn.hoctienganh.repository.VocabularyRepository;
import vn.hoctienganh.services.VocabularyService;

@Service
public class VocabularyServiceImpl implements VocabularyService {
	@Autowired
    private VocabularyRepository vocabularyRepository;

    @Override
    public List<Vocabulary> getAllVocabularies() {
        return vocabularyRepository.findAll();
    }

    @Override
    public Vocabulary getVocabularyById(Integer id) {
        return vocabularyRepository.findById(id).orElse(null);
    }

    @Override
    public Vocabulary saveVocabulary(Vocabulary vocabulary) {
        return vocabularyRepository.save(vocabulary);
    }

    @Override
    public void deleteVocabulary(Integer id) {
        vocabularyRepository.deleteById(id);
    }
    public List<Map<String, Object>> getVocabulariesForMatching() {
        return vocabularyRepository.findAllForMatching(); // Trả về dữ liệu cần thiết
    }
    
    public Vocabulary getVocabularyByWord(String word) {
        return vocabularyRepository.findByWord(word);
    }
    
    public Vocabulary getRandomWord() {
        List<Vocabulary> words = vocabularyRepository.findTop1ByOrderByIdAsc(); // Lấy từ ngẫu nhiên
        return words.isEmpty() ? null : words.get(0);
    }
    
}
