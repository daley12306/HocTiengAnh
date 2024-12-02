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
import vn.hoctienganh.entity.Curriculum;
import vn.hoctienganh.entity.Vocabulary;
import vn.hoctienganh.repository.VocabularyRepository;

@Service
public class VocabularyServiceImpl implements VocabularyService {
	
	private static final String BASE_AUDIO_URL = "https://trumtuvung.com/audio/9/";
	private static final String BASE_IMAGE_URL = "https://trumtuvung.com/images/9/";
	private String generateAudioUrl(String word) {
		String formattedWord = word.trim().toLowerCase().replaceAll("\\s+", "");
		return BASE_AUDIO_URL + formattedWord + "_uk.mp3";
	}

	private String generateImageUrl(String word) {
		String formattedWord = word.trim().toLowerCase().replaceAll("\\s+", "");
		return BASE_IMAGE_URL + formattedWord + ".jpg";
	}

	
	@Autowired
	VocabularyRepository vocabularyRepository;
	
	@Autowired
	private CurriculumService curriculumService;

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
		validateVocabularyData(vocabulary);
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
			return new String[] { "Not enough words", "Not enough words" };
		}

		Collections.shuffle(vocabularyList);
		return new String[] { vocabularyList.get(0).getWord(), vocabularyList.get(1).getWord() };
	}

	@Override
	public String[] getRandomExampleWithMatch() {
		List<Vocabulary> vocabularyList = getAllVocabularies();
		updateVocabularyMatches(vocabularyList);

		if (vocabularyList.isEmpty()) {
			return new String[] { "No vocabulary available", "", "" };
		}

		Random random = new Random();
		Vocabulary randomVocabulary = vocabularyList.get(random.nextInt(vocabularyList.size()));

		return new String[] { randomVocabulary.getWord(), randomVocabulary.getMatch(), randomVocabulary.getExample(), };
	}

	@Override
	public Vocabulary addVocabularyToCurriculum(Integer curriculumId, Vocabulary vocabulary) {
		// Kiểm tra curriculum có tồn tại
				if (!curriculumService.existsById(curriculumId)) {    
					throw new RuntimeException("Curriculum không tồn tại với id: " + curriculumId);
				}
				
				// Thêm kiểm tra từ vựng tồn tại trước khi thêm mới
				if (isWordExists(vocabulary.getWord())) {
					throw new RuntimeException("Từ vựng '" + vocabulary.getWord() + "' đã tồn tại trong hệ thống");
				}
				
				// Validate dữ liệu từ vựng
				validateVocabularyData(vocabulary);
				
				String audioUrl = generateAudioUrl(vocabulary.getWord());
				String imageUrl = generateImageUrl(vocabulary.getWord());
				System.out.println("Generated Audio URL: " + audioUrl);
				System.out.println("Generated Image URL: " + imageUrl);
				vocabulary.setAudio(audioUrl);
				vocabulary.setImage(imageUrl);
				Curriculum curriculum = curriculumService.findById(curriculumId);
				vocabulary.setCurriculum(curriculum);
				return vocabularyRepository.save(vocabulary);
	}

	@Override
	public Vocabulary updateVocabulary(Integer id, Vocabulary vocabulary) {
		// Validate input
				if (id == null || vocabulary == null) {
					throw new RuntimeException("Dữ liệu đầu vào không hợp lệ");
				}
				
				Vocabulary existingVocabulary = vocabularyRepository.findById(id)
						.orElseThrow(() -> new RuntimeException("Từ vựng không tồn tại với id: " + id));
					
				validateVocabularyData(vocabulary);
				
				existingVocabulary.setWord(vocabulary.getWord());
				existingVocabulary.setDefinition(vocabulary.getDefinition());
				existingVocabulary.setPronunciation(vocabulary.getPronunciation());
				existingVocabulary.setExample(vocabulary.getExample());
				existingVocabulary.setAudio(vocabulary.getAudio());
				existingVocabulary.setImage(vocabulary.getImage());
				
				return vocabularyRepository.save(existingVocabulary);
	}

	@Override
	public void validateVocabularyData(Vocabulary vocabulary) {
		if (vocabulary.getWord() == null || vocabulary.getWord().trim().isEmpty()) {
			throw new RuntimeException("Từ vựng không được để trống");
		}
		if (vocabulary.getDefinition() == null || vocabulary.getDefinition().trim().isEmpty()) {
			throw new RuntimeException("Định nghĩa không được để trống");
		}
		if (vocabulary.getPronunciation() == null || vocabulary.getPronunciation().trim().isEmpty()) {
			throw new RuntimeException("Phát âm không được để trống");
		}
		if (vocabulary.getExample() == null || vocabulary.getExample().trim().isEmpty()) {
			throw new RuntimeException("Ví dụ không được để trống");
		}
		if (vocabulary.getAudio() == null || vocabulary.getAudio().trim().isEmpty()) {
			throw new RuntimeException("Audio không được để trống");
		}
		if (vocabulary.getImage() == null || vocabulary.getImage().trim().isEmpty()) {
			throw new RuntimeException("Hình ảnh không được để trống");
		}
	}

	@Override
	public Vocabulary findById(Integer id) {
		return vocabularyRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Từ vựng không tồn tại với id: " + id));
	}

	@Override
	public boolean isWordExists(String word) {
		if (word == null)
			return false;

		// Chuẩn hóa từ và tìm kiếm không phân biệt chữ hoa/thường
		String normalizedWord = word.trim().toLowerCase();
		return vocabularyRepository.existsByWordIgnoreCase(normalizedWord);
	}

}
