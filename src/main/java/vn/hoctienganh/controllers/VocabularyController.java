package vn.hoctienganh.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoctienganh.entity.Vocabulary;
import vn.hoctienganh.services.VocabularyService;
import vn.hoctienganh.services.CurriculumService;
import vn.hoctienganh.entity.Curriculum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;

@RestController
@RequestMapping("/api/vocabularies")
@CrossOrigin(origins = "*")
public class VocabularyController {

	private static final String BASE_AUDIO_URL = "https://trumtuvung.com/audio/9/";
	private static final String BASE_IMAGE_URL = "https://trumtuvung.com/images/9/";
	@Autowired
	private VocabularyService vocabularyService;
	
	@Autowired
	private CurriculumService curriculumService;
	
	private String cleanFileName(String fileName) {
		if (fileName == null) return "";
		
		// Nếu là URL, lấy phần tên file cuối cùng
		if (fileName.startsWith("http")) {
			fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
		}
		
		// Tách tên file và phần mở rộng
		String extension = "";
		int lastDotIndex = fileName.lastIndexOf('.');
		if (lastDotIndex > 0) {
			extension = fileName.substring(lastDotIndex);
			fileName = fileName.substring(0, lastDotIndex);
		}
		
		// Tách các phần bằng dấu gạch dưới
		String[] parts = fileName.split("_");
		
		// Xử lý từng phần
		StringBuilder cleanName = new StringBuilder();
		for (int i = 0; i < parts.length; i++) {
			// Chỉ loại bỏ timestamp ở đầu tên file
			if (i == 0 && parts[i].matches("^\\d+$")) {
				continue;
			}
			
			if (cleanName.length() > 0) {
				cleanName.append("_");
			}
			cleanName.append(parts[i]);
		}
		
		return cleanName.toString() + extension;
	}


	@PostMapping("/curriculums/{curriculumId}/vocabularies")
	public ResponseEntity<?> addVocabulary(
			@PathVariable Integer curriculumId,
			@RequestParam("word") String word,
			@RequestParam("pronunciation") String pronunciation,
			@RequestParam("definition") String definition,
			@RequestParam("example") String example,
			@RequestParam(value = "audio", required = false) MultipartFile audio,
			@RequestParam(value = "image", required = false) MultipartFile image) {
		
		try {
			// Kiểm tra từ vựng tồn tại TRƯỚC KHI tạo đối tượng vocabulary mới
			String normalizedWord = word.trim();
			if (vocabularyService.isWordExists(normalizedWord)) {
				return ResponseEntity.badRequest()
						.body("Từ vựng '" + normalizedWord + "' đã tồn tại trong hệ thống");
			}
			
			// Chỉ tạo đối tượng vocabulary mới sau khi đã kiểm tra
			Vocabulary vocabulary = new Vocabulary();
			vocabulary.setWord(normalizedWord);
			vocabulary.setPronunciation(pronunciation);
			vocabulary.setDefinition(definition);
			vocabulary.setExample(example);

			// Xử lý audio file
			if (audio != null && !audio.isEmpty()) {
				String cleanedAudioName = cleanFileName(audio.getOriginalFilename());
				vocabulary.setAudio(BASE_AUDIO_URL + cleanedAudioName);
			}

			// Xử lý image file
			if (image != null && !image.isEmpty()) {
				String cleanedImageName = cleanFileName(image.getOriginalFilename());
				vocabulary.setImage(BASE_IMAGE_URL + cleanedImageName);
			}

			Vocabulary saved = vocabularyService.addVocabularyToCurriculum(curriculumId, vocabulary);
			return ResponseEntity.ok(saved);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError()
					.body("Lỗi: khi thêm từ " + e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateVocabulary(
		@PathVariable Integer id,
        @RequestParam("word") String word,
        @RequestParam("pronunciation") String pronunciation, 
        @RequestParam("definition") String definition,
        @RequestParam("example") String example,
        @RequestParam(value = "audio", required = false) MultipartFile audio,
        @RequestParam(value = "image", required = false) MultipartFile image) {
    
    try {
        Vocabulary existingVocab = vocabularyService.findById(id);
        if (existingVocab == null) {
            return ResponseEntity.notFound().build();
        }

        // Kiểm tra từ vựng tồn tại khi update (chỉ kiểm tra nếu từ thay đổi)
        if (!word.equals(existingVocab.getWord()) && vocabularyService.isWordExists(word)) {
            return ResponseEntity.badRequest()
                    .body("Từ vựng '" + word + "' đã tồn tại trong hệ thống");
        }

        existingVocab.setWord(word);
        existingVocab.setPronunciation(pronunciation);
        existingVocab.setDefinition(definition);
        existingVocab.setExample(example);

        
		// Xử lý audio file
		if (audio != null && !audio.isEmpty()) {
			String cleanedAudioName = cleanFileName(audio.getOriginalFilename());
			existingVocab.setAudio(BASE_AUDIO_URL + cleanedAudioName);
		}

		// Xử lý image file
		if (image != null && !image.isEmpty()) {
			String cleanedImageName = cleanFileName(image.getOriginalFilename());
			existingVocab.setImage(BASE_IMAGE_URL + cleanedImageName);
		}

        Vocabulary updated = vocabularyService.updateVocabulary(id, existingVocab);
        return ResponseEntity.ok(updated);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError()
                .body("Lỗi khi cập nhật từ vựng: " + e.getMessage());
    }
	}

	@PostMapping("/{id}/audio")
	public ResponseEntity<Vocabulary> updateAudio(
			@PathVariable Integer id, 
			@RequestParam("audio") String audioUrl) {
		Vocabulary vocabulary = vocabularyService.findById(id);
		if (vocabulary == null) {
			return ResponseEntity.notFound().build();
		}
		
		vocabulary.setAudio(audioUrl);
		Vocabulary updated = vocabularyService.saveVocabulary(vocabulary);
		return ResponseEntity.ok(updated);
	}

	@PostMapping("/{id}/image")
	public ResponseEntity<Vocabulary> updateImage(
			@PathVariable Integer id, 
			@RequestParam("image") String imageUrl) {
		Vocabulary vocabulary = vocabularyService.findById(id);
		if (vocabulary == null) {
			return ResponseEntity.notFound().build();
		}
		
		vocabulary.setImage(imageUrl);
		Vocabulary updated = vocabularyService.saveVocabulary(vocabulary);
		return ResponseEntity.ok(updated);
	}

	@GetMapping("/curriculums/{curriculumId}")
	public ResponseEntity<List<Vocabulary>> getVocabulariesByCurriculum(@PathVariable Integer curriculumId) {
		try {
			List<Vocabulary> vocabularies = vocabularyService.getAllVocabulariesByCurriculumId(curriculumId);
			return ResponseEntity.ok(vocabularies);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getVocabulary(@PathVariable Integer id) {
		try {
			Vocabulary vocabulary = vocabularyService.findById(id);
			if (vocabulary == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(vocabulary);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Lỗi: " + e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteVocabulary(@PathVariable Integer id) {
		try {
			vocabularyService.deleteVocabulary(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Lỗi: " + e.getMessage());
		}
	}
}