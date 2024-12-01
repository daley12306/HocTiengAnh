package vn.hoctienganh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hoctienganh.entity.Vocabulary;
import java.util.List;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Integer> {
	// JpaRepository cung cấp sẵn các phương thức CRUD cơ bản (save, findById, findAll, deleteById).
	
	// Phương thức tùy chỉnh để tìm từ vựng theo curriculum_id
    List<Vocabulary> findByCurriculumId(Integer curriculumId);
	
	// Thêm phương thức tìm kiếm từ vựng theo từ
    Vocabulary findByWord(String word);
    
    // Thêm phương thức kiểm tra từ vựng đã tồn tại
    boolean existsByWord(String word);
	
    
}
