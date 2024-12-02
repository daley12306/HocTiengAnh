package vn.hoctienganh.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.hoctienganh.entity.Vocabulary;
import vn.hoctienganh.repository.VocabularyRepository;

@Service 
public class VocabularyServiceImpl implements VocabularyService{
	@Autowired
	VocabularyRepository vocabularyRepository;

	@Override
	public List<Vocabulary> findByCurriculumId(Integer curriculumId) {
		return vocabularyRepository.findByCurriculumId(curriculumId);
	}

	@Override
	public void deleteById(int id) {
		vocabularyRepository.deleteById(id);
	}

	@Override
	public long count() {
		return vocabularyRepository.count();
	}

	@Override
	public Optional<Vocabulary> findById(int id) {
		return vocabularyRepository.findById(id);
	}

	@Override
	public List<Vocabulary> findAll() {
		return vocabularyRepository.findAll();
	}

	@Override
	public Page<Vocabulary> findAll(Pageable pageable) {
		return vocabularyRepository.findAll(pageable);
	}

	@Override
	public List<Vocabulary> findAll(Sort sort) {
		return vocabularyRepository.findAll(sort);
	}

	@Override
	public <S extends Vocabulary> S save(S entity) {
		return vocabularyRepository.save(entity);
	}

	@Override
	public List<Vocabulary> findWordsByCurriculumName(String name) {
		return vocabularyRepository.findWordsByCurriculumName(name);
	}

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

	@Override
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

	@Override
	public String[] getRandomExamples() {
		List<Vocabulary> vocabularyList = getAllVocabularies();
        if (vocabularyList.size() < 2) {
            return new String[] {"Not enough words", "Not enough words"};
        }

        Collections.shuffle(vocabularyList);
        return new String[] {vocabularyList.get(0).getWord(), vocabularyList.get(1).getWord()};
	}

	@Override
	public String[] getRandomExampleWithMatch() {
		List<Vocabulary> vocabularyList = getAllVocabularies();
        updateVocabularyMatches(vocabularyList);

        if (vocabularyList.isEmpty()) {
            return new String[] {"No vocabulary available", "", ""};
        }

        Random random = new Random();
        Vocabulary randomVocabulary = vocabularyList.get(random.nextInt(vocabularyList.size()));

        return new String[] {randomVocabulary.getWord(), randomVocabulary.getMatch(), randomVocabulary.getExample(),};
	}
}
