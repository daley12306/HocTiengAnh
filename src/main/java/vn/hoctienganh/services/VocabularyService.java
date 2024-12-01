package vn.hoctienganh.services;

import vn.hoctienganh.entity.Vocabulary;
import java.util.List;

public interface VocabularyService {
	// Thêm mới từ vựng.
	Vocabulary addVocabularyToCurriculum(Integer curriculumId, Vocabulary vocabulary);
	// Cập nhật từ vựng dựa trên ID.
	Vocabulary updateVocabulary(Integer id, Vocabulary vocabulary);
	// Xóa từ vựng dựa trên ID.
	void deleteVocabulary(Integer id);
	// Lấy chi tiết từ vựng theo ID.
	Vocabulary getVocabularyById(Integer id);
	// Lấy danh sách tất cả từ vựng theo curriculumId
	List<Vocabulary> getAllVocabulariesByCurriculumId(Integer curriculumId);
	// Lưu Từ Vựng
    Vocabulary saveVocabulary(Vocabulary vocabulary);

	void validateVocabularyData(Vocabulary vocabulary);
	
	Vocabulary findById(Integer id);

}
