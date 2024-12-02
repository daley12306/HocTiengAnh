package vn.hoctienganh.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vn.hoctienganh.entity.Vocabulary;
import vn.hoctienganh.services.CurriculumService;
import vn.hoctienganh.services.VocabularyService;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/vocabularies")
@CrossOrigin(origins = "*")
public class VocabularyController {
	
	private static final String BASE_AUDIO_URL = "https://trumtuvung.com/audio/9/";
	private static final String BASE_IMAGE_URL = "https://trumtuvung.com/images/9/";

	
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
    private VocabularyService vocabularyService;

    @GetMapping
    public List<Vocabulary> getAllVocabularies() {
        return vocabularyService.getAllVocabularies();
    }

    @GetMapping("/get/{id}")
    public Vocabulary getVocabularyById(@PathVariable Integer id) {
        return vocabularyService.getVocabularyById(id);
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

    @PostMapping
    public Vocabulary saveVocabulary(@RequestBody Vocabulary vocabulary) {
        return vocabularyService.saveVocabulary(vocabulary);
    }
    
//    @DeleteMapping("/{id}")
//    public void deleteVocabulary(@PathVariable Integer id) {
//        vocabularyService.deleteVocabulary(id);
//    }
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
    @GetMapping("/match-vocabulary")
    public String matchVocabulary(Model model) {
        //List<Vocabulary> vocabularies = vocabularyService.getAllVocabularies();
    	List<Map<String, Object>> vocabularies = vocabularyService.getVocabulariesForMatching();
        model.addAttribute("vocabularies", vocabularies);
        return "matchVocabulary";  // Return the Thymeleaf view name
    }
    
    @GetMapping("/learn")
    public String showWord(Model model) {
        Vocabulary word = vocabularyService.getRandomWord();  // Lấy từ ngẫu nhiên
  
        model.addAttribute("word", word);  // Truyền từ vào model
        return "listening_view";  // Trả về view listening_view.html
    }

    @PostMapping("/learn")
    public String checkAnswer(@RequestParam("written_word") String writtenWord, Model model) {
        Vocabulary word = vocabularyService.getRandomWord();  // Lấy lại từ ngẫu nhiên
        model.addAttribute("word", word);  // Truyền lại từ vào model
        model.addAttribute("writtenWord", writtenWord);  // Truyền từ người dùng nhập vào

        return "listening_view";  // Trả về cùng một view để hiển thị kết quả
    }

     @GetMapping("/vocabulary")
    public String getVocabulary(Model model) {
        var vocabularyList = vocabularyService.getAllVocabularies();
        vocabularyService.updateVocabularyMatches(vocabularyList);
        model.addAttribute("vocabulary", vocabularyList);
        return "vocabulary";
    }

    @GetMapping("/random-example")
    public String getRandomExample(Model model) {
        String[] randomWords = vocabularyService.getRandomExamples();
        String[] randomExampleData = vocabularyService.getRandomExampleWithMatch();

        model.addAttribute("word", randomExampleData[0]);
        model.addAttribute("match", randomExampleData[1]);
        model.addAttribute("example", randomExampleData[2]);
        model.addAttribute("randomWords", randomWords);

        return "randomExample"; // Trả về view "randomExample.html"
    }

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
			if (vocabularyService.isWordExists(word)) {
				return ResponseEntity.badRequest()
						.body("Từ vựng '" + word + "' đã tồn tại trong hệ thống");
			}
			
			// Chỉ tạo đối tượng vocabulary mới sau khi đã kiểm tra
			Vocabulary vocabulary = new Vocabulary();
			vocabulary.setWord(word);
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
					.body("Khi Thêm từ vựng: " + e.getMessage());
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
                .body("Khi cập nhật từ vựng: " + e.getMessage());
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
			List<Vocabulary> vocabularies = vocabularyService.findByCurriculumId(curriculumId);
			return ResponseEntity.ok(vocabularies);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}



	
}
