package vn.hoctienganh.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.hoctienganh.entity.Vocabulary;
import vn.hoctienganh.entity.Curriculum;
import vn.hoctienganh.repository.VocabularyRepository;
import vn.hoctienganh.services.VocabularyService;
import vn.hoctienganh.services.CurriculumService;

import java.util.List;

@Service
public class VocabularyServiceImpl implements VocabularyService {

	private static final String BASE_AUDIO_URL = "https://trumtuvung.com/audio/9/";
	private static final String BASE_IMAGE_URL = "https://trumtuvung.com/images/9/";

	@Autowired
	private VocabularyRepository vocabularyRepository;

	@Autowired
	private CurriculumService curriculumService;	

	private String generateAudioUrl(String word) {
		String formattedWord = word.trim().toLowerCase().replaceAll("\\s+", "");
		return BASE_AUDIO_URL + formattedWord + "_uk.mp3";
	}

	private String generateImageUrl(String word) {
		String formattedWord = word.trim().toLowerCase().replaceAll("\\s+", "");
		return BASE_IMAGE_URL + formattedWord + ".jpg";
	}

	@Override
	public Vocabulary addVocabularyToCurriculum(Integer curriculumId, Vocabulary vocabulary) {
		// Kiểm tra curriculum có tồn tại
		if (!curriculumService.existsById(curriculumId)) {    
			throw new RuntimeException("Curriculum không tồn tại với id: " + curriculumId);
		}
		
		String word = vocabulary.getWord().trim();
        if (isWordExists(word)) {
            throw new IllegalArgumentException("Từ vựng '" + word + "' đã tồn tại trong hệ thống");
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
	public void deleteVocabulary(Integer id) {
		if (!vocabularyRepository.existsById(id)) {
			throw new RuntimeException("Từ vựng không tồn tại với id: " + id);
		}
		vocabularyRepository.deleteById(id);
	}

	@Override
	public Vocabulary getVocabularyById(Integer id) {
		return vocabularyRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Từ vựng không tồn tại với id: " + id));
	}

	@Override
	public List<Vocabulary> getAllVocabulariesByCurriculumId(Integer curriculumId) {
		if (!curriculumService.existsById(curriculumId)) {
			throw new RuntimeException("Curriculum không tồn tại với id: " + curriculumId);
		}
		return vocabularyRepository.findByCurriculumId(curriculumId);
	}

	@Override
	public Vocabulary saveVocabulary(Vocabulary vocabulary) {
		// Không xử lý URL ở đây, để Controller xử lý
		validateVocabularyData(vocabulary);
		return vocabularyRepository.save(vocabulary);
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
	}

	@Override
	public Vocabulary findById(Integer id) {
		return vocabularyRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Từ vựng không tồn tại với id: " + id));
	}

	@Override
	public boolean isWordExists(String word) {
		if (word == null) return false;
        
        // Chuẩn hóa từ và tìm kiếm không phân biệt chữ hoa/thường
        String normalizedWord = word.trim().toLowerCase();
        return vocabularyRepository.existsByWordIgnoreCase(normalizedWord);
	}


}