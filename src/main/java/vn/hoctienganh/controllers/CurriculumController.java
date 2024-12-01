package vn.hoctienganh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import vn.hoctienganh.entity.Curriculum;
import vn.hoctienganh.entity.Vocabulary;
import vn.hoctienganh.services.CurriculumService;
import vn.hoctienganh.services.VocabularyService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/curriculums")
@CrossOrigin(origins = "*")
public class CurriculumController {

    @Autowired
    private CurriculumService curriculumService;
    
    @Autowired
    private VocabularyService vocabularyService;

    @GetMapping
    public ResponseEntity<?> getAllCurriculums() {
        try {
            System.out.println("=== Bắt đầu lấy danh sách curriculum ===");
            List<Curriculum> curriculums = curriculumService.findAll();
            System.out.println("Danh sách curriculum: " + curriculums);
            return ResponseEntity.ok(curriculums);
        } catch (Exception e) {
            System.err.println("=== LỖI KHI LẤY DANH SÁCH CURRICULUM ===");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi: " + e.getMessage());
        }
    }

    @PostMapping("/{curriculumId}/vocabularies")
    public ResponseEntity<?> addVocabulary(@PathVariable Integer curriculumId,
                                     @RequestParam("word") String word,
                                     @RequestParam("pronunciation") String pronunciation,
                                     @RequestParam("definition") String definition,
                                     @RequestParam("example") String example,
                                     @RequestParam(value = "audio", required = false) MultipartFile audio,
                                     @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Curriculum curriculum = curriculumService.findById(curriculumId);
            if (curriculum == null) {
                return ResponseEntity.notFound().build();
            }

            Vocabulary vocabulary = new Vocabulary();
            vocabulary.setWord(word);
            vocabulary.setPronunciation(pronunciation);
            vocabulary.setDefinition(definition);
            vocabulary.setExample(example);
            vocabulary.setCurriculum(curriculum);
            vocabulary.setMemLevel(0);
            vocabulary.setLearnCount(0);
            vocabulary.setLastLearn(LocalDateTime.now());

            // Xử lý file audio
            if (audio != null && !audio.isEmpty()) {
                String audioFileName = saveFile(audio, "audio");
                vocabulary.setAudio(audioFileName);
            }

            // Xử lý file hình ảnh
            if (image != null && !image.isEmpty()) {
                String imageFileName = saveFile(image, "images");
                vocabulary.setImage(imageFileName);
            }

            Vocabulary saved = vocabularyService.saveVocabulary(vocabulary);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi: " + e.getMessage());
        }
    }

    private String saveFile(MultipartFile file, String folder) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get("uploads", folder, fileName);
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    @GetMapping("/{curriculumId}/vocabularies")
    public ResponseEntity<?> getVocabulariesByCurriculum(@PathVariable Integer curriculumId) {
        try {
            List<Vocabulary> vocabularies = vocabularyService.getAllVocabulariesByCurriculumId(curriculumId);
            return ResponseEntity.ok(vocabularies);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi lấy danh sách từ vựng: " + e.getMessage());
        }
    }

} 