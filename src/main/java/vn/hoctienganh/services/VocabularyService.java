package vn.hoctienganh.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.hoctienganh.entity.Vocabulary;
import vn.hoctienganh.repository.VocabularyRepository;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class VocabularyService {
    private final VocabularyRepository vocabularyRepository;

    @Autowired
    public VocabularyService(VocabularyRepository vocabularyRepository) {
        this.vocabularyRepository = vocabularyRepository;
    }

    public List<Vocabulary> getAllVocabulary() {
        return vocabularyRepository.findAll();
    }

    // Cập nhật câu ví dụ với từ vựng
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
